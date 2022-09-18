package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.Map;

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
        if (sub != null) {
            area.subdivide(sub);
            if(area.getLand().getValue() > getBestValue()) {
                incrementSubdivisions();
                currentLandValue = getLand().getValue();
                sub(area.getArea1());
                sub(area.getArea2());
            } else {
                area.unSubdivide();
            }
        }
    }

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

    public static void main(String[] args)  {
        GreedyApproach greedyApproach = new GreedyApproach(new Land(6, 6, 20, 20,1000));
        Result solution = greedyApproach.solve();
        if(solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "ms");
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