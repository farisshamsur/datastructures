package ds.stack;

import java.util.Iterator;

import ds.linkedlist.DLL;

/**
 * Doubly Linked List implementation of a Stack
 */
public class Stack<E> implements Iterable<E> {
    // Instance variables
    private DLL<E> list = new DLL<>();

    // Constructors

    // Create an empty stack
    public Stack(){ }

    // Create a stack with an initial element
    public Stack(E firstElement) {
        list.add(firstElement);
    }

    // Return the size of the stack
    public int size() { return list.size(); }

    // Check if stack is Empty
    public boolean isEmpty() { return size() == 0; }

    // Pushes an element onto the top of the stack.
    // @return the item argument 
    public E push(E e) {
        list.addFirst(e);
        return e;
    }

    // Removes the object at the top of this stack and returns that object as the value of this function.
    // @return top element of stack
    public E pop() {
        if(isEmpty())
            throw new java.util.EmptyStackException();
        return list.remove(0);
    }
    
    // Looks at the object at the top of this stack without removing it from the stack.
    // @return top element of stack
    public E peek() {
        if(isEmpty())
            throw new java.util.EmptyStackException();
        return list.getFirst();
    } 

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }


}