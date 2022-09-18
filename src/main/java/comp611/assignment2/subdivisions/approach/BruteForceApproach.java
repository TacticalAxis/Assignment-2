package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.Set;

public class BruteForceApproach extends Approach {

    private Area bestArea;

    public BruteForceApproach(Land land) {
        super(land, "Brute Force Approach");
        this.bestArea = land.getArea().copy();
    }

    public static void main(String[] args) {
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 6, 20, 20, 1000));
        Result solution = bruteForceApproach.solve();
        if (solution != null) {
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

        if (bestArea != null) {
            return new Result(this, eval(bestArea), getSubdivisions());
        } else {
            return null;
        }
    }

    @Override
    public double getBestValue() {
        return eval(bestArea);
    }

    // single-level
    private void findSub(Area area) {
        // check area not null
        if (area == null) {
            return;
        }

//        System.out.println("Value: " + eval(area.getRoot()));
        if (eval(area.getRoot()) > eval(bestArea)) {
            bestArea = area.getRoot().copy();
        }

        // check area is big enough to subdivide
        if (area.getWidth() < 2 || area.getHeight() < 2) {
            return;
        }

        Set<Subdivision> areaSubdivisions = area.getPossibleSubdivisions().keySet();
        for(Subdivision areaSub : areaSubdivisions) {
            area.subdivide(areaSub);

            Area a1 = area.getArea1();
            Area a2 = area.getArea2();

            Set<Subdivision> a1subs = a1.getPossibleSubdivisions().keySet();
            Set<Subdivision> a2subs = a2.getPossibleSubdivisions().keySet();

            for(Subdivision area1sub : a1subs) {
                incrementSubdivisions();
                a1.subdivide(area1sub);

                for(Subdivision area2sub : a2subs) {
                    incrementSubdivisions();
                    a2.subdivide(area2sub);

                    if(eval(a2.getRoot()) > eval(bestArea)) {
                        bestArea= a2.getRoot().copy();
                    }

                    if(eval(a1.getRoot()) > eval(bestArea)) {
                        bestArea= a1.getRoot().copy();
                    }

                    findSub(a1);
                    findSub(a2);

                    a2.unSubdivide();
                }

                a1.unSubdivide();
            }

            area.unSubdivide();
        }
    }
}