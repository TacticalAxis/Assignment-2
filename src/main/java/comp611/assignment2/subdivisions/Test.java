package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.approach.Approach;
import comp611.assignment2.subdivisions.approach.BruteForceApproach;
import comp611.assignment2.subdivisions.approach.ExactApproach;
import comp611.assignment2.subdivisions.approach.GreedyApproach;
import comp611.assignment2.subdivisions.land.Land;

public class Test {

    public static void main(String[] args) {
        // brute force approach, 3x3, with a subdivision value of 20
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(3, 3, 20));
        Approach.Result solution = bruteForceApproach.solve();
        if (solution != null) {
            System.out.println("Bruteforce Solution Found: " + solution.getValue());
            System.out.println("This took " + bruteForceApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + bruteForceApproach.getSubdivisions());
            System.out.println("Solution:\n" + bruteForceApproach.getLand());
        }
        System.out.println("=======================================================================");

        // brute force approach, 6x3, with a subdivision value of 20
        bruteForceApproach = new BruteForceApproach(new Land(6, 3, 20));
        solution = bruteForceApproach.solve();
        if (solution != null) {
            System.out.println("Bruteforce Solution Found: " + solution.getValue());
            System.out.println("This took " + bruteForceApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + bruteForceApproach.getSubdivisions());
            System.out.println("Solution:\n" + bruteForceApproach.getLand());
        }
        System.out.println("=======================================================================");

        // brute force approach, 6x6, with a subdivision value of 20
        bruteForceApproach = new BruteForceApproach(new Land(6, 6, 20));
        solution = bruteForceApproach.solve();
        if (solution != null) {
            System.out.println("Bruteforce Solution Found: " + solution.getValue());
            System.out.println("This took " + bruteForceApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + bruteForceApproach.getSubdivisions());
            System.out.println("Solution:\n" + bruteForceApproach.getLand());
        }
        System.out.println("=======================================================================");

        // greedy approach, 3x3, with a subdivision value of 20
        GreedyApproach greedyApproach = new GreedyApproach(new Land(3, 3, 20));
        solution = greedyApproach.solve();
        if (solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + greedyApproach.getSubdivisions());
            System.out.println("Solution:\n" + greedyApproach.getLand());
        }
        System.out.println("=======================================================================");

        // greedy approach, 6x3, with a subdivision value of 20
        greedyApproach = new GreedyApproach(new Land(6, 3, 20));
        solution = greedyApproach.solve();
        if (solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + greedyApproach.getSubdivisions());
            System.out.println("Solution:\n" + greedyApproach.getLand());
        }
        System.out.println("=======================================================================");

        // greedy approach, 6x6, with a subdivision value of 20
        greedyApproach = new GreedyApproach(new Land(6, 6, 20));
        solution = greedyApproach.solve();
        if (solution != null) {
            System.out.println("Greedy Solution Found: " + solution.getValue());
            System.out.println("This took " + greedyApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + greedyApproach.getSubdivisions());
            System.out.println("Solution:\n" + greedyApproach.getLand());
        }
        System.out.println("=======================================================================");

        // exact approach, 3x3, with a subdivision value of 20
        ExactApproach exactApproach = new ExactApproach(new Land(3, 3, 20));
        solution = exactApproach.solve();
        if (solution != null) {
            System.out.println("Exact Solution Found: " + solution.getValue());
            System.out.println("This took " + exactApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + exactApproach.getSubdivisions());
            System.out.println("Solution:\n" + exactApproach.getLand());
        }
        System.out.println("=======================================================================");

        // exact approach, 6x3, with a subdivision value of 20
        exactApproach = new ExactApproach(new Land(6, 3, 20));
        solution = exactApproach.solve();
        if (solution != null) {
            System.out.println("Exact Solution Found: " + solution.getValue());
            System.out.println("This took " + exactApproach.getTime() + "ms");
            System.out.println("Subdivisions Found: " + exactApproach.getSubdivisions());
            System.out.println("Solution:\n" + exactApproach.getLand());
        }
        System.out.println("=======================================================================");
    }
}