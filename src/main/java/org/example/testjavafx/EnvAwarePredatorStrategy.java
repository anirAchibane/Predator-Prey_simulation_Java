package org.example.testjavafx;

import javafx.util.Pair;

import javax.swing.event.InternalFrameEvent;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;

public class EnvAwarePredatorStrategy implements MovementStrategy{
    /*
    this class defines the movement of agents, where the predator is environment-aware and the prey isn't.
    * In this method, the predator is aware of its surrouding, up to a radius r.
    * If the predator found a prey in his surrounding, it will move accordingly to a place a celle that will make it closer to it.
    * Otherwise, it'll just move freely.
    */

    // since the predator is the only one that is smart here, then, the radius is only for the predator
    static int radius; // i made it static to make it easy while making an inteface (i guess)
    boolean potentialPredatorsCollision = true;


    EnvAwarePredatorStrategy(int r){
        this.radius = r;
    }



    public void move(Agent agent, Agent[][] grid, ArrayList<Agent> activeAgents){
        // i'll use this to sort the arraylists preySurroundings and predatorSurroudings to find the closest to my agent.
        DistanceComparator distanceSorter = new DistanceComparator(agent.getPosition_x(),agent.getPosition_y());

        //  if agent is not alive: return
        if (!agent.isAlive()){
            return;
        }

        int x = agent.getPosition_x();
        int y = agent.getPosition_y();

        // finding agent's R-surroundings,
        ArrayList<Pair<Integer,Integer>> preySurroundings = new ArrayList<>();  // preys that are in the R-box of the agent.
        ArrayList<Pair<Integer,Integer>> predatorSurroundings = new ArrayList<>();  // predators that are in the R-box of the agent.
        for(int i = -radius; i <= radius; i++){
            for(int j = -radius; j <= radius; j++){
                if((x+i<= grid[0].length -1 && x+i >= 0) && (y+j <= grid.length -1 && y+j >= 0)){
                    if (grid[x+i][y+j] instanceof Prey && !(i == 0 && j == 0) ){ // added 2d condition only to ensure that i don't take the coordinates of my agent int he surroundings.
                        preySurroundings.add(new Pair<>(x+i,y+j));
                }
                    if (grid[x+i][y+j] instanceof Predator && ((i == 1 || i== -1) && (j == -1 || j == 1))){ // Im only interested of about neighboring cells in this heuristtic.
                        predatorSurroundings.add(new Pair<>(x+i,y+j));
                    }
            }
        }

    }


        if (agent instanceof Predator){

            if (predatorSurroundings.isEmpty()) {
                // if this array list is empty then, there are no predators close to my predator. so, move freely and eat prey if possible.
                potentialPredatorsCollision = false ;
            }


            // i'll check for the PreySurroundings.
            if (preySurroundings.isEmpty() && !potentialPredatorsCollision) {
                // if there are no prey in the surroudings and no potential collisions with other predator, move randomly;
                Random random = new Random();
                int zeroOrOne1 = random.nextInt(2); // chosing ints from 0 to 2 (exluded) in my case, 0or 1.
                int choice1 = (zeroOrOne1 == 0) ? -1 : 1; // just to map result to -1 or 1.
                int zeroOrOne2 = random.nextInt(2);
                int choice2 = (zeroOrOne2 == 0) ? -1 : 1;
                MovementStrategy.updateAgentPosition(agent,(x + choice1) % GridSimulation.WIDTH, (y+choice2) % GridSimulation.WIDTH, grid);
                return;
            }


            // in this case potential surroundings is already true
            if (preySurroundings.isEmpty()) {
                // if there are no prey in the surroundings but potential collisions with other predator, check where they are and avoid them
                // first if the predator cannot move, he loses energy : -2 (cannot move <=> size of predatorSurroundings is 8)
                if (predatorSurroundings.size() == 8){
                    int energyPenalty = 3;
                    agent.setEnergy_level(agent.getEnergy_level() - energyPenalty);
                    if (agent.getEnergy_level() - energyPenalty <= 0){ // if the energy of the predator is negative. Predator dies.
                        for(int i = 0; i < activeAgents.size(); i++){
                            if(activeAgents.get(i) == grid[agent.getPosition_x()][agent.getPosition_y()]){
                                activeAgents.get(i).setAlive(false);
                                return;
                            }
                    }
                }// soufiane comment: WE ARE HERE TRYING CODE THIS CASE!
                    else{ // now, if the predator is not fully surrounded, move to
                        for (Pair<Integer,Integer> predatorCoord: predatorSurroundings){
                            if (agent.getPosition_x() != predatorCoord.getValue() ||
                        }
                        }

                    }





                Random random = new Random();
                int zeroOrOne1 = random.nextInt(2); // chosing ints from 0 to 2 (exluded) in my case, 0or 1.
                int choice1 = (zeroOrOne1 == 0) ? -1 : 1; // just to map result to -1 or 1.
                int zeroOrOne2 = random.nextInt(2);
                int choice2 = (zeroOrOne2 == 0) ? -1 : 1;
                MovementStrategy.updateAgentPosition(agent,(x + choice1) % GridSimulation.WIDTH, (y+choice2) % GridSimulation.WIDTH,grid);
                return;
            }
            // sort the arrayList and get the closest prey (the first element)
            preySurroundings.sort(distanceSorter);

            Prey closestPrey = new Prey(preySurroundings.getFirst().getValue(),preySurroundings.getFirst().getKey());

            // now, i check first if the closest prey is a neighbor to our predator. We eat.
            if ((agent.getPosition_x() - 1 <= closestPrey.getPosition_x() && closestPrey.getPosition_x() <= agent.getPosition_x() + 1) &&
                    (agent.getPosition_y() - 1 <= closestPrey.getPosition_y() && closestPrey.getPosition_y() <= agent.getPosition_x() + 1)) {
                for (Agent activeAgent : activeAgents) {
                    // checking for the radius=1 box. if prey found, EAT.
                    if (activeAgent == grid[closestPrey.getPosition_x()][closestPrey.getPosition_y()]) {
                        activeAgent.setAlive(false);
                        break;
                    }

                }
                MovementStrategy.updateAgentPosition(agent, closestPrey.getPosition_x(), closestPrey.getPosition_y(), grid);
                return;
            }
            else {
            // now, we have a closest prey that is not a neighbor. Next, thing to do is: update the predator's position in the prey's direction:

                if (closestPrey.getPosition_x() == agent.getPosition_x()) {

                    if (closestPrey.getPosition_y() < agent.getPosition_y()){
                        MovementStrategy.updateAgentPosition(agent, agent.getPosition_x(), agent.getPosition_y() - 1, grid);
                    }
                    else{
                        MovementStrategy.updateAgentPosition(agent, agent.getPosition_x(), agent.getPosition_y()+1, grid);
                    }
            }
                else {
                    if (closestPrey.getPosition_y() == agent.getPosition_y()) {

                        if (closestPrey.getPosition_x() < agent.getPosition_x()){
                            MovementStrategy.updateAgentPosition(agent, agent.getPosition_x()-1, agent.getPosition_y(), grid);
                        }
                        else{
                            MovementStrategy.updateAgentPosition(agent, agent.getPosition_x() + 1, agent.getPosition_y(), grid);
                        }
                    }
                    else {
                        if (closestPrey.getPosition_x() > agent.getPosition_x() && closestPrey.getPosition_y() > agent.getPosition_y()) {
                                MovementStrategy.updateAgentPosition(agent, agent.getPosition_x()+1, agent.getPosition_y() + 1, grid);
                            }
                        }
                        else{
                            if (closestPrey.getPosition_x() < agent.getPosition_x() && closestPrey.getPosition_y() > agent.getPosition_y()) {
                                MovementStrategy.updateAgentPosition(agent, agent.getPosition_x()-1, agent.getPosition_y() + 1, grid);
                        }
                            else{
                                if (closestPrey.getPosition_x() > agent.getPosition_x() && closestPrey.getPosition_y() < agent.getPosition_y()) {
                                    MovementStrategy.updateAgentPosition(agent, agent.getPosition_x()+1, agent.getPosition_y() - 1, grid);
                                }
                                else {
                                    if (closestPrey.getPosition_x() < agent.getPosition_x() && closestPrey.getPosition_y() < agent.getPosition_y()) {
                                        MovementStrategy.updateAgentPosition(agent, agent.getPosition_x() - 1, agent.getPosition_y() - 1, grid);
                                    }
                            }
                    }

                    }
                }










            for(Pair<Integer,Integer> coords: preySurroundings){
                // if the prey in the radius = 1. search its position in activeAgents array. remove it. move the Predator to its position.
                if ((agent.getPosition_x() - 1 <= coords.getValue() && coords.getValue() <= agent.getPosition_x() + 1) &&
                        (agent.getPosition_y() - 1 <= coords.getKey() && coords.getKey() <= agent.getPosition_x() + 1)) {
                    for (int i = 0; i < activeAgents.size(); i++) {
                        // checking for the radius=1 box. if prey found, EAT.
                        if (activeAgents.get(i) == grid[coords.getValue()][coords.getKey()]) {
                            activeAgents.get(i).setAlive(false);
                            break;
                        }

                    }
                    MovementStrategy.updateAgentPosition(agent, coords.getKey(), coords.getValue(), grid);
                    return;
                }
                // now, if the prey isn't in a neighboring cell. i create a variable of type Prey called ClosestPrey that we'll update, while looping on all surroundings.
                else{

                }

            }
        }
    }
    // if agent is prey, move randomly
    // removing surroundings occupied by preys
        }




        /* match steps in naive random strategy:



           if predator:
                check surroundings
                if surroundings(radius) has prey: follow prey heuristically.
                else: move randomly

         */

}
