package deliveryroute;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Graph <T extends Comparable<T>> {
    Vertex <T> head;
    int size;
    static TextArea textArea = new TextArea();
    static TextField text = new TextField(); //for displaying the cycle
    
    public Graph() {
        head = null;
        size = 0;
    }

    public int getSize() { //equals to the number of nodes
        return size;
    }
    
    public boolean hasVertex(T v) {
        if (head == null)
            return false;
        Vertex<T> temp = head;
        while(temp !=null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return true;
            temp = temp.nextVertex;
        }
        return false;
    }
    
    public int getXCor(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.xCor;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }

    public int getYCor(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.yCor;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }
    
    public int getDemandSize(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) ==0)
                    return temp.demand;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }
    
       public double getH(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0)
                    return temp.h;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }
    
    public double getF(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0)
                    return temp.f;
                temp =temp.nextVertex;
            }
         }
        return -1;
    }
    
    public double getG(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0)
                    return temp.g;
                temp =temp.nextVertex;
            }
        }
        return -1;
    }
    
    public T getParent(T v) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0)
                    return temp.parent;
                temp =temp.nextVertex;
            }
        }
        return null;
    }
    
    public void setH(T v, double h) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0) {
                    temp.h = h;
                    return;
                }
                temp = temp.nextVertex;
            }
         }
    }
    
    public void setF(T v, double f) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0) {
                    temp.f = f;
                    return;
                }
                temp = temp.nextVertex;
            }
         }
    }
    
    public void setG(T v, double g) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0) {
                    temp.g = g;
                    return;
                }
                temp = temp.nextVertex;
            }
         }
    }
    
    public void setParent(T v, T parent) {
        if (hasVertex(v) == true) {
            Vertex<T> temp = head;
            while(temp!= null) {
                if (temp.vertexInfo.compareTo(v) == 0) {
                    temp.parent = parent;
                    return;
                }
                temp = temp.nextVertex;
            }
        }
    }
    
    public boolean addVertex(T v, int xCor, int yCor, int d) {
        if (hasVertex(v) == false) {
            Vertex<T> temp = head;
            Vertex<T> newVertex = new Vertex<>(v, null, xCor, yCor, d) ;
            if (head == null)
                head = newVertex;
            else {
                Vertex<T> previous = head;
                while(temp != null) {
                    previous = temp;
                    temp = temp.nextVertex;
                } 
                previous.nextVertex = newVertex;
            }
            size++;
            return true;
        } else {
            return false;
        } 
    }
    
    public T getVertex(int pos) {
        if (pos>size-1 || pos<0)
            return null;
        Vertex<T> temp = head;
        for (int i=0; i<pos; i++) 
            temp = temp.nextVertex;
        return temp.vertexInfo;
    }
    
    public int getIndex(T v) {
        Vertex<T> temp = head;
        int pos = 0;
        while(temp!=null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return pos;
            temp = temp.nextVertex;
            pos+=1;
        }
        return -1;
    }
        
    public ArrayList<T> getAllVertexObjects() {
        ArrayList<T> list = new ArrayList<>();
        Vertex <T> temp = head;
        while(temp!=null) {
            list.add(temp.vertexInfo);
            temp = temp.nextVertex;
        }
        return list;
    }
        
    public boolean addEdge(T source, T destination) {
        if (head == null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Vertex<T> destinationVertex = head;
                while(destinationVertex!=null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        Edge<T> currentEdge = sourceVertex.firstEdge;
                        Edge<T> newEdge = new Edge<>(destinationVertex, currentEdge);
                        sourceVertex.firstEdge = newEdge;
                        return true;
                    }
                    destinationVertex = destinationVertex.nextVertex;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }
    
    public boolean hasEdge(T source, T destination) {
        if (head==null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T> sourceVertex = head;
        while(sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Edge<T> currentEdge = sourceVertex.firstEdge;
                while(currentEdge != null) {
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0)
                        return true;
                    currentEdge = currentEdge.nextEdge;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }
    
    public ArrayList<T> getNeighbours(T v) {
        if (!hasVertex(v))
            return null;
        ArrayList<T> list = new ArrayList<>();
        Vertex<T> temp = head;
        while(temp!=null) {
            if (temp.vertexInfo.compareTo(v) == 0) {
                Edge<T> currentEdge = temp.firstEdge;
                while(currentEdge!=null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge = currentEdge.nextEdge;
                }
            }
            temp = temp.nextVertex;
        }
        return list;
    }
     
    public Line drawEdge(T source, T destination) { //draws edge between two points on the screen
        Line line = new Line();
        line.setStroke(Color.BEIGE);
        if( hasEdge(source, destination) ){
            line.setStartX(getXCor(source)*2+200);
            line.setStartY(getYCor(source)*2+15);
            line.setEndX(getXCor(destination)*2+200);
            line.setEndY(getYCor(destination)*2+15);
            line.setStrokeWidth(2);
        }
        Main.root.getChildren().add(line);
        return line;
    }
    
    public Circle drawVertex(T v) {  //draws vertex on the screen
        Circle vertex = new Circle(6);
        vertex.setCenterX(getXCor(v)*2 + 200);
        vertex.setCenterY(getYCor(v)*2+15);
        vertex.setFill(Color.rgb(86, 119, 150));
        Main.root.getChildren().add(vertex);
        return vertex;
    }
    
    public int get2dListSize(ArrayList<ArrayList<T>> list) {
        int n = 0;
        for (int i=0; i<list.size(); i++) {
            n += list.get(i).size();
        }
        return n;
    }
    
    public void drawVisitedEdges(ArrayList<ArrayList<T>> vertices) { //changes the color of visited edges and animates
        int n = get2dListSize(vertices);
        final IntegerProperty i = new SimpleIntegerProperty(0);
        final IntegerProperty j = new SimpleIntegerProperty(0);
        Timeline timelineEdges = new Timeline();
        timelineEdges.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                    if (i.getValue() < vertices.size()) {
                        if (j.getValue() < vertices.get(i.get()).size()-1) {
                            Line edge  = drawEdge(vertices.get(i.get()).get(j.get()), vertices.get(i.get()).get(j.get()+1));
                            edge.setStroke(Color.rgb(182, 162, 132));
                            j.set(j.get() + 1);   
                        } else {
                            timelineEdges.playFromStart();
                            j.set(0);
                            i.set(i.get() + 1);   
                        }
                    }
                    if (Main.reset) {
                        timelineEdges.stop(); //stop the simulation if reset button is clicked
                    }
                } 
            )
        );
        timelineEdges.setDelay(Duration.seconds(1));
        timelineEdges.setCycleCount(n);
        timelineEdges.play(); 
    }
    
    //displays cycle count on the screen
    public void setCycleText(ArrayList<ArrayList<T>> vertices) {
        int n = get2dListSize(vertices);
        final IntegerProperty count = new SimpleIntegerProperty(1);
        final int length = n; 
        Timeline timelineText = new Timeline();
        timelineText.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1.25),
                event -> {
                    text.setText("Cycle: " +count.getValue().toString());
                    if (count.get() < length) {
                        count.set(count.get() + 1);
                    }
                    if (Main.reset) {
                        timelineText.stop(); 
                    }
                } 
            )
        );
        timelineText.setDelay(Duration.seconds(1));
        timelineText.setCycleCount(n);
        timelineText.play(); 
    }
    
    public void drawVisitedVertices(ArrayList<ArrayList<T>> vertices) { //changes the color of visited vertices and animates
        int n = get2dListSize(vertices);
        final IntegerProperty i = new SimpleIntegerProperty(0);
        final IntegerProperty j = new SimpleIntegerProperty(0);
        Timeline timelineVertices = new Timeline();
        timelineVertices.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                    if (i.getValue() < vertices.size()) {
                        if (j.getValue() < vertices.get(i.get()).size()) {
                            Circle vertex  = drawVertex(vertices.get(i.get()).get(j.get()));
                            vertex.setFill(Color.rgb(205,92,92));
                            j.set(j.get() + 1);   
                            
                        } else {
                            j.set(0);
                            i.set(i.get() + 1);   
                        }
                    }
                    if (Main.reset) {
                        timelineVertices.stop(); //stop the simulation if reset button is clicked
                    }
                } 
            )
        );
        timelineVertices.setCycleCount(n+10);
        timelineVertices.play();    
    }
    
    public ImageView moveVehicle(ImageView im, T src, T dest) { //animates and moves the vehicle from one vertex to another
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), im);
        transition.setFromX(getXCor(src)*2 + 190);
        transition.setFromY(getYCor(src)*2 - 20);
        transition.setToX(getXCor(dest)*2 + 190);
        transition.setToY(getYCor(dest)*2 - 20);
        transition.playFromStart();
        transition.play();
        return im;
    }
    
    public void vehicleTravel(ArrayList<ArrayList<T>> vertices) { //animates and moves the vehicle through visited vertices/path
        int n = get2dListSize(vertices);
        FileInputStream input = null;
        try {
            input = new FileInputStream(
                    "C:\\Users\\hp\\OneDrive\\Documents\\NetBeansProjects\\DeliveryRoute\\src\\deliveryroute\\vehicle.png");
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        ImageView vehicle = new ImageView(new Image(input));
        Main.root.getChildren().addAll(vehicle);

        final IntegerProperty i = new SimpleIntegerProperty(0);
        final IntegerProperty j = new SimpleIntegerProperty(0);
        Timeline timelineVehicle = new Timeline();
        timelineVehicle.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                    if (i.getValue() < vertices.size()) {
                        if (j.getValue() < vertices.get(i.get()).size()-1) {
                            moveVehicle(vehicle, vertices.get(i.get()).get(j.get()), vertices.get(i.get()).get(j.get()+1));
                            j.set(j.get() + 1);   
                        } else {
                            timelineVehicle.playFromStart();
                            j.set(0);
                            i.set(i.get() + 1);   
                        }
                    } 
                    if (Main.reset) {
                        timelineVehicle.stop(); //stop the simulation if reset button is clicked
                    }
                } 
            )
        );
        timelineVehicle.setDelay(Duration.seconds(1));
        timelineVehicle.setCycleCount(n);
        timelineVehicle.play(); 
    }
    
    
    public double calculateDistance(T src, T dest) { //euclidean distance between two points
        int x1 = getXCor(src);
        int x2 = getXCor(dest);
        int y1 = getYCor(src);
        int y2 = getYCor(dest);
        double d = Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2);
        return Math.sqrt(d);
    }    
    
    public double calculateCost(ArrayList<T> vertices) { //path cost and demand of one vehicle
        textArea.appendText("\n");
        int n = vertices.size();
        
        for (int a=0; a<n; a++) { //prints out the path
            if (a!= (n-1)) {
                System.out.print(vertices.get(a) + " -> ");
                textArea.appendText(vertices.get(a) + " -> ");
            }
            else {
                System.out.print(vertices.get(a));
                textArea.appendText(""+vertices.get(a));
            }
        }
        System.out.println("");
        
        double pathCost = 0; //total path cost of a vehicle
        int vehicleLoad = 0; //total load of a vehicle
        int i=0;
        int j=1;
        
        while(i<(n-1)) { //calculates the pathCost
            pathCost += calculateDistance(vertices.get(i), vertices.get(j));
            i++;
            j++;
        }
        for (int a=0; a<vertices.size(); a++) { //calculates the vehicleLoad
            vehicleLoad += getDemandSize(vertices.get(a));
        }
        
        System.out.println("Capacity: " + vehicleLoad);
        textArea.appendText("\nCapacity: " + vehicleLoad);
        System.out.println("Cost: " + pathCost);
        textArea.appendText("\nCost: " + pathCost + "\n");
        
        return pathCost;
    }
    
    /**
     * @param arr
     * @param vehicleID - note: set this parameter to 0 if it's not parallel simulation
     * @return  the tourCost and prints out results of a search
     */
    public double calculateTour(ArrayList<ArrayList<T>> arr, int vehicleID) {
        double tourCost = 0;
        int n = arr.size(); 
        
        drawVisitedVertices(arr);
        drawVisitedEdges(arr);
        vehicleTravel(arr);

        for (int j=0; j<n; j++) {
            tourCost+= calculateCost(arr.get(j)); //calculating tour cost
        }
        //printing empty lines to make the console ouput area look cleaner
        for(int i = 0; i < 1000; i++) {
            System.out.println();
        }
        textArea.clear(); //clearing the text area
        
        System.out.print("Tour");
        textArea.setText("Tour");
        textArea.setVisible(true);

        //adding timeline to show output one by one on the screen
        final IntegerProperty j = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(arr.get(j.get()).size()-0.5),
                event -> {
                    if (j.getValue() < arr.size()) {
                        if (vehicleID == 0) {  //if 0 means, it's not parallel simulation, so increase the vehicle number
                            System.out.println("\nVehicle " + (j.getValue()+1));
                            textArea.appendText("\nVehicle " + (j.getValue()+1)); 
                        } else {  //if parallel, just print the ID of the vehicle
                            System.out.println("\nVehicle " + vehicleID);
                            textArea.appendText("\nVehicle " + vehicleID); 
                        }
                        calculateCost(arr.get(j.get()));
                    }
                    j.set(j.get() + 1);  
                }
            )
        );
        timeline.setCycleCount(arr.size());
        timeline.play(); 
        
        if (vehicleID == 0) {
            System.out.println("\nTour Cost: " + tourCost);
        }
        return tourCost;
    }
    
    
    //Methods below are used for parallel simulation, 
    //Assuming that we have 3 vehicles, it will be simulated parallelly
    public void tourParallel(ArrayList<ArrayList<T>> arr) { 
        //intializing paths of 3 vehicles
        ArrayList<ArrayList<T>> vehicle1path = new ArrayList<>();
        ArrayList<ArrayList<T>> vehicle2path = new ArrayList<>();
        ArrayList<ArrayList<T>> vehicle3path = new ArrayList<>();

        switch (arr.size()) {
            case 1 -> { //in case of only one route
                vehicle1path.add(arr.get(0));
                calculateTour(vehicle1path, 1);
            }
            case 2 -> { //in case of two routes
                vehicle1path.add(arr.get(0));
                vehicle1path.add(arr.get(1));
                calculateTour(vehicle1path, 1);
                calculateTour(vehicle2path, 2);
            }
            case 3 -> { //in case of three routes
                vehicle1path.add(arr.get(0));
                vehicle1path.add(arr.get(1));
                vehicle3path.add(arr.get(2));
                calculateTour(vehicle1path, 1); 
                calculateTour(vehicle2path, 2);
                calculateTour(vehicle3path, 3);
            }
            default -> { //if more than three routes, the first vehicle that comes to the depot will take the next order
                for (int i=0; i<arr.size(); i++) {
                    min(vehicle1path, vehicle2path, vehicle3path).add(arr.get(i)); 
                }
            }
        }
        
        double tourCost = calculateTour(vehicle1path, 1) + calculateTour(vehicle2path, 2) + calculateTour(vehicle3path, 3);
        textArea.appendText("\nTour Cost: " + tourCost);
        System.out.println("\nTour Cost: " + tourCost);
        //setting the cycle count limit to the vehicle path with largest number of elememts
        setCycleText(max(vehicle1path, vehicle2path, vehicle3path)); 
        
    }
    
    //returns a 2D array with the smallest number of elements
    public ArrayList<ArrayList<T>> min(ArrayList<ArrayList<T>> a, ArrayList<ArrayList<T>> b, ArrayList<ArrayList<T>> c) {
        int size1 = get2dListSize(a), size2 = get2dListSize(b), size3 = get2dListSize(c);
        
        if (Math.min(Math.min(size1, size2), size3) == size1) 
            return a;
        else if (Math.min(Math.min(size1, size2), size3) == size2) 
            return b;
        else if (Math.min(Math.min(size1, size2), size3) == size3) 
            return c;
        else {
            return a;
        }
    }
    
    //returns a 2D array with the largest number of elements
    public ArrayList<ArrayList<T>> max(ArrayList<ArrayList<T>> a, ArrayList<ArrayList<T>> b, ArrayList<ArrayList<T>> c) {
        int size1 = get2dListSize(a), size2 = get2dListSize(b), size3 = get2dListSize(c);
        
        if (Math.max(Math.max(size1, size2), size3) == size1) 
            return a;
        else if (Math.max(Math.max(size1, size2), size3) == size2) 
            return b;
        else if (Math.max(Math.max(size1, size2), size3) == size3) 
            return c;
        else {
            return a;
        }
    }
    
    
    
}
