package nz.ac.auckland.se281.datastructures.stackandqueue;

import java.util.NoSuchElementException;

/**
 * A class representing a stack data structure.
 *
 * @param <T> the type of elements stored in the stack
 */
public class Stack<T> {

  // Instance field.
  private Node<T> top;
  private int size;

  public Stack() {
    this.top = null;
    this.size = 0;
  }

  /**
   * Pushes an element onto the top of the stack.
   *
   * @param data the element to be pushed
   */
  public void push(T data) {
    Node<T> newNode = new Node<T>(data);
    newNode.setNext(top);
    top = newNode;
    size++;
  }

  /**
   * Pops an element from the top of the stack.
   *
   * @return the popped element
   * @throws NoSuchElementException if the stack is empty
   */
  public T pop() {
    if (isEmpty()) {
      throw new NoSuchElementException("Cannot pop from an empty stack");
    }

    T data = top.getData();
    top = top.getNext();
    size--;
    return data;
  }

  /**
   * Retrieves the element at the top of the stack without removing it.
   *
   * @return the element at the top of the stack
   * @throws NoSuchElementException if the stack is empty
   */
  public T peek() {
    if (isEmpty()) {
      throw new NoSuchElementException("Cannot peek an empty stack");
    }
    return top.getData();
  }

  /**
   * Returns the size of the stack.
   *
   * @return the size of the stack
   */
  public int size() {
    return this.size;
  }

  /**
   * Checks if the stack is empty.
   *
   * @return true if the stack is empty, false otherwise
   */
  public boolean isEmpty() {
    return this.size == 0;
  }
}
