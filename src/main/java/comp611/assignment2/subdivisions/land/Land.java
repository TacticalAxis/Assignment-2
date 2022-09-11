package comp611.assignment2.subdivisions.land;

import java.util.Random;

public class Land {

    private final Area area;
    private final double subValue;
    private final LandValue landValue;

    public Land(int width, int height, int subValue, double minValue, double maxValue) {
        this.landValue = new LandValue(width, height, minValue, maxValue);
        this.area = new Area(width, height, 0, 0, landValue.getValue(width, height));
        this.subValue = subValue;
    }

    public static void main(String[] args) {
        Land land = new Land(6, 3, 50, 20, 1000);
        System.out.println("Total land price: " + land.getValue());

        land.subdivide(Direction.VERTICAL, 3, 2);
        land.printArea();

        System.out.println("Total land price: " + land.getValue());
    }

    public double getValue() {
        return area.getValue() - getValueOfSubdivisions();
    }

    public double getValueOfSubdivisions() {
        return area.getFullSubdivisionLength() * subValue;
    }

    public Area getArea() {
        return area;
    }

    public int[][] getAreaArray() {
        return area.toAreaArray(0, new int[area.getHeight()][area.getWidth()]);
    }

    public void printArea() {
        int[][] areaArray = getAreaArray();
        for (int[] ints : areaArray) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public void subdivide(Direction direction, int x, int y) {
        // first check if x and y are valid
        if (x <= 0 || x >= area.getWidth() || y <= 0 || y >= area.getHeight()) {
            throw new IllegalArgumentException("x and y must be within the area: x=" + x + ", y=" + y);
        }

        Area a = this.area.findAreaWithCoordinates(x, y);
        if (a == null) {
            throw new IllegalArgumentException("x and y must be within the area (a is null): x=" + x + ", y=" + y);
        }

        a.subdivide(direction, x, y, landValue);
    }

    enum Direction {
        VERTICAL, HORIZONTAL
    }

    public static class Area {
        public final int width;
        public final int height;
        private final int xCoordinate;
        private final int yCoordinate;
        private Area area1;
        private Area area2;
        private Direction subdivisionAxis;
        private final double value;

        public Area(int width, int height, int xCoordinate, int yCoordinate, double value) {
            this.width = width;
            this.height = height;
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
            this.area1 = null;
            this.area2 = null;
            this.subdivisionAxis = null;
            this.value = value;
        }

        public double getValue() {
            if (isSubdivided()) {
                return area1.getValue() + area2.getValue();
            } else {
                return value;
            }
        }

        public boolean isSubdivided() {
            return area1 != null && area2 != null;
        }

        public int getSubdivisionLength() {
            if (!isSubdivided()) {
                return 0;
            }
            if (subdivisionAxis == Direction.VERTICAL) {
                return height;
            }
            return width;
        }

        public int getFullSubdivisionLength() {
            int length = getSubdivisionLength();

            if (isSubdivided()) {
                length += area1.getFullSubdivisionLength();
                length += area2.getFullSubdivisionLength();
            }

            return length;
        }

        public int[][] toAreaArray(int subIndex, int[][] areaArray) {
            if (areaArray == null) {
                areaArray = new int[height][width];
            }

            if (isSubdivided()) {
                area1.toAreaArray(subIndex + 1, areaArray);
                area2.toAreaArray(subIndex + 2, areaArray);
            } else {
                for (int i = yCoordinate; i < yCoordinate + height; i++) {
                    for (int j = xCoordinate; j < xCoordinate + width; j++) {
//                        areaArray[i][j] = subIndex;
                        areaArray[i][j] = this.hashCode();
                    }
                }
            }

            return areaArray;
        }

        public void subdivide(Direction direction, int x, int y, LandValue landValue) {
            // if area size is 1, then it cannot be subdivided
            if (width == 1 && height == 1) {
                return;
            }

            this.subdivisionAxis = direction;
            if (isSubdivided()) {
                throw new IllegalStateException("Area is already subdivided");
            }

            if (direction == Direction.VERTICAL) {
                if (x < xCoordinate || x >= xCoordinate + width) {
                    throw new IllegalArgumentException("x must be within the area");
                }

                int width1 = x - xCoordinate;
                int width2 = width - width1;

                // if wxh for either area is the same as parent wxh, then it cannot be subdivided
                if (width1 == width || width2 == width) {
                    return;
                }

                area1 = new Area(width1, height, xCoordinate, yCoordinate, landValue.getValue(width1, height));
                area2 = new Area(width2, height, x, yCoordinate, landValue.getValue(width2, height));
            } else {
                if (y < yCoordinate || y >= yCoordinate + height) {
                    throw new IllegalArgumentException("y must be within the area");
                }

                int height1 = y - yCoordinate;
                int height2 = height - height1;

                // if wxh for either area is the same as parent wxh, then it cannot be subdivided
                if (height1 == height || height2 == height) {
                    return;
                }

                double value1 = landValue.getValue(width, height1);
                double value2 = landValue.getValue(width, height2);

                area1 = new Area(width, height1, xCoordinate, yCoordinate, value1);
                area2 = new Area(width, height2, xCoordinate, y, value2);
            }
        }

        public Area findAreaWithCoordinates(int x, int y) {
            if (x < xCoordinate || x >= xCoordinate + width || y < yCoordinate || y >= yCoordinate + height) {
                return null;
            }

            if (isSubdivided()) {
                Area a = this.area1.findAreaWithCoordinates(x, y);
                if (a != null) {
                    return a;
                }
                return this.area2.findAreaWithCoordinates(x, y);
            } else {
                return this;
            }
        }

        public int[][] toAreaArray() {
            Random random = new Random();
            int num = random.nextInt(9);
            int[][] areaArray = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    areaArray[i][j] = num;
                }
            }
            return areaArray;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Area: ").append(xCoordinate).append(", ").append(yCoordinate).append(" - ").append(width).append("x").append(height).append("\n");
            int[][] areaArray = toAreaArray();
            for (int[] ints : areaArray) {
                for (int anInt : ints) {
                    sb.append(anInt).append(" ");
                }
                sb.append("\n");
            }

            return sb.toString();
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}