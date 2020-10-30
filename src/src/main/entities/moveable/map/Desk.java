package main.entities.moveable.map;

import engine.drawing.Draw;
import main.Main;
import main.entities.moveable.Moveable;

import java.awt.Color;

public class Desk extends Moveable {

    int variation;
    int rotation;

    public Desk(double x, double y, int w, int h, int variation, int rotation) {
        super(x, y, w, h);
        this.variation = variation;
        this.rotation = rotation;

        // determine size
        switch (variation) {
            case 0:
                if (rotation == 0 || rotation == 2) {
                    rect.resize(14, 12);
                } else {
                    rect.resize(12, 14);
                }
                break;
            case 1:
                rect.resize(20, 20);
                break;
            case 2:
                if (rotation == 0 || rotation == 2) {
                    rect.resize(30, 20);
                } else {
                    rect.resize(20, 30);
                }
                break;
        }
    }

    @Override
    public void draw() {
        Draw.image("desk" + variation, (int) rect.x, (int) rect.y, rotation * Math.PI / 2.0, 1.0);
        if (Main.showHitboxes) {
            Draw.setColor(Color.WHITE);
            Draw.rectOutline(rect);
        }
    }
}
