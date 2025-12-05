package org.example.testjavafx;

import javafx.animation.AnimationTimer;
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
import java.util.Timer;

public class GridSimulation extends Application {

    Random random = new Random();
    // soufiane comment:  i made tile size, width and height public, because i may use them in another place.
    public static final int TILE_SIZE = 5;
    public static final int WIDTH = 100;    // number of tiles in width.
    public static final int HEIGHT = 100;   // number of tiles in height.

    private ArrayList<Agent> activeAgents; // active agents in current iteration
    private Agent[][] grid;                // placement of agents in grid

    MovementStrategy movementStrategy = new NaiveRandomStrategy(); // naive movement strategy controller to test


    // This part below is a controller - it should be removed from this View type class
    // adds new agent to grid
    private void addAgent(Agent agent){
        activeAgents.add(agent);
        grid[agent.position_y][agent.position_x] = agent;
    }

    // Initially populate grid with agents
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
    // The part above is a controller - it should be removed from this View type class


    @Override
    public void start(Stage stage) throws IOException { // Soufiane comment : why you use throws IOException
        activeAgents = new ArrayList<Agent>();
        grid = new Agent[HEIGHT][WIDTH];

        // spawn initial agents:
        spawnInitialAgents(100,100);
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

        // AnimationTimer class for the simulation loop.
        AnimationTimer loop = new AnimationTimer() {
            /*
            * Setting lastUpdate and interval to keep
            * the animation at around 24fps
            * */
            private long lastUpdate = 0;
            private long interval = 68_000_000;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= interval) {
                    for(Agent agent: activeAgents){
                        movementStrategy.move(agent,grid,activeAgents);
                    }
                    activeAgents.removeIf(agent -> !agent.isAlive());

                    drawGrid(gc);
                    lastUpdate = now;
                }

            }
        };

        loop.start();
    }

    private void drawGrid(GraphicsContext gc){
        // Filling the screen in black (no agents in the scene)
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,WIDTH*TILE_SIZE,HEIGHT*TILE_SIZE);

        for(Agent agent: activeAgents){
            gc.setFill(agent.getColor());
            gc.fillRect(agent.getPosition_x()*TILE_SIZE, agent.getPosition_y()*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }

    }
}
