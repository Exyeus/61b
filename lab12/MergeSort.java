import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        // To directly get the smallest items from 2 queues, can be used in merge step
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        // To break small queues, and enable primary comparison and merge
        Queue<Queue<Item>> resultQueuesOfSplitting = new Queue<>();
        for (Item item : items) {
            Queue<Item> itemQueue = new Queue<Item>();
            resultQueuesOfSplitting.enqueue(itemQueue);
        }
        return resultQueuesOfSplitting;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        // Merge sorted queues, getMin must be used.
        Queue<Item> resultQueueOfMerge = new Queue<>();

        while (!(q1.isEmpty() && q2.isEmpty())) {
            resultQueueOfMerge.enqueue(getMin(q1, q2));
        }
        return resultQueueOfMerge;
    }

    private static <Item extends Comparable> Queue<Item> splitQueue(
            Queue<Item> singleItemQueues, int combineRange) {
        if (combineRange <= 0 || combineRange >= singleItemQueues.size()) {
            throw new IllegalArgumentException();
        }
        Queue<Item> headPartQueue = new Queue<>();
        for (int i = 0; i < combineRange; i++) {
            headPartQueue.enqueue(singleItemQueues.dequeue());
        }
        return headPartQueue;
    }
    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Maybe only calling functions. Asking mergeSortedQueues, to keep merging 2 parts.
        /* Now we have methods to:
          Break Queue to lots of singleItem queues; Not knowing to use this at the beginning
                or after the splitting work is done.
          Merge the **sorted** 2 queues to a large queue; Final result must be out of this.
          Also, what is the method for the basic sorting / exchanging work?
          If there not being help methods, its should be done with current mergeSort method!
          Note that, its input is a single Queue, not sorted.
        */
        Queue<Item> resultQueueOfSort = new Queue<>();
        if (items.size() == 1) {
            return items;
        } else if (items.size() == 2) {

            Item item1 = items.dequeue();
            Item item2 = items.dequeue();

            Queue<Item> smallSortedQueue = new Queue<>();

            if (item1.compareTo(item2) <= 0) {
                smallSortedQueue.enqueue(item1);
                smallSortedQueue.enqueue(item2);
            } else {
                smallSortedQueue.enqueue(item2);
                smallSortedQueue.enqueue(item1);
            }
            return smallSortedQueue;
        } else {
            Queue<Item> queuePart1 = splitQueue(items, items.size() / 2);
            // I think due to the destructive change from the splitQueue, the Queue items
            // refers to only keeps the tail part.
            return mergeSortedQueues(mergeSort(queuePart1), mergeSort(items));
        }
    }

    /** create a Queue of unsorted objects and print that queue.
     *  Next, call MergeSort.mergeSort() on that queue,
     *  and print both the original queue (which should be unchanged)
     *  and the returned, sorted queue.
     */
    public static void main(String[] args) {
        Queue<String> empires = new Queue<String>();
        String[] empireList = {"China", "Babylon", "India", "Rome", "Cree", "Indonesia", "Khmer", "England",
                "Sumeria", "Macedon", "Aztec", "France", "Spain", "Colombia", "Scythia", "Carthage", "Zulu", "Congo",
                "Korea", "Japan", "Ethiopia"};

        for (String item : empireList) {
            empires.enqueue(item);
            System.out.print(item + " ");
        }
        System.out.print("\n");
        Queue<String> sortedEmpires = MergeSort.mergeSort(empires);
        for (String item : sortedEmpires) {
            System.out.print(item + " ");
        }
    }
}
