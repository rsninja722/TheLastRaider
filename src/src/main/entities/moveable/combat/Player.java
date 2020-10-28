package main.entities.moveable.combat;

import engine.Input;
import engine.KeyCodes;
import engine.Utils;
import engine.drawing.Camera;
import main.entities.moveable.Movable;

public class Player extends Movable {

    public Player(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update() {
        if (Input.keyDown(KeyCodes.W)) {
            applyForce(0, -3);
        }
        if (Input.keyDown(KeyCodes.A)) {
            applyForce(-3, 0);
        }
        if (Input.keyDown(KeyCodes.S)) {
            applyForce(0, 3);
        }
        if (Input.keyDown(KeyCodes.D)) {
            applyForce(3, 0);
        }

        Camera.zoom = 5;
        Camera.centerOn((int)this.rect.x, (int)this.rect.y);

        Utils.putInDebugMenu("x", this.rect.x);
        Utils.putInDebugMenu("y", this.rect.y);
    }
    
}
