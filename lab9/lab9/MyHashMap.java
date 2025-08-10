package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hashCode = hash(key);
        return buckets[hashCode].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int hashCode = hash(key);
        if (get(key) == null) {
            size += 1;
        }
        buckets[hashCode].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> resultSet = new HashSet<K>();
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket.size == 0) {
                continue;
            } else {
                for (int j = 0; j < bucket.size; j++) {
                    resultSet.addAll(bucket.keySet());
                }
            }
        }
        return resultSet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int hashCode = hash(key);

        V result = buckets[hashCode].remove(key);
        if (result != null) {
            size -= 1;
            return result;
        } else {
            return null;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int hashCode = hash(key);
        if (buckets[hashCode].get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    private class HashMapIterator<K> implements Iterator<K> {
        private int currentPos;
        private Iterator<K> arrayMapIterator;
        private int currentBucketNo;
        // private int currentBucketIndex;
        public HashMapIterator() {
            currentPos = 0;
            // currentBucketIndex = 0;
            currentBucketNo = 0;
        }
        public boolean hasNext() {
            return currentPos < size;
        }
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Here comes finding and returning
            if (buckets[currentBucketNo].size == 0
                    || !arrayMapIterator.hasNext()) {
                // skip current one.
                do {
                    currentBucketNo += 1;
                } while (buckets[currentBucketNo].size > 0);
                // try to find a filled arrayMap and start to iterate on that
                arrayMapIterator = (Iterator<K>) buckets[currentBucketNo].iterator();
                // sweep away the cache in each ArrayMap.
            }
            currentPos += 1;
            return arrayMapIterator.next();
        }
    }
    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator<>();
    }
}
