/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        // It is LSD that we should use
        // Negative numbers can be judged directly.
        // Question: To enlarge the dictionary double, or to store and
        // cast them specially?
        // Make a long array, and we make "virtual indices", it is at the middle
        // that we store those close to 0.
        // To get the digit? Modular arithmetic should be ok.
        // x - x % 10; x - x % 100 ... etc
        int[] sorted = new int[arr.length];
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int item : arr) {
            max = Math.max(max, item);
            min = Math.min(min, item);
        }
        // No matter what the signs of min/max be, we need the range ok
        // Having established nice range, we can emulate the position for integers
        // For example, -2 and 5, we need -2, -1, 0, 1, 2, 3, 4, 5.
        // That is max - min + 1. If 2 and 5, 2, 3, 4, 5: 5 - 2 + 1.
        // x should be put into: count[x - min] -2 - (-2) -> 0;



        int[] count = new int[max - min + 1];
        for (int item : arr) {
            count[item - min] += 1;
        }
        int[] starts = new int[max - min + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += count[i];
        }

        for (int item : arr) {
            int place = starts[item - min];
            sorted[place] = item;
            starts[item - min] += 1;
        }

        // return the sorted array
        return sorted;
    }
    public static void main(String[] args) {
        int[] trialArray = new int[]{-1, -2, 9, 4, 5, 1, 100, 1000000};
        for (int item : trialArray) {
            System.out.print(item + " ");
        }
        System.out.print("\n");
        int[] sortedArray = CountingSort.betterCountingSort(trialArray);
        for (int item : sortedArray) {
            System.out.print(item + " ");
        }
    }

}
