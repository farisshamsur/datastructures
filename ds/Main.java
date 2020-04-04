package ds;

import java.util.LinkedList;
import java.util.Stack;
/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Stack<String> s = new Stack<>();
        s.push("oneee");
        s.push("twooo");
        s.push("skreeet");
        s.push("foooor");
        s.push("fiverr");

        System.out.println(s.search("fiverr"));
    }
}