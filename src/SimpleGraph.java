import java.util.*;

public class SimpleGraph<T> {
    protected HashMap<T, HashSet<T>> adjacencyMap;

    SimpleGraph() {
        adjacencyMap = new HashMap<>();
    }

    SimpleGraph(SimpleGraph<T> simpleGraph) {
        adjacencyMap = simpleGraph.adjacencyMap;
    }

    void addVertex(T vertex) {
        adjacencyMap.computeIfAbsent(vertex, k -> new HashSet<>());
    }

    void addVertex(T vertex, Iterable<T> neighbors) {
        addVertex(vertex);
        for (T neighbor: neighbors) {
            addVertex(neighbor);
            addEdge(vertex, neighbor);
        }
    }

    void removeVertex(T vertex) {
        for (T key : adjacencyMap.keySet()) {
            adjacencyMap.get(key).remove(vertex);
        }
        adjacencyMap.remove(vertex);
    }

    void addEdge(T vertex1, T vertex2) {
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).add(vertex2);
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).add(vertex1);
    }

    void removeEdge(T vertex1, T vertex2) {
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).remove(vertex2);
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).remove(vertex1);
    }

    void print() {
        System.out.println(adjacencyMap);
    }
}
