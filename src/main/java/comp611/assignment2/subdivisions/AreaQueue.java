package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.land.Area;

import java.util.Map;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class AreaQueue<E, F> implements Runnable {

    private final Queue<E> queue;
    private final Map<E, F> map;

    public AreaQueue(Queue<E> queue, Map<E, F> map) {
        this.queue = queue;
        this.map = map;
    }

    public void add(Area area) {
        queue.add((E) area);
        map.put((E) area, (F) area);
    }

    public Area remove() {
        return (Area) queue.remove();
    }

    @Override
    public void run() {
        while(!queue.isEmpty()) {
            Area area = remove();
            if(area != null) {
//                area.subdivide();
            }
        }
//        // TODO Auto-generated method stub
//        //add to queue
//        queue.add((E) this);
//        //add to map
//        map.put((E) this, (F) this);
    }
}
