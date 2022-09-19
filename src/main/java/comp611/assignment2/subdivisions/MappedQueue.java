package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.land.Area;

import java.util.Queue;

public class MappedQueue implements Runnable{
    private Queue<Area> queue;

    public MappedQueue(Queue<Area> queue) {
        this.queue = queue;
    }


    public void add(Area area) {
        queue.add(area);
    }

    @Override
    public void run() {
        

    }
}
