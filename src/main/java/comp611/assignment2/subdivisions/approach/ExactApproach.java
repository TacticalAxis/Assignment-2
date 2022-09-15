package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.List;

@SuppressWarnings("unused")
public class ExactApproach extends Approach {

    private final ExactApproachLayoutSet permutations;

    public ExactApproach(Land area) {
        super(area, "Exact Approach");
        this.permutations = new ExactApproachLayoutSet(area);
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

    //
    //get all possible itterations
    public void getAllIterations(Area area){
        if(area == null){
            return;
        }

        //check if the area is divisible
        if(!area.canSubdivide()){
            return;
        }

        if(area.getRoot().isFullySubdivided()){
            area.getRoot().getSubdivisions();
            return;
        }

        //iterate through all possible subdivisions
        for(Subdivision sub : area.getSubdivisions()){
            area.subdivide(sub);
            incrementSubdivisions();
            permutations.add(area.getRoot().copy());
            getAllIterations(area.getArea1());
            getAllIterations(area.getArea2());
            area.unSubdivide();
        }

    }

    private static class ExactApproachLayoutSet {
        private final List<Area> values;
        private final Land land;

        public ExactApproachLayoutSet(Land land) {
            this.land = land;
            this.values = new java.util.ArrayList<>();
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



}
