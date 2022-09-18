package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.List;

public class BruteForceApproach extends Approach {

    private Area bestArea;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
        this.bestArea = land.getArea().copy();
    }

    @Override
    public Result solve() {
        // reset complete flag
        setComplete(false);

        // start timer
        startTimer();

        // start recursive search
        findSub(getLand().getArea());

        getLand().setArea(bestArea);

        // stop timer
        stopTimer();

        // set complete
        setComplete(true);

        if(bestArea != null) {
            return new Result(this, bestArea.getLand().getValue(), getSubdivisions());
        } else {
            return null;
        }
    }

    @Override
    public double getBestValue() {
        return 0;
    }

    // single-level
    private void findSub(Area area) {
        // check area not null
        if (area == null) {
            return;
        }

        System.out.println("Value: " + eval(area.getRoot()));
        if (eval(area.getRoot()) > eval(bestArea)) {
            bestArea = area.getRoot().copy();
        }

        // check area is big enough to subdivide
        if (area.getWidth() < 2 || area.getHeight() < 2) {
            return;
        }

        // get all possible subdivisions
        List<Area> areas = area.getSubAreas();
        for(Area subArea : areas) {
//            findSub(subArea);
            for (Subdivision sub : subArea.getPossibleSubdivisions().keySet()) {
                // subdivide
                subArea.subdivide(sub);
                incrementSubdivisions();

                // recurse
                findSub(subArea);
//                findSub(area.getArea1());
//                findSub(area.getArea2());

                // un-subdivide
                subArea.unSubdivide();
            }
        }
//        for (Subdivision sub : area.getPossibleSubdivisions().keySet()) {
//            // subdivide
//            area.subdivide(sub);
//            incrementSubdivisions();
//
//            // recurse
//            findSub(area.getArea1());
//            findSub(area.getArea2());
//
//            // un-subdivide
//            area.unSubdivide();
//        }
    }

    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 6, 20, 20,1000));
        Result solution = bruteForceApproach.solve();
        if(solution != null) {
            System.out.println("Bruteforce Solution Found: " + solution.getValue());
            System.out.println("This took " + bruteForceApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + bruteForceApproach.getSubdivisions());
            System.out.println("Solution:\n" + bruteForceApproach.getLand());
        }

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LandGUI(bruteForceApproach, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}