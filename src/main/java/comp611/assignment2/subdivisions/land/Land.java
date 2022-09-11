package comp611.assignment2.subdivisions.land;

public class Land {

    private final Area area;
    private final double subValue;
    private final LandValue landValue;

    public Land(int width, int height, int subValue, double minValue, double maxValue) {
        this.landValue = new LandValue(width, height, minValue, maxValue);
        this.area = new Area(this, width, height, 0, 0, landValue.getValue(width, height));
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

        a.subdivide(new Subdivision(a, direction, x, y));
    }

    public static void main(String[] args) {
        Land land = new Land(6, 3, 50, 20, 1000);
        System.out.println("Total land price: " + land.getValue());

        land.subdivide(Direction.VERTICAL, 2, 2);

        land.printArea();
        System.out.println();
        System.out.println(land.landValue);

        for (Subdivision subdivision : land.getArea().getPossibleSubdivisions()) {
            System.out.println(subdivision);
        }

        System.out.println("Total land price: " + land.getValue());
    }
}