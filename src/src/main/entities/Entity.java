package main.entities;

import java.util.ArrayList;

import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Rect;
import java.awt.Color;

public class Entity {

    public static ArrayList<ArrayList<Entity>> entitiesList = new ArrayList<ArrayList<Entity>>();

    public static ArrayList<Entity> entities;

    public Rect rect;
    public boolean moveable = false;
    public boolean damageable = false;
    public boolean neutral = false;
    public int hp;
    public int maxHp;

    public static void updateAll() {
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).update()) {
                entities.remove(i);
            }
        }
    }

    public Entity(double x, double y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }

    public boolean update() {
        return false;
    }

    public void draw() {
        if(Utils.debugMode) {
            Draw.setColor(Color.LIGHT_GRAY);
            Draw.rectOutline(rect);
        }
    }

    public void move() {
    }

    public int roundVelocity(double vel) {
        return 0;
    }

    public void setHP(int hp) {
        this.hp = hp;
        this.maxHp = hp;
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
