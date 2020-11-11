package main.ui;

import java.awt.Color;
import java.util.ArrayList;

import engine.drawing.Draw;
import engine.physics.Rect;
import main.Main;

public class Component {

    // used by button and slider
    public String text;
    public float percent = 1.0f;

    // collision
    Rect rect;

    // position in 0.0-1.0 for ratio of full screen dimensions
    float x;
    float y;

    public boolean show = true;

    public Component(int w, int h, float x, float y) {
        rect = new Rect(0, 0, w, h);
        this.x = x;
        this.y = y;
    }

    public static void drawAll(ArrayList<Component> components) {
        Draw.setLineWidth(2);
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (c.show) {
                c.draw();
            }
        }
        Draw.setLineWidth(1);
    }

    public static void updateAll(ArrayList<Component> components) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (c.show) {
                // set position
                c.rect.x = c.x * (float) Main.gw;
                c.rect.y = c.y * (float) Main.gh;
                c.update();
            }
        }
    }

    public void draw() {
        Draw.setColor(Color.WHITE);
        Draw.rect(rect);
    }

    public void update() {
    }
}
