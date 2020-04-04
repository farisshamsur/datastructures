package javatest.stack;

import ds.stack.*;

/**
 * StackTest
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();

        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push(5);

        s.pop();
        s.pop();

        for (int i : s) {
            System.out.println(i);
        }
    }
}