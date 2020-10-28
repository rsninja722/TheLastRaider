package main.entities.moveable.combat;

import engine.Input;
import engine.KeyCodes;
import engine.Utils;
import engine.drawing.Camera;
import engine.drawing.Draw;
import engine.physics.Point;
import main.Constants;
import main.entities.moveable.Moveable;
import main.Main;

public class Player extends Moveable {

    double angle = 0;
    double walkCycle = 0;
    Point cameraTarget;

    public Player(double x, double y, int w, int h) {
        super(x, y, w, h);
        cameraTarget = new Point(Camera.x, Camera.y);

        this.friction = Constants.PLAYER_FRICTION;
    }

    @Override
    public void update() {

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
        
        if(moveX != 0 && moveY != 0) {
            moveX /= 1.5;
            moveY /= 1.5;
        }

        applyForce(moveX, moveY);
        move();

        if(moveX != 0 || moveY != 0) { 
            walkCycle += 0.25;
            if (walkCycle >= 6) {
                walkCycle = 0;
            }

            angle = Utils.turnTo(angle, Utils.pointTo(rect.x, rect.y, rect.x+moveX, rect.y+moveY), 0.1);
        }

        Camera.zoom = 3;

        // cameraTarget.x = -this.rect.x + Main.gw / 2;
        // cameraTarget.y = -this.rect.y + Main.gh / 2;

        // Camera.x = (int) Utils.lerp(Camera.x, cameraTarget.x, 0.1);
        // Camera.y = (int) Utils.lerp(Camera.y, cameraTarget.y, 0.1);
        Camera.centerOn((int) rect.x, (int) rect.y);

        Utils.putInDebugMenu("x", rect.x);
        Utils.putInDebugMenu("y", rect.y);
        Utils.putInDebugMenu("angle", angle);
    }

    @Override
    public void draw() {
        int walk = (int) walkCycle;
        walk = walk > 3 ? 6 - walk : walk;
        Draw.image("playerLegs" + walk, (int)rect.x, (int)rect.y, angle, 1.0);

        double sideX = Math.cos(angle-Math.PI/2.0)*5;
        double sideY = Math.sin(angle-Math.PI/2.0)*5;
        double frontX = Math.cos(angle);
        double frontY = Math.sin(angle);
        Draw.image("playerSword0", (int)(rect.x-sideX+frontX*3.0), (int)(rect.y-sideY+frontY*3.0), angle, 1.0);
        Draw.image("playerShield", (int)(rect.x+sideX+frontX*3.0), (int)(rect.y+sideY+frontY*3.0), angle, 1.0);
        
        Draw.image("playerArmRight", (int)(rect.x-sideX+frontX), (int)(rect.y-sideY+frontY), angle, 1.0);
        Draw.image("playerArmLeft", (int)(rect.x+sideX+frontX), (int)(rect.y+sideY+frontY), angle, 1.0);

        Draw.image("playerBody", (int)rect.x, (int)rect.y, angle, 1.0);
        Draw.image("playerHead", (int)rect.x, (int)rect.y, angle, 1.0);
    }

}
