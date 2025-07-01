package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    // <T> decorator needed everywhere?
    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return capacity;
    }
    public int fillCount() {
        return fillCount;
    }
}
