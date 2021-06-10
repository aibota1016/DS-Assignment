
package deliveryroute;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    
    static Group root = new Group();
    static Scene scene = new Scene(root, 820, 420);
    static boolean reset;
    static int N, C;
    static Graph<Integer> graph;
    static ArrayList<Integer> coordinates = new ArrayList<>();
    static int[] vertices = new int[N];
    
     public static void buildGraph() {
        graph = new Graph<>();
        //Reading from file
        try{
            try (Scanner scan = new Scanner(new FileInputStream("input.txt"))) {
                N = scan.nextInt();
                C = scan.nextInt();
                int i = 0;
                vertices = new int[N];
                while(scan.hasNext()) {
                    vertices[i] = i;
                    //Adds a vertex that has (ID, xCoordinate, yCoordinate, demandSize)
                    graph.addVertex(vertices[i], scan.nextInt(), scan.nextInt(), scan.nextInt());
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
        //Creating connected edges
        for (int i=0; i<graph.getSize(); i++) {
            for (int j=0; j<graph.getSize(); j++) {
                if (i!= j)
                    graph.addEdge(vertices[i], vertices[j]);
            }
        }
        
        //get Coordinates (both X, Y)
        for (int v: vertices) {
            coordinates.add(graph.getXCor(v));
            coordinates.add(graph.getYCor(v));
        }
        
        
        //Drawing Edges on the screen
        for (int i=0; i<graph.getSize(); i++) 
            for (int j=0; j<graph.getSize(); j++) 
                if (i!= j)
                    graph.drawEdge(i, j);

        //Drawing vertices and ID labels on the screen
        for (int i=0, j=1, a=0; i<coordinates.size() && i<coordinates.size() && a<vertices.length; i+=2, j+=2, a++) {
            Text label = new Text();
            label.setText(String.valueOf(a));
            label.setX(coordinates.get(i)*2+195);
            label.setY(coordinates.get(j)*2+10);
            label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
            root.getChildren().add(label);
            graph.drawVertex(vertices[a]);
        }
        
        //Drawing Depot Image at 0
        FileInputStream input = null;
        try {
            input = new FileInputStream("C:\\Users\\hp\\OneDrive\\Documents\\NetBeansProjects\\DeliveryRoute\\src\\deliveryroute\\depot.png");
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        Image depotImage = new Image(input);
        ImageView depot = new ImageView(depotImage);
        depot.setX(graph.getXCor(0)*2 + 180);
        depot.setY(graph.getYCor(0)*2 - 20);
        Text label = new Text();
        label.setText("Depot");
        label.setX(graph.getXCor(0)*2 + 180);
        label.setY(graph.getYCor(0)*2 - 20);
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        
        root.getChildren().add(depot);
        root.getChildren().add(label);
    }
    
    
    

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Button btnBasicSimulation = new Button("Basic Simulation");
        btnBasicSimulation.setPrefSize(120,12);
        btnBasicSimulation.setLayoutX(15);
        btnBasicSimulation.setLayoutY(20);
        btnBasicSimulation.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
   
        Button btnGreedySimulation = new Button("Greedy Simulation");
        btnGreedySimulation.setPrefSize(120,12);
        btnGreedySimulation.setLayoutX(15);
        btnGreedySimulation.setLayoutY(50);
        btnGreedySimulation.setOnAction((ActionEvent event) -> {
            System.out.println("Greedy Simulation");
            GreedySearch<Integer> greedy = new GreedySearch(graph, C);
            graph.setCycleText(greedy.pathList);
            Graph.textArea.appendText("\nTour Cost: " + graph.calculateTour(greedy.pathList, 0));
        });
        
        Button btnMCTSSimulation = new Button("MCTS Simulation");
        btnMCTSSimulation.setPrefSize(120,12);
        btnMCTSSimulation.setLayoutX(15);
        btnMCTSSimulation.setLayoutY(80);
        btnMCTSSimulation.setOnAction((ActionEvent event) -> {
            System.out.println("MCTS Simulation");
            MCTSSearch<Integer> MCTS = new MCTSSearch(graph, C);
            graph.setCycleText(MCTS.pathList);
            Graph.textArea.appendText("\nTour Cost: " + graph.calculateTour(MCTS.pathList, 0));
        });
        
        Button btnAstarSimulation = new Button("A* Simulation");
        btnAstarSimulation.setPrefSize(120,12);
        btnAstarSimulation.setLayoutX(15);
        btnAstarSimulation.setLayoutY(110);
        btnAstarSimulation.setOnAction((var event) -> {
            System.out.println("A star Simulation");
            AStarSearch<Integer> AStar = new AStarSearch(graph, C);
            graph.setCycleText(AStar.pathList);
            Graph.textArea.appendText("\nTour Cost: " + graph.calculateTour(AStar.pathList, 0));
        });
        
        
        //Buttons for Parallel Simulations
        Button btnBasicParallel = new Button("Basic Parallel");
        btnBasicParallel.setPrefSize(120,12);
        btnBasicParallel.setLayoutX(15);
        btnBasicParallel.setLayoutY(160);
        btnBasicParallel.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        Button btnGreedyParallel = new Button("Greedy Parallel");
        btnGreedyParallel.setPrefSize(120,12);
        btnGreedyParallel.setLayoutX(15);
        btnGreedyParallel.setLayoutY(190);
        btnGreedyParallel.setOnAction((ActionEvent event) -> {
            System.out.println("Greedy Parallel Simulation");
            GreedySearch<Integer> greedy = new GreedySearch(graph, C);
            graph.tourParallel(greedy.pathList);
        });
        
        Button btnMCTSSimulationParallel = new Button("MCTS Parallel");
        btnMCTSSimulationParallel.setPrefSize(120,12);
        btnMCTSSimulationParallel.setLayoutX(15);
        btnMCTSSimulationParallel.setLayoutY(220);
        btnMCTSSimulationParallel.setOnAction((ActionEvent event) -> {
            System.out.println("MCTS Parallel Simulation");
            MCTSSearch<Integer> MCTS = new MCTSSearch(graph, C);
            graph.tourParallel(MCTS.pathList);
        });
        
        Button btnAstarParallel = new Button("A* Parallel");
        btnAstarParallel.setPrefSize(120,12);
        btnAstarParallel.setLayoutX(15);
        btnAstarParallel.setLayoutY(250);
        btnAstarParallel.setOnAction((ActionEvent event) -> {
            System.out.println("A Star Parallel Simulation");
            AStarSearch<Integer> AStar = new AStarSearch(graph, C);
            graph.tourParallel(AStar.pathList);
        });
        
        Button btnReset = new Button("Reset"); //resets the graph to its initial look
        btnReset.setPrefSize(120,12);
        btnReset.setLayoutX(15);
        btnReset.setLayoutY(370);
        btnReset.setOnAction((ActionEvent event) -> {
            reset = true;
            buildGraph();
            Graph.textArea.clear();
            Graph.textArea.setVisible(false);
            Graph.text.clear();
        });
        

        primaryStage.setResizable(false);
        primaryStage.setTitle("Always On Time Sdn Bhd");
        
        Rectangle rect = new Rectangle(150,420);
        rect.setFill(Color.rgb(133, 152, 170));
        Rectangle rect2 = new Rectangle(200,420);
        rect2.setX(620);
        rect2.setY(0);
        rect2.setFill(Color.rgb(241, 242, 246)); 
        
        Graph.textArea.setPrefHeight(420);
        Graph.textArea.setPrefWidth(200);
        Graph.textArea.setLayoutX(620);
        Graph.textArea.setVisible(false);
        
        Graph.text.setLayoutX(150);
        Graph.text.setPrefHeight(20);
        Graph.text.setPrefWidth(90);
        Graph.text.setEditable(false);
        Graph.text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));

        
        root.getChildren().add(rect);
        root.getChildren().add(rect2);
        root.getChildren().add(Graph.textArea);
        root.getChildren().add(Graph.text);
        root.getChildren().add(btnBasicSimulation);
        root.getChildren().add(btnGreedySimulation);
        root.getChildren().add(btnMCTSSimulation);
        root.getChildren().add(btnAstarSimulation);
        root.getChildren().add(btnBasicParallel);
        root.getChildren().add(btnGreedyParallel);
        root.getChildren().add(btnMCTSSimulationParallel);
        root.getChildren().add(btnAstarParallel);
        root.getChildren().add(btnReset);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    
    
    
    public static void main(String[] args) {
        buildGraph();
        launch(args);
    }
    
}

