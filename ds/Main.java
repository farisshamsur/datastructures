package ds;

import java.util.Objects;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        // int k = 20;
        // while (k > 0) {
        //     double val = k << 1;
        //     System.out.println("k= " + k + ": " + val );
        //     k--;
        // }
        String key = "test";
        String val = "zero";
        int n = 16; // capacity of hashmap. always a power of 2.

        // int k = Objects.hashCode(s);
        // System.out.println("Object.hashCode(s): " + k);      // Returns 0 if s is null
        // System.out.println("s.hashCode(): " + s.hashCode()); // Returns an exception

        int h; 
        int hashFun = (h = key.hashCode()) ^ (h >>> 16);  // Node and static hash functions are different.
        int hashNode = key.hashCode() ^ val.hashCode();
        System.out.println("hashFun: " + hashFun);
        System.out.println("hashNode: " + hashNode);
        
        int regular = hashFun % n;
        int java = (n-1) & hashFun; // Bit-wise & operation as replacement for modulo in base 2.
        System.out.println("modulo index: " + regular);
        System.out.println("bitwise index: " + java);
        System.out.println(-1 >>> Integer.numberOfLeadingZeros(4)); // -1 in 32 bit binary is 32 1's.
        System.out.println(Math.pow(2, 3)); // 2**k , where k = 32 - numberOfLeadingZeros // k = leftmost position of bit
    }
}