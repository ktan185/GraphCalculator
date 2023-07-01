package nz.ac.auckland.se281.datastructures.stackandqueue;

import java.util.NoSuchElementException;

/**
 * A class representing a queue data structure.
 *
 * @param <T> the type of elements stored in the queue
 */
public class Queue<T> {

  // Instance field.
  private Node<T> head;
  private Node<T> tail;
  private int size;

  /** Constructs an empty queue. */
  public Queue() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  /**
   * Enqueues an element to the back of the queue.
   *
   * @param data the element to be enqueued
   */
  public void enqueue(T data) {
    Node<T> newNode = new Node<>(data);

    if (isEmpty()) {
      // If the queue is empty, the new node becomes both the head and the tail.
      this.head = newNode;
      this.tail = newNode;
    } else {
      // If the queue is not empty, append the new node to the tail.
      this.tail.setNext(newNode);
      this.tail = newNode;
    }
    size++;
  }

  /**
   * Dequeues an element from the front of the queue.
   *
   * @return the dequeued element
   * @throws NoSuchElementException if the queue is empty
   */
  public T dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Cannot dequeue from an empty queue");
    }

    T data = this.head.getData();
    head = this.head.getNext();
    size--;
    return data;
  }

  /**
   * Retrieves the element at the front of the queue without removing it.
   *
   * @return the element at the front of the queue
   * @throws NoSuchElementException if the queue is empty
   */
  public T peek() {
    if (isEmpty()) {
      throw new NoSuchElementException("Cannot peek an empty queue");
    }
    return this.head.getData();
  }

  /**
   * Returns the size of the queue.
   *
   * @return the size of the queue
   */
  public int size() {
    return this.size;
  }

  /**
   * Checks if the queue is empty.
   *
   * @return true if the queue is empty, false otherwise
   */
  public boolean isEmpty() {
    return this.size == 0;
  }
}
