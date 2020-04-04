package ds.linkedlist;
/**
 * LinkedListTest
 */
public class LinkedListTest {

    public static void main(String[] args) {
      DLL<String> list = new DLL<>();
        list.addFirst("1");
        list.addFirst("233");
        list.addFirst("35");
        list.addFirst("823");
        list.addFirst("92");
        list.addFirst("3");
        list.addFirst("13");
        list.addFirst("983");

        list.set(2,"2");

        for(String s : list) {
          System.out.println(s);
        }
    }
}