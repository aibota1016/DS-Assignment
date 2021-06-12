
package deliveryroute;

import java.util.ArrayList;

public class BFSSearch <T extends Comparable<T>>{
    ArrayList<ArrayList<T>> pathList;
    ArrayList<T> unvisitedNodes;
    Graph<T> graph;
    int vehicleCapacity;
     
    public BFSSearch(Graph<T> graph, int vehicleCapacity) {
        this.graph = graph;
        pathList = new ArrayList<>();
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = graph.getNeighbours(graph.getVertex(0)); //store customers in a list
        execute(); //execute the simulation
    }
    
    private void execute() {
        while (!unvisitedNodes.isEmpty()) {
            ArrayList<T> path = new ArrayList<>();
            path = search();
            pathList.add(path);
            unvisitedNodes.removeAll(path);
        }
    }
    
    private ArrayList<T> search () {
        //store possible paths
        ArrayList<T> temp = new ArrayList<>();
        ArrayList<T> path = new ArrayList<>();
        
        //initial state
        Queue<T> fringe = new Queue<>();
        T currentNode = graph.getVertex(0);
        fringe.enqueue(currentNode);
        
        while (!fringe.isEmpty()) {
            //reset load
            int load = 0;
            
            //get the head of the queue
            currentNode = fringe.dequeue();
            if (!currentNode.equals(graph.getVertex(0)))
                load += graph.getDemandSize(currentNode);
            
            //get possible successors
            ArrayList<T> successors = graph.getNeighbours(currentNode);
            
            //remove previous nodes and adjust load
            T parent = graph.getParent(currentNode);
            while (parent!=null) {
                if (successors.contains(parent)) {
                    successors.remove(parent);
                }
                
                if (!parent.equals(graph.getVertex(0)))
                    load += graph.getDemandSize(parent);
                
                parent = graph.getParent(parent);
            }
            
            //remove nodes that violate the rules
            for (int i=0; i<successors.size(); i++) {
                if (!unvisitedNodes.contains(successors.get(i)) || load + graph.getDemandSize(successors.get(i)) > vehicleCapacity) {
                    successors.remove(i);
                    i--;
                }
                else {
                    fringe.enqueue(successors.get(i));
                    graph.setParent(successors.get(i), currentNode);
                }
            }
            
            //check for goal node
            if (successors.isEmpty()) {
                temp.add(graph.getVertex(0));
                temp.add(currentNode);
                T ancestor = graph.getParent(currentNode);
                
                while (ancestor!=null) {
                    temp.add(ancestor);
                    ancestor = graph.getParent(ancestor);
                }
                
                if (path.isEmpty()) {
                    path = temp;
                }
                else if (getRouteCost(temp) < getRouteCost(path)) {
                    path = temp;
                }
                
                temp = new ArrayList<>();
            }
        }
        return path;
    }

    private double getRouteCost(ArrayList<T> route) {
        double routeCost = 0;
        for (int i=0; i<route.size()-1; i++)
            routeCost += graph.calculateDistance(route.get(i), route.get(i+1));
        return routeCost;
    }
}
