package dsassignment;

public  class Vertex<T extends Comparable<T>> {
    T vertexInfo;
    int xCor;
    int yCor;
    int demand;
    Vertex<T> nextVertex;
    Edge<T> firstEdge;

    public Vertex() {
        vertexInfo = null;
        nextVertex = null;
        firstEdge = null;
    }
    
    public Vertex(T vInfo, Vertex<T> next, int x, int y, int d) {
        vertexInfo = vInfo;
        nextVertex = next;
        firstEdge = null;
        xCor = x;
        yCor = y;
        demand = d;
    }    
}
