
package dsassignment;

import java.util.ArrayList;
import java.util.Random;

public final class MCTS <T extends Comparable<T>, N extends Comparable <N>>{
    
    int N;
    int level = 3;
    int[][][] policy = new int[level][N][N];
    int[][] globalPolicy = new int[N][N];
    int iterations = 100;
    int ALPHA = 1;
    double cost;
    
    ArrayList<T> unvisitedNodes;
    Graph<T,N> graph;
    int vehicleCapacity;
    
    public MCTS(Graph<T,N> graph, int vehicleCapacity) {
        this.graph = graph;
        this.vehicleCapacity = vehicleCapacity;
        unvisitedNodes = graph.getNeighbours(graph.getVertex(0)); //store customers in a list
        this.search(level, iterations); //execute the simulation
    }
    
    
    /*
* Input Parameters
 * level: int, refer README.md for more info
 * iterations: int, refer README.md for more info
 * Return Type
 * a_tour: class object, convey information about tour cost, all routes and etc. (depend on you)
    function search (level, iterations) {
    initialize best_tour with positive infinity cost
    if level = 0
        return rollout()
    else
        policy[level] = globalPolicy
        for iteration from 0 to iterations
            new_tour = search (level-1, iteration)
            if new_tour cost < best_tour cost
                best_tour = new_tour
                adapt(best_tour, level)
            // if the searching time is far too long then directly return the best tour we can search of after limited time
            if processing_time exceed time limit
                return best_tour
        globalPolicy = policy[level]
    return best_tour
    }
    */
    public ArrayList<ArrayList<T>> search(int level, int iterations) {
        ArrayList<ArrayList<T>> best_tour = new ArrayList<>();
       // double best_tour_cost = Double.POSITIVE_INFINITY; 
       // best_tour_cost == graph.calculateTour(best_tour);
        if(level== 0){
            return rollout();
        } else {
            policy[level] = globalPolicy;
            for (int i=0; i<iterations; i++){
                ArrayList<ArrayList<T>> new_tour = search(level - 1, iterations);
                if (graph.calculateTour((best_tour))< graph.calculateTour((new_tour))){
                    best_tour = new_tour;
                    adapt(best_tour, level);
                }
                if(System.currentTimeMillis() > 60000){ //if exceeds 60 seconds
                    return best_tour;
                }
            }
            globalPolicy = policy[level];
        }
        return best_tour;
    }
    

    
/*
 * Return Type
 * a_tour: class object, convey information about tour cost, all routes and etc. (depend on you)
function rollout () {
    initialize new_tour with first route with first stop at 0  // every route must start and end at depot (ID=0)
    while true
        currentStop = new_tour last route last stop
        find every possible successors that is not yet checked for the currentStop
        if no successors is available
            currentRoute is completed and should return to depot
            if all stop are visited
                break while loop  // rollout process is done
            add new route into new_tour  // else add new vehicle, again start at depot with ID 0
            continue  // skip to next loop to continue search a route for new vehicle
        nextStop = select_next_move(currentStop, possible_successors)
        if add nextStop to currentRoute does not violate any rules
            add nextStop to currentRoute
            set nextStop as visited
        else
            set nextStop as checked
    return new_tour
}
    */
    public ArrayList<ArrayList<T>> rollout() {
        ArrayList<ArrayList<T>> new_tour = new ArrayList<>();
        ArrayList<T> currentRoute = new ArrayList<>();
        currentRoute.add(graph.getVertex(0));
        new_tour.add(currentRoute);
        int n = new_tour.size();
        while (true) {
            T currentStop = new_tour.get(n-1).get(n-1);
            ArrayList<T> possible_successors = graph.getNeighbours(currentStop);
            if (possible_successors.isEmpty()) {
                currentRoute.add(graph.getVertex(0));
                if (unvisitedNodes.isEmpty()) {
                    break;
                }
                new_tour.add(new ArrayList<>());
                continue;
            }
            T nextStop = select_next_move(currentStop, possible_successors);
            if (!currentRoute.contains(nextStop)) {
                currentRoute.add(nextStop);
                unvisitedNodes.remove(nextStop);
            } else {
                
            }
        }
        return new_tour;
        
    }
    
    
    
    
 /*
 * Input Parameters
 * currentStop: depend, it can be an int or node class as long as it convey information about what is your current node 
 * possible_successors: depend, it can be a list of int or list of node class objects as long as it convey information about what is the possible move from your current node
 * [More explanations] let say I want search from A to B or C or D, then my currentStop is A, and if B is searched/checked and it is impossible to be next node of A anymore,
 * then my possible_successors are C and D only. The 1d probability array will then be an double array of size 2 (2 possible nodes C and D).
 * Return Type
 * selected_successor: depend, it can be an int or node class as long as it convey information about what is the node selected to move next from current node
function select_next_move(currentStop, possible_successors) {
    initialize 1d probability array that have same size with possible_successors
    sum = 0
    for i=0 to size of possible_successors
        probability[i] = Math.exp(globalPolicy[currentStop][possible_successors[i]])
        sum += probability[i]
    mrand = new Random().nextDouble() * sum
    i = 0;
    sum = probability[0];
    while sum < mrand
        sum += probability[++i];
    return possible_successors[i]
  }
*/
    public T select_next_move(T currentStop, ArrayList<T> possible_successors) {
        int n = possible_successors.size();
        int[] probability = new int[n];
        double sum = 0;
        for (int i=0; i<n; i++) {
            probability[i] = (int) Math.exp(globalPolicy[(Integer)currentStop][(Integer)possible_successors.get(i)]);
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
    
    /*
 * Input Parameters
 * a_tour: class object, convey information about tour cost, all routes and etc. (depend on you)
 * level: int, refer README.md for more info
 * Return Type
 * No object returned (void)
function adapt (a_tour, level) {
    for every route in a_tour
        for every stop in route
            policy[level][stop][next_stop] += ALPHA
            z = 0.0
            for every possible move that can be made by stop
                if the move is not visited yet
                    z += Math.exp(globalPolicy[stop][move]);
            for every possible move that can be made by stop
                if the move is not visited yet
                    policy[level][stop][move] -= ALPHA * (Math.exp(globalPolicy[stop][move]) / z)
            set stop as visited
}
    */
    public void adapt(ArrayList<ArrayList<T>> a_tour, int level) {
        for (ArrayList<T> route: a_tour) {
            for (T stop: route) {
                T next_stop = route.get(route.indexOf(stop)+1);
                policy[level][(Integer)stop][(Integer)next_stop] += ALPHA;
                double z = 0.0;
                for (T move: graph.getNeighbours(stop)) {  //need to check
                    if (unvisitedNodes.contains(move)) 
                         z += Math.exp(globalPolicy[(Integer)stop][(Integer)move]);
                }
                for (T move: graph.getNeighbours(stop)) {
                    if (unvisitedNodes.contains(move)) 
                        policy[(Integer)level][(Integer)stop][(Integer)move] -= ALPHA * (Math.exp(globalPolicy[(Integer)stop][(Integer)move]) / z);
                }
                unvisitedNodes.remove(stop);
            }
        }
    }

    
    
}
