/**
 * A simple node class used to represent a vertex in a graph data structure.
 */
public class Node {
    // A unique name for the node
    public String name;
    // The nodes color value represented as an int
    public Integer color;

    Node(String name) {
        this.name = name;
        this.color = 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
