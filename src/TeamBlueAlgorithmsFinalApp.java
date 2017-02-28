import java.util.*;

public class TeamBlueAlgorithmsFinalApp {
    public static void main(String [] args) {
        SimpleGraph<String> graph = new SimpleGraph<>();
        graph.print();
        graph.addVertex("A", new ArrayList<>(Arrays.asList("A", "B", "C", "D")));
        graph.print();
        graph.addVertex("D");
        graph.print();
        graph.removeVertex("A");
        graph.print();
    }
}
