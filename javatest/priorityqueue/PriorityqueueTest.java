package javatest.priorityqueue;

import java.util.Arrays;

import ds.priorityqueue.*;

public class PriorityqueueTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(9);
        pq.offer(7);
        pq.offer(11);
        pq.offer(1);
        pq.offer(9);
        pq.offer(12);
        pq.offer(4);
        pq.offer(8);
        pq.offer(3);
        pq.offer(6);
        pq.offer(10);
        pq.offer(2);
        pq.offer(5);

        
        while(0 < pq.size()) {
            System.out.println(pq.poll());
        }
        
        // System.out.println(Arrays.toString(pq.toArray()));

    }
}