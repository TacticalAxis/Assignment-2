package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.ArrayList;
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
        return currentLandValue;
    }

    //recursive method to find all the subdivisions
    private void findSubDivisions(Area area) {
        //get all the subdivisions
        //loop through all the subdivisions
        for(Subdivision sub : area.getPossibleSubdivisions().keySet()) {
            //subdivide the area
            area.subdivide(sub);
            //increment the subdivisions
            incrementSubdivisions();
            //add the subdivision to the hashmap
            subMap.put(sub, subdivisions);
            //check if the value is greater than the current value
            if(area.getLand().getValue() > currentLandValue) {
                //if it is, set the current value to the new value
                currentLandValue = (int) area.getLand().getValue();
                //call the recursive method again
                findSubDivisions(area.getArea1());
                findSubDivisions(area.getArea2());
            } else {
                //if it is not, unSubdivide the area
                area.unSubdivide();
                //remove the subdivision from the hashmap
                subMap.remove(sub);
                //decrement the subdivisions
                subdivisions--;
            }
        }
    }

    //method to get the solution
    private Area getSolution() {
        //get the area
        Area area = getLand().getArea();
        //loop through the hashmap
        for(Subdivision sub : subMap.keySet()) {
            //compare the area with the best area found
            if(area.getValue() > currentLandValue) {
                //if it is, return the area
                return area;
            } else {
                //if it is not, subdivide the area
                area.subdivide(sub);
                //call the recursive method again
                area = getSolution();
            }
        }
        //return the area
        return area;


//        //get the area
//        Area area = getLand().getArea();
//        //loop through the hashmap
//        for(Subdivision sub : subMap.keySet()) {
//            //subdivide the area
//            area.subdivide(sub);
//        }
//        //return the area
//        return area;
    }


    public static void main(String[] args) {
        ExactApproach exactApproach = new ExactApproach(new Land(6, 10, 70, 20,1000));
        Result solution = exactApproach.solve();
        if(solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + exactApproach.getTime() + "s");
            System.out.println("Subdivisions Found: " + exactApproach.getSubdivisions());
            System.out.println("Solution:\n" + exactApproach.getLand());

        }

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LandGUI(exactApproach, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
