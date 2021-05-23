
import java.util.ArrayList;

/*
Personal Notes:
The vehicle must always return to the depot in the end. /

Path Conditions:
Must be lowest cost (flexible). /
Must not exceed max load. /
Must not be visited. /

Notes for Group Mates:
The pathList is not private, so you can easily access it.
*/

public class GreedySimulation<T extends Comparable<T>> {
    ArrayList<ArrayList<T>> pathList;
    ArrayList<T> unvisitedNodes;
    Graph<T> input;
    int vehicleCapacity;
    
    public GreedySimulation(Graph<T> input, int vehicleCapacity) {
        this.input = input;
        pathList = new ArrayList<>();
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = input.getNeighbours(input.getVertex(0)); //store customers in a list
        this.search(); //execute the simulation
    }
    
    private void search() {
        int demandCount = 0; //track the current load size
        ArrayList<T> path = new ArrayList<>(); //store the path of the current vehicle
        path.add(input.getVertex(0));
        T current = input.getVertex(0); //start at depot
        
        look:
        while (!unvisitedNodes.isEmpty()) { //loops until all nodes are visited
            ArrayList<T> currentNeighbours = input.getNeighbours(current);

            //remove all paths to visited nodes or that result in demandCount > vehicleCapacity
            for (int i=0; i<currentNeighbours.size(); i++) {
                T temp = currentNeighbours.get(i);
                if (!unvisitedNodes.contains(temp) || demandCount + input.getDemandSize(temp) > vehicleCapacity) {
                    currentNeighbours.remove(temp);
                    i--;
                }
            }
            
            //when there are no possible paths
            if (currentNeighbours.isEmpty()) {
                path.add(input.getVertex(0));
                pathList.add(path); //store finished path 
                current = input.getVertex(0); //return to depot
                demandCount = 0; //reset demand counter
                path = new ArrayList<>(); //start new path for next vehicle
                path.add(input.getVertex(0));
                continue look;
            }

            //bubble sort the leftover nodes
            for (int i=0; i<currentNeighbours.size()-1; i++) {
                for (int j=0; j<currentNeighbours.size()-1-i; j++) {
                    if (input.calculateCost(current, currentNeighbours.get(j)) > input.calculateCost(current, currentNeighbours.get(j+1))) {
                        T hold = currentNeighbours.get(j);
                        currentNeighbours.set(j, currentNeighbours.get(j+1));
                        currentNeighbours.set(j+1, hold);
                    }
                }
            }
            
            //go to the lowest cost node
            T lowestCost = currentNeighbours.get(0);
            path.add(lowestCost);
            demandCount += input.getDemandSize(lowestCost);
            current = lowestCost;
            unvisitedNodes.remove(lowestCost);
            
            //ensure the last vehicle path is stored
            if (unvisitedNodes.isEmpty()) {
                path.add(input.getVertex(0));
                pathList.add(path);
            }
        }
    }
}