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

    // walk animation frame
    protected int walkCycle = 0;
    // counter for changing walk animation 
    int walkCount = 0;
    protected int walkCountInterval = 15;

    protected boolean moving = false;

    // range enemy can attack at 
    protected int range = 16;

    // walk speed
    protected double speed = 1.0;

    protected int attackTime = 0;

    protected double angle = 0;

    ArrayList<Point> path = new ArrayList<Point>();

    public Enemy(double x, double y, int w, int h) {
        super(x, y, w, h);

        damageable = true;
    }

    @Override
    public boolean update() {
        if (hp < 1) {
            Sounds.play("enemydeath" + Utils.rand(0, 4));
            return true;
        }

        // path find when player is close enough 
        double playerDist = Physics.dist(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
        if (Main.updateCount % Math.ceil(playerDist / 3) == 0 && playerDist < 200) {
            path = AStar.findPath((int) rect.x, (int) rect.y, (int) Main.player.rect.x, (int) Main.player.rect.y);
        }

        // move towards player if not in range and not attacking
        if (playerDist > range && attackTime == 0) {
            // walk animation
            walkCount++;
            if (walkCount % walkCountInterval == 0) {
                walkCycle++;
                if (walkCycle > 3) {
                    walkCycle = 0;
                }
            }
            moving = true;

            // move if there are more points along path
            if (path.size() > 0) {
                angle = Utils.pointTo(rect.x, rect.y, path.get(0).x, path.get(0).y);
            }
            if (Math.abs(velX) < speed) {
                velX = Math.cos(angle) * speed;
            }
            if (Math.abs(velY) < speed) {
                velY = Math.sin(angle) * speed;
            }
        } else {
            // if close to player and not attacking, look at player
            moving = false;
            if (attackTime == 0) {
                angle = Utils.pointTo(rect.x, rect.y, Main.player.rect.x, Main.player.rect.y);
            }
            attack();
        }
        move();

        // if point along path has been reached, remove it
        if (path.size() > 0) {
            if (Physics.dist(rect.x, rect.y, path.get(0).x, path.get(0).y) < 4) {
                path.remove(0);
            }
        }
        return false;
    }

    public void attack() {
    }

    public void baseDraw() {
        // health bar
        Draw.setColor(new Color(80, 31, 31, 200));
        Draw.rect((int) rect.x, (int) rect.y + 12, 16, 2);

        int size = (int) Utils.mapRange(hp, 0, maxHp, 0, 16);
        Draw.setColor(new Color(201, 52, 52, 200));
        Draw.rect((int) rect.x - (8 - size / 2), (int) rect.y + 12, size, 2);

        // hit box
        if (Utils.debugMode) {
            Draw.setColor(Color.BLUE);
            Draw.rectOutline(rect);
        }
    }

}
