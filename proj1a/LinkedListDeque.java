public class LinkedListDeque<T> {
    /**
     *add and remove operations must not involve
     * any looping or recursion. A single such operation
     * must take “constant time”, i.e. execution time
     * should not depend on the size of the deque.
     * get must use iteration, not recursion.
     * size must take constant time.
     * The amount of memory that your program uses at any given
     * time must be proportional to the number of items.
     * public LinkedListDeque(): Creates an empty linked list deque.
     * public T getRecursive(int index): Same as get, but uses recursion.
     * You may add any private helper classes or methods in LinkedListDeque.java
     * */
    /**
     * Do sentinel nodes need special definition?
     * That is undoubtedly improper!
     * */

    private final IntNode frontSentinel;
    private final IntNode backSentinel;
    private int size;

    public LinkedListDeque() {
        /*
         * Instantiate a new empty LLD.
         */
        this.frontSentinel = new IntNode(null,
                null, null);
        this.backSentinel = new IntNode(null,
                frontSentinel, null);
        frontSentinel.next = backSentinel;
        backSentinel.prev = frontSentinel;
        this.size = 0;
    }

    private class IntNode {
        public IntNode(T item, IntNode prev, IntNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
        private IntNode prev;
        private T item;
        private IntNode next;
    }
    public void addFirst(T item) {
        IntNode originalNext = frontSentinel.next;
        frontSentinel.next = new IntNode(item, frontSentinel, originalNext);
        originalNext.prev = frontSentinel.next;
        size += 1;
    }
    public void addLast(T item) {
        IntNode originalPrev = backSentinel.prev;
        backSentinel.prev = new IntNode(item, originalPrev, backSentinel);
        originalPrev.next = backSentinel.prev;
        size += 1;
    }
    public boolean isEmpty() {
        return frontSentinel.next == backSentinel
                && backSentinel.prev == frontSentinel;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        IntNode headPointer = frontSentinel.next;
        while (headPointer.next != backSentinel) {
            System.out.print(headPointer.item + " ");
            headPointer = headPointer.next;
        }
        System.out.print(headPointer.item);
    }
    public T removeFirst() {
        if (this.isEmpty()){
            return null;
        }
        T result = frontSentinel.next.item;
        // remember to completely throw away all references in discarding!

        IntNode newNext = frontSentinel.next.next;
        frontSentinel.next = newNext;
        newNext.prev = frontSentinel;
        this.size -= 1;
        return result;
    }
    public T removeLast() {
        if (this.isEmpty()){
            return null;
        }
        T result = backSentinel.prev.item;
        IntNode newPrev = backSentinel.prev.prev;
        backSentinel.prev = newPrev;
        newPrev.next = backSentinel;
        this.size -= 1;
        return result;
    }
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        IntNode startPointer = frontSentinel.next;
        // The first node after frontSentinel!
        while (index > 0) {
            index -= 1;
            startPointer = startPointer.next;
        }
        return startPointer.item;
    }
    public T getRecursive(int index) {
        return recurHelper(frontSentinel.next, index);
    }
    private T recurHelper(IntNode accessObject, int index) {
        if (index < 0 | index >= size) {
            System.out.print("invalid index!");
            return null;
        }
        if (index == 0) {
            return accessObject.item;
        } else {
            return recurHelper(accessObject.next, index - 1);
        }
    }
    // Here the implementation seems to end!
    /* public static void main(String[] args) {
        LinkedListDeque<Integer> A = new LinkedListDeque<Integer>();
        A.addFirst(0);
        A.removeLast();
        A.addFirst(2);
        A.removeFirst();
        A.addLast(4);
        A.get(0);
    } */
}
