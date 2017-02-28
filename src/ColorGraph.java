import javafx.util.Pair;

import java.util.ArrayList;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.Collections.singletonList;

import java.util.Comparator;
import java.util.HashSet;


class ColorGraph extends SimpleGraph<Node>{
    ArrayList<Pair<Node, Integer>> getAdjacencyDegrees() {
        final ArrayList<Pair<Node, Integer>> adjacencyDegrees = new ArrayList<>();
        adjacencyMap.forEach((key, value) -> adjacencyDegrees.add(new Pair<>(key, value.size())));
        // Sorting Reference
        // http://stackoverflow.com/questions/29920027/how-can-i-sort-a-list-of-pairstring-integer
        final Comparator<Pair<Node, Integer>> comparator = reverseOrder(comparing(Pair::getValue));
        adjacencyDegrees.sort(comparator);
        return adjacencyDegrees;
    }

    HashSet<Integer> getNeighborColors(Node node) {
        final HashSet<Integer> neighborColors = new HashSet<>();
        if (adjacencyMap.containsKey(node)) {
            for (Node neighbor : adjacencyMap.get(node)) {
                neighborColors.add(neighbor.color);
            }
            return neighborColors;
        } else {
            return null;
        }
    }

    void BrelazDSatur() {
        ArrayList<Pair<Node, Integer>> adjacencyDegrees = getAdjacencyDegrees();
        System.out.println(adjacencyDegrees);
        final int minColor = 1;
        for (Pair p: adjacencyDegrees) {
            final Node currentNode = (Node) p.getKey();
            final HashSet<Integer> neighborColors = getNeighborColors(currentNode);
            int assignedColor = minColor;
            while(neighborColors.contains(assignedColor)) assignedColor++;
            currentNode.color = assignedColor;
        }
        printNodeColors();
    }

    void printNodeColors() {
        adjacencyMap.forEach((key, value) -> {
            System.out.println(key.name + ", " + key.color);
        });
    }
}
