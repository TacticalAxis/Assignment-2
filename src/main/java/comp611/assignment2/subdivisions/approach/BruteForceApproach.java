package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.Solution;
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
        // start timer

        // get area with getLand().getArea()
        startTimer();
        findSub(getLand().getArea());
        stopTimer();
        System.out.println("Subdivisions: " + subdivisions);
        return 0.0;
    }

    public void findSub(Area area) {
        // if the area is not divisible, return
        if (!area.canSubdivide()) {
            return;
        }

        // if the area is divisible, find the best subdivision
        for(Subdivision sub : area.getPossibleSubdivisions()) {
            area.subdivide(sub);
            subdivisions++;
            findSub(area.getArea1());
            findSub(area.getArea2());
            area.unSubdivide();
        }

        return;
    }

//    // this is a recursive function that finds the optimal subdivision of the land
//    public void findSub(Area area) {
//        // if the area is not divisible, return
//        if (!area.canSubdivide()) {
//            return;
//        }
//        // if the area is divisible, find the best subdivision
//        Subdivision bestSub = findBest(area);
//        // if the best subdivision is not null, add it to the land and increment the number of subdivisions
//        if (bestSub != null) {
//            area.subdivide(bestSub);
//            subdivisions++;
//
//            // recursively call the function on the two new areas
//            findSub(area.getArea1());
//            findSub(area.getArea2());
//        }
//    }

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

//    private void findSub(Area area) {
//        if (area != null) {
//            if (area.canSubdivide()) {
//                for (Subdivision subdivision : area.getPossibleSubdivisions()) {
//                    area.subdivide(subdivision);
//                    findSub(area.getArea1());
//                    findSub(area.getArea2());
//                    area.unSubdivide();
//                    subdivisions++;
//                }
//
//                if(!area.isSubdivided()) {
//                    Subdivision bestSub = findBest(area);
//                    if(bestSub != null) {
//                        System.out.println("Currently subdividing:\n" + area);
//                        area.subdivide(bestSub);
//                        System.out.println("After Subdivided:\n" + area);
//                    }
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}