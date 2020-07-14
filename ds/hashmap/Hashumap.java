package ds.hashmap;

import java.util.Arrays;

import ds.linkedlist.DLL;

class Entry<K,V> {

    K key;
    V value;
    int hash;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public boolean equals(Entry<K,V> o) {
        if (hash != o.hash) return false;
        return key.equals(o.key);
    }

    public final K getKey() {return key;}
    public final V getValue() {return value;}
    public final String toString() {return key + ":" + value + " ";}

    public final V setValue(V newValue) {
        V oldvalue = value;
        this.value = newValue;
        return oldvalue;
    }
}

@SuppressWarnings("unchecked")
public class Hashumap<K,V> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    // HashMap properties
    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private DLL<Entry<K,V>>[] table;

    // CONSTRUCTORS

    public Hashumap(int capacity, double maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (capacity * maxLoadFactor);
        table = new DLL[this.capacity]; 
    }

    public Hashumap(int capacity){
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public Hashumap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // PUBLIC METHODS

    /**
     * Associates the specified value with the specified key in the Hash Map
     */
    public V put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Null key");
        Entry<K,V> newEntry = new Entry<K,V>(key, value);
        int bucketIndex = toIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    /**
     * Removes the entry with the specified key
     * @param key
     * @return
     */
    public V remove(K key) {
      if (key == null) return null;
      int bucketIndex = toIndex(key.hashCode());
      return bucketRemoveEntry(bucketIndex, key);
    }

    /**
     * Returns the value mapped to the specified key, or null if key is null
     */
    public V get(K key) {
        if (key == null) return null;
        int bucketIndex = toIndex(key.hashCode());
        Entry<K,V> entry = getEntry(bucketIndex, key);
        if (entry != null) return entry.value;
        return null;
    }

    /**
     * Returns True if the HashMap contains the key
     * @param key
     * @return True if HashMap contains key.
     */
    public boolean containsKey(K key) {
        int bucketIndex = toIndex(key.hashCode());
        return getEntry(bucketIndex, key) != null;
    }

    /**
     * Removes all elements inside the HashMap
     */
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V[] toArray() {
        Object[] arr = new Object[0];
        for (int i=0; i<table.length; i++) {
            if (table[i] != null) {
                DLL<Entry<K,V>> list = table[i];
                arr = combine(arr, list.toArray());
            }
        }
        return (V[]) arr;
    }

    public String toString() {
        String s = "";
        for (int i=0; i<table.length; i++) {
          if (table[i] != null) {
              for(Entry<K,V> entry : table[i]) {
                  s += entry.toString();
                //s += String.join(" ", entry.toString());
              }
          }
      }
      return s;
    }

    // PRIVATE MEHTODS

    /**
     * Removes the sign bit and converts to index.
     * @param hash : The hash value of key
     * @return index in the domain [0,capacity-1]
     */
    private int toIndex(int hash) {
        return (hash & 0x7FFFFFFF) % table.length;
    }

    /***
     * Returns the entry(key-value pair) from the specified key and index
     * @param bucketIndex : Index where the entry is in
     * @param key : key of the entry
     * @return entry if bucket contains entry , null otherwise
     */
    private Entry<K,V> getEntry(int bucketIndex, K key) {
        if (key == null) return null;
        DLL<Entry<K,V>> bucket = table[bucketIndex];
        if (bucket == null) return null;
        for(Entry<K,V> entry:bucket) if (entry.key.equals(key)) return entry;
        return null;
    }

    /**
     * Inserts specified entry to the bucket if entry is not already inside, else update values
     * @param bucketIndex
     * @param entry
     * @return null if entry is inserted, else returns the previous value
     */
    private V bucketInsertEntry(int bucketIndex, Entry<K,V> entry) {
        DLL<Entry<K,V>> bucket = table[bucketIndex];
        if (bucket == null) table[bucketIndex] = bucket = new DLL<>();

        Entry<K,V> existentEntry = getEntry(bucketIndex, entry.key);
        if (existentEntry == null) {
            bucket.add(entry);
            if (++size > threshold) resizeTable();
            return null;
        } else {
            V oldval = existentEntry.getValue();
            existentEntry.setValue(entry.value);
            return oldval;
        }
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        DLL<Entry<K,V>>[] newTable = new DLL[capacity];

        for (int i=0; i<table.length; i++) {
            if (table[i] != null) {
                for (Entry<K,V> entry : table[i]) {
                    int bucketIndex = toIndex(entry.hash);
                    DLL<Entry<K,V>> bucket = newTable[bucketIndex];
                    
                    if (bucket == null) newTable[bucketIndex] = bucket = new DLL<>();
                    bucket.add(entry);
                }
            }

        // Avoid memory leak. Help GC.
        table[i].clear();
        table[i] = null;
        }

        table = newTable;
    }

    /**
     * Removes the entry given the specified bucket index and key.
     * @param bucketIndex
     * @param key
     * @return the value of previous entry, else null
     */
    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K,V> entry = getEntry(bucketIndex, key);
        if (entry != null) {
            DLL<Entry<K,V>> links = table[bucketIndex];
            links.remove(entry);
            --size;
            return entry.value;
        } else return null;
    }

    private static Object[] combine(Object[] a, Object[] b){
        int length = a.length + b.length;
        Object[] result = new Object[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}