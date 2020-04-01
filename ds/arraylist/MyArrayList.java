package ds.arraylist;
/**
 * ArrayList implementation of the List Interface.
 * 
 */

@SuppressWarnings("unchecked")
public class MyArrayList<E> implements Iterable<E> {

  // class variables
  private E[] array;
  private static final int CAPACITY = 10; // Default capacity value
  private int size = 0;

  //constructors

  public MyArrayList(){this(CAPACITY);}

  public MyArrayList(int initialCapacity) {
    array = (E[]) new Object[initialCapacity];
  }

  // Methods

  // Adds specified element to the end of the list
  public boolean add(E e) {
    int N = this.size;

    if(N<array.length && array[N]==null) {
      array[N] = e;
      size++;
      return true;
    }else {
      this.resize(size*2);
      array[size++] = e;
      return true;
    }
  }

  // Adds element to the specified index. Shifts the initial element and any subsequent elements to the right
  public void add(int index, E element) throws IndexOutOfBoundsException {
    checkIndex(index, size+1);
    
    if(size == array.length)
      resize(2*array.length);
    // for(int k=size-1; k>=index; k--) {
    //   array[k+1] = array[k];
    // }
    System.arraycopy(array, index, array, index + 1, size - index);
    array[index] = element;
    size++;
  }

  public boolean contains(Object o) {
    for(int i=0; i<size; i++) {
      if(array[i].equals(o))
        return true;
    }
    return false;
  }

  public E get(int i) throws IndexOutOfBoundsException {
    checkIndex(i, array.length);
    return array[i];
  }

  // Returns number of elements in the arraylist
  public int size() { return this.size; }

  // Returns llength / capacity of arraylist
  public int length() { return array.length;}

  public boolean isEmpty() {return size==0;}

  // Removes the element at the specified position. Shifts subsequent elements to the left.
  public E remove(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);

    E temp = array[i];
    for(int j=i; j<size-1; j++) {
      array[j] = array[j+1];
    }
    array[--size] = null;
    return temp;
  }

  public boolean remove(Object o) {
    for(int i=0; i<array.length; i++) {
      if(o.equals(array[i])) {
        System.out.println(i);
        remove(i);
        return true;
      }
    }
    return false;
  }

  // Replaces the element in list with specified element
  // Returns the element previously at the specified location
  public E set(int i, E e) throws IndexOutOfBoundsException {
    checkIndex(i, size);

    E temp = array[i];
    array[i] = e;
    return temp;
  }

  // Checks if index is between 0 to n-1;
  protected void checkIndex(int i, int n) {
    if( i<0 || i>= n)
      throw new IndexOutOfBoundsException("Illegal index: "+ i);
  }

  protected void resize(int capacity) {
    E[] temp = (E[]) new Object[capacity];
    for(int i=0; i<array.length; i++) {
      temp[i] = array[i];
    }
    array = temp;
  }

  @Override
  public java.util.Iterator<E> iterator() {
    return new java.util.Iterator<E>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < size;
      }

      @Override
      public E next() {
        return array[index++];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    if (size == 0) return "[]";
    else {
      StringBuilder sb = new StringBuilder(size).append("[");
      for (int i = 0; i < size - 1; i++) sb.append(array[i] + ", ");
      return sb.append(array[size - 1] + "]").toString();
    }
  }

}