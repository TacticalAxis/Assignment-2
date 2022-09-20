package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.*;

public class OLDExactApproach extends Approach {

    private Area bestArea;

    private final Set<SubdividedArea> areaValues;

    public OLDExactApproach(Land land) {
        super(land, "Exact Approach");
        this.bestArea = land.getArea().copy();
        this.areaValues = new HashSet<>();
    }

    private class SubdividedArea {
        private final int a1h;
        private final int a1w;
        private final int sL;

        private final int a2h;
        private final int a2w;

        private final double value;

        public SubdividedArea(int a1h, int a1w, int sL, int a2h, int a2w) {
            this.a1h = a1h;
            this.a1w = a1w;

            this.sL = sL;

            this.a2h = a2h;
            this.a2w = a2w;

            double v1 = getLand().getLandValue().getValue(a1w, a1h);
            double v2 = getLand().getLandValue().getValue(a2w, a2h);
            this.value = v1 + v2 - getLand().getSubValue() * sL;
        }

        public double getValue() {
            return value;
        }

        public boolean check(int a1h, int a1w, int subLength, int a2h, int a2w) {
            return this.a1h == a1h && this.a1w == a1w && this.sL == subLength && this.a2h == a2h && this.a2w == a2w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SubdividedArea that = (SubdividedArea) o;
            return a1h == that.a1h && a1w == that.a1w && a2h == that.a2h && a2w == that.a2w && Double.compare(that.value, value) == 0 && sL == that.sL;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a1h, a1w, sL, a2h, a2w, value);
        }
    }

    public boolean check(int a1h, int a1w, int subLength, int a2h, int a2w) {
        return areaValues.contains(new SubdividedArea(a1h, a1w, subLength, a2h, a2w));
    }

    public static void main(String[] args) {
        OLDExactApproach exactApproach = new OLDExactApproach(new Land(6, 3, 20));
        Result solution = exactApproach.solve();
        if (solution != null) {
            System.out.println("Exact Solution Found: " + solution.getValue());
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
        if (area.getWidth() < 2 || area.getHeight() < 2) {
            return;
        }

        // breadth-first search
        Set<Subdivision> areaSubdivisions = area.getPossibleSubdivisions().keySet();
        for(Subdivision areaSub : areaSubdivisions) {
            // add subdivision
            area.subdivide(areaSub);

            // check
            Area a1 = area.getArea1();
            Area a2 = area.getArea2();

            if(check(a1.height, a1.width, areaSub.getLength(), a2.height, a2.width)) {
                area.unSubdivide();
                continue;
            } else {
                areaValues.add(new SubdividedArea(a1.height, a1.width, areaSub.getLength(), a2.height, a2.width));
            }

            // get possible subdivisions for both areas
            Set<Subdivision> a1subs = a1.getPossibleSubdivisions().keySet();
            Set<Subdivision> a2subs = a2.getPossibleSubdivisions().keySet();

            // check left/upper area
            for(Subdivision area1sub : a1subs) {
                incrementSubdivisions();
                a1.subdivide(area1sub);

                Area a1a1 = a1.getArea1();
                Area a1a2 = a1.getArea2();

                if(check(a1a1.height, a1a1.width, area1sub.getLength(), a1a2.height, a1a2.width)) {
                    a1.unSubdivide();
                    continue;
                } else {
                    areaValues.add(new SubdividedArea(a1a1.height, a1a1.width, area1sub.getLength(), a1a2.height, a1a2.width));
                }

                // check right/lower area
                for(Subdivision area2sub : a2subs) {
                    incrementSubdivisions();
                    a2.subdivide(area2sub);

                    Area a2a1 = a2.getArea1();
                    Area a2a2 = a2.getArea2();

                    if(check(a2a1.height, a2a1.width, area2sub.getLength(), a2a2.height, a2a2.width)) {
                        a2.unSubdivide();
                        continue;
                    } else {
                        areaValues.add(new SubdividedArea(a2a1.height, a2a1.width, area2sub.getLength(), a2a2.height, a2a2.width));
                    }

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
}