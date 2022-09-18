package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.HashMap;

@SuppressWarnings("unused")
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
        System.out.println("Starting off with value: " + getLand().getValue());
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
        System.out.println("Best: " + sub);
        if (sub != null) {
//            System.out.println("Before: " + area.getLand().getValue());
            area.subdivide(sub);
            if(area.getLand().getValue() > getBestValue()) {
//                System.out.println("After: " + area.getLand().getValue());
//                sub(area);
                incrementSubdivisions();
                currentLandValue = getLand().getValue();
                sub(area.getArea1());
                sub(area.getArea2());
            } else {
//                System.out.println("After: " + area.getLand().getValue());
                area.unSubdivide();
            }
//            System.out.println("After: " + area.getLand().getValue() + "\n");

        }
    }

    private Subdivision findBest(Area area) {
        if(area != null) {
            Subdivision best = null;
            HashMap<Subdivision, Double> subValues = area.getPossibleSubdivisions();
            System.out.println("Possible: " + subValues);
            // get the subdivision with the highest value double
            for (Subdivision sub : subValues.keySet()) {
                if (best == null || subValues.get(sub) > subValues.get(best)) {
                    best = sub;
                }
            }
//            List<Subdivision> subs = area.getPossibleSubdivisions();
//            double currentLandValue = getLand().getValue();
//            for (Subdivision sub : subs) {
//                area.subdivide(sub);
//                if(getLand().getValue() >= currentLandValue) {
//                    best = sub;
//                    currentLandValue = getLand().getValue();
//                }
//                area.unSubdivide();
//            }
            return best;
        }

        return null;
    }

    public static void main(String[] args) {
        GreedyApproach greedyApproach = new GreedyApproach(new Land(10, 8, 50, 20,1000));
        Result solution = greedyApproach.solve();
        if(solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "s");
            System.out.println("Subdivisions Found: " + greedyApproach.getSubdivisions());
            System.out.println("Solution:\n" + greedyApproach.getLand());
        }

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LandGUI(greedyApproach, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}