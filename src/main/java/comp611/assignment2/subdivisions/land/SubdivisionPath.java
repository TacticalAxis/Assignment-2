package comp611.assignment2.subdivisions.land;


import java.util.ArrayList;
import java.util.List;

public class SubdivisionPath {

    private final List<SubdivisionCombo> combinations;

    public SubdivisionPath() {
        this.combinations = new ArrayList<>();
    }

    public void add(Subdivision subdivision, AreaSelector selector) {
        combinations.add(new SubdivisionCombo(subdivision, selector));
    }

    public List<SubdivisionCombo> getCombinations() {
        return combinations;
    }

    public int getLength() {
        return combinations.size();
    }

    public SubdivisionCombo get(int index) {
        return combinations.get(index);
    }

    public SubdivisionCombo getLast() {
        return combinations.get(combinations.size() - 1);
    }

    private static class SubdivisionCombo {

        private final Subdivision subdivision;
        private final AreaSelector areaSelector;

        public SubdivisionCombo(Subdivision subdivision, AreaSelector areaSelector) {
            this.subdivision = subdivision;
            this.areaSelector = areaSelector;
        }

        public Subdivision getSubdivision() {
            return subdivision;
        }

        public AreaSelector getAreaSelector() {
            return areaSelector;
        }
    }

    enum AreaSelector {
        AREA_1,
        AREA_2
    }
}
