package hw3.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        // M is the sum of buckets; N is the sum of Oomages.
        int N = oomages.size();
        List<HashSet<Oomage>> bucketList = new ArrayList<>();
        boolean hashCodeIsNice = true;
        for (int i = 0; i < M; i++) {
            bucketList.add(new HashSet<>());
        }
        for (Oomage o: oomages) {
            int hashCode = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketList.get(hashCode).add(o);
        }
        for (HashSet<Oomage> bucket : bucketList) {
            if (bucket.size() < N / 50 || bucket.size() > N / 2.5) {
                hashCodeIsNice = false;
            }
        }

        return hashCodeIsNice;
    }
}
