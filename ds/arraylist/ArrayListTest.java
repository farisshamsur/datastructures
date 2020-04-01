package ds.arraylist;

public class ArrayListTest {
    public static void main(String[] args) {
        MyArrayList<String> nums = new MyArrayList<>();

        System.out.println("isEmpty = " + nums.isEmpty());
        nums.add("Zero");
        System.out.println("isEmpty = "+ nums.isEmpty());
        
        // add(E e) works
        nums.add("One");
        nums.add("Two");
        nums.add("Three");
        nums.add("four");
        nums.add("five");
        nums.add("six");
        nums.add("seven");
        nums.add("eight");
        nums.add("nine");
        nums.add("ten");
        nums.add("eleven");
        nums.add("twelve");

        // set(int i, E e) 
        nums.set(12,"Impurities");
        
        // remove(int i) works
        // nums.remove(4);

        //remove (Object o)
        nums.remove("six");

        System.out.println(nums);
        System.out.println("size = " + nums.size());
        System.out.println(nums.length());

        for(String num : nums) {
            System.out.println(num);
        }
    }
}