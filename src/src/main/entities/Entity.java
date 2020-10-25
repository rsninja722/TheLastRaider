package main.entities;

import engine.physics.Rect;

public class Entity {

    public Rect rect;

    public Entity(double x, double y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }
}
