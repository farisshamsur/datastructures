package ds.binarysearchtree;

/**
 * 2nd Test of building a Binary Search Tree. 
 * Based on Algorithms by C.L.R.S, DSA in Java book by Robert Lafore & Emory University.
 * Duplicate values are not allowed. Will update values if entry already exists.
 * Insert O(log n) on Average : put(key, value)
 * Search O(log n) on Average : get(key)
 * Remove O(log n) on Average : delete(key) 
 */
public class BST<K extends Comparable<K>, V> {

    // Nested Node Class
    private class Node {
        private K key;
        private V value;
        private Node left, right, p;       // pointers to left, right child and parent

        public Node(K k, V v, Node p) {
            this.key = k;
            this.value = v;
            this.p = p;
        }
    }
    // End of Nested Node Class

    // BST Properties
    private Node root;
    private int size;           // Number of nodes in BST

    // Constructors 

    /**
     * Initialises and empty BST
     */
    public BST(){
        root = null;
    }

    /**
     * Finds the Node with key K.
     * @param x : Starting Node to search for Node with key k
     * @param k : Key to search for Node
     * @return Node with key k, or null if Node does not exist
     */
    private Node getNode(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("key is null @getNode()");
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp == 0)
                break;
            else if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        return x;
    }

    /**
     * Get the value of the specified key. Returns null if specified key does not exist.
     * @param key
     * @return Value of specified key. Returns null if specified key does no exist.
     */
    public V get(K key) {
       Node x = getNode(root, key);
       if (x == null) return null;
       else return x.value;
    }

    /**
     * Time Complexity : O(log n)
     * @param x : Starting Node 
     * @return The minimum node from Node x.
     */
    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Time Complexity : O(log n)
     * @param x :  Starting Node
     * @return the minimum node from Node x
     */
    private Node max(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Inserts the key and value into a new Node in the BST.
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        Node y = null;          // Node y tracks parent node
        Node x = root;

        while (x != null) {
            y = x;
            int cmp = key.compareTo(x.key);

            if (cmp < 0) 
                x = x.left;
            else if (cmp > 0) 
                x = x.right;
            else 
                x.value = value;       // Case where key is identical, Update the values.
        }
        if (y == null)                 // Case where Tree is empty
            root = new Node(key, value, null);          // Only root node has a null parent
        else if (key.compareTo(y.key) < 0)
            y.left = new Node(key, value, y);
        else
            y.right = new Node(key, value, y);
        size++;
    }

    /**
     * Successor: The next element define by the sorted inorder traversal.
     * if Node x has a right subtree, its successor is the minimum node in the right subtree.
     * if Node x does not have a right subtree, its successor is its parent node.
     * if Node x is has the highest key, returns null.
     * @param x 
     * @return Successor of Node x.
     */
    private Node getSucc(Node x) {
        if (x.right != null)
            return min(x.right);
        Node y = x.p; 
        while (y != null && x == y.right) {     // for 3rd case: Will terminate when y points to null and x is at root.
            x = y;                              // for 2nd case: Will terminate immediately because of x == y.right condition.
            y = y.p;
        }
        return y;
    }
    
    /**
     * Sets Node u's parent's left/right pointer to v and sets Node v's parent pointer to Node u's parent.
     * Effect: Removes Node u from the tree. 
     * @param u : The node we want to remove.
     * @param v : The root of Node u's subtree.
     */
    private void transplant(Node u, Node v) {
        if (u.p == null)
            root = v;
        else if (u == u.p.left)
            u.p.left = v;
        else
            u.p.right = v;

        if (v != null)
            v.p = u.p;
    }

    /**
     * Remove Node with specified key.
     * @param key
     */
    public void remove(K key) {
        Node z = getNode(root, key);   // Node to be removed.

        if (z.left == null)
            transplant(z, z.right);
        else if (z.right == null)
            transplant(z, z.left);
        else {
            Node y = min(z.right);  // Successor of Node z. Does not have a left subtree.
            if (y.p != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void inOrder() {
        inOrder(root);
    }
    
    private void inOrder(Node x) {
        if (x != null) {
            inOrder(x.left);
            System.out.println(x.value + " ");
            inOrder(x.right);
        }
    }

}