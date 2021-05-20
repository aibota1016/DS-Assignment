/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsassignment;

public  class Vertex<T extends Comparable<T>, N extends Comparable <N>> {
        T vertexInfo;
        int xCor;
        int yCor;
        int demand;
        Vertex<T,N> nextVertex;
        Edge<T,N> firstEdge;
        
        public Vertex() {
            vertexInfo = null;
            nextVertex = null;
            firstEdge = null;
        }
        public Vertex(T vInfo, Vertex<T,N> next, int x, int y, int d) {
            vertexInfo = vInfo;
            nextVertex = next;
            firstEdge = null;
            xCor = x;
            yCor = y;
            demand = d;
        }    
    }
