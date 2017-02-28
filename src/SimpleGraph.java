import java.util.*;

public class SimpleGraph<T> {
    private HashMap<T, HashSet<T>> adjacencyMap;

    public SimpleGraph() {
        adjacencyMap = new HashMap<>();
    }

    public SimpleGraph(SimpleGraph<T> simpleGraph) {
        adjacencyMap = simpleGraph.adjacencyMap;
    }

    public void addVertex(T vertex) {
        adjacencyMap.computeIfAbsent(vertex, k -> new HashSet<>());
    }

    public void addVertex(T vertex, List<T> neighbors) {
        addVertex(vertex);
        for (T neighbor: neighbors) {
            addVertex(neighbor);
            addEdge(vertex, neighbor);
        }
    }

    public void removeVertex(T vertex) {
        for (T key : adjacencyMap.keySet()) {
            adjacencyMap.get(key).remove(vertex);
        }
        adjacencyMap.remove(vertex);
    }

    public void addEdge(T vertex1, T vertex2) {
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).add(vertex2);
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).add(vertex1);
    }

    public void removeEdge(T vertex1, T vertex2) {
        adjacencyMap.computeIfPresent(vertex1, (k, v) -> v).remove(vertex2);
        adjacencyMap.computeIfPresent(vertex2, (k, v) -> v).remove(vertex1);
    }

    public void print() {
        System.out.println(adjacencyMap);
    }
}
