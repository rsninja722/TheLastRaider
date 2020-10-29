package main.entities;

import java.awt.Color;

import engine.GameJava;
import engine.Utils;
import engine.drawing.Draw;
import main.Main;
import main.entities.Entity;

public class Wall extends Entity {

    public Wall(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw() {
        if(Main.showHitboxes) {
            Draw.setColor(Color.WHITE);
            Draw.rectOutline(rect);
        }
    }
}
