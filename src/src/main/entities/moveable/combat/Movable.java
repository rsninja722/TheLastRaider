package main.entities.moveable.combat;

import engine.physics.Rect;
import main.entities.Entity;
import main.Main;

public class Movable extends Entity {

    double friction;

    public Movable(double x, double y, int w, int h) {
        super(x, y, w, h);

        friction = 0.5;
        moveable = true;
    }


    // returns whether it moved without hitting anything
    public boolean applyForce(double xVel, double yVel) {
        this.rect.x += xVel * friction;
        this.rect.y += xVel * friction;

        Entity col = Main.colliding(this.rect);

        if(col == null) {
            return true;
        } else {
            if(col.moveable) {
                col.applyForce(xVel * friction, yVel * friction);
            }
            return false;
        }
    }
}
