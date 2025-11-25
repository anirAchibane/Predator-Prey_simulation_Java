package org.example.testjavafx;

import java.util.ArrayList;
import java.util.Random;

public interface MovementStrategy {
    Random randomizer = new Random();
    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents);
}
