
package deliveryroute;


import java.util.ArrayList;

public class GreedySearch<T extends Comparable<T>> {
    ArrayList<ArrayList<T>> pathList;
    ArrayList<T> unvisitedNodes;
    Graph<T> graph;
    int vehicleCapacity;
    
    public GreedySearch(Graph<T> graph, int vehicleCapacity) {
        this.graph = graph;
        pathList = new ArrayList<>();
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = graph.getNeighbours(graph.getVertex(0)); //store customers in a list
        this.search(); //execute the simulation
    }
    
    private void search() {
        int demandCount = 0; //track the load size
        ArrayList<T> path = new ArrayList<>(); //store the current path
        path.add(graph.getVertex(0));
        T current = graph.getVertex(0); //start at depot
        
        look: //until all nodes are visited
        while (!unvisitedNodes.isEmpty()) { 
            ArrayList<T> currentNeighbours = graph.getNeighbours(current);
            
            //remove all unfeasible paths
            for (int i=0; i<currentNeighbours.size(); i++) {
                T temp = currentNeighbours.get(i);
                if (!unvisitedNodes.contains(temp) || demandCount + graph.getDemandSize(temp) > vehicleCapacity) {
                    currentNeighbours.remove(temp);
                    i--;
                }
            }
            
            //when there are no possible paths
            if (currentNeighbours.isEmpty()) {
                path.add(graph.getVertex(0));
                pathList.add(path);
                current = graph.getVertex(0);
                demandCount = 0;
                path = new ArrayList<>();
                path.add(graph.getVertex(0));
                continue look;
            }
            
            //find lowest cost node
            T lowestCost = currentNeighbours.get(0);
            for (int i=0; i<currentNeighbours.size()-1; i++) {
                if(graph.calculateDistance(current, lowestCost) > graph.calculateDistance(current, currentNeighbours.get(i+1)))
                    lowestCost = currentNeighbours.get(i+1);
            }
            
            //go to lowest cost node
            path.add(lowestCost);
            demandCount += graph.getDemandSize(lowestCost);
            current = lowestCost;
            unvisitedNodes.remove(lowestCost);
            
            //ensure the last vehicle path is stored
            if (unvisitedNodes.isEmpty()) {
                path.add(graph.getVertex(0));
                pathList.add(path);
            }
        }
    }
}