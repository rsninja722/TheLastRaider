package main.entities.moveable.combat;

import java.util.ArrayList;

import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Point;
import main.Main;
import main.entities.moveable.Moveable;
import main.util.AStar;

public class Enemy extends Moveable {

    int walkCycle = 0;
    int walkCount = 0;
    protected int walkCountInterval = 15;
    protected int range = 16;
    protected double speed = 1.0;
    protected String name = "normal";

    double angle = 0;

    ArrayList<Point> path = new ArrayList<Point>();

    public Enemy(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update() {
        walkCount++;
        if(walkCount % walkCountInterval == 0) {
            walkCycle++;
            if(walkCycle > 3) {
                walkCycle = 0;
            }
        }

        double playerDist = Physics.dist(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
        if(Main.updateCount % Math.ceil(playerDist/3) == 0) {
            path = AStar.findPath((int) rect.x, (int) rect.y,(int) Main.player.rect.x, (int) Main.player.rect.y);
        }
        
        if(playerDist > range) {
            if(path.size() > 0) {
                angle = Utils.pointTo(rect.x, rect.y, path.get(0).x, path.get(0).y);
            }
            applyForce(Math.cos(angle) * speed, Math.sin(angle) * speed);
        } else {
            angle = Utils.pointTo(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
            attack();
        }
        move();

        if(path.size() > 0) { 
            if(Physics.dist(rect.x, rect.y, path.get(0).x, path.get(0).y) < 4) {
                path.remove(0);
            }
        }
    }

    public void attack() {
    }

    @Override
    public void draw() {
        Draw.image(name+"Walk"+walkCycle, (int)rect.x, (int)rect.y, angle, 1);
    }

}
