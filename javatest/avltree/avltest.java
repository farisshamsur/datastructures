package javatest.avltree;

import ds.avltree.AVL;

public class avltest {
    public static void main(String[] args) {
        AVL<Integer, String> treeID = new AVL<>();
        treeID.put(5, "John");
        treeID.put(2, "LOL");
        treeID.put(10, "Mag");
        treeID.put(8, "Lily");
        treeID.put(7, "Fam");
        treeID.put(11, "Test");
        treeID.remove(10);

    
        for (String name: treeID) {
            System.out.println(name);
        }
        System.out.println("getTest: " + treeID.get(11));
    }
}