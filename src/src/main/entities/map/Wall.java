package main.entities.map;

import java.awt.Color;

import engine.drawing.Draw;
import main.entities.Entity;

public class Wall extends Entity {

    public Wall(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw() {
        Draw.setColor(Color.WHITE);
        Draw.rectOutline(this.rect);
    }
}
