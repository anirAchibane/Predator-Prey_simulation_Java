package org.example.testjavafx;

import javafx.util.Pair;

import java.util.ArrayList;

public class NaiveRandomStrategy implements MovementStrategy{
    private void updateAgentPosition(Agent agent, int newX, int newY, Agent[][] grid){
        // remove prey from initial position
        grid[agent.getPosition_y()][agent.getPosition_x()] = null;
        // update prey's coordinate attributes
        agent.setPosition_x(newX);
        agent.setPosition_y(newY);
        // put agent in new grid;
        grid[agent.getPosition_y()][agent.getPosition_x()] = agent;
        return;
    }
    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents){
        if (!agent.isAlive()){
            return;
        }

        int x = agent.getPosition_x();
        int y = agent.getPosition_y();

        // finding agent's surroundings:
        ArrayList<Pair<Integer,Integer>> surroundings = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((x+i<= grid[0].length -1 && x+i >= 0)
                        && (y+j <= grid.length -1 && y+j >= 0)){
                    surroundings.add(new Pair<>(x+i,y+j));
                }
            }
        }

        // moving based on agent's type
        if (agent instanceof Predator){
            // looking for nearby predator
            for(Pair<Integer,Integer> coords: surroundings){
                if(grid[coords.getValue()][coords.getKey()] instanceof Prey){ // eating a prey
                    // removing prey from active agents array
                    for(int i = 0; i < activeAgents.size(); i++){
                        if(activeAgents.get(i) == grid[coords.getValue()][coords.getKey()]){
                            activeAgents.get(i).setAlive(false);
                            break;
                        }
                    }

                    updateAgentPosition(agent,coords.getKey(), coords.getValue(), grid);
                    return;
                }
            }
            // removing surroundings occupied by predators:
            int i = 0;
            while(i < surroundings.size()){
                Pair<Integer,Integer> coords = surroundings.get(i);
                if (grid[coords.getValue()][coords.getKey()] instanceof Predator){
                    surroundings.remove(i);
                }
                else{
                    i++;
                }
            }
            // selecting next move
            if (!surroundings.isEmpty()) {
                    int moveIndex = randomizer.nextInt(surroundings.size());
                    Pair<Integer, Integer> newCoordinates = surroundings.get(moveIndex);

                    updateAgentPosition(agent,newCoordinates.getKey(), newCoordinates.getValue(), grid);
                }
        }
        else{ // if agent is prey, move randomly
            // removing surroundings occupied by preys
            int i = 0;
            while(i < surroundings.size()){
                Pair<Integer,Integer> coords = surroundings.get(i);
                if (grid[coords.getValue()][coords.getKey()] != null){
                    surroundings.remove(i);
                }
                else{
                    i++;
                }
            }
            // selecting next move
            if (!surroundings.isEmpty()){
                    int moveIndex = randomizer.nextInt(surroundings.size());
                    Pair<Integer,Integer> newCoordinates = surroundings.get(moveIndex);

                    updateAgentPosition(agent,newCoordinates.getKey(), newCoordinates.getValue(), grid);
                }
            }
        }

}

