package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BruteForceApproach extends Approach {

    private int subdivisions;
    private final LayoutSet permutations;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
        this.permutations = new LayoutSet();
        this.subdivisions = 0;
    }

    @Override
    public Result solve() {
        // start timer
        startTimer();

        //setup list for combinations
//        LayoutSet permutations = new LayoutSet();
//        List<SubdivisionPath> paths = new ArrayList<>();

        //parse both a path and
        findSub(getLand().getArea(), permutations);

        if(permutations.getSolution() != null) {
            System.out.println("Solution found");
            getLand().setArea(permutations.getSolution());
        }

        System.out.println("Subdivisions Found: " + subdivisions);
        System.out.println("Unique Combinations: " + permutations.size());
        System.out.println("Solution:\n" + permutations.getSolution());
//        System.out.println("Solution Value: " + permutations.getSolution().getValue());

        // stop timer
        stopTimer();

        return new Result(this, permutations.getBestValue(), subdivisions);

        // return the best value
//        System.out.println("Subdivisions: " + subdivisions);
//        System.out.println("Amount of paths: " + permutations.size());
//        System.out.println("Best value: " + permutations.getBest());
//
//        return 0.0;
    }

    public void findSub(Area area, LayoutSet permutations) {
        if(area == null) {
            return;
        }

        permutations.add(getLand().getArea().arrayToNumberStructure(), getLand().getArea().getValue(), getLand().getArea().copy());

        // if area is not divisible, return
        if (!area.canSubdivide()) {
            // go through entire land and check that all areas are subdivided
            // if not, return
            if (!getLand().getArea().isFullySubdivided()) {
                area.getLand().resetHard();
            }

            return;
        }

        //iterate through all possible subdivisions
        for(Subdivision sub : area.getPossibleSubdivisions()) {
            // add it to the land
            area.subdivide(sub);
            // increment the number of subdivisions
            subdivisions++;
            // find the best subdivision for the new area
            findSub(area.getArea1(), permutations);
            findSub(area.getArea2(), permutations);
        }
    }

    private static class LayoutSet {
        private final HashMap<List<Integer>, Double> values;
        private final List<Area> areas;
//        private Area bestArea;

        public LayoutSet() {
            values = new HashMap<>();
            areas = new ArrayList<>();
        }

        public int size() {
            return values.size();
        }

        private void add(List<Integer> list, double value, Area area) {
            if(!values.containsKey(list)) {
                values.put(list, value);
                areas.add(area);
            }
//            // check if a list with the exact same values in the same order exists
//            if(values.containsKey(list)) {
//                // if it does, check if the value is better
//                if(values.get(list) < value) {
//                    // if it is, replace the value
//                    values.put(list, value);
//                    areas.set(areas.indexOf(area), area);
//                }
//            } else {
//                // if it doesn't, add it
//                values.put(list, value);
//            }
        }

        private double getBestValue() {
            double best = 0.0d;
            for (Double value : values.values()) {
                if (value > best) {
                    best = value;
                }
            }

            return best;
        }

        private Area getSolution() {
            if(areas.isEmpty()) {
                return null;
            }

            int indexOfBest = 0;
            double best = 0.0d;
            for (int i = 0; i < areas.size(); i++) {
                if (areas.get(i).getValue() > best) {
                    best = areas.get(i).getValue();
                    indexOfBest = i;
                }
            }

            return areas.get(indexOfBest);
        }
    }


    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 3, 50, 20,1000));
        bruteForceApproach.startTimer();
        Result solution = bruteForceApproach.solve();
//        System.out.println("Brute Force Solution Value: " + solution.getValue());
//        System.out.println("Brute Force Solution Time: " + solution.getTime());
//        System.out.println("This took " + bruteForceApproach.getTime() + "ms");
//        System.out.println(bruteForceApproach.getLand());
        bruteForceApproach.stopTimer();
    }
}