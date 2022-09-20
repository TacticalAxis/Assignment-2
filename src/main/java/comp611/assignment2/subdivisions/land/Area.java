package comp611.assignment2.subdivisions.land;

import comp611.assignment2.subdivisions.ArrayUtil;

import java.util.*;

//@SuppressWarnings({"ManualArrayCopy"})
public class Area {
    // the land this area belongs to
    private final Land land;

    // the area parent
    private final Area parent;

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

    public Area(Land land, Area parent, int width, int height, int xCoordinate, int yCoordinate) {
        this.land = land;
        this.parent = parent;

        this.width = width;
        this.height = height;

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.area1 = null;
        this.area2 = null;
        this.subdivision = null;
    }

    // make copy constructor
    public Area(Area area) {
        this.land = area.land;
        this.parent = area.parent;
        this.width = area.width;
        this.height = area.height;
        this.xCoordinate = area.xCoordinate;
        this.yCoordinate = area.yCoordinate;
        this.area1 = area.area1;
        this.area2 = area.area2;
        this.subdivision = area.subdivision;
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
        if(isSubdivided()) {
            return area1.getValue() + area2.getValue();
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

    public List<Area> getSubAreas() {
        List<Area> subAreas = new ArrayList<>();
        if(isSubdivided()) {
            System.out.println("it is subdivided");
            subAreas.addAll(area1.getSubAreas());
            subAreas.addAll(area2.getSubAreas());
        } else {
            if(this.height > 1 && this.width > 1) {
                subAreas.add(this);
            }
//            subAreas.add(this);
        }
        return subAreas;
    }

    public boolean isSubdivided() {
        return area1 != null && area2 != null;
    }

    public boolean canSubdivide() {
        return !isSubdivided() && width > 1 && height > 1;
    }

    public Map<Subdivision, Double> getPossibleSubdivisions() {
        HashMap<Subdivision, Double> possibleSubdivisions = new HashMap<>();
//        List<Subdivision> subdivisions = new ArrayList<>();

        if (isSubdivided()) {return possibleSubdivisions;}

        // iterate over all possible vertical subdivisions
        for (int x = 1; x < width; x++) {
            Subdivision s = new Subdivision(this, Direction.VERTICAL, x + xCoordinate, 0);
            this.subdivide(s);
            if(isSubdivided()) {
                possibleSubdivisions.put(s, land.getValue());
            }
            unSubdivide();
        }

        // iterate over all possible horizontal subdivisions
        for (int y = 1; y < height; y++) {
            Subdivision s = new Subdivision(this, Direction.HORIZONTAL, 0, y + yCoordinate);
            this.subdivide(s);
            if(isSubdivided()) {
                possibleSubdivisions.put(s, land.getValue());
            }
            unSubdivide();
        }

        return possibleSubdivisions;
    }

    public int[][] toArray() {
        int[][] array = new int[height][width];

        if (isSubdivided()) {
            int[][] area1Array = area1.toArray();
            int[][] area2Array = area2.toArray();

            if(subdivision.getDirection() == Direction.VERTICAL) {
                for (int i = 0; i < area1Array.length; i++) {
                    System.arraycopy(area1Array[i], 0, array[i], 0, area1Array[i].length);
                }
                for (int i = 0; i < area2Array.length; i++) {
                    System.arraycopy(area2Array[i], 0, array[i], area1Array[i].length, area2Array[i].length);
                }
            } else {
                for (int i = 0; i < area1Array.length; i++) {
                    System.arraycopy(area1Array[i], 0, array[i], 0, area1Array[i].length);
                }
                for (int i = 0; i < area2Array.length; i++) {
                    System.arraycopy(area2Array[i], 0, array[i + area1Array.length], 0, area2Array[i].length);
                }
            }
        } else {
            for (int[] ints : array) {
                Arrays.fill(ints, this.hashCode());
            }
        }

        return array;
    }

//    public void subdivide(Direction direction, int x, int y) {
//        // first check if x and y are valid
//        if (x <= 0 || x >= getWidth() || y <= 0 || y >= getHeight()) {
//            throw new IllegalArgumentException("x and y must be within the area: x=" + x + ", y=" + y);
//        }
//
//        Area a = findAreaWithCoordinates(x, y);
//        if (a == null) {
//            throw new IllegalArgumentException("x and y must be within the area (a is null): x=" + x + ", y=" + y);
//        }
//
//        a.subdivide(new Subdivision(a, direction, x, y));
//    }

    public void subdivide(Subdivision subdivision) {
        // if area size is 1, then it cannot be subdivided
        if (width == 1 && height == 1) {
            System.out.println("Cannot subdivide area of size 1");
            return;
        }

        if (isSubdivided()) {
            return;
        }

        if (subdivision.getDirection() == Direction.VERTICAL) {
            if (subdivision.getX() < xCoordinate || subdivision.getX() >= xCoordinate + width) {
                throw new IllegalArgumentException("x must be within the area!");
            }

            int width1 = subdivision.getX() - xCoordinate;
            int width2 = width - width1;

            // if wxh for either area is the same as parent wxh, then it cannot be subdivided
            if (width1 == width || width2 == width) {
                return;
            }

            area1 = new Area(land, this, width1, height, xCoordinate, yCoordinate);
            area2 = new Area(land, this, width2, height, subdivision.getX(), yCoordinate);
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

            area1 = new Area(land, this, width, height1, xCoordinate, yCoordinate);
            area2 = new Area(land, this, width, height2, xCoordinate, subdivision.getY());
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

        return sb.toString().trim();
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

    public int getAllSubdivisionLength() {
        int length = 0;
        if (isSubdivided()) {
            length += subdivision.getLength();
            length += area1.getAllSubdivisionLength();
            length += area2.getAllSubdivisionLength();
        }
        return length;
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

    public Area getRoot() {
        if (getParent() == null) {
            return this;
        } else {
            return getParent().getRoot();
        }
    }

    // make a copy so that the hashcode is not the same
    public Area copy() {
        Area area = new Area(this);
        if (isSubdivided()) {
            area.subdivision = subdivision;
            area.area1 = area1.copy();
            area.area2 = area2.copy();
        }
        return area;
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // compare two areas
    public boolean compare(Area area) {
        if(area == null) {
            return false;
        }

        return ArrayUtil.areTheyTheSame(toArray(), area.toArray());
    }

    public Area getParent() {
        return parent;
    }

}