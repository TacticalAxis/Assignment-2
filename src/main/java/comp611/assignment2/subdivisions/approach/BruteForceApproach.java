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

    /**
     * "Find the best subdivision for the given area by trying each possible subdivision and returning the one that gives
     * the best value."
     *
     * The function is a bit more complicated than that, but that's the gist of it
     *
     * @param area The area to be subdivided.
     * @return The best subdivision for the area.
     */
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

    /**
     * If the area can be subdivided, then for each possible subdivision, subdivide the area, find the subdivisions of the
     * two new arnsubeas, and then usidize the area
     *
     * @param area The area to be subdivided
     */
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


    /**
     * > If the area can be subdivided, then subdivide it and calculate the value of each of the two new areas
     *
     * @param area The area to calculate the value for.
     * @return The value of the area.
     */
    public double calculateValue(Area area) {
    	if(area != null) {
    		if(area.canSubdivide()) {
    			for(Subdivision subdivision : area.getPossibleSubdivisions()) {
    				area.subdivide(subdivision);
    				calculateValue(area.getArea1());
    				calculateValue(area.getArea2());
    				area.unSubdivide();
                    System.out.println("Area value: " + area.getValue());
    			}
    		}
    		else {
    			return area.getValue();
    		}
    	}
    	return 0.0;
    }

    /**
     * If the area can be subdivided, then for each possible subdivision, subdivide the area, calculate the subdivisions
     * for the two new areas, and then un-subdivide the area
     *
     * @param area The area to calculate the subdivisions for.
     * @return The number of subdivisions.
     */
    public int calculateSubdivisions(Area area) {
    	if(area != null) {
    		if(area.canSubdivide()) {
    			for(Subdivision subdivision : area.getPossibleSubdivisions()) {
    				area.subdivide(subdivision);
    				calculateSubdivisions(area.getArea1());
    				calculateSubdivisions(area.getArea2());
    				area.unSubdivide();
    			}
    		}
    		else {
    			return subdivisions++;
    		}
    	}
    	return 0;
    }

    public static void main(String[] args) {
        Land land = new Land(100, 100,20,10,30);
        BruteForceApproach bruteForceApproach = new BruteForceApproach(land);
        bruteForceApproach.solve();
        bruteForceApproach.calculateValue(land.getArea());
        bruteForceApproach.calculateSubdivisions(land.getArea());
    }








}