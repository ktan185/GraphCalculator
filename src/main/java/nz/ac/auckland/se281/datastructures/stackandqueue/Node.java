package nz.ac.auckland.se281.datastructures.stackandqueue;

/**
 * A class representing a node in a linked list.
 *
 * @param <T> the type of data stored in the node
 */
public class Node<T> {

  // Instance field.
  private T data;
  private Node<T> nextNode;

  public Node(T data) {
    this.data = data;
    this.nextNode = null;
  }

  /**
   * Retrieves the data stored in the node.
   *
   * @return the data stored in the node
   */
  public T getData() {
    return this.data;
  }

  /**
   * Sets the data stored in the node.
   *
   * @param data the data to be stored in the node
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Retrieves the reference to the next node in the linked list.
   *
   * @return the next node in the linked list
   */
  public Node<T> getNext() {
    return nextNode;
  }

  /**
   * Sets the reference to the next node in the linked list.
   *
   * @param next the next node to be set
   */
  public void setNext(Node<T> next) {
    this.nextNode = next;
  }

  /**
   * Returns a string representation of the node.
   *
   * @return a string representation of the node
   */
  @Override
  public String toString() {
    return data.toString();
  }
}
