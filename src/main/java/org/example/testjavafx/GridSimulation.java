package org.example.testjavafx;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GridSimulation extends Application {
    Random random = new Random();

    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 30;    // number of tiles in width.
    private static final int HEIGHT = 20;   // number of tiles in height.

    private ArrayList<Agent> activeAgents; // active agents in current iteration
    private Agent[][] grid;                // placement of agents in grid

    // adds new agent to grid
    private void addAgent(Agent agent){
        activeAgents.add(agent);
        grid[agent.position_y][agent.position_x] = agent;
    }

    // spawns initial agents into the grid
    private void spawnInitialAgents(int num_of_predators,int num_of_preys){
        boolean predator_created;
        while(num_of_predators > 0){
            predator_created = false;
            while(!predator_created){
                int pos_x = random.nextInt(WIDTH);
                int pos_y = random.nextInt(HEIGHT);
                if (grid[pos_y][pos_x] == null){
                    addAgent(new Predator(pos_x,pos_y));
                    predator_created = true;
                }
            }
            num_of_predators--;
        }
        boolean prey_created;
        while(num_of_preys > 0){
            prey_created = false;
            while(!prey_created){
                int pos_x = random.nextInt(WIDTH);
                int pos_y = random.nextInt(HEIGHT);
                if (grid[pos_y][pos_x] == null){
                    addAgent(new Prey(pos_x,pos_y));
                    prey_created = true;
                }
            }
            num_of_preys--;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        activeAgents = new ArrayList<Agent>();
        grid = new Agent[HEIGHT][WIDTH];

        // Create group as root:
        Group root = new Group();

        // Create Canvas based on grid dimensions:
        Canvas canvas = new Canvas(WIDTH*TILE_SIZE,HEIGHT*TILE_SIZE);
        root.getChildren().add(canvas);

        // Get graphicContext to draw:
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // draw initial grid:
        drawGrid(gc);

        // Set scene and show:
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void drawGrid(GraphicsContext gc){
        // Filling the screen in black (no agents in the scene)
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,WIDTH*TILE_SIZE,HEIGHT*TILE_SIZE);

    }
}
