package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    int capacity();
    int fillCount();
    void enqueue(T x);
    // Add x to the end
    T dequeue();
    // Delete and return item from the front
    T peek();
    // Return but do not delete the item from the front
    default boolean isEmpty() {
        return fillCount() == 0;
    }
    default boolean isFull() {
        return capacity() == fillCount();
    }
}
