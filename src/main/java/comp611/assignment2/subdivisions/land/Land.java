package comp611.assignment2.subdivisions.land;

public class Land {

    private Area area;
    private final double subValue;
    private final LandValue landValue;

    public Land(int width, int height, double subValue, double minValue, double maxValue) {
        this.landValue = new LandValue(width, height, minValue, maxValue);
        this.area = new Area(this, null, width, height, 0, 0);
        this.subValue = subValue;
    }

    public LandValue getLandValue() {
        return landValue;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public double getSubValue() {
        return subValue;
    }

    public double getValue() {
        return area.getValue() - (subValue * area.getAllSubdivisionLength());
    }

    public double getValue(Area a) {
        return a.getValue() - (subValue * a.getAllSubdivisionLength());
    }

    public Area getArea() {
        return area;
    }

    public synchronized int[][] getAreaArray() {
        return area.toArray();
    }

    //amogngus; 2019-05-06 10:55:00
    //what happened at 10:55:00
    //what happened at 10:55:00
    //what happened at 10:55:00
    //what happened at 10:55:00

//    public void subdivide(Direction direction, int x, int y) {
//        // first check if x and y are valid
//        if (x <= 0 || x >= area.getWidth() || y <= 0 || y >= area.getHeight()) {
//            throw new IllegalArgumentException("x and y must be within the area: x=" + x + ", y=" + y);
//        }
//
//        Area a = this.area.findAreaWithCoordinates(x, y);
//        if (a == null) {
//            throw new IllegalArgumentException("x and y must be within the area (a is null): x=" + x + ", y=" + y);
//        }
//
//        a.subdivide(new Subdivision(a, direction, x, y));
//    }

    @Override
    public String toString() {
        return area.toString();
    }

    public static void main(String[] args) {
//        Land land = new Land(6, 6, 50, 20, 1000);
//        System.out.println("INITIAL LAND VALUE: " + land.getValue());
//        land.subdivide(Direction.HORIZONTAL, 5, 5);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.VERTICAL, 5, 4);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.VERTICAL, 3, 4);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.HORIZONTAL, 4, 4);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.HORIZONTAL, 4, 2);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.HORIZONTAL, 1, 3);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.VERTICAL, 2, 2);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.HORIZONTAL, 1, 1);
//        System.out.println("Current value: " + land.getValue());
//        land.subdivide(Direction.VERTICAL, 4, 1);
//        System.out.println("Current value: " + land.getValue());
//
//        System.out.println(land);
//
//        JFrame frame = new JFrame("Land GUI");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        frame.getContentPane().add(new LandGUI(land, frame));
//        frame.pack();
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        // setup land
//        System.out.println("Land 1");
//        Land land = new Land(6, 3, 50, 20, 1000);
//        System.out.println(land.getArea());
//
//        System.out.println("Land 2");
//        Land land2 = new Land(6, 3, 50, 20, 1000);
//        System.out.println(land2.getArea());
//
//        System.out.println("Subdividing Land 1");
//        land.subdivide(Direction.VERTICAL, 2, 2);
//        land.subdivide(Direction.VERTICAL, 4, 2);
//
//        System.out.println("Subdividing Land 2");
//        land2.subdivide(Direction.VERTICAL, 2, 2);
//        land2.subdivide(Direction.VERTICAL, 4, 2);
//
//        System.out.println("Land 1 after subdivision");
//        System.out.println(land.getArea());
//
//        System.out.println("Land 2 after subdivision");
//        System.out.println(land2.getArea());
//
//        System.out.println("Land 1 as array");
//        System.out.println(land.getArea().inline(land.getAreaArray()));
//
//        System.out.println("Land 2 as array");
//        System.out.println(land2.getArea().inline(land2.getAreaArray()));
//
//        System.out.println("Are they similar?");
//        System.out.println(land.getArea().compare(land2.getArea()));
//
//
//        Area a1 = land.getArea();
//        Area a2 = land.getSnapshot();
//        System.out.println("Hashcode of a1: " + a1.hashCode());
//        System.out.println("Hashcode of a2: " + a2.hashCode());
//    }
}