package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class ExactApproach extends Approach {

    private Area bestArea;

    public Set<Subdivision> allSubdivisions;

    public ExactApproach(Land land) {
        super(land, "Brute Force Approach");
        this.bestArea = land.getArea().copy();
        this.allSubdivisions = new HashSet<>();
    }

    public static void main(String[] args) {
        ExactApproach exactApproach = new ExactApproach(new Land(6, 1, 20));
        Result solution = exactApproach.solve();
        if (solution != null) {
            System.out.println("Bruteforce Solution Found: " + solution.getValue());
            System.out.println("This took " + exactApproach.getTime() + "ms");
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

        // check if area value
        if (eval(area.getRoot()) > eval(bestArea)) {
            bestArea = area.getRoot().copy();
        }

        // check area is big enough to subdivide
        if (area.getWidth() < 1 || area.getHeight() < 1) {
            return;
        }

        // breadth-first search
        Set<Subdivision> areaSubdivisions = area.getPossibleSubdivisions().keySet();
        for(Subdivision areaSub : areaSubdivisions) {
            // add subdivision
            area.subdivide(areaSub);

            System.out.println(area.getRoot());

            Area a1 = area.getArea1();
            Area a2 = area.getArea2();

            // get possible subdivisions for both areas
            Set<Subdivision> a1subs = a1.getPossibleSubdivisions().keySet();
            Set<Subdivision> a2subs = a2.getPossibleSubdivisions().keySet();

            // check left/upper area
            for(Subdivision area1sub : a1subs) {
                incrementSubdivisions();
                a1.subdivide(area1sub);

                // check right/lower area
                for(Subdivision area2sub : a2subs) {
                    incrementSubdivisions();
                    a2.subdivide(area2sub);

                    // check if area value
                    if(eval(a2.getRoot()) > eval(bestArea)) {
                        bestArea= a2.getRoot().copy();
                    }

                    // check if area value
                    if(eval(a1.getRoot()) > eval(bestArea)) {
                        bestArea= a1.getRoot().copy();
                    }

                    // recurse
                    findSub(a1);
                    findSub(a2);

                    // undo subdivision
                    a2.unSubdivide();
                }

                // undo subdivision
                a1.unSubdivide();
            }

            // undo subdivision
            area.unSubdivide();
        }

        // depth-first search
        for(Subdivision areaSub : areaSubdivisions) {
            area.subdivide(areaSub);

            if(eval(area.getRoot()) > eval(bestArea)) {
                bestArea= area.getRoot().copy();
            }

            findSub(area.getArea1());
            findSub(area.getArea2());

            area.unSubdivide();
        }
    }

    public Set<Subdivision> getAllSubdivisions() {
        return allSubdivisions;
    }
}