package comp611.assignment2.subdivisions.land;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"ManualArrayCopy"})
public class Area {
    // the land this area belongs to
    private final Land land;

    // the area's width and height
    public final int width;
    public final int height;

    // the coordinates of the top left corner of the area
    private final int xCoordinate;
    private final int yCoordinate;

    // subdivision data
    private Area area1;
    private Area area2;
    private Subdivision subdivision;

    public Area(Land land, int width, int height, int xCoordinate, int yCoordinate) {
        this.land = land;

        this.width = width;
        this.height = height;

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.area1 = null;
        this.area2 = null;
        this.subdivision = null;
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
            return land.getLandValue().getValue(width, height);
        }
    }

    public boolean isFullySubdivided() {
        if (isSubdivided()) {
            return area1.isFullySubdivided() && area2.isFullySubdivided();
        } else {
            return false;
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

    public int[][] toArray() {
        int[][] array = new int[height][width];

        if (isSubdivided()) {
            int[][] area1Array = area1.toArray();
            int[][] area2Array = area2.toArray();

            if(subdivision.getDirection() == Direction.VERTICAL) {
                for (int i = 0; i < area1Array.length; i++) {
                    for (int j = 0; j < area1Array[i].length; j++) {
                        array[i][j] = area1Array[i][j];
                    }
                }
                for (int i = 0; i < area2Array.length; i++) {
                    for (int j = 0; j < area2Array[i].length; j++) {
                        array[i][j + area1Array[i].length] = area2Array[i][j];
                    }
                }
            } else {
                for (int i = 0; i < area1Array.length; i++) {
                    for (int j = 0; j < area1Array[i].length; j++) {
                        array[i][j] = area1Array[i][j];
                    }
                }
                for (int i = 0; i < area2Array.length; i++) {
                    for (int j = 0; j < area2Array[i].length; j++) {
                        array[i + area1Array.length][j] = area2Array[i][j];
                    }
                }
            }
        } else {
            for (int[] ints : array) {
                Arrays.fill(ints, this.hashCode());
            }
        }

        return array;
    }

    public void subdivide(Subdivision subdivision) {
        // if area size is 1, then it cannot be subdivided
        if (width == 1 && height == 1) {
            System.out.println("Cannot subdivide area of size 1");
            return;
        }

        if (isSubdivided()) {
            return;
//            throw new IllegalStateException("Area is already subdivided");
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

            area1 = new Area(land, width1, height, xCoordinate, yCoordinate);
            area2 = new Area(land, width2, height, subdivision.getX(), yCoordinate);
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

            area1 = new Area(land, width, height1, xCoordinate, yCoordinate);
            area2 = new Area(land, width, height2, xCoordinate, subdivision.getY());
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

    @Override
    public String toString() {
        int[][] array = toArray();
        StringBuilder sb = new StringBuilder();
        for (int[] ints : array) {
            for (int anInt : ints) {
                sb.append(anInt);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public List<Subdivision> getSubdivisions() {
        List<Subdivision> subdivisions = new ArrayList<>();
        if (isSubdivided()) {
            subdivisions.add(subdivision);
            subdivisions.addAll(area1.getSubdivisions());
            subdivisions.addAll(area2.getSubdivisions());
        }
        return subdivisions;
    }

    private boolean compareSubdivisions(List<Subdivision> subdivisions) {
        List<Subdivision> thisSubdivisions = getSubdivisions();
        if (subdivisions.size() != thisSubdivisions.size()) {
            return false;
        }

        for (int i = 0; i < subdivisions.size(); i++) {
            if (!subdivisions.get(i).equals(thisSubdivisions.get(i))) {
                return false;
            }
        }

        return true;
    }

    public Area copy() {
        Area clone = new Area(land, width, height, xCoordinate, yCoordinate);
        if (isSubdivided()) {
            clone.subdivide(subdivision);
            clone.area1 = area1.copy();
            clone.area2 = area2.copy();
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;

        // an area is equal to another area they have the same coordinates, size and the same subdivisions
        return xCoordinate == area.xCoordinate &&
                yCoordinate == area.yCoordinate &&
                width == area.width &&
                height == area.height &&
                compareSubdivisions(area.getSubdivisions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(land, width, height, xCoordinate, yCoordinate, area1, area2, subdivision);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // compare two areas
    // when you convert an area to an array, you get a 2d array of integers
    // the integers represent the hashcode of the land
    // two areas may have different hashcodes, but if they have the same subdivisions, then they are equal
    // this method compares two areas by converting them to arrays and comparing the arrays
    public boolean compare(Area area) {
        if(area == null) {
            return false;
        }

        int[][] array1 = toArray();
        int[][] array2 = area.toArray();

        if (array1.length != array2.length || array1[0].length != array2[0].length) {
            return false;
        }

        List<Integer> list1 = arrayToNumberStructure(array1);
        List<Integer> list2 = arrayToNumberStructure(array2);

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public List<Integer> arrayToNumberStructure() {
//        System.out.println("Returning array to number structure: " + Arrays.deepToString(toArray()));
        return arrayToNumberStructure(toArray());
    }

    public List<Integer> arrayToNumberStructure(int[][] array) {
        List<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        AtomicInteger count = new AtomicInteger();

        for (int[] ints : array) {
            for (int anInt : ints) {
                map.computeIfAbsent(anInt, k -> count.getAndIncrement());
                list.add(map.get(anInt));
            }
        }

        return list;
    }

    public List<Area> getAreas() {
        List<Area> areas = new ArrayList<>();
        if (isSubdivided()) {
            areas.add(area1);
            areas.add(area2);
            areas.addAll(area1.getAreas());
            areas.addAll(area2.getAreas());
        }

        return areas;
    }

    public boolean contains(Area area) {
        if (area == null) {
            return false;
        }

        if (area.xCoordinate < xCoordinate || area.yCoordinate < yCoordinate || area.xCoordinate + area.width > xCoordinate + width || area.yCoordinate + area.height > yCoordinate + height) {
            return false;
        }

        if (isSubdivided()) {
            return area1.contains(area) || area2.contains(area);
        } else {
            return true;
        }
    }

    public Area getParent() {
        return land.getArea().getAreas().stream().filter(a -> a.contains(this)).findFirst().orElse(null);
    }
}