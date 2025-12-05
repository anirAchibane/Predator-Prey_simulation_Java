package org.example.testjavafx;

import javafx.util.Pair;

import java.util.ArrayList;

public class NaiveRandomStrategy implements MovementStrategy{

    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents){
        if (!agent.isAlive()){
            return;
        }

        int x = agent.getPosition_x();
        int y = agent.getPosition_y();

        // finding agent's surroundings:
        ArrayList<Pair<Integer,Integer>> surroundings = new ArrayList<>(); // soufiane : why the choice of using arrayList<> instead of just simple 2d array?
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(( grid[0].length-1 >= x+i  && x+i >= 0) && (grid.length -1 >= y+j && y+j >= 0)){
                    surroundings.add(new Pair<>(x+i,y+j));
                }
            }
        }

        // moving based on agent's type
        if (agent instanceof Predator){
            // looking for nearby prey
            for(Pair<Integer,Integer> coords: surroundings){
                if(grid[coords.getValue()][coords.getKey()] instanceof Prey){ // eating a prey
                    // removing prey from active agents array
                    for(int i = 0; i < activeAgents.size(); i++){
                        if(activeAgents.get(i) == grid[coords.getValue()][coords.getKey()]){
                            activeAgents.get(i).setAlive(false);
                            break;
                        }
                    }

                    MovementStrategy.updateAgentPosition(agent,coords.getKey(), coords.getValue(), grid);
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

                    MovementStrategy.updateAgentPosition(agent,newCoordinates.getKey(), newCoordinates.getValue(), grid);
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

                    MovementStrategy.updateAgentPosition(agent,newCoordinates.getKey(), newCoordinates.getValue(), grid);
                }
            }
        }

}

