package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("DuplicatedCode")
public class ExactApproach extends Approach {

    private final HashMap<AreaSet, Double> areas;
    private Area bestArea;

    public ExactApproach(Land land) {
        super(land, "Exact Approach");
        this.bestArea = land.getArea().copy();
        this.areas = new HashMap<>();
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

        return (bestArea != null) ? new Result(this, eval(bestArea), getSubdivisions()) : null;
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
        for (Subdivision areaSub : areaSubdivisions) {
            // add subdivision
            area.subdivide(areaSub);

            Area a1 = area.getArea1();
            Area a2 = area.getArea2();

            AreaSet areaSet = new AreaSet(a1.getHeight(), a1.getWidth(), a2.getHeight(), a2.getWidth(), areaSub.getLength());
            if (areas.containsKey(areaSet)) {
                area.unSubdivide();
                continue;
            } else {
                areas.put(areaSet, eval(area));
            }

            // get possible subdivisions for both areas
            Set<Subdivision> a1subs = a1.getPossibleSubdivisions().keySet();
            Set<Subdivision> a2subs = a2.getPossibleSubdivisions().keySet();

            // check left/upper area
            for (Subdivision area1sub : a1subs) {
                incrementSubdivisions();
                a1.subdivide(area1sub);

                Area a1a1 = a1.getArea1();
                Area a1a2 = a1.getArea2();

                AreaSet areaSet1 = new AreaSet(a1a1.getHeight(), a1a1.getWidth(), a1a2.getHeight(), a1a2.getWidth(), area1sub.getLength());
                if (areas.containsKey(areaSet1)) {
                    a1.unSubdivide();
                    continue;
                } else {
                    areas.put(areaSet1, eval(a1));
                }

                // check right/lower area
                for (Subdivision area2sub : a2subs) {
                    incrementSubdivisions();
                    a2.subdivide(area2sub);

                    Area a2a1 = a2.getArea1();
                    Area a2a2 = a2.getArea2();

                    AreaSet areaSet2 = new AreaSet(a2a1.getHeight(), a2a1.getWidth(), a2a2.getHeight(), a2a2.getWidth(), area2sub.getLength());
                    if (areas.containsKey(areaSet2)) {
                        a2.unSubdivide();
                        continue;
                    } else {
                        areas.put(areaSet2, eval(a2));
                    }

                    // check if area value
                    if (eval(a2.getRoot()) > eval(bestArea)) {
                        bestArea = a2.getRoot().copy();
                    }

                    // check if area value
                    if (eval(a1.getRoot()) > eval(bestArea)) {
                        bestArea = a1.getRoot().copy();
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
        for (Subdivision areaSub : areaSubdivisions) {
            area.subdivide(areaSub);

            Area a1 = area.getArea1();
            Area a2 = area.getArea2();

            AreaSet areaSet = new AreaSet(a1.getHeight(), a1.getWidth(), a2.getHeight(), a2.getWidth(), areaSub.getLength());
            if (areas.containsKey(areaSet)) {
                area.unSubdivide();
                continue;
            } else {
                areas.put(areaSet, eval(area));
            }

            if (eval(area.getRoot()) > eval(bestArea)) {
                bestArea = area.getRoot().copy();
            }

            findSub(area.getArea1());
            findSub(area.getArea2());

            area.unSubdivide();
        }
    }

    private static class AreaSet {
        private final int a1h;
        private final int a1w;
        private final int a2h;
        private final int a2w;
        private final int subLength;

        public AreaSet(int a1h, int a1w, int a2h, int a2w, int subLength) {
            this.a1h = a1h;
            this.a1w = a1w;
            this.a2h = a2h;
            this.a2w = a2w;
            this.subLength = subLength;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AreaSet areaSet = (AreaSet) o;
            return a1h == areaSet.a1h && a1w == areaSet.a1w && a2h == areaSet.a2h && a2w == areaSet.a2w && subLength == areaSet.subLength;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a1h, a1w, a2h, a2w, subLength);
        }

        @Override
        public String toString() {
            return "AreaSet{" +
                    "a1h=" + a1h +
                    ", a1w=" + a1w +
                    ", a2h=" + a2h +
                    ", a2w=" + a2w +
                    ", subLength=" + subLength +
                    '}';
        }
    }
}