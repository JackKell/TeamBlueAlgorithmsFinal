public class Node {
    public String name;
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
