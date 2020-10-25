package main.entities;

import java.util.ArrayList;

import engine.physics.Rect;

public class Entity {

    public static ArrayList<Entity> entities = new ArrayList<Entity>();

    public Rect rect;
    public boolean moveable = false;

    public Entity(double x, double y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }

    public void draw() {
    };

    public boolean applyForce(double xVel, double yVel) {
        return false;
    }
}
