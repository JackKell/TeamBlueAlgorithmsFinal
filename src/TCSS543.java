import javafx.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This program is used to answer the question posed in the TCSS543A – Winter 2017 Homework 3: Programming Project.
 * The following implements and tests the Dsatur graph color algorithm.
 *
 * @author Dan Gray
 * @author Brandon Olson
 * @since 2017-2-27
 */
public class TCSS543 {
    /**
     * The main function outputs the average run times to the given output file.
     *
     * @param args output-file
     */
    public static void main(String [] args) {
        String outputFile = args[0];
        outputDsaturAverageRunTimes(outputFile, 100, 100, 10);
        outputDsaturAverageMinColor("output2.txt", 100, 100, 10);
    }

    /**
     * This function outputs the results of a getDsaturAverageRunTimes() call to a given output file
     *
     * @param outputFile The location where the results will be stored.
     * @param numberOfGraphsPerTest The number of graphs generated to compute each run time average.
     * @param maxNumberOfVertices The max number of vertices that will be tested in any graph.
     * @param stepSize The rate at which vertices will be added the current number of vertices to be tested.
     */
    private static void outputDsaturAverageRunTimes(String outputFile, int numberOfGraphsPerTest, int maxNumberOfVertices, int stepSize) {
        try {
            FileWriter csvFile = new FileWriter(outputFile);

            for(Pair<Integer, Double> averageRunTime : getDsaturAverageRunTimes(numberOfGraphsPerTest, maxNumberOfVertices, stepSize)){
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

    /**
     * This function outputs the results of a getDsaturAverageMinColors() call to a given output file
     *
     * @param outputFile The location where the results will be stored.
     * @param numberOfGraphsPerTest The number of graphs generated to compute each run time average.
     * @param maxNumberOfVertices The max number of vertices that will be tested in any graph.
     * @param stepSize The rate at which vertices will be added the current number of vertices to be tested.
     */
    private static void outputDsaturAverageMinColor(String outputFile, int numberOfGraphsPerTest, int maxNumberOfVertices, int stepSize) {
        try {
            FileWriter csvFile = new FileWriter(outputFile);

            for(Pair<Integer, Double> averageRunTime : getDsaturAverageMinColors(numberOfGraphsPerTest, maxNumberOfVertices, stepSize)){
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

    /**
     * Gets a list of pairs of graph sizes and corresponding average run times to color them using the Dsatur algorithm.
     *
     * @param numberOfGraphsPerTest The number of graphs generated to compute each run time average.
     * @param maxNumberOfVertices The max number of vertices that will be tested in any graph.
     * @param stepSize The rate at which vertices will be added the current number of vertices to be tested.
     * @return a list of pairs of graph sizes and corresponding run times to color them.
     */
    private static List<Pair<Integer, Double>> getDsaturAverageRunTimes(int numberOfGraphsPerTest, int maxNumberOfVertices, int stepSize) {
        List<Pair<Integer, Double>> averageRunTimes = new ArrayList<>();
        for (int numberOfVertices = 0; numberOfVertices <= maxNumberOfVertices; numberOfVertices += stepSize) {
            System.out.println("Calculating Average Run Time For " + numberOfVertices + " Vertices...");
            averageRunTimes.add(new Pair<>(numberOfVertices, getAverageRunTimeOfDsatur(numberOfGraphsPerTest, numberOfVertices)));
        }
        return averageRunTimes;
    }

    /**
     * Gets a list of pairs of graph sizes and corresponding average minimum number of colors needed to color the graph
     * using the Dsatur algorithm.
     *
     * @param numberOfGraphsPerTest The number of graphs generated to compute each run time average.
     * @param maxNumberOfVertices The max number of vertices that will be tested in any graph.
     * @param stepSize The rate at which vertices will be added the current number of vertices to be tested.
     * @return a list of pairs of graph sizes and corresponding minimum number of colors.
     */
    private static List<Pair<Integer, Double>> getDsaturAverageMinColors(int numberOfGraphsPerTest, int maxNumberOfVertices, int stepSize) {
        List<Pair<Integer, Double>> averageMinColors = new ArrayList<>();
        for (int numberOfVertices = 0; numberOfVertices <= maxNumberOfVertices; numberOfVertices += stepSize) {
            System.out.println("Calculating Average Min Color Count For " + numberOfVertices + " Vertices...");
            averageMinColors.add(new Pair<>(numberOfVertices, getAverageMinColorOfDsatur(numberOfGraphsPerTest, numberOfVertices)));
        }
        return  averageMinColors;
    }

    /**
     * This function gets the average run time to color a given number of randomly generated graphs with 4 different
     * groups each having a different random edge density range and colored using the Dsatur algorithm.
     *
     * @param numberOfGraphs The total number of graphs that will be generated to compete the average.
     * @param numberOfVertices The total number of vertices in each of the generated graphs.
     * @return double The average time in milliseconds to color the graph using the Dsatur Algorithm.
     */
    private static double getAverageRunTimeOfDsatur(int numberOfGraphs, int numberOfVertices) {
        List<ColorGraph> colorGraphs = randColorGraphs(numberOfGraphs, numberOfVertices);

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

    /**
     * This function gets the average number of colors needed to color a graph using the Dsatur algorithm.
     *
     * @param numberOfGraphs The total number of graphs that will be generated to compete the average.
     * @param numberOfVertices The total number of vertices in each of the generated graphs.
     * @return double The average number of colors need to color the graph.
     */
    private static double getAverageMinColorOfDsatur(int numberOfGraphs, int numberOfVertices) {
        List<ColorGraph> colorGraphs = randColorGraphs(numberOfGraphs, numberOfVertices);

        double totalMinColorCount = 0;
        for (ColorGraph graph: colorGraphs) {
            graph.Dsatur();
            totalMinColorCount += graph.getColorCount();
        }

        return totalMinColorCount / numberOfGraphs;
    }

    /**
     * Generates a list of random generated graphs with 4 different and equally sized density groups.
     *
     * @param numberOfGraphs The number of graphs to be returned in the list.
     * @param numberOfVertices The number of vertices in each graph.
     * @return List of the randomly generated graphs.
     */
    private static List<ColorGraph> randColorGraphs(int numberOfGraphs, int numberOfVertices) {
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
        return colorGraphs;
    }

    /**
     * Generates a random color graph given a number of vertices and edge edge density
     *
     * @param numVertices The number of nodes within the graph.
     * @param edgeDensity The average density of edges per vertex.
     * @return ColorGraph that is randomly generated.
     */
    private static ColorGraph randColorGraph(int numVertices, double edgeDensity){
        ColorGraph graph = new ColorGraph();

        // Create vertices (nodes)
        for (int i = 0; i < numVertices; i++) {
            graph.addVertex(new Node("n" + String.valueOf(i)));
        }

        // Create edges based on uniform distribution and desired edgeDensity
        for(Node node_k : graph.getNodes()){
            for(Node node_j : graph.getNodes()){
                if(Math.random() < edgeDensity && node_k != node_j){
                    // Include the edge
                    graph.addEdge(node_k,node_j);
                }
            }
        }

        return graph;
    }

    /**
     * This function represents a coloring graph problem from the slides in class and can be used to check to see if
     * the Dsatur implemenation is working properly.
     */
    private static void testColorGraph() {
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
