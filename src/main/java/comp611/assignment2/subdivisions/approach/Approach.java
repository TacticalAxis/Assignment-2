package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

@SuppressWarnings("unused")
public abstract class Approach<E,F> {

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

    public abstract E solve();

    public abstract F findBest(Area area);

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

    public float getTime() {
        return timer.getTime();
    }

    public boolean isComplete() {
        return complete;
    }

    public String toString() {
        return name + ": hash{" + this.hashCode() + "}";
    }

    public abstract Subdivision findBest(Area area);

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
}
