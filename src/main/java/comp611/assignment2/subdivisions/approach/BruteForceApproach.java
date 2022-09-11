package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

public class BruteForceApproach extends Approach<Double, Subdivision> {

    private int subdivisions = 0;

    // Calling the constructor of the super class, Approach.
    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
    }

    /**
     * The function `solve()` is called by the `main()` function in the `Main` class. It starts the timer, calls the
     * `findSub()` function, stops the timer, prints the number of subdivisions, and returns 0.0
     *
     * @return The return type is Double, but the method returns 0.0.
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
    // Finding the best subdivision for the given area.
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

    /**
     * If the area can be subdivided, then for each possible subdivision, subdivide the area, find the subdivisions of the
     * two new areas, and then unsubsidize the area
     *
     * @param area The area to be subdivided
     */
    private void findSub(Area area) {
        if (area != null) {
            if (area.canSubdivide()) {
                for (Subdivision subdivision : area.getPossibleSubdivisions()) {
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

    //


}