package nz.ac.auckland.se281.datastructures.stackandqueue;

/**
 * A class representing a LinkedList data structure.
 *
 * @param <T> the type of elements stored in the queue
 */
public class LinkedList<T> {

  // Instance field.
  private Node<T> head;
  private Node<T> tail;
  private int size;

  public LinkedList() {
    this.size = 0;
  }

  /**
   * Method to add data to the list.
   *
   * @param data the data to add.
   */
  public void add(T data) {

    // Create a new node with the data stored inside.
    Node<T> newNode = new Node<T>(data);
    if (size == 0) { // if the list is empty,
      // the new node is both the head and the tail.
      head = newNode;
      tail = newNode;
    } else { // Otherwise, append the new node to the tail.
      tail.setNext(newNode);
      tail = newNode;
    }
    size++;
  }

  /**
   * Method to get the size of the list.
   *
   * @param index the index to get the data from.
   * @return the size of the list.
   */
  public T get(int index) throws IndexOutOfBoundsException {

    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }

    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      current = current.getNext();
    }
    return current.getData();
  }

  /**
   * Method to insert data into the list.
   *
   * @param index the index to insert the data at.
   * @param data the data to insert.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public void insert(int index, T data) throws IndexOutOfBoundsException {

    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }

    Node<T> newNode = new Node<>(data);

    if (index == 0) { // inserting at head
      newNode.setNext(head);
      head = newNode;
      if (size == 0) { // if the list was empty, the new node is also the tail
        tail = newNode;
      }
    } else if (index == size) { // inserting at tail
      tail.setNext(newNode);
      tail = newNode;
    } else { // inserting somewhere in the middle
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) { // find the node preceding the insertion point
        current = current.getNext();
      }
      newNode.setNext(current.getNext());
      current.setNext(newNode);
    }
    size++;
  }

  /**
   * Method to get the size of the list.
   *
   * @return the size of the list.
   */
  public int size() {
    return this.size;
  }

  /**
   * Method to check if the list is empty.
   *
   * @return true if this list contains no elements.
   */
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if data
   * not found.
   *
   * @param data the data to search for.
   * @return the index of data.
   */
  public int indexOf(T data) {

    Node<T> current = head;
    int index = 0;

    while (current != null) {
      if (current.getData().equals(data)) {
        return index;
      }
      current = current.getNext();
      index++;
    }
    return -1; // data not found in the list
  }
}
