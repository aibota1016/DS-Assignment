
package deliveryroute;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class MCTSSearch <T extends Comparable<T>> {

    //hyperparameters and policies
    int level = 3;
    double[][][] policy;
    double[][] globalPolicy;
    int iterations = 100;
    int ALPHA = 1;

    Instant startTime;
    ArrayList<T> unvisitedNodes;
    ArrayList<ArrayList<T>> pathList;
    Graph<T> graph;
    int vehicleCapacity;

    public MCTSSearch(Graph<T> graph, int vehicleCapacity) {
        this.graph = graph;
        int N = graph.getSize();
        globalPolicy = new double[N][N];
        policy = new double[level][N][N];
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = graph.getNeighbours(graph.getVertex(0)); //store customers in a list
        startTime = Instant.now(); //start timer
        pathList = search(level, iterations); //execute the simulation
    }

    private ArrayList<ArrayList<T>> search(int level, int iterations) {
        ArrayList<ArrayList<T>> best_tour = new ArrayList<>();
        if(level == 0){
            return rollout();
        } else {
            policy[level-1] = globalPolicy;
            for (int i=0; i<iterations; i++) {
                ArrayList<ArrayList<T>> new_tour = search(level - 1, iterations);
                if (getTourCost(new_tour) < getTourCost(best_tour)) {
                    best_tour = new_tour;
                    adapt(best_tour, level);
                }
                if (i==0) {
                    best_tour = new_tour;
                    adapt(best_tour, level);
                }
                if (Duration.between(startTime, Instant.now()).toMinutes()>1) { //end if more than 1 minute
                    return best_tour;
                }
            }
            globalPolicy = policy[level-1];
        }

        return best_tour;
    }

    private ArrayList<ArrayList<T>> rollout() {
        ArrayList<ArrayList<T>> new_tour = new ArrayList<>();
        ArrayList<T> unvisitedNodes = new ArrayList<>();
        ArrayList<T> checkedNodes = new ArrayList<>();
        ArrayList<T> currentRoute = new ArrayList<>();
        unvisitedNodes.addAll(this.unvisitedNodes);
        currentRoute.add(graph.getVertex(0));
        new_tour.add(currentRoute);
        int load = 0;

        //until all customers are visited
        while (!unvisitedNodes.isEmpty()) { 
            T currentStop = currentRoute.get(currentRoute.size()-1);
            ArrayList<T> possible_successors = graph.getNeighbours(currentStop);

            //remove all unfeasible succesors
            for (int i=0; i<possible_successors.size(); i++) {
                T element = possible_successors.get(i);
                if (checkedNodes.contains(element) || !unvisitedNodes.contains(element)) {
                    possible_successors.remove(element);
                    i--;
                }
            }

            //no moves left, start new route
            if (possible_successors.isEmpty()) {
                currentRoute.add(graph.getVertex(0));
                if (unvisitedNodes.isEmpty()) { //all customers are visited
                    break;
                }
                currentRoute = new ArrayList<>();
                currentRoute.add(graph.getVertex(0));
                new_tour.add(currentRoute);
                checkedNodes.clear();
                load = 0;
                continue;
            }

            //decide next stop
            T nextStop = select_next_move(currentStop, possible_successors);
            if (graph.getDemandSize(nextStop) + load <= vehicleCapacity) {
                currentRoute.add(nextStop);
                load += graph.getDemandSize(nextStop);
                unvisitedNodes.remove(nextStop);
            }
            else {
                checkedNodes.add(nextStop);
            }

            if (unvisitedNodes.isEmpty())
                currentRoute.add(graph.getVertex(0));
        }

        return new_tour;
    }

    private T select_next_move(T currentStop, ArrayList<T> possible_successors) {
        int n = possible_successors.size();
        double[] probability = new double[n];
        double sum = 0;
        for (int i=0; i<n; i++) {
            probability[i] = Math.exp(globalPolicy[(Integer)currentStop][(Integer)possible_successors.get(i)]);
            sum += probability[i];
        }
        double mrand = new Random().nextDouble() * sum;
        int i = 0;
        sum = probability[0];
        while(sum<mrand) {
            sum += probability[++i];
        }
        return possible_successors.get(i);
    }

    private void adapt(ArrayList<ArrayList<T>> a_tour, int level) {
        ArrayList<T> unvisitedNodes = new ArrayList<>();
        unvisitedNodes.addAll(this.unvisitedNodes);

        for (ArrayList<T> route: a_tour) {
            for (T stop : route) {
                if (route.indexOf(stop)==route.size()-1)
                    break;
                T next_stop = route.get(route.indexOf(stop)+1);
                policy[level-1][(Integer)stop][(Integer)next_stop] += ALPHA;
                double z = 0.0;
                for (T move: graph.getNeighbours(stop)) {
                    if (unvisitedNodes.contains(move)) 
                        z += Math.exp(globalPolicy[(Integer)stop][(Integer)move]);
                }
                for (T move: graph.getNeighbours(stop)) {
                    if (unvisitedNodes.contains(move)) 
                        policy[level-1][(Integer)stop][(Integer)move] -= ALPHA * (Math.exp(globalPolicy[(Integer)stop][(Integer)move]) / z);
                }
                unvisitedNodes.remove(stop);
            }
        }
    }

    private double getTourCost(ArrayList<ArrayList<T>> a_tour) {
        int n = a_tour.size();
        double tourCost = 0;
        for (int j=0; j<n; j++) {
            tourCost+= getRouteCost(a_tour.get(j));
        }
        return tourCost;
    }

    private double getRouteCost(ArrayList<T> route) {
        double routeCost = 0;
        for (int i=0; i<route.size()-1; i++)
            routeCost += graph.calculateDistance(route.get(i), route.get(i+1));
        return routeCost;
    }

}
