package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

public class BruteForceApproach extends Approach<Double, Subdivision> {

    private int subdivisions = 0;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
    }

    /**
     * The function `solve()` is called by the `main()` function in the `Main` class. It calls the `findSub()` function,
     * which recursively finds the optimal subdivision of the land
     *
     * @return The number of subdivisions.
     */
    @Override
    public Double solve() {
        startTimer();
        findSub(getLand().getArea());
        stopTimer();
        System.out.println("Subdivisions: " + subdivisions);
        return 0.0;
    }

    @Override
    public Subdivision findBest(Area area) {
        if(area != null) {
            double best = 0.0d;
            Subdivision bestSub = null;
            for(Subdivision sub : area.getPossibleSubdivisions()) {
                area.subdivide(sub);
                if(area.getValue() > best) {
                    best = area.getValue();
                    bestSub = sub;
                }
                area.unSubdivide();
            }

            return bestSub;
        }
        return null;
    }

    private void findSub(Area area) {
        if (area != null) {
            if (area.canSubdivide()) {
                for (Subdivision subdivision : area.getPossibleSubdivisions()) {
                    // Creating two new areas from the given area, and setting the given area's `area1` and `area2` to the
                    // new areas.
                    area.subdivide(subdivision);
                    findSub(area.getArea1());
                    findSub(area.getArea2());
//                    area.unSubdivide();
                    subdivisions++;
                }

                if(!area.isSubdivided()) {
                    Subdivision bestSub = findBest(area);
                    if(bestSub != null) {
                        System.out.println("Currently subdividing:\n" + area);
                        area.subdivide(bestSub);
                        System.out.println("After Subdivided:\n" + area);
                    }
                }
            }
        }
    }
}