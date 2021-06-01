package dsassignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Update Notes:
1/6  - Changed type parameter from String to Integer.
*/

public class Main <T> {
    static int N, C;
    static Graph<Integer> graph;
    
    public static void main(String[] args) {
        
        buildGraph(); //accept graph and store as a graph
        
        System.out.println("Greedy Simulation");
        GreedySearch<Integer> test = new GreedySearch(graph, C);
        System.out.println(graph.calculateTour(test.pathList));
        
        System.out.println("\nMCTS Simulation");
        MCTSSearch<Integer> test2 = new MCTSSearch(graph, C);
        System.out.println(graph.calculateTour(test2.pathList));
        
    }
    
    public static void buildGraph() {
        graph = new Graph<>();
        int[] vertices = null;
        
        try{
            try (Scanner scan = new Scanner(new FileInputStream("input.txt"))) {
                N = scan.nextInt();
                C = scan.nextInt();
                int i = 0;
                vertices = new int[N];
                while(scan.hasNext()) {
                    vertices[i] = i;
                    //Adds a vertex that has (ID, xCoordinate, yCoordinate, demandSize)
                    graph.addVertex(vertices[i], scan.nextInt(), scan.nextInt(), scan.nextInt());
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
        //To create connected edges
        for (int i=0; i<graph.getSize(); i++) {
            for (int j=0; j<graph.getSize(); j++) {
                if (i!= j)
                    graph.addEdge(vertices[i], vertices[j]);
            }
        }
    }
}
