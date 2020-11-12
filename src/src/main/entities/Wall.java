package main.entities;

import java.awt.Color;

import engine.Utils;
import engine.drawing.Draw;
import main.Options;

public class Wall extends Entity {

    public Wall(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw() {
        if (Utils.debugMode || !Options.quality) {
            Draw.setColor(Color.LIGHT_GRAY);
            Draw.rectOutline(rect);
        }
    }
}
