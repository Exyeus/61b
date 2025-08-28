import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Optimized by AI
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
        if (asciis == null || asciis.length <= 1) {
            return asciis; // No need to sort if null, empty, or single element
        }

        int length = asciis.length;
        int maxLen = 0;
        // Find the maximum length of strings to determine the number of passes
        for (String s : asciis) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        // Auxiliary arrays for sorting. We'll use reference swapping to avoid System.arraycopy.
        String[] buffer1 = asciis; // Initially, buffer1 holds the original unsorted data
        String[] buffer2 = new String[length]; // buffer2 will hold the sorted data in each pass

        // countCharByInt will store counts for ASCII characters (0-255) plus one extra for shorter strings.
        // Index 0-255 for ASCII values, index 256 for strings shorter than current iterateTimes.
        int[] count = new int[257]; // 256 ASCII chars + 1 for 'padding' (shorter strings)

        // LSD Radix Sort iterates from the least significant character (rightmost) to the most significant (leftmost).
        // We iterate from maxLen-1 down to 0.
        for (int iterateTimes = maxLen - 1; iterateTimes > -1; iterateTimes--) {

            // Reset the count array for the current character position.
            // Principle: Resetting count array for each pass is crucial for correctness.
            Arrays.fill(count, 0);

            // --- Counting Phase ---
            // Count the frequency of each character (or padding) at the current position.
            // We will use buffer1 as the source for this pass.
            for (int i = 0; i < length; i++) {
                int charCode = getAsciiAtIndex(buffer1[i], iterateTimes);
                count[charCode]++;
            }

            // --- Cumulative Sum Phase ---
            // Convert counts to starting indices for each character.
            // arr[i] will store the count of elements less than or equal to i.
            // Principle: This transforms counts into stable positions.
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            // --- Placement Phase ---
            // Place the strings into the correct positions in buffer2 based on the current character.
            // We iterate backwards through the source array (buffer1) to ensure stability.
            // Principle: Iterating backwards and using count[charCode]-- ensures stability.
            for (int i = length - 1; i >= 0; i--) {
                int charCode = getAsciiAtIndex(buffer1[i], iterateTimes);
                // count[charCode] gives the position where the current element should be placed.
                // Since array indices are 0-based, and count[charCode] is 1-based (total count),
                // we use count[charCode] - 1 as the index.
                buffer2[count[charCode] - 1] = buffer1[i];
                // Decrement the count for this character code, so the next element with the same code goes to the previous slot.
                count[charCode]--;
            }

            // --- Reference Swap ---
            // Swap buffer1 and buffer2 for the next iteration.
            // buffer1 will now point to the sorted data from buffer2.
            // buffer2 will be ready to receive the next pass's sorted data.
            // Principle: Avoids O(N) array copy by just swapping array references (O(1)).
            String[] temp = buffer1;
            buffer1 = buffer2;
            buffer2 = temp;
        }

        // After all passes, buffer1 holds the final sorted array.
        // Since the original 'asciis' array must not be modified, and buffer1 might have
        // swapped back to point to the original array's memory if the number of passes was even,
        // we should return a new array containing the sorted elements.
        // However, if buffer1 is NOT the original `asciis` buffer (meaning it's `buffer2`),
        // then we have a distinct sorted array.
        // A safe approach is always to return a copy, or check which buffer holds the final result.
        // If the number of passes (maxLen) is odd, buffer1 will point to buffer2.
        // If the number of passes is even, buffer1 will point to the original buffer1's memory.
        // Since we cannot modify the original `asciis` which buffer1 might point to,
        // we need to ensure the returned array is a distinct copy if buffer1 IS the original buffer.
        // A simpler way: always return a copy of whatever buffer1 points to.

        String[] finalSortedResult = new String[length];
        System.arraycopy(buffer1, 0, finalSortedResult, 0, length);
        return finalSortedResult;
    }

    /**
     * Helper function to get the character code for Radix Sort.
     * Handles strings of varying lengths by assigning a special code (0) to
     * characters that fall "off the end" of a shorter string.
     *
     * @param s The string to get the character code from.
     * @param index The character position (0-based from the left, MSD first).
     * @return The character code (0-255 for ASCII) or 0 for padding.
     */
    private static int getAsciiAtIndex(String s, int index) {
        // If the current index is out of bounds for the string, return 0
        // (representing the "padding" character, which will sort before any real ASCII character).
        // Principle: Consistent mapping of "missing" characters to a dedicated bucket (index 0).
        if (index >= s.length()) {
            return 0; // Special code for shorter strings
        } else {
            // Return ASCII value + 1 to differentiate from the padding code (0).
            // This maps ASCII 0 to bucket 1, ASCII 1 to bucket 2, ..., ASCII 255 to bucket 256.
            // This avoids collision between the padding character and actual ASCII character 0.
            // Principle: Shifting ASCII values to avoid collision with the padding index.
            return (int) s.charAt(index) + 1;
        }
    }

    // The sortHelperLSD and sortHelperMSD methods were optional and not used, so they are removed.

    public static void main(String[] args) {
        // Test cases to demonstrate sorting
        String[] unsortedStrings1 = new String[]{"Virgil", "Dante", "Nero", "V", "Nightmare", "Griffon", "Shadow", "Urizon", "Trish", "Kyrie"};
        System.out.println("Original 1: " + Arrays.toString(unsortedStrings1));
        String[] sorted1 = RadixSort.sort(unsortedStrings1);
        System.out.println("Sorted 1:   " + Arrays.toString(sorted1));
        System.out.println("--------------------");

        String[] unsortedStrings2 = new String[]{"apple", "banana", "app", "apricot", "bandana"};
        System.out.println("Original 2: " + Arrays.toString(unsortedStrings2));
        String[] sorted2 = RadixSort.sort(unsortedStrings2);
        System.out.println("Sorted 2:   " + Arrays.toString(sorted2));
        System.out.println("--------------------");

        String[] unsortedStrings3 = new String[]{"a", "b", "c", "ba", "ab", "abc"};
        System.out.println("Original 3: " + Arrays.toString(unsortedStrings3));
        String[] sorted3 = RadixSort.sort(unsortedStrings3);
        System.out.println("Sorted 3:   " + Arrays.toString(sorted3));
        System.out.println("--------------------");

        // Test case with special characters and empty strings
        String[] unsortedStrings4 = new String[]{"Virgil", "Dante", "Nero", "V", "&^(^(&^", "*)@!!", "", "apple"};
        System.out.println("Original 4: " + Arrays.toString(unsortedStrings4));
        String[] sorted4 = RadixSort.sort(unsortedStrings4);
        System.out.println("Sorted 4:   " + Arrays.toString(sorted4));
        System.out.println("--------------------");

        // Test case with identical strings
        String[] unsortedStrings5 = new String[]{"test", "test", "test"};
        System.out.println("Original 5: " + Arrays.toString(unsortedStrings5));
        String[] sorted5 = RadixSort.sort(unsortedStrings5);
        System.out.println("Sorted 5:   " + Arrays.toString(sorted5));
        System.out.println("--------------------");
    }
}