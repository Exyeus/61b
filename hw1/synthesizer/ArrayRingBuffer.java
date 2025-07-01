//  Make sure to make this class a part of the synthesizer package
package synthesizer;
// import java.util.Iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Make sure to make this class and all of its methods public
// Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T>{
        private int wizPos;

        public ArrayRingBufferIterator() {
            wizPos = first;
        }

        @Override
        public boolean hasNext() {
            return wizPos < fillCount;
        }

        @Override
        public T next() {
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            T returnItem = rb[wizPos];
            wizPos = (wizPos + 1) % capacity;
            return returnItem;
        }
    }
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        //  Create new array with capacity elements. ok
        //       first, last, and fillCount should all be set to 0. ok
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        //  Enqueue the item. Don't forget to increase fillCount and update last.
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        } else {
            fillCount += 1;
            rb[last] = x;
            last = (last + 1) % capacity;
        }

    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        //  Dequeue the first item. Don't forget to decrease fillCount and update
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T dequeResult = rb[first];
        first = (first + 1) % capacity;
        fillCount -= 1;
        return dequeResult;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        //  Return the first item. None of your instance variables should change.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

    //  When you get to part 5, implement the needed code to support iteration.
}
