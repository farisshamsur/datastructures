/**
 * Doubly-linked list implementation of the List Interface. 
 * Does not implement all List operations.
 */

package ds.linkedlist;

import java.util.Iterator;

public class LinkedListt<E> implements Iterable<E> {

    // Nested Node class
    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(final Node<E> p, final E e, final Node<E> n) {
            this.element = e;
            this.prev = p;
            this.next = n;
        }
    }
    // End of Nested Node Class

    // Linked list properties 
    private Node<E> first; // pointer to first Node
    private Node<E> last; // pointer to last Node
    private int size = 0;

    // Constructor

    public LinkedListt() {}

    // Links e as first element (add to front ie leftmost)
    void linkFirst(final E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<E>(null, e, f);
        first = newNode;

        if (f == null) // if LinkedList was empty
            last = newNode;
        else
            first.prev = newNode;
        size++;
    }

    // Link e as last element (add to last ie rightmost)
    void linkLast(final E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<E>(l, e, null);
        last = newNode;

        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    // Inserts element e before non-null Node succ
    void linkBefore(final E e, final Node<E> succ) {
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<E>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    // Unlinks non-null Node x.
    // @return Returns the previous element at the specified index
    E unlink(final Node<E> x) {
        final E element = x.element;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if(next == null) { // unlinking tail
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        if(prev == null) { //unlinking head
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        x.element = null;
        size--;
        return element;
    }

    // Returns the (non-null) Node at the specified element index.
    Node<E> node(final int index) {

        if(index < (size >> 1)) {
            Node<E> x = first;
            for(int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for(int i = size-1; i > index; i++) {
                x = x.prev;
            }
            return x;
        }
    }

    // Unlinks Node x
    
    
    // Inserts the specified element at the beginning of this list (leftmost)
    public void addFirst(final E e) {
        linkFirst(e);
    }

    // Appends the specified element to the end of this list.
    public void addLast(final E e) {
        linkLast(e);
    }

    // Adds element to the end of the list (rightmost)
    public boolean add(final E e) {
        linkLast(e);
        return true;
    }

    private void checkIndex(final int index) {
        if(index < 0 ||  index > size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    // Adds element to the specified index. Shifts the initial element and any subsequent elements to the right
    public void add(final int index, final E element) throws IndexOutOfBoundsException {
        checkIndex(index);

        if(index == size)
            linkLast(element);
        else 
            linkBefore(element, node(index));

    }

    // Replaces the element in list with specified element
    // @return the element previously in the list
    public E set(final int index, final E element) {
        checkIndex(index);

        final Node<E> node = node(index);
        final E prevE = node.element;
        node.element = element;

        return prevE;
    }

    // Returns the index of the first occurence of the specified element, or -1 if element not in the list
    public int indexOf(final Object o) {
        int index = 0;

        if(o == null) {
            for(Node<E> x = first; x != null; x = x.next) {
                if(x.element == null)
                    return index;
                index++;
            }
        } else {
            for(Node<E> x = first; x != null; x = x.next) {
                if(o.equals(x.element))
                    return index;
                index++;
            }
        }
        return -1;
    }

    // Returns true if this list contains the element
    public boolean contains (final Object o) {
        return indexOf(o) != -1;
    }

    // Returns the element at the specified index
    public E get(final int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return node(index).element;
    }

    // Returns the first element in the list
    public E getFirst() {
        return first.element;
    }

    //Returns the last element in the list
    public E getLast() {
        return last.element;
    }

    // Returns the number of elements in the list
    public int size() {return size;}

    // Returns true if list contains no elements
    public boolean isEmpty() {
        return size == 0;
    }

    // Removes the element at the specified position. Shifts subsequent elements to the left.
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return unlink(node(index));
    }

    // Removes the first specified element. Returns true on success, false otherwise
    public boolean remove(final Object o){
        if(o == null) {
            for(Node<E> x = first; x != null; x = x.next) {
                if(x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for(Node<E> x = first; x != null; x = x.next) {
                if(o.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    // Returns an array containing all of the elements in this list in proper sequence (from first to last element).
    public Object[] toArray() {
        final Object[] array = new Object[size];
        int index = 0;

        for(Node<E> x = first; x != null; x = x.next) {
            array[index++] = x.element;
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> x = first;
        while(x.next != null) {
            sb.append(x.element + ", ");
            x = x.next;
        }
        sb.append(x.element + "]");
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private Node<E> trav = first;

            @Override
            public boolean hasNext() {
                return trav.next != null;
            }

            // Returns current element, traverses to next element
            // @return Returns the current element
            @Override
            public E next() {
                final E data = trav.element;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
    }

}