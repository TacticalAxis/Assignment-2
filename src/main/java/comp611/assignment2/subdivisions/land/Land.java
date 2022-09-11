package comp611.assignment2.subdivisions.land;

public class Land {

    private final Area area;
    private final double subValue;
    private final LandValue landValue;

    public Land(int width, int height, int subValue, double minValue, double maxValue) {
        this.landValue = new LandValue(width, height, minValue, maxValue);
        this.area = new Area(this, width, height, 0, 0);
        this.subValue = subValue;
    }

    public LandValue getLandValue() {
        return landValue;
    }

    public double getSubValue() {
        return subValue;
    }

    public double getValue() {
        return area.getValue();
    }

    public Area getArea() {
        return area;
    }

    public int[][] getAreaArray() {
        return area.toArray();
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

        a.subdivide(new Subdivision(a, direction, x, y));
    }

    @Override
    public String toString() {
        return area.toString();
    }

    public static void main(String[] args) {
        // setup land
        Land land = new Land(6, 3, 50, 20, 1000);
        System.out.println("Total land price: " + land.getValue());

        // show before
        System.out.println("before");
        land.printArea();

        // get possible subdivisions
        for (Subdivision subdivision : land.getArea().getPossibleSubdivisions()) {
            System.out.println(subdivision);
        }

        // subdivide
        land.subdivide(Direction.VERTICAL, 2, 2);
        land.subdivide(Direction.VERTICAL, 4, 2);

        // show after
        System.out.println("\nall area");
        System.out.println(land.getArea());
        System.out.println("area 1");
        System.out.println(land.getArea().getArea1());
        System.out.println("area 2");
        System.out.println(land.getArea().getArea2());

        // show total value
        System.out.println("Total land price: " + land.getValue());
    }
}