import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {

        // Question: which array to fill?
        int[] countCharByInt = new int[257];
        int[] start = new int[257];
        int length = asciis.length;
        String[] copyOfAsciis = new String[length];

        System.arraycopy(asciis, 0, copyOfAsciis, 0, length);

        String[] sorted = new String[length];
        // A char array of length 256, to deal with those asciis, and 1 for void char
        int max = Integer.MIN_VALUE;
        for (String item : copyOfAsciis) {
            max = Math.max(item.length(), max);
            // Checking ascii ranges may reduce space consumption.
            // But this may lift time complexity by one scale.
        }
        // Get the max size of Strings, to decide the time for loops.
        for (int iterateTimes = max - 1; iterateTimes > -1; iterateTimes--) {
            // Iterate for max time,
            // TODO: Check those chars being ascii's or not, throwing Exceptions if needed.


            for (int index = 0; index < length; index++) {
                int currentCharNumber = (int) getAsciiAtIndex(copyOfAsciis[index], iterateTimes);
                // An int of [0, 256]
                countCharByInt[currentCharNumber] += 1;
                // After the primitive write, computer out the start indices.

            }

            countArrayToStartsIndices(countCharByInt);

            // After count is constructed, fill the sorted with what we get
            // Then copy this array to original

            for (int index = length - 1; index > -1; index--) {
                int currentIntCharOfString = getAsciiAtIndex(copyOfAsciis[index], iterateTimes);
                sorted[countCharByInt[currentIntCharOfString] - 1] = copyOfAsciis[index];
                countCharByInt[currentIntCharOfString] -= 1;
            }

            System.arraycopy(sorted, 0, copyOfAsciis, 0, length);
            Arrays.fill(countCharByInt, 0);
        }
        return sorted;
    }

    /**
     * Offers an in-place transformation for count array.
     * @param arr Count array after counting through the items
     */
    private static void countArrayToStartsIndices(int[] arr) {

        for (int index = 0; index < arr.length - 1; index++) {
            arr[index + 1] += arr[index];
        }
    }

    /**
     * A helper function to automatically enlarge strings and return
     * their corresponding ascii code at the given index.
     * @param s A string from the target array
     * @param index The index you want, ignoring the actual size of s
     * @return the ascii value.
     */
    private static int getAsciiAtIndex(String s, int index) {
        if (s.length() < index + 1) {
            return 0;
        } else {
            return (int) s.charAt(index) + 1;
        }
    }
    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
    public static void main(String[] args) {
        String unsortedStrings[] = new String[]{"Virgil", "Dante", "Nero", "V", "Nightmare", "Griffon", "Shadow",
        "Urizon", "Trish", "Kyrie"};
        String[] sorted = RadixSort.sort(unsortedStrings);
        for (String s : sorted) {
            System.out.print(s + " ");
        }
    }
}
