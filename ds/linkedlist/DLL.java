package ds.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DLL<E> implements Iterable<E> {

    // Nested Node class
    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> p, E e, Node<E> n) {
            this.prev = p;
            this.element = e;
            this.next = n;
        }
    }
    // End of Nested Node class

    // Linked List properties
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public DLL() {

    }

    /**
     * Links e as first element.
     */
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    /**
     * Links e as last element.
     */
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    /**
     * Inserts element e before non-null Node succ.
     */
    void linkBefore(E e, Node<E> succ) {
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }
    
    /**
     * Unlinks the non-null first Node.
     */
    private E unlinkFirst(Node<E> f) {
        final E element = f.element;
        final Node<E> next = f.next;
        f.next = null;
        f.element = null;
        first = next;
        if (next == null)
            last = null;
        else 
            next.prev = null;
        size--;
        return element;
    }

    /**
     * Unlinks the non-null last Node.
     */
    private E unlinkLast(Node<E> l) {
        final E element = l.element;
        final Node<E> prev = l.prev;
        l.element = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else 
            prev.next = null;
        size--;
        return element;
    }

    /**
     * Unlinks non-null Node x.
     * 
     * @return the element removed from list
     */
    E unlink(Node<E> x) {
        final E element = x.element;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.element = null;
        size--;
        return element;
    }

    /**
     * @return (non-null) Node at the specified index
     */
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /**
     * Inserts the specified element at the beginning of the list
     */
    public void addFirst(E e) {
        linkFirst(e);
    }

    /**
     * Appends the specified element to the end of the list
     */
    public void addLast(E e) {
        linkLast(e);
    }

    /**
     * Appends the specified element to the end of the list
     * 
     * @return true
     */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * Inserts the specified element to the specified position in the list. Shifts
     * the initial element at the position and any subsequent elements to the right
     * 
     * @throws IndexOutOfBoundsException
     */
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /**
     * Replaces the current element at the specified position with the specified
     * element
     * 
     * @return the previous element
     * @throws IndexOutOfBoundsException
     */
    public E set(int index, E element) {
        checkElementIndex(index);

        Node<E> x = node(index);
        E oldVal = x.element;
        x.element = element;
        return oldVal;
    }

    /**
     * Returns the index of the first occurence of the specified element,, or -1 if
     * list does not contain the element
     * 
     * @return index of first occurence of specified element, or -1 if list does not
     *         contain the element
     */
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.element == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.element))
                    return index;
                index++;
            }
        }
        return -1;
    }

    /**
     * @return true if list contains the specified element, false otherwise
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * @return the element at the specified position
     */
    public E get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    /**
     * @return the first element 
     */
    public E getFirst() {
        return first.element;
    }

    /**
     * @return the last element
     */
    public E getLast() {
        return last.element;
    }

    /**
     * @return the size of the list (number of elements)
     */
    public int size() {
        return size;
    }

    /**
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes the element at the specified position
     * 
     * @return the previous element
     */
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    /**
     * Removes the specified element
     * 
     * @return true
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes the first element
     * @return the first element 
     * @throws NoSuchElementException if list is empty
     */
    public E removeFirst() {
        final Node<E> f = first;
        if(f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    /**
     * Removes the last element
     * @return the last element
     * @throws NoSuchElementException if list is empty
     */
    public E removeLast() {
        final Node<E> l = last;
        if (last == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    /**
     * Removes all elements in this list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        for (Node<E> x = first ; x != null; ) {
            Node<E> next = x.next;
            x.element = null;
            x.prev = null;
            x.next = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    /**
     * @return An array version of the linked list
     */
    public Object[] toArray() {
        final Object[] array = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next) {
            array[i++] = x.element;
        }
        return array;
    }

    /**
     * Tells if argument is an index of an existing element.
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("Invalid Index: " + index + "Size = " + size);
    }

    /**
     * Tells if argument is the index of a valid position for iterator and add
     */
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("Invalid Index: " + index + "Size = " + size);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private Node<E> lastReturned;
        private Node<E> next = first;
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();
            
            lastReturned = next; 
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }
        
    }






}    