package dsassignment;

import java.util.LinkedList;

public class Queue<T extends Comparable<T>> {
    private LinkedList<T> list = new LinkedList<>();
    
    public void enqueue(T e) {
        list.addLast(e);
    }
    
    public T dequeue() {
        return list.removeFirst();
    }

    @Override
    public String toString() {
        return "Queue: " + list.toString();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int getSize() {
        return list.size();
    }
    
    public void clear() {
        list.clear();
    }
    
    public T peek() {
        return list.peek();
    }
}
