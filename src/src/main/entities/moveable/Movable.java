package main.entities.moveable;

import main.entities.Entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Point;
import engine.physics.Rect;
import main.Main;

public class Movable extends Entity {

    double friction;
    public static int level = 0;
    public static ArrayList<Entity> checked = new ArrayList<Entity>();
    public Color color = new Color(Utils.rand(0, 150),Utils.rand(0, 150),Utils.rand(0, 150));

    public Movable(double x, double y, int w, int h) {
        super(x, y, w, h);

        friction = 0.7;
        moveable = true;
    }


    // returns amount to move back
    @Override
    public Rect applyForce(double xVel, double yVel) {
        if(level == 0) {
            checked = new ArrayList<Entity>();
        }
        level++;
        checked.add(this);

        double xMove = xVel * friction;
        double yMove = yVel * friction;
        this.rect.x += xMove;
        this.rect.y += yMove;

        Entity col = Entity.colliding(this.rect, checked);

        if(col == null) {
            level--;
            return this.rect;
        } else {
            if(col.moveable) {
                Rect moved = col.applyForce(xMove, yMove);

                if(moved == null) {
                    level--;
                    return this.rect;
                } else {
                    Rect.preventIntersection(this.rect, moved);
                    level--;
                    return this.rect;
                }
            } else {
                Rect.preventIntersection(this.rect, col.rect);
                level--;
                return this.rect;
            }
        }
    }
    @Override
    public void draw() {
        Draw.setColor(color);
        Draw.rect(this.rect);
    }
}
