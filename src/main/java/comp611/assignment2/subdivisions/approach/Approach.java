package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Land;

@SuppressWarnings("unused")
public abstract class Approach {

    private final String name;
    private final Land land;
    private final ApproachTimer timer;
    private boolean complete;

    public Approach(Land area, String name) {
        this.land = area;
        this.complete = false;
        this.timer = new ApproachTimer();
        this.name = name;
    }

    public abstract Result solve();

    public Land getLand() {
        return land;
    }

    public String getName() {
        return name;
    }

    public ApproachTimer getTimer() {
        return timer;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public long getTime() {
        return timer.getTime();
    }

    public boolean isComplete() {
        return complete;
    }

    public String toString() {
        return name + ": hash{" + this.hashCode() + "}";
    }

    public static class ApproachTimer {
        private long startTime;
        private long endTime;

        public void start() {
            startTime = System.nanoTime();
        }

        public void stop() {
            endTime = System.nanoTime();
        }

        public long getTime() {
            return (endTime - startTime) / 1000000;
        }
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

        public long getTime() {
            return approach.getTime();
        }
    }
}
