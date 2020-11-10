package main.ui;

import java.awt.Color;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import engine.drawing.Draw;
import engine.physics.Rect;
import main.Main;

public class Component {

    Rect rect;

    float x;
    float y;

    public boolean show = true;

    public boolean absolute = false;

    public Component parent = null;

    public Component(int w,int h,float x, float y) {
        rect = new Rect(0,0,w,h);
        this.x = x;
        this.y = y;
    }

    public static void drawAll(ArrayList<Component> components) {
        for(int i =0;i<components.size();i++) {
            Component c = components.get(i);
            if(c.show) {
                c.draw();
            }
        }
    }

    public static void updateAll(ArrayList<Component> components) {
        for(int i =0;i<components.size();i++) {
            Component c = components.get(i);
            if(c.show) {
                c.rect.x = c.x * (float)Main.gw;
                c.rect.y = c.y * (float)Main.gh;
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
