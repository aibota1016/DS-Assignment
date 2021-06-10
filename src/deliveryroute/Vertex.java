package deliveryroute;

public  class Vertex<T extends Comparable<T>> {
    T vertexInfo;
    int xCor;
    int yCor;
    int demand;
    Vertex<T> nextVertex;
    Edge<T> firstEdge;
    
    T parent;
    double f = 0;
    double g = 0;
    double h = 0;

    public Vertex() {
        vertexInfo = null;
        nextVertex = null;
        firstEdge = null;
        parent = null;
    }
    
    public Vertex(T vInfo, Vertex<T> next, int x, int y, int d) {
        vertexInfo = vInfo;
        nextVertex = next;
        firstEdge = null;
        parent = null;
        xCor = x;
        yCor = y;
        demand = d;
    }

}
