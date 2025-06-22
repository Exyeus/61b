public class ArrayDeque<T> {
        /**
         * add and remove must take constant time,
         * except during resizing operations.
         * get and size must take constant time.
         * The starting size of your array should be 8.
         * The amount of memory that your program uses
         * at any given time must be proportional to the number of items.
         * We strongly recommend that you treat your array as circular.
         */
        private T[] data;
        // 'capacity' is a more descriptive name for the length of the underlying array.
        // The user used 'size', so we will stick to that to avoid confusion.
        private int size; // Represents the capacity of the array
        private int itemSum; // Represents the number of elements in the deque
        private int rightExtendIndex; // Index for the next addLast operation
        private int leftExtendIndex; // Index for the next addFirst operation

        public ArrayDeque() {
            size = 8;
            data = (T[]) new Object[size];
            itemSum = 0;

            /*
             * We start with pointers in the middle to allow growth in both directions.
             * An empty deque is where leftExtendIndex is one position to the left of rightExtendIndex.
             * For example, left=3, right=4.
             */
            rightExtendIndex = 4;
            leftExtendIndex = 3;
        }

        /**
         * Resizes the underlying array to the given capacity.
         * This method "straightens out" the circular deque into a linear one,
         * which is much simpler and more robust than trying to copy wrapped segments.
         * The straightened items will be placed at the beginning of the new array.
         * @param newCapacity The new capacity for the array.
         */
        private void resize(int newCapacity) {
            T[] tempNew = (T[]) new Object[newCapacity];

            // The index of the first logical element in the old circular array.
            // FIX: Correctly find the first element's index by adding 1 to leftExtendIndex.
            int current = (leftExtendIndex + 1) % size;

            // Copy elements from the old array to the new array, straightening them out.
            for (int i = 0; i < itemSum; i++) {
                tempNew[i] = data[current];
                current = (current + 1) % size;
            }

            this.data = tempNew;
            this.size = newCapacity; // Update the capacity

            // After straightening, the elements occupy indices 0 to itemSum - 1.
            // The new left pointer should be at the end of the new array.
            this.leftExtendIndex = newCapacity - 1;
            // The new right pointer is just after the last element.
            this.rightExtendIndex = itemSum;
        }

        public void addFirst(T item) {
            // Enlarge the array if it is full.
            if (itemSum == size) {
                resize(size * 2);
            }

            data[leftExtendIndex] = item;
            // Move the left pointer one step to the left, wrapping around if necessary.
            leftExtendIndex = (leftExtendIndex - 1 + size) % size;
            itemSum += 1;
        }

        public void addLast(T item) {
            // Enlarge the array if it is full.
            if (itemSum == size) {
                resize(size * 2);
            }

            data[rightExtendIndex] = item;
            // Move the right pointer one step to the right, wrapping around if necessary.
            rightExtendIndex = (rightExtendIndex + 1) % size;
            itemSum += 1;
        }

        public boolean isEmpty() {
            return itemSum == 0;
        }

        public int size() {
            return this.itemSum;
        }

        /**
         * Prints the items in the deque from first to last, separated by a space.
         */
        public void printDeque() {
            // FIX: The loop must start from the actual first element and run for itemSum iterations.
            int current = (leftExtendIndex + 1) % size;
            for (int i = 0; i < itemSum; i++) {
                System.out.print(data[current] + " ");
                current = (current + 1) % size;
            }
            System.out.println(); // Add a newline for better formatting.
        }


        public T removeFirst() {
            if (itemSum == 0) {
                // It's better to return null silently for an empty deque as per common implementations.
                return null;
            }

            // FIX: The first item is at the index *after* leftExtendIndex.
            int firstItemIndex = (leftExtendIndex + 1) % size;
            T result = data[firstItemIndex];
            data[firstItemIndex] = null; // Help garbage collector.

            // The boundary now moves to where the removed item was.
            leftExtendIndex = firstItemIndex;
            itemSum -= 1;

            // Shrink the array if the usage factor is too low (e.g., < 25%).
            // We check for size >= 16 to avoid excessive shrinking.
            if (size >= 16 && itemSum * 4 < size) {
                resize(size / 2);
            }

            return result;
        }

        public T removeLast() {
            if (itemSum == 0) {
                return null;
            }

            // FIX: The last item is at the index *before* rightExtendIndex.
            // The `+ size` prevents a negative result from the subtraction.
            int lastItemIndex = (rightExtendIndex - 1 + size) % size;
            T result = data[lastItemIndex];
            data[lastItemIndex] = null; // Help garbage collector.

            // The boundary now moves to where the removed item was.
            rightExtendIndex = lastItemIndex;
            itemSum -= 1;

            // Shrink the array if the usage factor is too low.
            if (size >= 16 && itemSum * 4 < size) {
                resize(size / 2);
            }

            return result;
        }

        public T get(int index) {
            // Basic bounds check.
            if (index < 0 || index >= itemSum) {
                return null;
            }

            // FIX: The i-th logical item is located at an offset from the *first item's* physical index.
            int physicalIndex = (leftExtendIndex + 1 + index) % size;
            return data[physicalIndex];
        }

        // A corrected main method for testing purposes.
        public static void main(String[] args) {
            System.out.println("--- Basic Operations Test ---");
            ArrayDeque<Integer> ad = new ArrayDeque<>();
            ad.addFirst(5);    // Deque: 5
            ad.addLast(6);     // Deque: 5 6
            System.out.println("Get item at index 0: " + ad.get(0)); // Expected: 5
            System.out.println("Get item at index 1: " + ad.get(1)); // Expected: 6
            System.out.println("Current size: " + ad.size());      // Expected: 2
            ad.printDeque(); // Expected: 5 6

            Integer rmFirst = ad.removeFirst(); // Removes 5
            System.out.println("Removed first: " + rmFirst);      // Expected: 5
            Integer rmLast = ad.removeLast();   // Removes 6
            System.out.println("Removed last: " + rmLast);       // Expected: 6
            System.out.println("Size after removals: " + ad.size()); // Expected: 0
            System.out.println("Is empty: " + ad.isEmpty());          // Expected: true

            System.out.println("\n--- Resizing (Enlarging) Test ---");
            // Add 10 items to trigger a resize from 8 to 16
            for (int i = 0; i < 10; i++) {
                ad.addLast(i);
            }
            System.out.println("Size (item count) after adding 10 items: " + ad.size()); // Expected: 10
            System.out.println("Capacity (array length) after resize: " + ad.data.length); // Expected: 16
            System.out.print("Deque content: ");
            ad.printDeque(); // Expected: 0 1 2 3 4 5 6 7 8 9

            Integer firstItemAfterResize = ad.removeFirst();
            System.out.println("Removed first after resize: " + firstItemAfterResize); // Expected: 0
            System.out.println("Get item at index 3: " + ad.get(3)); // Corresponds to original '4'
            System.out.println("Size is now: " + ad.size()); // Expected: 9

            System.out.println("\n--- Resizing (Shrinking) Test ---");
            // Remove items until it shrinks. Capacity is 16. It will shrink when itemSum < 16/4 = 4.
            // Current size is 9. We need to remove 6 more items to have 3 left.
            for (int i = 0; i < 6; i++) {
                ad.removeLast();
            }
            System.out.println("Size (item count) before shrink: " + ad.size()); // Expected: 3
            System.out.println("Capacity before shrink: " + ad.data.length); // Expected: 16
            System.out.println("Removing one more item to trigger shrink...");
            ad.removeLast(); // This removal will drop itemSum to 2, which is < 4. Resize will be triggered.
            System.out.println("Size (item count) after shrink: " + ad.size()); // Expected: 2
            System.out.println("Capacity after shrink: " + ad.data.length); // Expected: 8
            System.out.print("Deque content: ");
            ad.printDeque(); // Expected: 1 2
        }
}


