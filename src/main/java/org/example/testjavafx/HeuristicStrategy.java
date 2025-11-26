package org.example.testjavafx;

import javafx.util.Pair;

import java.util.ArrayList;

public class HeuristicStrategy implements MovementStrategy{
    // add necessary attributes ( radius.. )

    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents){
        /* match steps in naive random strategy:
           if not alive: return

           if predator:
                check surroundings
                if surroundings(radius) has prey: follow prey heuristically.
                else: move randomly
           if prey:
                check surroundings
                if surroundings(radius) has predator: flee heuristically
                else: move randomly
         */
    };

}
