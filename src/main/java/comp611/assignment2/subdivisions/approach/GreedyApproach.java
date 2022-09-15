package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.LandGUI;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;

@SuppressWarnings("unused")
public class GreedyApproach extends Approach {

    public GreedyApproach(Land area) {
        super(area, "Greedy Approach");
    }

    @Override
    public Result solve() {
        // start timer
        startTimer();

        // start search
        sub(getLand().getArea());

        // stop timer
        stopTimer();

        return new Result(this, getLand().getValue(), getSubdivisions());
    }

    //assign a value to each subdivision with a preset value

    //we pass these values of the subdivisions to the greedy algorithm

    //greedy algorithm will find the best subdivision

    //we will then recursively call the function on the two new areas

    //we will keep doing this until the area is not divisible

    //we will then return the best value

    private void sub(Area area) {
        Subdivision sub = findBest(area);
        if (sub != null) {
            area.subdivide(sub);
            incrementSubdivisions();
            sub(area.getArea1());
            sub(area.getArea2());
        }
    }

    private Subdivision findBest(Area area) {
        if(area != null) {
            double best = 0.0d;

            Subdivision bestSub = null;

            for(Subdivision sub : area.getPossibleSubdivisions()) {
                area.subdivide(sub);
                if(area.getLand().getValue() > best) {
                    best = area.getLand().getValue();
                    bestSub = sub;
                }

                area.unSubdivide();
            }

            return bestSub;
        }
        return null;
    }

    public static void main(String[] args) {
        GreedyApproach greedyApproach = new GreedyApproach(new Land(10, 10, 50, 20,1000));
        Result solution = greedyApproach.solve();
        if(solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "s");
            System.out.println("Subdivisions Found: " + greedyApproach.getSubdivisions());
            System.out.println("Solution:\n" + greedyApproach.getLand());
        }

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LandGUI(greedyApproach.getLand(), frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}