package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.land.Area;

import java.util.HashMap;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class MappedQueue<E,F> implements Runnable{
    private final Queue<E> queue;
    private final HashMap<E,F> map;

    public MappedQueue(Queue<E> queue, HashMap<E, F> map) {
        this.queue = queue;
        this.map = map;
    }


    public void add(Area area) {
        //add to queue
        queue.add((E) area);
        //add to map
        map.put((E) area, (F) area);
    }

    public Area remove() {
        return (Area) queue.remove();
    }

    @Override
    public void run() {
    // TODO Auto-generated method stub
        //add to queue
        queue.add((E) this);
        //add to map
        map.put((E) this, (F) this);

    }
}
