package ds.avltree;

import ds.stack.Stack;

/**
 * AVL Tree.
 * Duplicate values are not allowed. Does NOT update values if key already exists. 
 * Insert O(log n) : put(key, value) 
 * Search O(log n) : get(key)
 * Remove O(log n) : remove(key)
 */
public class AVL<K extends Comparable<K>, V> implements Iterable<V> {
    // Nested Node Class
    private class Node {
        private K key;
        private V value;
        private Node left, right; // Left and Right child of Node
        private int height;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
    }
    // End of Nested Node Class

    // AVL tree properties
    private Node root;
    private int size;   // Number of nodes in AVL tree

    // Constructor
    public AVL() {
        root = null;
    }

    // Main Methods

    /**
     * Rotates the imbalanced node to the left.
     * This rotation is done when the x's right subtree is heavy 
     * and y's left subtree is not heavy.
     * @param x : the imbalanced node
     *  @return the new root of subtree
     */
    private Node leftRotate(Node x) {
        Node y = x.right;

        x.right = y.left;
        y.left = x;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /**
     * Rotates the imbalanced node to the right.
     * This rotation is done when x's left subtree is heavy
     * and y's right subtree is not heavy.
     * @param x : the imbalanced node
     * @return the new root of subtree
     */
    private Node rightRotate(Node x) {
        Node y = x.left;

        x.left = y.right;
        y.right = x;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /**
     * @param key
     * @param value
     * @throws IllegalArgumentException if key is null.
     */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("key is null @put()");
        if (value == null) throw new IllegalArgumentException("value is null @put()");
        root = insert(root, key, value);
    }

    /**
     * Inserts the key-value pair into the subtree.
     * @param x : the subtree
     * @param key : the key
     * @param value : the value
     * @return subtree
     */
    private Node insert(Node x, K key, V value) {
        // Recursive BST Insertion
        if (x == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);

        if (cmp < 0) 
            x.left = insert(x.left, key, value);
        else if (cmp > 0) 
            x.right = insert(x.right, key, value);
        else
            return x;

        updateHeight(x);
        return balance(x);
    }

    /**
     * Removes the entry with the specified key.
     * @param key 
     * @throws IllegalArgumentException if key is null
     */
    public void remove(K key) {
        if (key == null) throw new IllegalArgumentException("key is null @remove(key)");
        if (!contains(key)) return;
        root = delete(root, key);
        size--;
    }

    /**
     * Removes Node with specified key from the tree.
     * 
     * @param x   : Starting node // root // node pointer
     * @param key : Key of the node to be removed
     * @return
     */
    private Node delete(Node x, K key) {
        if (x == null) return x;

        if (key.compareTo(x.key) < 0) 
            x.left = delete(x.left, key);
        else if (key.compareTo(x.key) > 0)
            x.right = delete(x.right, key);
        else {
            if (x.left == null)     // Case: Node to be deleted has only a right subtree OR has no children
                return x.right;
            else if (x.right == null)   // Case: Node to be deleted has only a left subtree
                return x.left;
            else {
                // Case: Node has both left and right subtree
                Node successorNode = min(x.right);
                x.key = successorNode.key;
                x.value = successorNode.value;
                x.right = delete(x.right, successorNode.key);
            }
        }

        updateHeight(x);
        return balance(x);        
    }

    /**
     * 
     * @param key
     * @return Value of Node with specified key
     */
    public V get(K key) {
        Node x = get(root, key);
        if (x == null) return null;
        return x.value;
    }

    /**
     * @param key 
     * @return True if key is in the tree, False otherwise
     */
    public boolean contains(K key) {
        return get(key) != null;
    }

    // Helper Methods

    /**
     * @return the height of Node x or -1 is node is null (no node)
     */
    private int height(Node x) {
        if (x == null) return -1;
        return x.height;
    }

    /**
     * Updates the height of Node x after a rotation operation
     * @param x : Node to update the height
     */
    private void updateHeight(Node x) {
        x.height = Math.max(height(x.left), height(x.right)) + 1;
    }

    /**
     * @param x : Node to get the balance factor
     * @return Balance factor of Node x or null if Node is null
     */
    private int balanceFactor(Node x) {
        if (x == null) return 0;
        return height(x.left) - height(x.right);
    }

    /**
     * Restores the AVL tree property of the subtree.
     * 
     * @param x the subtree
     * @return the subtree with restored AVL property
     */
    private Node balance(Node x) {
        // Right heavy tree
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {       // RL case
                x.right = rightRotate(x.right);
            }
            x = leftRotate(x);
        } else if (balanceFactor(x) > 1) {          // Left heavy tree
            if (balanceFactor(x.left) < 0) {        // LR case
                x.left = leftRotate(x.left);
            }
            x = rightRotate(x);
        }
        return x;
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
     * Finds the Node with specified key.
     * @param x : Starting node // root
     * @param key : the key
     * @return Node with specified key or null if no such key.
     */
    private Node get(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("key is null @get(x, key)");

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

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    // Returns as iterator to traverse the tree in order.
    public java.util.Iterator<V> iterator() {

        final int expectedNodeCount = size;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new java.util.Iterator<V>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public V next() {

                if (expectedNodeCount != size) throw new java.util.ConcurrentModificationException();

                while (trav != null && trav.left != null) {
                stack.push(trav.left);
                trav = trav.left;
                }

                Node node = stack.pop();

                if (node.right != null) {
                stack.push(node.right);
                trav = node.right;
                }

                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}