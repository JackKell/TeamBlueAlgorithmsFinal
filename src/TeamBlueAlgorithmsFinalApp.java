import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;


public class TeamBlueAlgorithmsFinalApp {

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

    public static void main(String [] args) throws IOException {

        int numVertices = 10; // Starting number of vertices
        double density = .5; // Desired density of edges

        ArrayList<Double> times_list = new ArrayList<>();
        double startTime, endTime;
        int p = numVertices;
        while (p <= 100){
            // Create random graph
            ColorGraph graph = randColorGraph(p, density);

            // Time DSatur on random graph
            startTime = (double)System.nanoTime();
            graph.BrelazDSatur(); // Note need to record the number of colors in the graph
            endTime = (double)System.nanoTime();
            times_list.add(endTime - startTime); // Record time

            p += 10;
        }

        System.out.print("in nanoseconds:  ");
        System.out.println(times_list);
        for (int x = 0; x < times_list.size(); x++){
            times_list.set(x, times_list.get(x) * 0.000001);
        }
        System.out.print("in milliseconds: ");
        System.out.print(times_list);

        String csvFile_ = "./out.csv";
        FileWriter writer = new FileWriter(csvFile_);

        p = numVertices;
        for(double x : times_list){
            writer.append("\n" + String.valueOf(p) + "," + String.valueOf(x)); // ...manually remove the first \n...
        }
        writer.flush();
        writer.close();

        /*
        //ColorGraph graph = new ColorGraph();
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
        */

        //graph.print();
        //graph.BrelazDSatur();
    }
}
