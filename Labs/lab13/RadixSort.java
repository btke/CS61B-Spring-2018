/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
import java.util.Queue;
import java.util.LinkedList;

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
     *
     * @source Edd Mann http://eddmann.com/posts/least-significant-digit-lsd-radix-sort-in-java/
     * His implementation is for a radix sort using Integers. I used his idea to implement a
     * radix sort for Strings
     */


    public static String[] sort(String[] asciis) {
        Queue<String>[] buckets = new Queue[256];
        for (int i = 0; i < 256; i++)
            buckets[i] = new LinkedList();

        boolean sorted = false;
        int lengthInc = 0;

        String[] sortedArr = new String[asciis.length];
        System.arraycopy(asciis, 0, sortedArr, 0, asciis.length);

        while (!sorted) {
            sorted = true;

            for (String item : sortedArr) {
                int index = item.length() - lengthInc - 1;
                if (index >= 0) {
                    sorted = false;
                    int ofASCII = (int) item.charAt(index);
                    buckets[ofASCII].add(item);
                } else {
                    buckets[(int) item.charAt(0)].add(item);
                }


            }

            lengthInc++;
            int index = 0;

            for (Queue<String> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    sortedArr[index] = bucket.remove();
                    index++;
                }
            }

            //System.out.println();

        }

        //System.out.println("");

        return sortedArr;
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
}
