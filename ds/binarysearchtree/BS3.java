package ds.binarysearchtree;

/**
 * Refer to Algorithms by Robert Sedgewick.
 * Binary Search Tree implementation of the Ordered Symbol Table API.
 * Null keys and values are not allowed.
 * Duplicate values are not allowed.
 * Insert O(log n) on Average : put(key, value)
 * Search O(log n) on Average : get(key)
 * Remove O(log n) on Average : delete(key)
 */
public class BS3<K extends Comparable<K>, V> {
    
    // Nested Node Class
    private class Node {
        private K key;
        private V val;
        private Node left, right;   // Left and right subtrees

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
    // End of Nested Node Class

    // BST Properties
    private Node root;      // Root of BST
    private int size = 0;   // Number of nodes

    // Constructors

    /**
     * Initialises an empty symbol table
     */
    public BS3() {
        root = null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        size++;
        //assert check();
    }

    private Node put(Node x, K key, V val) {
        if (x == null) return new Node(key, val);   // null case. all calls will end with this
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        if (cmp > 0) return get(x.right, key);
        else return x.val;          // calls will end with this if node exists.
    }

    /**
     * Returns the node with key k or null if node does not exists.
     * @param k key of the node we want to find
     * @return the node with key k or null if node does not exists.
     */
    private Node getNode(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("key is null @getNode()");
        // if (x == null) return x;
        // int cmp = key.compareTo(x.key);
        // if (cmp < 0) return x.left = getNode(x.left, key);
        // if (cmp > 0) return x.right = getNode(x.right, key);
        // else return x;

        while (x != null && key != x.key) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else x = x.right;
        }
        return x;
    }

    public boolean contains(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        size--;
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;     // for no subtrees case and node wihout left subtree
            if (x.right == null) return x.left;     // for no subtrees case and node wihtout right subtree
            Node t = x;                             // case when node has both left and right subtrees // t holds the node we want to delete.
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    private Node deleteMin(Node x) {
        // while (x != null) {
        //     if (x.left == null) return x.right;
        //     x = x.left;
        // }
        // return x;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    /**
     * Returns the minimum node from node x (leftmost node from x)
     * @param x
     * @return minimum node from node x
     */
    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }
}