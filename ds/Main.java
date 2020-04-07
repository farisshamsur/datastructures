package ds;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        int k = 20;
        while (k > 0) {
            double val = k << 1;
            System.out.println("k= " + k + ": " + val );
            k--;
        }
    }
}