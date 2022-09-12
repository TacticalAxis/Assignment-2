package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.approach.BruteForceApproach;
import comp611.assignment2.subdivisions.approach.ExactApproach;
import comp611.assignment2.subdivisions.land.Land;

public class Solution {

    public static void main(String[] args) {
        // setup timing things

        // bruteForce
        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 3, 50, 20,1000));
        bruteForceApproach.startTimer();
        double solution = bruteForceApproach.solve();
        System.out.println("Brute Force Solution: " + solution);
        System.out.println("This took " + bruteForceApproach.getTime() + "ms");
        System.out.println(bruteForceApproach.getLand());
        bruteForceApproach.stopTimer();

        // greedy

        // exact
        ExactApproach exactApproach = new ExactApproach(new Land(6, 3, 50, 20,1000));
        exactApproach.startTimer();
        solution = exactApproach.solve();
        System.out.println("Exact Solution: " + solution);
        System.out.println("This took " + exactApproach.getTime() + "ms");
        System.out.println(exactApproach.getLand());
        exactApproach.stopTimer();

        // update gui to show results
    }
}