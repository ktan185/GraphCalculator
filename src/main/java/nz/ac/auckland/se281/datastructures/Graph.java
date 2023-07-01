package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import nz.ac.auckland.se281.datastructures.stackandqueue.LinkedList;
import nz.ac.auckland.se281.datastructures.stackandqueue.Queue;
import nz.ac.auckland.se281.datastructures.stackandqueue.Stack;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  // Instance field.
  private Set<T> vertices;
  private Set<Edge<T>> edges;
  private HashMap<T, LinkedList<Edge<T>>> adjacencyMap;

  /**
   * Creates a graph with the given verticies and edges.
   *
   * @param verticies is the vertices of the graph.
   * @param edges is the edges of the graph.
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.adjacencyMap = new HashMap<T, LinkedList<Edge<T>>>();
    this.vertices = verticies;
    this.edges = edges;
    createAdjacencyMap(verticies, edges);
  }

  /**
   * Returns the roots in the graph.
   *
   * @return returns roots of the graph in numberical assending order.
   */
  public Set<T> getRoots() {

    Set<T> roots = new TreeSet<T>(getComparator());

    // Determine if the vertex has InDegree of 0,
    // If it does then it is a root.
    for (T vertex : vertices) {
      if (isInDegreeZero(vertex) && isOutDegreeOne(vertex)) {
        roots.add(vertex);
      }
    }
    // If the graph is an equvalence relation,
    // Determine the lowest vertex in the equvalence relation.
    if (isEquivalence()) {
      for (T vertex : vertices) {
        List<T> vertexList = new ArrayList<>();
        vertexList.addAll(getEquivalenceClass(vertex));
        roots.add(Collections.min(vertexList));
      }
    }
    return roots;
  }

  /**
   * Determines if the graph is reflexive.
   *
   * @return returns true if the graph is reflexive.
   */
  public boolean isReflexive() {
    int count = 0;

    // Loop through all edges and determine amount of edges that are reflexive.
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(edge.getDestination())) {
        count++;
      }
    }
    // Return true only if the number of reflexive edges is
    // equivalent to the number of vertices in the graph.
    return count == this.vertices.size();
  }

  /**
   * Determines if the graph is symmetric.
   *
   * @return returns true if the graph is symmetric.
   */
  public boolean isSymmetric() {

    Edge<T> temp;

    for (Edge<T> edge : edges) {
      T v1 = edge.getSource();
      T v2 = edge.getDestination();
      // Create temporary edge with the source and destination fliped
      temp = new Edge<>(v2, v1);
      if (!(edges.contains(temp))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if the graph is transitive.
   *
   * @return returns true if the graph is transitive.
   */
  public boolean isTransitive() {
    for (Edge<T> edge : edges) {
      // Create temporary edge with the source and destination fliped.
      T v1 = edge.getSource();
      T v2 = edge.getDestination();

      for (Edge<T> edgeFromV2 : edges) {

        if (edgeFromV2.getSource().equals(v2)) {
          T v3 = edgeFromV2.getDestination();
          Edge<T> temp = new Edge<>(v1, v3);
          // if the graph does not contain an edge from v2 to v3,
          // then the graph is not transitive.
          if (!edges.contains(temp)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Determines if the graph is anti-symmetric.
   *
   * @return returns true if the graph is anti-symmetric.
   */
  public boolean isAntiSymmetric() {

    // Loop through the set of edges,
    for (Edge<T> edge : edges) {
      // get the source and destination of the edge.
      T v1 = edge.getSource();
      T v2 = edge.getDestination();
      // if the source and destination are not equal,
      if (!v1.equals(v2)) {
        // create temporary edge with the source and destination fliped.
        Edge<T> reversedEdge = new Edge<>(v2, v1);
        // If the graph contains the reversed edge,
        // then the graph is not anti-symmetric.
        if (edges.contains(reversedEdge)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Determines if the graph is an equivalence relation.
   *
   * @return returns true if the graph is an equivalence relation.
   */
  public boolean isEquivalence() {
    return isReflexive() && isSymmetric() && isTransitive();
  }

  /**
   * Method to return the equivalence class of a vertex.
   *
   * @param vertex is the vertex being checked.
   * @return returns the equivalence class of a vertex.
   */
  public Set<T> getEquivalenceClass(T vertex) {

    // Initialise varaibles
    Set<T> equivalenceClass = new HashSet<>();
    LinkedList<Edge<T>> adjacent = this.adjacencyMap.get(vertex);

    if (!isEquivalence()) {
      return equivalenceClass;
    }

    for (int i = 0; i < adjacent.size(); i++) {
      equivalenceClass.add(adjacent.get(i).getDestination());
    }
    return equivalenceClass;
  }

  /**
   * Iterative function that impliments BFS.
   *
   * @return returns the list of vertices found by BFS.
   */
  public List<T> iterativeBreadthFirstSearch() {

    Queue<T> found = new Queue<>();
    Set<T> visited = new HashSet<>();
    List<T> foundVertices = new ArrayList<>();

    for (T root : getRoots()) {
      found.enqueue(root);
      visited.add(root);

      // While the queue is not empty,
      while (!found.isEmpty()) {
        // Dequeue the queue and add to list of found vertices,
        // granted the list does not already contain the vertex
        T currentVertex = found.dequeue();
        foundVertices.add(currentVertex);

        LinkedList<Edge<T>> vertexEdges = adjacencyMap.get(currentVertex);

        for (int i = 0; i < vertexEdges.size(); i++) {
          T vertex = vertexEdges.get(i).getDestination();
          if (!visited.contains(vertex)) {
            found.enqueue(vertex);
            visited.add(vertex);
          }
        }
      }
    }
    return foundVertices;
  }

  /**
   * Iterative function that impliments DFS.
   *
   * @return returns the list of vertices found by DFS.
   */
  public List<T> iterativeDepthFirstSearch() {

    List<T> output = new ArrayList<>();
    Stack<T> stack = new Stack<>();
    Set<T> visited = new HashSet<>();

    for (T root : getRoots()) {

      visited.add(root);
      stack.push(root);

      while (!stack.isEmpty()) {
        // sets the current vertex to the top of the stack.
        T currentVertex = stack.peek();

        if (!adjacencyMap.get(currentVertex).isEmpty()) {
          // Gets the List of edges connected to vertex.
          LinkedList<Edge<T>> vertexEdges = adjacencyMap.get(currentVertex);
          output.add(stack.pop()); // removes from stack and adds to output.

          for (int i = vertexEdges.size() - 1; i > -1; i--) {
            if (!visited.contains(vertexEdges.get(i).getDestination())) {
              // adds all connected vertices to the stack.
              stack.push(vertexEdges.get(i).getDestination());
              visited.add(vertexEdges.get(i).getDestination());
            }
          }
        } else {
          if (!stack.isEmpty()) {
            output.add(stack.pop());
          }
        }
      }
    }
    return output;
  }

  /**
   * Recursive function that impliments BFS.
   *
   * @return returns the list of vertices found by BFS.
   */
  public List<T> recursiveBreadthFirstSearch() {

    // Initialise datastructures
    Set<T> found = new HashSet<>();
    List<T> vertices = new ArrayList<>();
    Queue<T> queue = new Queue<>();

    // Loop through ordered roots for determinisism
    for (T root : getRoots()) {
      queue.enqueue(root);
      searchNeighbour(found, vertices, queue);
    }
    return vertices;
  }

  /**
   * Recursive function that searches the adjacent vertices of the graph.
   *
   * @param currentVertex is the current vertex level being searched.
   * @param found is the list of vertices that have been found.
   * @param vertices is the output of the function.
   * @param queue is the current vertices in queue.
   * @return returns the helper function itself.
   */
  private void searchNeighbour(Set<T> found, List<T> vertices, Queue<T> queue) {
    // Base condition: if the queue is empty, then return visited vertices
    if (queue.isEmpty()) {
      return;
    }
    // 'Dequeue': take the next vertex from the start of the queue
    T currentVertex = queue.dequeue();  
    found.add(currentVertex);
    vertices.add(currentVertex);
    LinkedList<Edge<T>> vertexEdges = adjacencyMap.get(currentVertex);

    // If this vertex is not already visited, add it to visited vertices and process it
    for (int i = 0; i < vertexEdges.size(); i++) {
      if (!found.contains(vertexEdges.get(i).getDestination())) {
        // If we have not visited this vertex before,
        // Add visit it now and add it to the queue.
        found.add(vertexEdges.get(i).getDestination());
        queue.enqueue(vertexEdges.get(i).getDestination());
      }
    }

    // Recursive call with updated queue and visitedVertices
    searchNeighbour(found, vertices, queue);
  }

  /**
   * Recursive function that searches the next depth of the graph.
   *
   * @return returns the list of vertices found by DFS.
   */
  public List<T> recursiveDepthFirstSearch() {

    List<T> vertices = new ArrayList<>();
    Stack<T> stack = new Stack<>();
    Set<T> visited = new HashSet<>();

    // Go through the roots in order for determinisim
    for (T root : getRoots()) {
      stack.push(root); // add the root to the stack.
      // Call the recursive function to search the next depth.
      searchNextDepth(visited, vertices, stack);
    }
    return vertices;
  }

  /**
   * Recursive function that searches the next depth of the graph.
   *
   * @param currentVertex is the current vertex level being searched.
   * @param found is the set of vertices that have been found.
   * @param vertices is the list of vertices that have been found.
   * @param stack is the current stack of vertices found.
   * @return the function itself.
   */
  private void searchNextDepth(Set<T> found, List<T> vertices, Stack<T> stack) {
    // Base condition: if the stack is empty, then return visited vertices
    if (stack.isEmpty()) {
      return;
    }

    // add to the list of vertices the found vertex,
    found.add(stack.peek());
    T currentVertex = stack.peek();
    vertices.add(stack.pop());
    LinkedList<Edge<T>> vertexEdges = this.adjacencyMap.get(currentVertex);

    if (!vertexEdges.isEmpty()) {
      for (int i = vertexEdges.size() - 1; i > -1; i--) {
        if (!found.contains(vertexEdges.get(i).getDestination())) {
          stack.push(vertexEdges.get(i).getDestination());
          found.add(vertexEdges.get(i).getDestination());
        }
      }
    } else {
      if (!stack.isEmpty() && !found.contains(stack.peek())) {
        vertices.add(stack.pop());
      }
    }
    searchNextDepth(found, vertices, stack);
  }

  /**
   * Creates an adjacency map of the graph.
   *
   * @param vertices is the list of vertices in the graph.
   * @param edges is the list of edges in the graph.
   */
  private void createAdjacencyMap(Set<T> vertices, Set<Edge<T>> edges) {

    List<T> sortVertices = new ArrayList<>();
    for (T vertex : vertices) {
      sortVertices.add(vertex);
    }
    // Sorts the list based on numberical order.
    Collections.sort(sortVertices, getComparator());
    // go through the sorted vertecies and add the edges to the adjacency map.
    for (T vertex1 : sortVertices) {
      LinkedList<Edge<T>> orderedEdges = new LinkedList<>();
      for (T vertex2 : sortVertices) {
        Edge<T> adjacent = new Edge<T>(vertex1, vertex2);
        if (edges.contains(adjacent)) {
          orderedEdges.add(adjacent);
        }
      } // Return the adjacency Map where each linked list is ordered.
      this.adjacencyMap.put(vertex1, orderedEdges);
    }
  }

  /**
   * Determines if the vertex has an in-degree of zero.
   *
   * @param vertex is the vertex being checked.
   * @return returns true if the vertex has an in-degree of zero.
   */
  private Boolean isInDegreeZero(T vertex) {
    Boolean isRoot = true;
    for (Edge<T> edge : edges) {
      // If the current vertex is not a destination of any edge,
      // then the in-degree is zero
      if (edge.getDestination().equals(vertex)) {
        isRoot = false;
        return isRoot;
      }
    }
    return isRoot;
  }

  /**
   * Determines if the vertex has an out-degree of one.
   *
   * @param vertex is the vertex being checked.
   * @return returns true if the vertex has an out-degree of one.
   */
  private Boolean isOutDegreeOne(T vertex) {

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Creates a comparator for the to compare vertices numerically.
   * 
   * 
   * @return returns the comparator.
   */
  private Comparator<T> getComparator() {
    return new Comparator<T>() {
        @Override
        public int compare(T vertex1, T vertex2) {
          return Integer.compare(Integer.parseInt(vertex1.toString()),
           Integer.parseInt(vertex2.toString()));
        }
    };
  }

}
