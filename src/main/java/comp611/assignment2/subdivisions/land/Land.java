package comp611.assignment2.subdivisions.land;

public class Land {

    private Area area;
    private final double subValue;
    private final LandValue landValue;

    public Land(int width, int height, double subValue) {
        this.landValue = new LandValue(width, height);
        this.area = new Area(this, null, width, height, 0, 0);
        this.subValue = subValue;
    }

    public double getSubValue() {
        return subValue;
    }

    public LandValue getLandValue() {
        return landValue;
    }

    public void setArea(Area area) {
        this.area = area;
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

    @Override
    public String toString() {
        return area.toString();
    }
}