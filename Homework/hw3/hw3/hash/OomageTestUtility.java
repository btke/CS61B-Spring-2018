package hw3.hash;


import java.util.List;
import java.util.HashMap;
import java.util.Set;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();
        int min = N / 50;
        int max = (int) (N / 2.5);
        HashMap<Integer, Integer> tracker = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (tracker.containsKey(bucketNum)) {
                tracker.put(bucketNum, tracker.get(bucketNum) + 1);
            } else {
                tracker.put(bucketNum, 0);
            }
        }

        Set<Integer> trackerKeys = tracker.keySet();
        for (int k : trackerKeys) {
            int value = tracker.get(k);
            if (value <= min || value >= max) {
                return false;
            }
        }

        return true;
    }
}
