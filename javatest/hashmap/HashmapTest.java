package javatest.hashmap;

import ds.hashmap.*;

public class HashmapTest {
    public static void main(String[] args) {
        HashMapSC<Integer, String> map = new HashMapSC<>(4);
        map.put(1, "Naruto");
        map.put(2, "Sasuke");
        map.put(3, "Sakura");
        map.put(4, "Kakashi");
        map.put(5, "Itachi");
        map.put(6, "Jiraiya");

        System.out.println(map.toString());
    
    }
}