
package dsassignment;

import java.util.ArrayList;
import java.util.*;

public class Graph <T extends Comparable<T>, N extends Comparable <N>>{
    Vertex <T,N> head;
    int size;
    
    public Graph() {
        head = null;
        size = 0;
    }

       
    
    //get number of vertices
    public int getSize() {
        return size;
    }
    
    public boolean hasVertex(T v) {
        if (head == null)
            return false;
        Vertex<T,N> temp = head;
        while(temp !=null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return true;
            temp = temp.nextVertex;
        }
        return false;
    }
    
    public int getXCor(T v) {
        if (hasVertex(v) == true) {
            Vertex<T,N> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.xCor;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }

    public int getYCor(T v) {
        if (hasVertex(v) == true) {
            Vertex<T,N> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.yCor;
                temp =temp.nextVertex;
            }
         }
        return -1;
   }
    
    public int getDemandSize(T v) {
        if (hasVertex(v) == true) {
            Vertex<T,N> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.demand;
                temp =temp.nextVertex;
            }
         }
        return -1;
   }
    
    
    public boolean addVertex(T v, int xCor, int yCor, int d) {
        if (hasVertex(v) == false) {
            Vertex<T,N> temp = head;
            Vertex<T,N> newVertex = new Vertex<>(v, null, xCor, yCor, d) ;
            if (head == null)
                head = newVertex;
            else {
                Vertex<T,N> previous = head;
                while(temp != null) {
                    previous = temp;
                    temp = temp.nextVertex;
                } 
                previous.nextVertex = newVertex;
            }
            size++;
            return true;
        } else {
            return false;
        } 
    }
    
    public T getVertex(int pos) {
        if (pos>size-1 || pos<0)
            return null;
        Vertex<T,N> temp = head;
        for (int i=0; i<pos; i++) 
            temp = temp.nextVertex;
        return temp.vertexInfo;
    }
    
    public int getIndex(T v) {
        Vertex<T,N> temp = head;
        int pos = 0;
        while(temp!=null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return pos;
            temp = temp.nextVertex;
            pos+=1;
        }
        return -1;
    }
        
    public ArrayList<T> getAllVertexObjects() {
        ArrayList<T> list = new ArrayList<>();
        Vertex <T,N> temp = head;
        while(temp!=null) {
            list.add(temp.vertexInfo);
            temp = temp.nextVertex;
        }
        return list;
    }
        
    public boolean addEdge(T source, T destination) {
        if (head == null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T,N> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Vertex<T,N> destinationVertex = head;
                while(destinationVertex!=null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        Edge<T,N> currentEdge = sourceVertex.firstEdge;
                        Edge<T,N> newEdge = new Edge<>(destinationVertex, currentEdge);
                        sourceVertex.firstEdge = newEdge;
                        return true;
                    }
                    destinationVertex = destinationVertex.nextVertex;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }
    
   
    public boolean hasEdge(T source, T destination) {
        if (head==null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T,N> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Edge<T,N> currentEdge = sourceVertex.firstEdge;
                while(currentEdge != null) {
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0)
                        return true;
                    currentEdge = currentEdge.nextEdge;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }
    
    
    public ArrayList<T> getNeighbours(T v) {
        if (!hasVertex(v))
            return null;
        ArrayList<T> list = new ArrayList<>();
        Vertex<T,N> temp = head;
        while(temp!=null) {
            if (temp.vertexInfo.compareTo(v) == 0) {
                Edge<T,N> currentEdge = temp.firstEdge;
                while(currentEdge!=null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge = currentEdge.nextEdge;
                }
            }
            temp = temp.nextVertex;
        }
        return list;
    }
    
    /**
     * @param src - source vertex
     * @param dest - destination vertex
     * @return calculation of distance between two points using Euclidean distance formula
     */
    public double calculateDistance(T src, T dest) {
        int x1 = getXCor(src);
        int x2 = getXCor(dest);
        int y1 = getYCor(src);
        int y2 = getYCor(dest);
        double d = Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2);
        return Math.sqrt(d);
    }
    
    /**
     * @param arr - the route of one vehicle
     * @return calculation of the tour cost, and capacity of one vehicle
     */
    public double calculateCost(int[] arr) {
        ArrayList<T> vertices = new ArrayList<>();
        int[] demands = new int[arr.length];
        int n=arr.length;
        for (int a=0; a<arr.length; a++) {
            vertices.add(getVertex(arr[a]));  //converts array elements to a vertex, and stores in arraylist
            demands[a] = getDemandSize(getVertex(arr[a])); //stores the demand size of each vertex in array
            if (a!= (n-1))
                System.out.print(arr[a] + " -> "); //printing out the path
            else {
                System.out.print(arr[a]);
            }
        }
        System.out.println("");
        double tour = 0; //tour cost of each vehicle
        int capacity = 0; //capacity of each vehicle
        int i=0;
        int j=1;      
        //calculates tour cost of each vehicle
        while(i<(n-1)) {
            tour += calculateDistance(vertices.get(i), vertices.get(j));
            i++;
            j++;
        }
        //calculates capacity of each vehicle
        for (int a=0; a<demands.length; a++) {
            capacity+=demands[a];
        }
        System.out.println("Capacity: " + capacity);
        System.out.println("Cost: " + tour );
        return tour;
    }
    
    /** Method to calculate total cost and integrates each vehicle tour
     * If you guys want to test out your simulation results, can use this method
     * @param arr - 2D array returned by the algorithms
     * @return the total tour cost
     */
    public double calculateTour(int[][] arr) {
        System.out.println("Tour");
        int n = arr.length; //equals to the number of vehicles needed
        double totalTourCost = 0; //total tour cost
        int i=0;
        for (int j=0; j<arr[i].length; j++) {
            System.out.println("Vehicle " + (j+1));
            totalTourCost+= calculateCost(arr[j]);
        }
        System.out.print("Tour Cost: ");
        return totalTourCost;
    }


    public void printEdges() {
        Vertex<T,N> temp = head;
        while(temp!=null) {
            System.out.print("#" + temp.vertexInfo + " : ");
            Edge<T,N> currentEdge = temp.firstEdge;
            while(currentEdge!=null) {
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo + "] ");
                currentEdge = currentEdge.nextEdge;
            }
            System.out.println();
            temp = temp.nextVertex;
        }
    }
    
    
    
}
