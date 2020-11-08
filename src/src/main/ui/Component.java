package main.ui;

import java.awt.Color;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import engine.drawing.Draw;

public class Component {
    
    public enum Alignment {
        LEFT,
        BOTTOM,
        RIGHT,
        TOP
    }

    int x;
    int y;
    int w;
    int h;
    int padX;
    int padY;

    Alignment alignment;

    public boolean show = false;

    public boolean absolute = false;

    ArrayList<Component> children = new ArrayList<Component>();

    public Component parent = null;

    public Component(int w,int h,Alignment alignment,int padX,int padY) {
        this.w = w;
        this.h = h;
        this.padX = padX;
        this.padY = padY;
        this.alignment = alignment;
    }

    public void renderThisAndChildren() {
        if(show) {
            draw();
            for(int i =0;i<children.size();i++) {
                renderThisAndChildren();
            }
        }
    }

    public void draw() {
        Draw.setColor(Color.WHITE);
        Draw.rect(x,y,w,h);
    }

    public void appendChild(Component c) {
        children.add(c);
        c.parent = this;
    }
}
