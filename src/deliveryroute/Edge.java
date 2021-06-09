/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliveryroute;

//import javafx.scene.control.Label;
//import javafx.scene.shape.Shape;
//
//public class Edge {
//
//    public Vertex source, target;
//    public double weight;
//    public Shape line;
//    public Label weightLabel;
//
//    public Shape getLine() {
//        return line;
//    }
//
//    public Edge(Vertex argSource, Vertex argTarget) {
//        source = argSource;
//        target = argTarget;
//        weight = 0;
//    }
//
//    public Edge(Vertex argSource, Vertex argTarget, double argWeight, Shape argline, Label weiLabel) {
//        source = argSource;
//        target = argTarget;
//        weight = argWeight;
//        line = argline;
//        this.weightLabel = weiLabel;
//    }
//
//}


public  class Edge <T extends Comparable<T>> {
    Vertex<T> toVertex;
    Edge<T> nextEdge;

    public Edge() {
        toVertex = null;
        nextEdge = null;
    }
    
    public Edge(Vertex<T> destination, Edge<T> a) {
        toVertex = destination;
        nextEdge = a;
    }
}