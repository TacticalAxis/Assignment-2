package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class ExactApproach extends Approach {

    private int currentLandValue;
    private int subdivisions;
    private HashMap<Subdivision, Integer> subMap = new HashMap<>();
    public ExactApproach(Land area) {
        super(area, "Exact Approach");
    }

    @Override
    public Result solve() {
        startTimer();

        //recursive search
        findSubDivisions(getLand().getArea());

        stopTimer();

        //call the solution method
        Area area = getSolution();
        if(area != null) {
            //solution found
            getLand().setArea(area);

            //return result
            return new Result(this, area.getLand().getValue(), getSubdivisions());
        }




        //no solution found
        return null;
    }

    @Override
    public double getBestValue() {
        return 0;
    }

    //create subdivide method
    private Area findSubDivisions(Area area){
        //get all possible subDivisions
        List<Subdivision> subDivisions = area.getSubdivisions();
        //iterate through all possible subDivisions
        for(Subdivision sub : subDivisions){
            //subdivide the area
            area.subdivide(sub);
            //increment the number of subdivisions
            incrementSubdivisions();
            //check if the value of the land is greater than the current value
            if(area.getLand().getValue() > currentLandValue){
                //if it is, set the current value to the new value
                currentLandValue = (int) area.getLand().getValue();
                //add the subdivision to the hashmap
                subMap.put(sub, currentLandValue);
                //call the method again
                findSubDivisions(area.getArea1());
                findSubDivisions(area.getArea2());
            } else {
                //if the value is not greater than the current value, unSubdivide the area
                area.unSubdivide();
            }
        }
        return area;

    }

    //create a method to find the best value
    private Area getSolution(){
        //returns the value stored in the hashmap
        if(subMap.containsValue(currentLandValue)){
            return getLand().getArea();
        }
        return null;

    }


//    private Subdivision getBestSub() {
//        //iterate through the hashmap
//        Iterator<Subdivision> iterator = subMap.keySet().iterator();
//        Subdivision bestSub = null;
//        while(iterator.hasNext()){
//            //get the next subdivision
//            Subdivision sub = iterator.next();
//            //if the bestSub is null, set it to the first subdivision
//            if(bestSub == null){
//                bestSub = sub;
//            } else {
//                //if the value of the subdivision is greater than the value of the bestSub, set the bestSub to the subdivision
//                if(subMap.get(sub) > subMap.get(bestSub)){
//                    bestSub = sub;
//                }
//            }
//        }
//        //return the bestSub
//        return bestSub;
//    }

    public static void main(String[] args) {
        ExactApproach exactApproach = new ExactApproach(new Land(6, 3, 50, 20,1000));
        Result solution = exactApproach.solve();
        if(solution != null) {
//            System.out.println("Greedy Solution Found: " + solution.getValue());
//            System.out.println("This took " + exactApproach.getTime() + "s");
//            System.out.println("Subdivisions Found: " + exactApproach.getSubdivisions());
//            System.out.println("Solution:\n" + exactApproach.getLand());
            System.out.println("Solution for best value: " + exactApproach.getBestValue());
        }

//        JFrame frame = new JFrame("Land GUI");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        frame.getContentPane().add(new LandGUI(exactApproach, frame));
//        frame.pack();
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
    }


}
