
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Difference with Greedy Search:
- Maintains an openList of all unremoved nodes.
- Has a heuristic.
*/

public class AStarSearch<T extends Comparable<T>> {
    Graph<T> graph;
    int vehicleCapacity;
    ArrayList<T> unvisitedNodes;
    ArrayList<ArrayList<T>> pathList;

    public AStarSearch(Graph<T> graph, int vehicleCapacity) {
        this.graph = graph;
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = graph.getNeighbours(graph.getVertex(0));
        pathList = new ArrayList<>();
        this.calculateHeuristic();
        this.execute(); //execute the simulation
    }
    
    private void execute() {
        int vehicle = 1;
        while (!unvisitedNodes.isEmpty()) {
            ArrayList<T> path = new ArrayList<>();
            pathList.add(path);
            path.add(graph.getVertex(0));
            T endNode = search();
            path.add(endNode);
            T parent = graph.getParent(endNode);
            while (parent!=null) {
                path.add(parent);
                parent = graph.getParent(parent);
            }
            unvisitedNodes.removeAll(path);
            Collections.reverse(path);
        }
    }
    
    private T search() {
        int load = 0; //track the load size
        T currentNode = graph.getVertex(0);
        ArrayList<T> parents = new ArrayList<>();
        ArrayList<T> openNodes = new ArrayList<>();
        ArrayList<T> closedNodes = new ArrayList<>();
        openNodes.add(graph.getVertex(0)); //start at depot
        
        while (!openNodes.isEmpty()) {
            //get lowest cost node
            T tempNode = openNodes.get(0);
            for (int i=0; i<openNodes.size()-1; i++)
                if (graph.getF(tempNode) > graph.getF(openNodes.get(i+1)))
                    tempNode = openNodes.get(i+1);
            
            //check parent            
            if (!tempNode.equals(graph.getVertex(0))) {
                if (!parents.contains(graph.getParent(tempNode))) {
                    parents.add(graph.getParent(tempNode));
                }
                else {
                    T parent = graph.getParent(currentNode);
                    while (parent!=null) {
                        if (parent.equals(graph.getParent(tempNode))) { 
                            load -= graph.getDemandSize(currentNode);
                            T backtrack = graph.getParent(currentNode);
                            while (backtrack!=parent) {
                                load -= graph.getDemandSize(backtrack);
                                backtrack = graph.getParent(backtrack);
                            }
                            break;
                        }
                        parent = graph.getParent(parent);
                    }
                }
            }

            //increase load
            currentNode = tempNode;
            load += graph.getDemandSize(currentNode);
            
            //get all possible successors
            ArrayList<T> successors = graph.getNeighbours(currentNode);
            for (int i=0; i<successors.size(); i++) {
                if (!unvisitedNodes.contains(successors.get(i)) || load + graph.getDemandSize(successors.get(i)) > vehicleCapacity) {
                    successors.remove(i);
                    i--;
                }
            }
            
            if (successors.isEmpty()) { //goal node
                return currentNode;
            }
            
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);
            
            for (T element : successors) {
                double totalCost = graph.getG(currentNode) + graph.calculateDistance(element, currentNode);
                
                if (!openNodes.contains(element) && !closedNodes.contains(element)) {
                    graph.setParent(element, currentNode);
                    graph.setG(element, totalCost);
                    graph.setF(element, graph.getG(element) + graph.getH(element));
                    openNodes.add(element);
                }
                else if (openNodes.contains(element)) {
                    graph.setParent(element, currentNode);
                    graph.setG(element, totalCost);
                    graph.setF(element, graph.getG(element) + graph.getH(element));
                }
                else {
                    if (totalCost < graph.getG(element)) {
                        graph.setParent(element, currentNode);
                        graph.setG(element, totalCost);
                        graph.setF(element, graph.getG(element) + graph.getH(element));
                        
                        if (closedNodes.contains(element)) {
                            closedNodes.remove(element);
                            openNodes.add(element);
                        }
                    }
                }
            }
        }
        return currentNode;
    }
    
    private void calculateHeuristic() {
        for (int i=0; i<graph.size; i++)
            graph.setH(graph.getVertex(i), graph.calculateDistance(graph.getVertex(i), graph.getVertex(0)));
    }
}