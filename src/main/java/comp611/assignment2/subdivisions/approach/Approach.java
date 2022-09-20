package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;

public abstract class Approach {

    // approach name
    private final String name;
    // store encapsulating land
    private final Land land;
    // store completion status
    private boolean complete;
    // store number of subdivisions made
    private int subdivisions;

    // start time
    private long startTime;
    // end time
    private long endTime;

    public Approach(Land area, String name) {
        this.land = area;
        this.complete = false;
        this.name = name;
        this.startTime = 0;
        this.endTime = 0;
        this.subdivisions = 0;
    }

    // subdivision incrementer
    public synchronized void incrementSubdivisions() {
        subdivisions++;
    }

    // get subdivisions
    public synchronized int getSubdivisions() {
        return subdivisions;
    }

    // abstract functions
    public abstract Result solve();
    public abstract double getBestValue();

    public Land getLand() {
        return land;
    }

    public String getName() {
        return name;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public double eval(Area area) {
        return getLand().getValue(area);
    }

    // get current time
    public synchronized double getCurrentTime() {
        return Math.round(((System.nanoTime() - startTime) / 1000000.0) * 100.0) / 100.0;
    }

    public double getTime() {
        return Math.round((endTime - startTime) / 1000000.0 * 100.0) / 100.0;
    }

    public boolean isComplete() {
        return complete;
    }

    public String toString() {
        return name + ": hash{" + this.hashCode() + "}";
    }

    public static class Result {
        private final Approach approach;
        private final double value;
        private final int subdivisions;

        public Result(Approach approach, double value, int subdivisions) {
            this.approach = approach;
            this.value = value;
            this.subdivisions = subdivisions;
        }

        public int getSubdivisions() {
            return subdivisions;
        }

        public Approach getApproach() {
            return approach;
        }

        public Object getValue() {
            return value;
        }

        public double getTime() {
            return approach.getTime();
        }
    }
}
