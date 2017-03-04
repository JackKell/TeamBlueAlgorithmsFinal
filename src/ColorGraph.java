import javafx.util.Pair;

import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

/**
 * The ColorGraph is a Simple Graph of type Node
 */
class ColorGraph extends SimpleGraph<Node>{
    // The minimum color that any node can be colored
    private static final int MIN_COLOR = 1;

    /**
     * This method is used to get the color values of the nodes connected to a given node.
     * @param node The given node that will be used to get a list of the connected nodes
     * @return HashSet<Integers> a unique set of all the connected colors to the given node
     */
    private HashSet<Integer> getNeighborColors(Node node) {
        final HashSet<Integer> neighborColors = new HashSet<>();
        if (adjacencyMap.containsKey(node)) {
            // Iterate over every node connected to the given node
            for (Node neighbor : adjacencyMap.get(node)) {
                neighborColors.add(neighbor.color);
            }
        }
        return neighborColors;
    }

    /**
     * Given a list of nodes contained within the graph this function returns the most saturated node (which is
     * the node neighboring the greatest number of colors) while using the node's adjacency degree to break ties between
     * equivalent saturation values.
     * @param nodes A List of nodes contained within the graph.
     * @return Node a node with the greatest saturation and greatest adjacency degree.
     */
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

    /**
     * Given a node contained within the graph this function get the number of unique colors that the given node
     * is connected to.
     * @param node The node that is being queried.
     * @return int The number of unique node colors that the given node is connected to.
     */
    private int getSaturationDegree(Node node) {
        return getNeighborColors(node).size();
    }

    /**
     * Given a node contained within the graph this function get the number of nodes that the given node is
     * connected to.
     * @param node The node that is being queried.
     * @return int The number of nodes that the given node is connected to.
     */
    private int getAdjacencyDegree(Node node) {
        return adjacencyMap.get(node).size();
    }

    /**
     * This function colors each of the nodes in the graph to use the smallest amount colors using the
     * Brélaz’s Dsatur algorithm.
     */
    void Dsatur() {
        List<Node> uncoloredNodes = new ArrayList<>();
        // Set all nodes to have a color of zero and add them to the uncolored nodes list
        for (Node node : adjacencyMap.keySet()) {
            node.color = 0;
            uncoloredNodes.add(node);
        }

        // While there are nodes that have yet to be colored
        while (uncoloredNodes.size() != 0) {
            final Node currentNode = getMostSaturatedNode(uncoloredNodes);
            final HashSet<Integer> neighborColors = getNeighborColors(currentNode);
            int assignedColor = MIN_COLOR;
            // If the current assigned color is being used by a neighbor continue to increment the assigned color
            while(neighborColors.contains(assignedColor)) assignedColor++;
            currentNode.color = assignedColor;
            uncoloredNodes.remove(currentNode);
        }
    }

    /**
     * This function return a unique set of the colors used in the graph.
     * @return Hashset of the color integers used in the graph.
     */
    HashSet<Integer> getColors() {
        HashSet<Integer> colors = new HashSet<>();
        adjacencyMap.forEach((key, value) -> colors.add(key.color));
        return colors;
    }

    /**
     * Gets the number of unique colors used in the graph.
     * @return An representing the number of unique colors.
     */
    int getColorCount() {
        return getColors().size();
    }

    /**
     * For every node in the graph, this function outputs the name of each node and the corresponding node color.
     */
    void printNodeColors() {
        adjacencyMap.forEach((key, value) -> System.out.println(key.name + ", " + key.color));
    }

    /**
     * Returns a list of pairs containing the node and its adjacency degree for all nodes in the graph.
     * @return ArrayList<> Containing Pairs of Nodes and the corresponding adjacency degree.
     */
    ArrayList<Pair<Node, Integer>> getAdjacencyDegrees() {
        final ArrayList<Pair<Node, Integer>> adjacencyDegrees = new ArrayList<>();
        adjacencyMap.forEach((key, value) -> adjacencyDegrees.add(new Pair<>(key, value.size())));
        // Sorting Reference
        // http://stackoverflow.com/questions/29920027/how-can-i-sort-a-list-of-pairstring-integer
        final Comparator<Pair<Node, Integer>> comparator = reverseOrder(comparing(Pair::getValue));
        adjacencyDegrees.sort(comparator);
        return adjacencyDegrees;
    }

    /**
     * This function sets the color of every node in the graph to 0.
     */
    void clearColors() {
        adjacencyMap.forEach((key, value) -> key.color = 0);
    }
}
