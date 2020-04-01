package ds;
import java.util.LinkedList;
/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.addFirst(1);
        list.addFirst(233);
        list.addFirst(35);
        list.addFirst(823);
        list.addFirst(92);
        list.addFirst(3);
        list.addFirst(13);
        list.addFirst(983);

        list.remove(3);

        for(int i:list) {
          System.out.println(i);
        }
    }
}