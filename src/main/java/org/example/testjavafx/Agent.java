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

    public int getPosition_x() {
        return position_x;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getEnergy_level() {
        return energy_level;
    }

    public void setEnergy_level(int energy_level) {
        this.energy_level = energy_level;
    }
}
