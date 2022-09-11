package comp611.assignment2.subdivisions.land;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Area {
    private final Land land;

    public final int width;
    public final int height;
    private final int xCoordinate;
    private final int yCoordinate;

    // subdivision data
    private Area area1;
    private Area area2;
    private Subdivision subdivision;

    // area value
    private final double value;

    public Area(Land land, int width, int height, int xCoordinate, int yCoordinate, double value) {
        this.land = land;
        this.width = width;
        this.height = height;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.area1 = null;
        this.area2 = null;
        this.value = value;
    }

    public Land getLand() {
        return land;
    }

    public Area getArea1() {
        return area1;
    }

    public Area getArea2() {
        return area2;
    }

    public double getValue() {
        if (isSubdivided()) {
            return area1.getValue() + area2.getValue() - (subdivision.getLength() * land.getSubValue());
        } else {
            return value;
        }
    }

    public boolean isSubdivided() {
        return area1 != null && area2 != null;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public boolean canSubdivide() {
        return width > 1 || height > 1;
    }

    public List<Subdivision> getPossibleSubdivisions() {
        List<Subdivision> subdivisions = new ArrayList<>();

        if (isSubdivided()) {return subdivisions;}

        // iterate over all possible vertical subdivisions
        for (int x = 1; x < width; x++) {
            Subdivision s = new Subdivision(this, Direction.VERTICAL, x + xCoordinate, 0);
            this.subdivide(s);
            subdivisions.add(s);
            unSubdivide();
        }

        // iterate over all possible horizontal subdivisions
        for (int y = 1; y < height; y++) {
            Subdivision s = new Subdivision(this, Direction.HORIZONTAL, 0, y + yCoordinate);
            this.subdivide(s);
            subdivisions.add(s);
            unSubdivide();
        }

        return subdivisions;
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

    public void subdivide(Subdivision subdivision) {
        // if area size is 1, then it cannot be subdivided
        if (width == 1 && height == 1) {
            System.out.println("Cannot subdivide area of size 1");
            return;
        }

        if (isSubdivided()) {
            throw new IllegalStateException("Area is already subdivided");
        }

        if (subdivision.getDirection() == Direction.VERTICAL) {
//            System.out.println("trying to sub - x: " + subdivision + ". X: " + subdivision.getX() + " xCoordinate: " + xCoordinate + " width: " + width);
            if (subdivision.getX() < xCoordinate || subdivision.getX() >= xCoordinate + width) {
                throw new IllegalArgumentException("x must be within the area!");
            }

            int width1 = subdivision.getX() - xCoordinate;
            int width2 = width - width1;

            // if wxh for either area is the same as parent wxh, then it cannot be subdivided
            if (width1 == width || width2 == width) {
                return;
            }

            double value1 = getLand().getLandValue().getValue(width1, height);
            double value2 = getLand().getLandValue().getValue(width2, height);

            area1 = new Area(land, width1, height, xCoordinate, yCoordinate, value1);
            area2 = new Area(land, width2, height, subdivision.getX(), yCoordinate, value2);

        } else {
            if (subdivision.getY() < yCoordinate || subdivision.getY() >= yCoordinate + height) {
                throw new IllegalArgumentException("y must be within the area");
            }

            int height1 = subdivision.getY() - yCoordinate;
            int height2 = height - height1;

            // if wxh for either area is the same as parent wxh, then it cannot be subdivided
            if (height1 == height || height2 == height) {
                return;
            }

            double value1 = getLand().getLandValue().getValue(width, height1);
            double value2 = getLand().getLandValue().getValue(width, height2);

            area1 = new Area(land, width, height1, xCoordinate, yCoordinate, value1);
            area2 = new Area(land, width, height2, xCoordinate, subdivision.getY(), value2);
        }

        this.subdivision = subdivision;
    }

    public void unSubdivide() {
        area1 = null;
        area2 = null;
        subdivision = null;
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

    private int[][] toAreaArray() {
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

        return sb.toString().trim();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}