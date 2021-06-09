
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
            input = new FileInputStream("C:\\Users\\hp\\OneDrive\\Documents\\NetBeansProjects\\DeliveryRoute\\src\\deliveryroute\\depot1.png");
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
        btnBasicSimulation.setLayoutY(50);
        btnBasicSimulation.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        Button btnGreedySimulation = new Button("Greedy Simulation");
        btnGreedySimulation.setPrefSize(120,12);
        btnGreedySimulation.setLayoutX(15);
        btnGreedySimulation.setLayoutY(100);
        btnGreedySimulation.setOnAction((ActionEvent event) -> {
            System.out.println("Greedy Simulation");
            GreedySearch<Integer> greedy = new GreedySearch(graph, C);
            System.out.println(graph.calculateTour(greedy.pathList));
        });
        
        Button btnMCTSSimulation = new Button("MCTS Simulation");
        btnMCTSSimulation.setPrefSize(120,12);
        btnMCTSSimulation.setLayoutX(15);
        btnMCTSSimulation.setLayoutY(150);
        btnMCTSSimulation.setOnAction((ActionEvent event) -> {
            System.out.println("MCTS Simulation");
            MCTSSearch<Integer> MCTS = new MCTSSearch(graph, C);
            System.out.println(graph.calculateTour(MCTS.pathList));
        });
        
        Button btnReset = new Button("Reset"); //resets the graph to its initial look
        btnReset.setPrefSize(120,12);
        btnReset.setLayoutX(15);
        btnReset.setLayoutY(350);
        btnReset.setOnAction((ActionEvent event) -> {
            buildGraph();
            Graph.textArea.clear();
            Graph.textArea.setVisible(false);
        });
        

        primaryStage.setResizable(false);
        primaryStage.setTitle("Always On Time Sdn Bhd");
        
        Rectangle rect = new Rectangle(150,420);
        rect.setFill(Color.rgb(133, 152, 170));
        Rectangle rect2 = new Rectangle(200,420);
        rect2.setX(620);
        rect2.setY(0);
        rect2.setFill(Color.rgb(241, 242, 246)); 

        
        root.getChildren().add(rect);
        root.getChildren().add(rect2);
        root.getChildren().add(btnBasicSimulation);
        root.getChildren().add(btnGreedySimulation);
        root.getChildren().add(btnMCTSSimulation);
        root.getChildren().add(btnReset);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    
    
    
    public static void main(String[] args) {
        buildGraph();
        launch(args);
    }
    
}
