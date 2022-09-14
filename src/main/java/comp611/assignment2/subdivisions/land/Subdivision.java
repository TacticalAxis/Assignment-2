package comp611.assignment2.subdivisions.land;

public class Subdivision {
    private final int x;
    private final int y;
    private final Direction direction;
    private final Area area;

    public Subdivision(Area area, Direction direction, int x, int y) {
        this.area = area;
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        if (direction == Direction.VERTICAL) {
            return area.height;
        } else {
            return area.width;
        }
    }

    @Override
    public String toString() {
        return "Subdivision{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", length=" + getLength() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subdivision that = (Subdivision) o;
        return x == that.x && y == that.y && direction == that.direction && getLength() == that.getLength();
    }

}