package org.example.testjavafx;

import javafx.scene.paint.Color;

public class Predator extends Agent{
    public Predator(int position_x, int position_y) {
        super(position_x, position_y);
        this.color = Color.RED;
    }

}
