package javatest.binarysearchtree;
import ds.binarysearchtree.*;

public class BstTest {
    public static void main(String[] args) {
        BST<Integer,String> tree = new BST<>();
        tree.put(32, "hi");
        tree.put(4,"yo");
        tree.put(9,"what");
        tree.put(23, "web");
        System.out.println("size: " + tree.size());
        tree.inOrder();
    }
}