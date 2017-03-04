import java.util.*;

/**
 * A Simple Graph represents a undirected and unweighted graph data structure.
 * @param <T> The templated type that will act as the value for a vertex within the graph
 */
public class SimpleGraph<T> {
    // A hash map with a key of a templated vertex and a value of a hashset containing vertexes representing edges.
    protected HashMap<T, HashSet<T>> adjacencyMap;

    SimpleGraph() {
        adjacencyMap = new HashMap<>();
    }

    SimpleGraph(SimpleGraph<T> simpleGraph) {
        adjacencyMap = simpleGraph.adjacencyMap;
    }

    /**
     * Add a vertex to the graph and initializes the value of the vertex if it does not already exists
     * @param vertex The vertex that will be add as a key to the adjacency map.
     */
    void addVertex(T vertex) {
        // If the graph does not contain the given vertex then it will be initialized to an empty hashset of type T
        adjacencyMap.computeIfAbsent(vertex, k -> new HashSet<>());
    }

    /**
     * Adds a vertex to the graph and any vertexs that it is connected to. If a given vertex connection does not
     * currently exist in the graph it is also added to the graph. Additionally if the given vertex already exists
     * in the graph any new vertex in the given Iterable of neighbors will simply be to the list of edges for the given
     * vertex.
     * @param vertex The vertex being added to or extend within the graph.
     * @param neighbors The list of vertexes that have a edge conntecting them to the given vertex.
     */
    void addVertex(T vertex, Iterable<T> neighbors) {
        addVertex(vertex);
        for (T neighbor: neighbors) {
            addVertex(neighbor);
            addEdge(vertex, neighbor);
        }
    }

    /**
     * Removes a given vertex from the graph and all edges connected to the given vertex.
     * @param vertex The vertex to be removed.
     */
    void removeVertex(T vertex) {
        for (T key : adjacencyMap.get(vertex)) {
            adjacencyMap.get(key).remove(vertex);
        }
        adjacencyMap.remove(vertex);
    }

    /**
     * Connects a given vertex1 to another given vertex2.
     * @param vertex1
     * @param vertex2
     */
    void addEdge(T vertex1, T vertex2) {
        // If vertex1 is in the graph then add vertex2 to its hashset of edges
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).add(vertex2);
        // If vertex2 is in the graph then add vertex1 to its hashset of edges
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).add(vertex1);
    }

    /**
     * Removes an edge between a given vertex1 and another given vertex2.
     * @param vertex1
     * @param vertex2
     */
    void removeEdge(T vertex1, T vertex2) {
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).remove(vertex2);
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).remove(vertex1);
    }

    /**
     * Returns a set of the nodes contained within the graph.
     * @return Set of type T containing all of the vertexes within the graph.
     */
    Set<T> getNodes() {
        return adjacencyMap.keySet();
    }

    /**
     * Prints the current state of the adjacency map to the console.
     */
    void print() {
        System.out.println(adjacencyMap);
    }
}
