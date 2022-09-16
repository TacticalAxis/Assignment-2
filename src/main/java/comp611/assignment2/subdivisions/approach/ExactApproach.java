package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class ExactApproach extends Approach {

    private static ExactApproachLayoutSet permutations;

    public ExactApproach(Land area) {
        super(area, "Exact Approach");
        this.permutations = new ExactApproachLayoutSet(area);
    }

    @Override
    public Result solve() {
        // start timer
        startTimer();

        // start recursive search
        getAllIterations(getLand().getArea());

        // stop timer
        stopTimer();

        // get solution
        Area area = permutations.findBest();
        if(area != null) {
            // solution found
            getLand().setArea(area);

            // return result
            return new Result(this, area.getLand().getValue(), getSubdivisions());
        }

        // no solution found
        return null;
    }

    //
    //get all possible itterations
    public static void getAllIterations(Area area){
        if(area == null){
            return;
        }

        //check if the area is divisible
        if(!area.canSubdivide()){
            return;
        }

        if(area.getRoot().isFullySubdivided()){
            area.getRoot().getSubdivisions();
            return;
        }

//         This is the recursive function that is used to find all possible solutions.
        for(Subdivision sub : area.getSubdivisions()){
            area.subdivide(sub);
            incrementSubdivisions();
            permutations.add(area.getRoot().copy());
            getAllIterations(area.getArea1());
            getAllIterations(area.getArea2());
            area.unSubdivide();
        }


        //find the single most optimal solution
        Area best = permutations.findBest();
        if(best != null) {
            getLand().setArea(best);
        }


    }

    public int getPossibleLayouts() {
        return permutations.size();
    }

    private static class ExactApproachLayoutSet {
        private final List<Area> values;
        private final Land land;

        public ExactApproachLayoutSet(Land land) {
            this.land = land;
            this.values = new java.util.ArrayList<>();
        }

        public int size() {
            return values.size();
        }

        private void add(Area area) {
            if(area == null) {
                return;
            }

            Area toAdd = area.copy();
            for(Area a : values) {
                if(a.compare(toAdd)) {
                    return;
                }
            }

            values.add(toAdd);
        }

//        private Area getSolution() {
//            if (values.isEmpty()) {
//                return null;
//            }
//
//            Area best = values.get(0);
//            for (Area area : values) {
//                if (land.getValue(area) > land.getValue(best)) {
//                    best = area;
//                }
//            }
//
//            return best;
//        }

        public Area findBest() {
            if (values.isEmpty()) {
                return null;
            }

            Area best = values.get(0);
            for (Area area : values) {
                if (land.getValue(area) > land.getValue(best)) {
                    best = area;
                }
            }

            values.clear();
            values.add(best);
            return best;
        }
    }

    public static void main(String[] args) {
        ExactApproach exactApproach = new ExactApproach(new Land(9, 9,50,20,1000));
        Result result = exactApproach.solve();
        if(result != null) {
            System.out.println("Solution found: " + result.getValue());
            System.out.println("Subdivisions: " + exactApproach.getSubdivisions());
            System.out.println("Time: " + exactApproach.getTime() + "ms");
            System.out.println("Possible layouts: " + exactApproach.getPossibleLayouts());
            System.out.println("Solution: " + exactApproach.getLand());
        }

//        JFrame frame = new JFrame("Land GUI");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        frame.getContentPane().add(new LandGUI(exactApproach.getLand(), frame));
//        frame.pack();
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
    }


    //depth first search of the width and the height of the land
    public static class depthFirstSearch{
        private int width;
        private int height;
        private int[][] land;
        private Approach approach;


        public depthFirstSearch(int width, int height){
            this.width = width;
            this.height = height;
        }

        public void search(Area area){
            //search the width
            for(int i = 0; i < width; i++){
                //search the height
                for(int j = 0; j < height; j++){
                    //check if the area is divisible
                    if(!area.canSubdivide()){
                        return;
                    }

                    if(area.getRoot().isFullySubdivided()){
                        area.getRoot().getSubdivisions();
                        return;
                    }

                    // This is the recursive function that is used to find all possible solutions.
                    for(Subdivision sub : area.getSubdivisions()){
                        area.subdivide(sub);
                        approach.incrementSubdivisions();
                        permutations.add(area.getRoot().copy());
                        getAllIterations(area.getArea1());
                        getAllIterations(area.getArea2());
                        area.unSubdivide();
                    }
                }
            }
        }
    }





}
