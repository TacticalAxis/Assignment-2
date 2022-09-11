package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.approach.BruteForceApproach;
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
        bruteForceApproach.stopTimer();

        // greedy

        // exact

        // update gui to show results
    }
}