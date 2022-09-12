package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.Solution;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;
import comp611.assignment2.subdivisions.land.SubdivisionPath;

import java.util.ArrayList;
import java.util.List;

public class BruteForceApproach extends Approach<Double, Subdivision> {

    private int subdivisions = 0;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
    }


    @Override
    public Double solve() {
        // start timer
        startTimer();

        //setup list for combinations
        List<SubdivisionPath> paths = new ArrayList<>();
        //parse both a path and
        findSub(getLand().getArea(), paths, new SubdivisionPath());
        stopTimer();
        System.out.println("Subdivisions: " + subdivisions);
        return 0.0;
    }


    /**
     * For each possible subdivision of the area, add the subdivision to the area, increment the number of subdivisions,
     * and recursively find the best subdivision for the two new areas
     *
     * @param area the area to be subdivided
     */

    public void findSub(Area area, List<SubdivisionPath> paths, SubdivisionPath currentPath) {
        // if area is not divisible, return

        if (!area.canSubdivide()) {
            paths.add(currentPath);
            return;
        }

        //iterate through all possible subdivisions
        for(Subdivision sub : area.getPossibleSubdivisions()) {
            // add it to the land
            area.subdivide(sub);
            // increment the number of subdivisions
            subdivisions++;
            // find the best subdivision for the new area
            findSub(area.getArea1(), paths, currentPath);
            findSub(area.getArea2(), paths, currentPath);
            // remove the subdivision from the land
            area.unSubdivide();
        }
    }

    //calculate the value of the total subdivision
    public double calculateValue(Area area) {
        if (area.isSubdivided()) {
            return calculateValue(area.getArea1()) + calculateValue(area.getArea2()) - (area.getSubdivision().getLength() * getLand().getSubValue());
        } else {
            return getLand().getLandValue().getValue(area.getWidth(), area.getHeight());
        }
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

//    public double calculateValue(Area area) {
//        if(area != null) {
//            if(area.canSubdivide()) {
//                for(Subdivision subdivision : area.getPossibleSubdivisions()) {
//                    area.subdivide(subdivision);
//                    calculateValue(area.getArea1());
//                    calculateValue(area.getArea2());
//                    area.unSubdivide();
//                    System.out.println("Area value: " + area.getValue());
//                }
//            }
//            else {
//                return area.getValue();
//            }
//        }
//        return 0.0;
//    }
//
//    public int calculateSubdivisions(Area area) {
//        if(area != null) {
//            if(area.canSubdivide()) {
//                for(Subdivision subdivision : area.getPossibleSubdivisions()) {
//                    area.subdivide(subdivision);
//                    calculateSubdivisions(area.getArea1());
//                    calculateSubdivisions(area.getArea2());
//                    area.unSubdivide();
//                }
//            }
//            else {
//                return subdivisions++;
//            }
//        }
//        return 0;
//    }




    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 3, 50, 20,1000));
        bruteForceApproach.startTimer();
        double solution = bruteForceApproach.solve();
        System.out.println("Brute Force Solution: " + solution);
        System.out.println("This took " + bruteForceApproach.getTime() + "ms");
        System.out.println(bruteForceApproach.getLand());
        bruteForceApproach.stopTimer();

    }
}