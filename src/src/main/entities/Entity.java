package main.entities;

import java.util.ArrayList;

import engine.physics.Physics;
import engine.physics.Point;
import engine.physics.Rect;

public class Entity {

    public static ArrayList<Entity> entities = new ArrayList<Entity>();

    public Rect rect;
    public boolean moveable = false;

    public double friction;
    public double velX;
    public double velY;

    public Entity(double x, double y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }

    public void update() {
    }

    public void draw() {
    }

    public void move() {
    }

    public int roundVelocity(double vel) {
        return 0;
    }

    public void handleCollision() {

    }

    public void applyForce(double xVel, double yVel) {
    }

    // returns an entity a rectangle is colliding with
    public static Entity colliding(Rect r) {

        for (int i = 0; i < entities.size(); i++) {
            Rect rect = entities.get(i).rect;
            if (rect.x == r.x && rect.y == r.y) {
                continue;
            }

            if (Physics.rectrect(rect, r)) {
                return entities.get(i);
            }
        }

        return null;
    }
}
