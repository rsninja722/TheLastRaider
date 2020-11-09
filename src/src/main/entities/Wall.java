package main.entities;

import java.awt.Color;

import engine.Utils;
import engine.drawing.Draw;

public class Wall extends Entity {

    public Wall(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw() {
        if(Utils.debugMode) {
            Draw.setColor(Color.LIGHT_GRAY);
            Draw.rectOutline(rect);
        }
    }
}
