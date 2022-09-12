package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

@SuppressWarnings("unused")
public class ExactApproach extends Approach<Double, Subdivision> {

    private int subdivisions = 0;

    public ExactApproach(Land area) {
        super(area, "Exact Approach");
    }


    @Override
    public Double solve() {
        startTimer();
        findSub(getLand().getArea());
        stopTimer();
        System.out.println("Subdivisions: " + subdivisions);
        return getLand().getValue();
    }

    // this is a recursive function that finds the optimal subdivision of the land
    public void findSub(Area area) {
        // if the area is not divisible, return
        if (!area.canSubdivide()) {
            return;
        }
        // if the area is divisible, find the best subdivision
        Subdivision bestSub = findBest(area);
        // if the best subdivision is not null, add it to the land and increment the number of subdivisions
        if (bestSub != null) {
            area.subdivide(bestSub);
            subdivisions++;

            // recursively call the function on the two new areas
            findSub(area.getArea1());
            findSub(area.getArea2());
        }
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
}
