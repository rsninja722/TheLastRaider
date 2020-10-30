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

    public Color color = new Color(Utils.rand(0, 150), Utils.rand(0, 150), Utils.rand(0, 150));

    public Moveable(double x, double y, int w, int h) {
        super(x, y, w, h);

        friction = 0.25;
        moveable = true;
    }

    // for velocities lower than 1, returns 1 periodically at a rate depending on how small the velocity is
    public int roundVelocity(double vel) {
        double absolute = Math.abs(vel);
        int inverse = (int) Math.round(1.0 / absolute);
        if (vel == 0 || (inverse == 0 && absolute < 1.0)) {
            return 0;
        }
        return (int) (absolute < 1.0 ? (Main.updateCount % inverse == 0 ? 1 * Math.signum(vel) : 0) : vel);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void move() {
        // x
        int moveX = roundVelocity(velX);
        rect.x += moveX;
        if (moveX != 0) {
            handleCollision();
        }

        // y
        int moveY = roundVelocity(velY);
        rect.y += moveY;
        if (moveY != 0) {
            handleCollision();
        }

        // friction
        velX = Utils.friction(velX, friction);
        velY = Utils.friction(velY, friction);
    }

    @Override
    public void handleCollision() {
        // find all collisions
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < entities.size(); i++) {
            Rect r = entities.get(i).rect;
            if (rect.x == r.x && rect.y == r.y) {
                continue;
            }

            if (Physics.rectrect(rect, r)) {
                indexes.add(i);
            }
        }

        // apply a force to and prevent intersection with all entities collided with
        double size = indexes.size();
        for (int i = 0; i < size; i++) {
            Entity e = entities.get(indexes.get(i));
            e.applyForce(velX / size, velY / size);
            Rect.preventIntersection(rect, e.rect);
        }
    }

    @Override
    public void applyForce(double velX, double velY) {
        if (this.moveable) {
            this.velX = velX * Constants.FORCE_TRANSFER;
            this.velY = velY * Constants.FORCE_TRANSFER;
        }
    }

    @Override
    public void draw() {
        Draw.setColor(color);
        Draw.rect(rect);
    }
}
