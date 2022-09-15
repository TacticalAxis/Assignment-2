package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.ArrayList;
import java.util.List;

public class BruteForceApproach extends Approach {

    private final BruteForceLayoutSet permutations;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
        this.permutations = new BruteForceLayoutSet(land);
    }

    @Override
    public Result solve() {
        // start timer
        startTimer();

        // start recursive search
        getAllIterations(getLand().getArea());

        // stop timer
        stopTimer();

        // get solution
        Area area = permutations.getSolution();
        if(area != null) {
            // solution found
            getLand().setArea(area);

            // return result
            return new Result(this, area.getLand().getValue(), getSubdivisions());
        }

        // no solution found
        return null;
    }


    public int getPossibleLayouts() {
        return permutations.size();
    }


    private static class BruteForceLayoutSet {
        private final List<Area> values;
        private final Land land;

        public BruteForceLayoutSet(Land land) {
            values = new ArrayList<>();
            this.land = land;
        }

        public int size() {
            return values.size();
        }

        private void add(Area area) {
            if(area == null) {
                return;
            }

            Area toAdd = area.copy();
            for(Area a : values) {
                if(a.compare(toAdd)) {
                    return;
                }
            }

            values.add(toAdd);
        }

        private Area getSolution() {
            if (values.isEmpty()) {
                return null;
            }

            Area best = values.get(0);
            for (Area area : values) {
                if (land.getValue(area) > land.getValue(best)) {
                    best = area;
                }
            }

            return best;
        }
    }

    // single-level
    private void getAllIterations(Area area) {
        // check area not null
        if(area == null) {
            return;
        }

        // check area is valid
        if(!area.canSubdivide()) {
            return;
        }

        // check area can be subdivided further
        if(area.getRoot().isFullySubdivided()) {
            area.getRoot().unSubdivide();
            return;
        }

        // iterate through all possible subdivisions
        for(Subdivision sub : area.getPossibleSubdivisions()) {
            area.subdivide(sub);
            incrementSubdivisions();
            permutations.add(area.getRoot().copy());
            getAllIterations(area.getArea1());
            getAllIterations(area.getArea2());
            area.unSubdivide();
        }
    }

    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 3, 50, 20,1000));
        Result solution = bruteForceApproach.solve();
        if(solution != null) {
            System.out.println("Brute Force Solution Found: " + solution.getValue());
            System.out.println("This took " + bruteForceApproach.getTime() + "s");
            System.out.println("Subdivisions Found: " + bruteForceApproach.getSubdivisions());
            System.out.println("Unique Combinations: " + bruteForceApproach.getPossibleLayouts());
            System.out.println("Solution:\n" + bruteForceApproach.getLand());
        }
    }
}