package ds.priorityqueue;

import java.util.Comparator;

/**
 * (Min) Binary Heap implementation of a Priority Queue ADT. Elements ordered
 * according to their natural ordering or by a Comparator. Does not permit null
 * elements. Does not permit non-comparable objects.
 * 
 * O(log n) time for offer and poll methods 
 * O(n) time for remove(Object) and contain(Object)
 * O(1) time for peek
 */ 
@SuppressWarnings("unchecked")
public class PriorityQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    Object[] queue;
    int size;

    //@SuppressWarnings("serial")
    private final Comparator<? super E> comparator;

    // CONSTRUCTORS

    /**
     * Creates a Priority Queue with the specified initial capacity and orders the elements
     * according to the specified comparator.
     * If specified comparator is set to null, the natural ordering will be used.
     */
    public PriorityQueue(int initialcapacity, Comparator<? super E> comparator) {
        this.queue = new Object[initialcapacity];
        this.comparator = comparator;
    }

    /**
     * Creates a Priority Queue with the default initial capacity of 11.
     * Orders elements according to their natural ordering.
     */
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    /**
     * Creates a Priority Queue with the given comparator and defaul capacity
     */
    public PriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    /**
     * Creates a Priority Queue with the specified capacity and orders according 
     * to the natural ordering
     * @param initialcapacity
     */
    public PriorityQueue(int initialcapacity) {
        this(initialcapacity, null);
    }

    // METHODS

    /**
     * Inserts the specified element into the Priority Queue.
     * @return true
     * @throws NullPointerException if specified element is null
     */
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();

        if (size >= queue.length)
            grow(2*queue.length);
        siftUp(size++, e); // adds element to insertion point and sifts up
        return true;
    }

    /**
     * Resizes the array to specified capacity;
     * @param newCapacity
     */
    private void grow(int newCapacity) {
        Object[] newQueue = new Object[newCapacity];
        System.arraycopy(queue, 0, newQueue, 0, queue.length);
        queue = newQueue;
    }

    /**
     * Inserts element x at index k. Maintains the heap invariant by moving x up
     * until x is greater than or equal to its parent, or is the root.
     * 
     * This method separates the situation when there is no comparator and when the
     * comparator is give.
     * 
     * @param k index to insert x   
     * @param x element to be inserted
     */
    private void siftUp(int k, E x) {
        if (comparator != null)
            siftUpComparator(k, x, queue, comparator);
        else 
            siftUpComparable(k, x, queue);

    }

    private static <T> void siftUpComparable(int k, T x, Object[] heap) {
        Comparable<? super T> key = (Comparable<? super T>) x; // we are telling java that x does implement the comparable interface
        while (k > 0) {
            int parent_index = (k-1) >>> 1;
            Object parent = heap[parent_index];
            if (key.compareTo((T) parent) >= 0)
                break;
            heap[k] = parent; 
            k = parent_index;
        }
        heap[k] = key;
    }

    private static <T> void siftUpComparator(int k, T x, Object[] heap, Comparator<? super T> cmp) {
        while (k >0) {
            int parent_index = (k-1) >>> 1;
            Object parent = heap[parent_index];
            if (cmp.compare(x,(T) parent) >= 0) 
                break;
            heap[k] = parent;
            k = parent_index;
        }
        heap[k] = x;
    }

    public E poll() {
        final Object[] heap = queue;
        final E root = (E) queue[0];

        if (root != null) {
            final int n = --size;
            final E x = (E) heap[n];
            heap[n] = null;
            if (n > 0) {
                final Comparator<? super E> cmp;
                if ((cmp = comparator) == null)
                    siftDownComparable(0, x, heap, n);
                else   
                    siftDownComparator(0, x, heap, n, cmp);
            }
        }
        return root;
    }

    private static <T> void siftDownComparator(int k, T x, Object[] heap, int n, Comparator<? super T> cmp) {
        int half = n >> 1;
        while (k < half) {
            int child_index = (k << 1) + 1;
            int right_index = child_index + 1;
            Object child = heap[child_index];

            if (right_index < n && cmp.compare((T) child, (T) heap[right_index]) > 0)
                child = heap[child_index = right_index];
            
            if (cmp.compare(x, (T) child) <= 0)
                break;
            
            heap[k] = child;
            k = child_index;
        }
        heap[k] = x;
    }

    private static <T> void siftDownComparable(int k, T x, Object[] heap, int n) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        int half = n >>> 1;
        while (k < half) {
            int child_index = (k << 1) + 1; // Assume left child is smaller than right
            int right_index = child_index + 1;
            Object child = heap[child_index];

            // If right child is smaller than left child, set child to right child
            if (right_index < n && ((Comparable<? super T>) child).compareTo((T) heap[right_index]) > 0)
                child = heap[child_index = right_index];

            // If key is at the correct position , terminate loop
            if (key.compareTo((T) child) <= 0)
                break;
            
            heap[k] = child;
            k = child_index;
        }
        heap[k] = key;
    }

    public int size() { return size;}

    public Object[] toArray() {
        return queue;
    }

    
}