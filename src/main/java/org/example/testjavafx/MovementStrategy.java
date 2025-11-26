package org.example.testjavafx;

import java.util.ArrayList;
import java.util.Random;

public interface MovementStrategy {

     static void updateAgentPosition(Agent agent, int newX, int newY, Agent[][] grid){
        // remove prey from initial position
        grid[agent.getPosition_y()][agent.getPosition_x()] = null;
        // update prey's coordinate attributes
        agent.setPosition_x(newX);
        agent.setPosition_y(newY);
        // put agent in new grid;
        grid[agent.getPosition_y()][agent.getPosition_x()] = agent;
        return;
    }

    Random randomizer = new Random();

    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents);
}
