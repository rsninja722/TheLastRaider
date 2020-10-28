package main.entities.moveable;

import main.entities.Entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;


import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Point;
import engine.physics.Rect;
import main.Constants;
import main.Main;

public class Moveable extends Entity {

    public double friction;
    public Color color = new Color(Utils.rand(0, 150), Utils.rand(0, 150), Utils.rand(0, 150));

    public double velX;
    public double velY;
    public static int level = 0;
    public static ArrayList<Entity> checked = new ArrayList<Entity>();

    public Moveable(double x, double y, int w, int h) {
        super(x, y, w, h);

        friction = 0.5;
        moveable = true;
    } 

    public int roundVelocity(double vel) {
        int inverse = (int)Math.round(1.0/vel);
        if(vel == 0  || inverse == 0) {
            return 0;
        }
        return (int)(vel < 1.0 ? (Main.updateCount%inverse == 0 ? 1 * Math.signum(vel) : 0) : vel);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void move() {
        rect.x += roundVelocity(velX);
        rect.y += roundVelocity(velY);

        Entity e = Entity.colliding(rect);
        if(e != null) {
            Rect.preventIntersection(rect, e.rect);
        }

        velX *= friction;
        velY *= friction;
    }

    @Override
    public void applyForce(double xVel, double yVel) {
        if (level == 0) {
            checked = new ArrayList<Entity>();
        }
        level++;
        checked.add(this);

        double xMove = roundVelocity(xVel * Constants.FORCE_TRANSFER);
        double yMove = roundVelocity(yVel * Constants.FORCE_TRANSFER);
        rect.x += xMove;
        rect.y += yMove;

        for (int i = 0; i < entities.size(); i++) {
            Rect r = entities.get(i).rect;
            if (rect.x == r.x && rect.y == r.y) {
                continue;
            }

            if (checked.contains(entities.get(i))) {
                continue;
            }

            if (Physics.rectrect(rect, r)) {
                
                Entity col =  entities.get(i);
                if (col.moveable) {
                    col.applyForce(xMove, yMove);
                    Rect.preventIntersection(rect, col.rect);
                } else {
                    Rect.preventIntersection(rect, col.rect);
                }
            }
        }

        level--;
    }

    @Override
    public void draw() {
        Draw.setColor(color);
        Draw.rect(rect);
    }
}
