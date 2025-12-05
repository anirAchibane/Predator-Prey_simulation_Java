package org.example.testjavafx;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Agent> {
    private final int refX,refY;

    DistanceComparator(int refX, int refY){
        this.refX = refX;
        this.refY = refY;
    }

    private double calculateEuclideanDistance(Agent otherAgent){
        double dx = otherAgent.getPosition_x() - refX;
        double dy = otherAgent.getPosition_y() - refY;
        return (dx*dx) +(dy*dy);
    }

    @Override
    public int compare(Agent agent1, Agent agent2){
        // i'll sort by ascending order (closest firstt)
        return Double.compare(calculateEuclideanDistance(agent1),calculateEuclideanDistance(agent2));
    }

}
