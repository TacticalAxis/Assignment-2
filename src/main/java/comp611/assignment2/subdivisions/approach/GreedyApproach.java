package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.Map;

public class GreedyApproach extends Approach {

    private double currentLandValue;

    public GreedyApproach(Land area) {
        super(area, "Greedy Approach");
        this.currentLandValue = getLand().getValue();
    }

    @Override
    public Result solve() {
        // start timer
        startTimer();

        // start search
        sub(getLand().getArea());
        setComplete(true);

        // stop timer
        stopTimer();

        return new Result(this, getLand().getValue(), getSubdivisions());
    }

    @Override
    public double getBestValue() {
        return currentLandValue;
    }

    private void sub(Area area) {
        Subdivision sub = findBest(area);
        if (sub != null) {
            // subdivision found
            area.subdivide(sub);

            // check if we have a better value
            if(area.getLand().getValue() > getBestValue()) {

                // inc sub
                incrementSubdivisions();

                // update best value
                currentLandValue = getLand().getValue();

                // recurse
                sub(area.getArea1());
                sub(area.getArea2());
            } else {
                // undo subdivision
                area.unSubdivide();
            }
        }
    }

    // find the best subdivision for the given area
    private Subdivision findBest(Area area) {
        if(area != null) {
            Subdivision best = null;
            Map<Subdivision, Double> subValues = area.getPossibleSubdivisions();

            // get the subdivision with the highest value double
            for (Subdivision sub : subValues.keySet()) {
                if (best == null || subValues.get(sub) > subValues.get(best)) {
                    best = sub;
                }
            }

            return best;
        }

        return null;
    }
}