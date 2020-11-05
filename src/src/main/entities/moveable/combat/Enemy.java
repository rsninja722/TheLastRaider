package main.entities.moveable.combat;

import engine.Utils;
import engine.drawing.Draw;
import main.Main;
import main.entities.moveable.Moveable;

public class Enemy extends Moveable {

    int[] walkFrames = {0,1,2,1};
    int walkCycle = 0;

    double angle;

    public Enemy(double x, double y, int w, int h) {
        super(x, y, w, h);
        angle = Utils.rand(0,3) * Math.PI/2.0;
    }

    @Override
    public void update() {
        if(Main.updateCount % 15 == 0) {
            walkCycle++;
            if(walkCycle > 3) {
                walkCycle = 0;
            }
        }
        applyForce(Math.cos(angle)/2.0, Math.sin(angle)/2.0);
        move();
    }

    @Override
    public void draw() {
        Draw.image("heavyWalk"+walkFrames[walkCycle], (int)rect.x, (int)rect.y, angle, 1);
    }

}
