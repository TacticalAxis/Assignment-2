package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

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

        // stop timer
        stopTimer();

        return new Result(this, permutations.getBestValue());

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
        private Area bestArea;

        public LayoutSet() {
            values = new HashMap<>();
            this.bestArea = null;
        }

        public int size() {
            return values.size();
        }

        private void add(List<Integer> list, double value, Area area) {
            // check if a list with the exact same values in the same order exists
            if(values.containsKey(list)) {
                // if it does, check if the value is better
                if(values.get(list) < value) {
                    // if it is, replace the value
                    values.put(list, value);
                    bestArea = area;
                }
            } else {
                // if it doesn't, add it
                values.put(list, value);
                bestArea = area;
            }
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
            return bestArea;
        }
    }


    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 3, 50, 20,1000));
        bruteForceApproach.startTimer();
        Result solution = bruteForceApproach.solve();
        System.out.println("Brute Force Solution Value: " + solution.getValue());
        System.out.println("Brute Force Solution Time: " + solution.getTime());
        System.out.println("This took " + bruteForceApproach.getTime() + "ms");
        System.out.println(bruteForceApproach.getLand());
        bruteForceApproach.stopTimer();
    }
}