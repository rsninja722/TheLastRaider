package main.entities.moveable.combat;

import java.util.ArrayList;
import java.util.Arrays;

import engine.Input;
import engine.KeyCodes;
import engine.Utils;
import engine.drawing.Camera;
import engine.drawing.Draw;
import engine.physics.Point;
import main.Constants;
import main.entities.moveable.Moveable;

public class Player extends Moveable {

    enum Attacks {
        JAB, SWIPE, SPIN;
    }

    double angle = 0;
    double walkCycle = 0;
    Point cameraTarget;

    int dashCooldown = 0;

    boolean recordingAttack = false;
    Point lastMousePos;
    double attackAngle;
    ArrayList<Double> angles;

    int attackTime;
    Attacks attack;

    public Player(double x, double y, int w, int h) {
        super(x, y, w, h);
        cameraTarget = new Point(Camera.x, Camera.y);

        this.friction = Constants.PLAYER_FRICTION;
    }

    @Override
    public void update() {

        // find speed
        double moveX = 0;
        double moveY = 0;
        if (Input.keyDown(KeyCodes.W)) {
            moveY = -Constants.PLAYER_SPEED;
        }
        if (Input.keyDown(KeyCodes.A)) {
            moveX = -Constants.PLAYER_SPEED;
        }
        if (Input.keyDown(KeyCodes.S)) {
            moveY = Constants.PLAYER_SPEED;
        }
        if (Input.keyDown(KeyCodes.D)) {
            moveX = Constants.PLAYER_SPEED;
        }

        if (moveX != 0 && moveY != 0) {
            moveX /= 1.3;
            moveY /= 1.3;
        }

        if (dashCooldown > 0) {
            dashCooldown--;
        }
        if (Input.keyClick(KeyCodes.SPACE) && dashCooldown == 0) {
            dashCooldown = Constants.PLAYER_DASH_TIME;
            velX += Math.signum(moveX) * Constants.PLAYER_DASH;
            velY += Math.signum(moveY) * Constants.PLAYER_DASH;
        }

        // move
        if (Math.abs(velX) < Constants.PLAYER_SPEED) {
            velX = moveX;
        }
        if (Math.abs(velY) < Constants.PLAYER_SPEED) {
            velY = moveY;
        }
        move();

        // walk animation
        if (moveX != 0 || moveY != 0) {
            walkCycle += 0.25;
            if (walkCycle >= 6) {
                walkCycle = 0;
            }

            // turning
            angle = Utils.turnTo(angle, Utils.pointTo(rect.x, rect.y, rect.x + moveX, rect.y + moveY), 0.2);
        }

        if (Input.mouseClick(0)) {
            recordingAttack = true;
            angles = new ArrayList<Double>();
        }
        if (recordingAttack) {
            if (!Input.mouseDown(0)) {
                recordingAttack = false;
            }

            if (lastMousePos == null) {
                lastMousePos = new Point(Input.mousePos.x, Input.mousePos.y);
                attackAngle = Utils.pointTo(rect.x, rect.y, lastMousePos.x, lastMousePos.y);
            } else {
                angles.add(Utils.pointTo(lastMousePos, Input.mousePos));
                lastMousePos = new Point(Input.mousePos.x, Input.mousePos.y);
                if (angles.size() > 9) {
                    analyzeAttack();
                }
            }
        }

        if (attackTime > 0) {
            attackTime--;
        }

        // move camera
        Camera.zoom = 3;
        Camera.centerOn((int) rect.x, (int) rect.y);

        // debug
        Utils.putInDebugMenu("x", rect.x);
        Utils.putInDebugMenu("y", rect.y);
        Utils.putInDebugMenu("vx", velX);
        Utils.putInDebugMenu("vy", velY);
        Utils.putInDebugMenu("angle", angle);
    }

    void analyzeAttack() {
        double size = (double) angles.size();
        double[] samples = new double[5];
        double average = 0;
        double differenceTotal = 0;

        if (size > 4) {
            for (double i = 0; i < 5; i++) {
                samples[(int) i] = angles.get((int) Math.floor(Utils.mapRange(i, 0, 4, 0, size - 1)));
                average += samples[(int) i];
            }
        }
        average /= 5;

        for (int i = 0; i < 5; i++) {
            differenceTotal += Math.abs(average - samples[i]);
        }

        if (differenceTotal > 5.0) {
            attack = Attacks.SPIN;
            attackTime = 20;
        } else {
            double diff = Math.abs(angle - average);
            if (diff > 1.0 && diff < 4.0) {
                attack = Attacks.SWIPE;
                attackTime = 15;
            }
            if (diff < 1.0) {
                attack = Attacks.JAB;
                attackTime = 10;
            }
        }

        if (attack != null) {
            recordingAttack = false;
            lastMousePos = null;
        }
    }

    @Override
    public void draw() {
        int walk = (int) walkCycle;
        walk = walk > 3 ? 6 - walk : walk;
        Draw.image("playerLegs" + walk, (int) rect.x, (int) rect.y, angle, 1.0);

        if (attackTime > 0) {
            double rotOff;
            switch (attack) {
                case SPIN:
                    rotOff = Utils.mapRange(attackTime, 0, 20, Math.PI * 2.0, 0);
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3, angle + rotOff);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1, angle + rotOff);
                    drawOff("playerSword1", rect.x, rect.y, angle + rotOff, -5, 3, angle + rotOff);
                    drawOff("playerArmRight", rect.x, rect.y, angle + rotOff, -5, 1, angle + rotOff);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle + rotOff, 1.0);
                    break;
                case SWIPE:
                    rotOff = Utils.mapRange(attackTime, 0, 15, Math.PI / 3.0, -Math.PI / 3.0);
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3, angle);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1, angle);
                    drawOff("playerSword1", rect.x, rect.y, angle + rotOff, -5, 3, angle + rotOff);
                    drawOff("playerArmRight", rect.x, rect.y, angle + rotOff, -5, 1, angle);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff / 2.0, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
                    break;
                case JAB:
                    rotOff = Utils.mapRange(attackTime, 0, 10, Math.PI / 4.0, -Math.PI / 4.0);
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3 - attackTime / 3, angle);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1 - attackTime / 3, angle);
                    drawOff("playerSword1", rect.x, rect.y, angle, -5, (int) (3 + attackTime / 1.5), angle);
                    drawOff("playerArmRight", rect.x, rect.y, angle, -5, (int) (1 + attackTime / 1.5), angle);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff / 2.0, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
                    break;
            }
        } else {
            drawOff("playerShield", rect.x, rect.y, angle, 5, 3, angle);
            drawOff("playerArmLeft", rect.x, rect.y, angle, 5, 1, angle);
            drawOff("playerSword0", rect.x, rect.y, angle, -5, 3, angle);
            drawOff("playerArmRight", rect.x, rect.y, angle, -5, 1, angle);
            Draw.image("playerBody", (int) rect.x, (int) rect.y, angle, 1.0);
            Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
        }

    }

    static void drawOff(String name, double x, double y, double offAng, int offX, int offY, double angle) {
        double sideX = Math.cos(offAng - Math.PI / 2.0) * offX;
        double sideY = Math.sin(offAng - Math.PI / 2.0) * offX;
        double frontX = Math.cos(offAng) * offY;
        double frontY = Math.sin(offAng) * offY;
        Draw.image(name, (int) (x + sideX + frontX), (int) (y + sideY + frontY), angle, 1.0);
    }

}
