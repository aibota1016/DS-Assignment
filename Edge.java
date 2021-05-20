
package dsassignment;

public  class Edge <T extends Comparable<T>, N extends Comparable <N>> {
        Vertex<T,N> toVertex;
        Edge<T,N> nextEdge;
        
        public Edge() {
            toVertex = null;
            nextEdge = null;
        }
        public Edge(Vertex<T,N> destination, Edge<T,N> a) {
            toVertex = destination;
            nextEdge = a;
        }
    }
