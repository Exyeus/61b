public class ArrayDeque<T> {
    /**
     * add and remove must take constant time,
     * except during resizing operations.
     * get and size must take constant time.
     * The starting size of your array should be __8__.
     * The amount of memory that your program uses
     * at any given time must be proportional to the number of items.
     * We strongly recommend that you treat your array as circular
     *  for this exercise. In other words, if your front pointer
     *  is at position zero, and you addFirst, the front pointer
     *  should loop back around to the end of the array (so the
     *  new front item in the deque will be the last item in the
     *  underlying array).
     * This will result in far fewer headaches than non-circular approaches.
     */
    private T[] data;
    private int size;
    private int itemSum;
    private int rightExtendIndex;
    private int leftExtendIndex;

    public ArrayDeque() {
        size = 8;
        data = (T[]) new Object[size];
        itemSum = 0;

        /* Index can be mod8 to circulate!
        * left will decrease will be mod with 8
        * right will increase will be mod with 8
        * Alright, the 2 situations can be judged with the 2 indices!
        * */
        rightExtendIndex = 4;
        leftExtendIndex = 3;
    }

    /**
     * The resizing implementation plan:
     * I will set the first item at the middle of the array
     * addFirst() will put the items on its left
     * addLast() will put the items on its right
     * From the middle, there could only be a gap point on *1* side
     * If it is on the left side, then the  structure will be:
     *       Right | Left Right
     * If it is on the right side, then:
     *       Left Right | Left
     * Enlarge the array by 2 in size will be definitely sufficient!
     * I can just concatenate the *circled* part !
     */
    private void sizeModifier() {
        // remember to update all the attributes!
        // The following code are for enlarging this array!


        // The following code are for shrinking this array!
        if (itemSum * 4 <= size
                && size >= 16) {
            T[] tempNew = (T[]) new Object[size / 2];
            System.arraycopy(data, leftExtendIndex, tempNew,
                    leftExtendIndex - size / 4, itemSum);

            // update all the parameters!
            this.data = tempNew;
            this.size = this.size / 2;
            this.rightExtendIndex = this.rightExtendIndex
                    - this.size / 4;
            this.leftExtendIndex = this.leftExtendIndex
                    - this.size / 4;
        }
        // there's no need to enlarge the size!

        else {
            // the array cannot be filled with anything else!
            T[] tempNew = (T[]) new Object[2 * size];
            if (rightExtendIndex < leftExtendIndex) {
                // One side exceeds!
                if (rightExtendIndex < (size / 2)) {
                    // Right exceeds!
                    // Copying left ones
                    System.arraycopy(data, leftExtendIndex,
                            tempNew, size/2 + leftExtendIndex,
                            size / 2 - leftExtendIndex);
                    // Copy right ones
                    System.arraycopy(data, size / 2, tempNew, size, size/2);
                    // exceeding right ones
                    System.arraycopy(data, 0, tempNew, (size / 2) * 3,
                            rightExtendIndex+1);

                    this.leftExtendIndex = size / 2 + this.leftExtendIndex;
                    this.rightExtendIndex = (size / 2) * 3 + this.rightExtendIndex;

                } else if (leftExtendIndex > (size / 2)) {
                    // Left exceeds
                    System.arraycopy(data, size / 2, tempNew, size,
                            rightExtendIndex - size / 2 + 1);
                    System.arraycopy(data, 0, tempNew, size / 2,
                            size / 2);
                    System.arraycopy(data, leftExtendIndex, tempNew,
                            (size / 2) - (size - rightExtendIndex), size - rightExtendIndex);

                    this.rightExtendIndex = this.rightExtendIndex + this.size / 2;
                    this.leftExtendIndex = this.size / 2 + (this.size - leftExtendIndex);

                }
            }else if (rightExtendIndex > leftExtendIndex) {
                System.arraycopy(data, leftExtendIndex, tempNew,
                        leftExtendIndex + (size / 2), itemSum);

                this.leftExtendIndex = this.leftExtendIndex + this.size / 2;
                this.rightExtendIndex = this.rightExtendIndex + this.size / 2;
            }

            this.size = this.size * 2;
            this.data = tempNew;
        }

    }
    public void addFirst(T item) {
        if (itemSum == size){
            sizeModifier();
        }
        data[leftExtendIndex] = item;
        leftExtendIndex = (leftExtendIndex - 1 + size) % size;
        itemSum += 1;
    }

    public void addLast(T item) {
        if (itemSum == size){
            sizeModifier();
        }
        data[rightExtendIndex] = item;
        rightExtendIndex = (rightExtendIndex + 1) % size;
        itemSum += 1;
    }

    public boolean isEmpty() {
        return itemSum == 0;
    }

    public int size() {
        return this.itemSum;
    }

    public void printDeque() {
        for (int start = leftExtendIndex; start <= this.size + rightExtendIndex; start++) {
            System.out.print(data[(start) % this.size] + " ");
        }
    }


    public T removeFirst() {
        if (itemSum == 0){
            System.out.print("No more elements for removal");
            return null;
        }
        T result = data[leftExtendIndex];
        leftExtendIndex = (leftExtendIndex + 1) % size;
        itemSum -= 1;
        return result;
    }

    public T removeLast() {
        if (itemSum == 0){
            System.out.print("No more elements for removal");
            return null;
        }
        T result = data[rightExtendIndex];
        rightExtendIndex = (rightExtendIndex - 1 + size) % size;
        itemSum -= 1;
        return result;
    }

    public T get(int index) {
        if ((leftExtendIndex + index) % size < rightExtendIndex) {
            return data[leftExtendIndex + index];
        }else{
            System.out.print("Index out of range");
            return null;
        }
    }

}
