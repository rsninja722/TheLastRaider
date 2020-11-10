package main.entities.moveable.combat;

import java.util.ArrayList;

import engine.Utils;
import engine.audio.Sounds;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Point;
import main.Main;
import main.entities.moveable.Moveable;
import main.util.AStar;
import java.awt.Color;

public class Enemy extends Moveable {

    protected int walkCycle = 0;
    int walkCount = 0;
    protected boolean moving = false;
    protected int walkCountInterval = 15;
    protected int range = 16;
    protected double speed = 0.2;

    protected int attackTime = 0;

    protected double angle = 0;

    ArrayList<Point> path = new ArrayList<Point>();

    public Enemy(double x, double y, int w, int h) {
        super(x, y, w, h);

        damageable = true;
    }

    @Override
    public boolean update() {
        if(hp < 1) {
            Sounds.play("enemydeath" + Utils.rand(0, 4));
            return true;
        }

        double playerDist = Physics.dist(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
        if(Main.updateCount % Math.ceil(playerDist/3) == 0 && playerDist < 200) {
            path = AStar.findPath((int) rect.x, (int) rect.y,(int) Main.player.rect.x, (int) Main.player.rect.y);
        }
        
        if(playerDist > range && attackTime == 0) {
            walkCount++;
            if(walkCount % walkCountInterval == 0) {
                walkCycle++;
                if(walkCycle > 3) {
                    walkCycle = 0;
                }
            }
            moving = true;

            if(path.size() > 0) {
                angle = Utils.pointTo(rect.x, rect.y, path.get(0).x, path.get(0).y);
            }
            if (Math.abs(velX) < speed) {
                velX = Math.cos(angle) * speed;
            }
            if (Math.abs(velY) < speed) {
                velY = Math.sin(angle) * speed;
            }
        } else {
            moving = false;
            if(attackTime == 0) {
                angle = Utils.pointTo(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
            }
            attack();
        }
        move();

        if(path.size() > 0) { 
            if(Physics.dist(rect.x, rect.y, path.get(0).x, path.get(0).y) < 4) {
                path.remove(0);
            }
        }
        return false;
    }

    public void attack() {
    }

    public void baseDraw() {
        Draw.setColor(new Color(80, 31, 31, 200));
        Draw.rect((int)rect.x, (int)rect.y + 12, 16, 2);

        int size = (int)Utils.mapRange(hp, 0, maxHp, 0, 16);
        Draw.setColor(new Color(201, 52, 52, 200));
        Draw.rect((int)rect.x - (8-size/2), (int)rect.y + 12, size, 2);

        if(Utils.debugMode) {
            Draw.setColor(Color.BLUE);
            Draw.rectOutline(rect);
        }
    }

}
