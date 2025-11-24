package org.example.testjavafx;

import javafx.scene.paint.Color;

public abstract class Agent {
    int position_x;
    int position_y;
    Color color;
    int energy_level;

    public abstract void move();

    public Agent(int position_x, int position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.energy_level = 10;
    }
}
