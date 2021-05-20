
package dsassignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DSAssignment <T> {


    public static void main(String[] args) {
        
        int N, C = 0;
        Graph<String, Integer> graph = new Graph<>();
        String[] vertices = null;
        
        try{
            try (Scanner scan = new Scanner(new FileInputStream("input.txt"))) {
                N = scan.nextInt();
                C = scan.nextInt();
                vehicle.setCapacity(C);
                int i =0;
                vertices = new String[N];
                while(scan.hasNext()) {
                    vertices[i] = String.valueOf(i);
                    //Adds a vertex that has (ID, xCoordinate, yCoordinate, demandSize)
                    graph.addVertex(vertices[i], scan.nextInt(), scan.nextInt(), scan.nextInt());
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
        //Testing 
        System.out.println(graph.getAllVertexObjects());
        for (String v: vertices) {
            System.out.println("Coordinates of " + v + " :" + graph.getXCor(v) + " " + graph.getYCor(v) + " Demand Size: " + graph.getDemandSize(v));
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
