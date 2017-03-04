import javafx.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TeamBlueAlgorithmsFinalApp {
    public static void main(String [] args) {
        outputDsaturAverageRunTime("out.csv", 1000, 10);
    }

    public static void outputDsaturAverageRunTime(String outputFile, int maxNumberOfVertices, int stepSize) {
        try {
            FileWriter csvFile = new FileWriter(outputFile);

            for(Pair<Integer, Double> averageRunTime : getAverageRunTimesOfDstaur(maxNumberOfVertices, stepSize)){
                csvFile.append(String.valueOf(averageRunTime.getKey()));
                csvFile.append(", ");
                csvFile.append(String.valueOf(averageRunTime.getValue()));
                csvFile.append("\n");
            }

                csvFile.flush();
                csvFile.close();
            }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pair<Integer, Double>> getAverageRunTimesOfDstaur(int maxNumberOfVertices, int stepSize) {
        List<Pair<Integer, Double>> averageRunTimes = new ArrayList<>();
        for (int numberOfVertices = 0; numberOfVertices <= maxNumberOfVertices; numberOfVertices += stepSize) {
            System.out.println("Calculating Run Time For " + numberOfVertices + " Vertices.");
            averageRunTimes.add(new Pair<>(numberOfVertices, getAverageRunTimeOfDsatur(numberOfVertices)));
        }
        return averageRunTimes;
    }

    public static double getAverageRunTimeOfDsatur(int numberOfVertices) {
        final int numberOfGraphs = 100;
        List<ColorGraph> colorGraphs = new ArrayList<>();
        for (int i = 1; i <= numberOfGraphs; i++) {
            final double groupPercentage = i / (double) numberOfGraphs;
            if (groupPercentage <= 0.25f) {
                colorGraphs.add(randColorGraph(numberOfVertices, Math.random() * (0.82 - 0.73) + 0.73));
            } else if (groupPercentage <= 0.5f) {
                colorGraphs.add(randColorGraph(numberOfVertices, Math.random() * (0.72 - 0.61) + 0.61));
            } else if (groupPercentage <= 0.75f) {
                colorGraphs.add(randColorGraph(numberOfVertices, Math.random() * (0.59 - 0.44) + 0.44));
            } else {
                colorGraphs.add(randColorGraph(numberOfVertices, Math.random() * (0.34 - 0.26) + 0.26));
            }
        }
        double totalRunTime = 0; // millisecond
        long start; // nanoseconds
        long end; // nanoseconds
        for (ColorGraph graph: colorGraphs) {
            start = System.nanoTime();
            graph.Dsatur();
            end = System.nanoTime();
            totalRunTime += TimeUnit.NANOSECONDS.toMillis(end - start);
        }
        return totalRunTime / numberOfGraphs;
    }

    // numVertices: The number of vertices to start with
    // density: The desired density of edges
    public static ColorGraph randColorGraph(int numVertices, double density){
        ColorGraph graph = new ColorGraph();

        // Create vertices (nodes)
        for (int i = 0; i < numVertices; i++) {
            graph.addVertex(new Node("n" + String.valueOf(i)));
        }

        // Create edges based on uniform distribution and desired density
        for(Node node_k : graph.getNodes()){
            for(Node node_j : graph.getNodes()){
                if(Math.random() < density && node_k != node_j){
                    // Include the edge
                    graph.addEdge(node_k,node_j);
                }
            }
        }

        return graph;

    }

    public static void testColorGraph() {
        ColorGraph graph = new ColorGraph();
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");
        Node H = new Node("H");
        Node I = new Node("I");
        Node J = new Node("J");
        Node K = new Node("K");
        Node L = new Node("L");
        Node M = new Node("M");
        Node N = new Node("N");
        graph.addVertex(A, Arrays.asList(B, E));
        graph.addVertex(B, Arrays.asList(C, G, F));
        graph.addVertex(C, Arrays.asList(D, I, G, H));
        graph.addVertex(D, Arrays.asList(I, J));
        graph.addVertex(E, Arrays.asList(K, B));
        graph.addVertex(F, Arrays.asList(K));
        graph.addVertex(H, Arrays.asList(K, G, L, M));
        graph.addVertex(I, Arrays.asList(M));
        graph.addVertex(M, Arrays.asList(J, L, N));
        graph.addVertex(J, Arrays.asList(N));
        graph.addVertex(G, Arrays.asList(K));
        graph.addVertex(L, Arrays.asList(K, N));
        graph.print();
        graph.Dsatur();
        graph.printNodeColors();
    }
}
