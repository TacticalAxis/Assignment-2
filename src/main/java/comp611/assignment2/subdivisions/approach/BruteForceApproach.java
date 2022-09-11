package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

public class BruteForceApproach extends Approach<Double> {

    private int subdivisions = 0;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
    }

    @Override
    public Double solve() {
        startTimer();
        findSub(getLand().getArea());
        stopTimer();
        System.out.println("Subdivisions: " + subdivisions);
        return 0.0;
    }

    private void findSub(Area area) {
        if (area != null) {
            if (area.canSubdivide()) {
                for (Subdivision subdivision : area.getPossibleSubdivisions()) {
                    area.subdivide(subdivision);
                    findSub(area.getArea1());
                    findSub(area.getArea2());
                    area.unSubdivide();
                    subdivisions++;
                }
            }
        }
    }
}
