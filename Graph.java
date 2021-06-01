package dsassignment;

import java.util.ArrayList;

public class Graph <T extends Comparable<T>> {
    Vertex <T> head;
    int size;
    
    public Graph() {
        head = null;
        size = 0;
    }

    public int getSize() { //equals to the number of nodes
        return size;
    }
    
    public boolean hasVertex(T v) {
        if (head == null)
            return false;
        Vertex<T> temp = head;
        while(temp !=null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return true;
            temp = temp.nextVertex;
        }
        return false;
    }
    
    public int getXCor(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
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
            Vertex<T> temp = head;
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
            Vertex<T> temp = head;
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
            Vertex<T> temp = head;
            Vertex<T> newVertex = new Vertex<>(v, null, xCor, yCor, d) ;
            if (head == null)
                head = newVertex;
            else {
                Vertex<T> previous = head;
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
        Vertex<T> temp = head;
        for (int i=0; i<pos; i++) 
            temp = temp.nextVertex;
        return temp.vertexInfo;
    }
    
    public int getIndex(T v) {
        Vertex<T> temp = head;
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
        Vertex <T> temp = head;
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
        Vertex<T> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Vertex<T> destinationVertex = head;
                while(destinationVertex!=null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        Edge<T> currentEdge = sourceVertex.firstEdge;
                        Edge<T> newEdge = new Edge<>(destinationVertex, currentEdge);
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
        Vertex<T> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Edge<T> currentEdge = sourceVertex.firstEdge;
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
        Vertex<T> temp = head;
        while(temp!=null) {
            if (temp.vertexInfo.compareTo(v) == 0) {
                Edge<T> currentEdge = temp.firstEdge;
                while(currentEdge!=null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge = currentEdge.nextEdge;
                }
            }
            temp = temp.nextVertex;
        }
        return list;
    }
    
    public double calculateDistance(T src, T dest) { //euclidean distance between two points
        int x1 = getXCor(src);
        int x2 = getXCor(dest);
        int y1 = getYCor(src);
        int y2 = getYCor(dest);
        double d = Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2);
        return Math.sqrt(d);
    }    
    
    private double calculateCost(ArrayList<T> vertices) { //path cost and demand of one vehicle
        int n = vertices.size();
        
        for (int a=0; a<n; a++) { //prints out the path
            if (a!= (n-1))
                System.out.print(vertices.get(a) + " -> ");
            else {
                System.out.print(vertices.get(a));
            }
        }
        System.out.println("");
        
        double pathCost = 0; //total path cost of a vehicle
        int vehicleLoad = 0; //total load of a vehicle
        int i=0;
        int j=1;
        
        while(i<(n-1)) { //calculates the pathCost
            pathCost += calculateDistance(vertices.get(i), vertices.get(j));
            i++;
            j++;
        }
        for (int a=0; a<vertices.size(); a++) { //calculates the vehicleLoad
            vehicleLoad += getDemandSize(vertices.get(a));
        }
        
        System.out.println("Capacity: " + vehicleLoad);
        System.out.println("Cost: " + pathCost);
        return pathCost;
    }
    
    public double calculateTour(ArrayList<ArrayList<T>> arr) { //prints out results of a search
        System.out.println("Tour");
        int n = arr.size(); //equals to the number of vehicles needed
        double tourCost = 0;
        for (int j=0; j<n; j++) {
            System.out.println("Vehicle " + (j+1));
            tourCost+= calculateCost(arr.get(j));
        }
        System.out.print("Tour Cost: ");
        return tourCost;
    }
    
    public void printEdges() {
        Vertex<T> temp = head;
        while(temp!=null) {
            System.out.print("#" + temp.vertexInfo + " : ");
            Edge<T> currentEdge = temp.firstEdge;
            while(currentEdge!=null) {
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo + "] ");
                currentEdge = currentEdge.nextEdge;
            }
            System.out.println();
            temp = temp.nextVertex;
        }
    }
}
