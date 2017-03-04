import javafx.util.Pair;

import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;


class ColorGraph extends SimpleGraph<Node>{
    private static final int minColor = 1;

    ArrayList<Pair<Node, Integer>> getAdjacencyDegrees() {
        final ArrayList<Pair<Node, Integer>> adjacencyDegrees = new ArrayList<>();
        adjacencyMap.forEach((key, value) -> adjacencyDegrees.add(new Pair<>(key, value.size())));
        // Sorting Reference
        // http://stackoverflow.com/questions/29920027/how-can-i-sort-a-list-of-pairstring-integer
        final Comparator<Pair<Node, Integer>> comparator = reverseOrder(comparing(Pair::getValue));
        adjacencyDegrees.sort(comparator);
        return adjacencyDegrees;
    }

    private HashSet<Integer> getNeighborColors(Node node) {
        final HashSet<Integer> neighborColors = new HashSet<>();
        if (adjacencyMap.containsKey(node)) {
            for (Node neighbor : adjacencyMap.get(node)) {
                neighborColors.add(neighbor.color);
            }
        }
        return neighborColors;
    }

    private Node getMostSaturatedNode(List<Node> nodes) {
        Node mostSaturatedNode = nodes.get(0);
        int highestSaturationDegree = getSaturationDegree(mostSaturatedNode);
        for (int i = 1; i < nodes.size(); i++) {
            Node currentNode = nodes.get(i);
            int currentSaturationDegree = getSaturationDegree(currentNode);
            if (highestSaturationDegree < currentSaturationDegree) {
                mostSaturatedNode = currentNode;
                highestSaturationDegree = currentSaturationDegree;
            } else if (highestSaturationDegree == currentSaturationDegree) {
                if (getAdjacencyDegree(mostSaturatedNode) < getAdjacencyDegree(currentNode)) {
                    mostSaturatedNode = currentNode;
                    highestSaturationDegree = currentSaturationDegree;
                }
            }
        }
        return mostSaturatedNode;
    }

    private int getSaturationDegree(Node node) {
        return getNeighborColors(node).size();
    }

    private int getAdjacencyDegree(Node node) {
        return adjacencyMap.get(node).size();
    }

    void Dsatur() {
        List<Node> uncoloredNodes = new ArrayList<>();
        for (Node node : adjacencyMap.keySet()) {
            node.color = 0;
            uncoloredNodes.add(node);
        }
        while (uncoloredNodes.size() != 0) {
            final Node currentNode = getMostSaturatedNode(uncoloredNodes);
            final HashSet<Integer> neighborColors = getNeighborColors(currentNode);
            int assignedColor = minColor;
            while(neighborColors.contains(assignedColor)) assignedColor++;
            currentNode.color = assignedColor;
            uncoloredNodes.remove(currentNode);
        }
    }

    void clearColors() {
        adjacencyMap.forEach((key, value) -> key.color = 0);
    }

    void printNodeColors() {
        adjacencyMap.forEach((key, value) -> System.out.println(key.name + ", " + key.color));
    }
}
