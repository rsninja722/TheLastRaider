package main.entities;

import java.util.ArrayList;

import engine.Utils;
import engine.audio.Sounds;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Rect;
import main.Main;

import java.awt.Color;

public class Damage {
    public static ArrayList<Damage> damages = new ArrayList<Damage>();

    Rect rect;

    int amount;

    // time before de-spawning
    int timeOut = 10;

    // keep track of what has been hit, so they are not hit again, and limit the amount of entities that can be hit
    int[] entitiesAffected = { 0, 0, 0 };

    // where the next entity should be stored
    int entitiesAffectedIndex = 0;

    boolean affectPlayer;

    public Damage(double x, double y, int w, int h, int amount, boolean affectPlayer) {
        rect = new Rect(x, y, w, h);
        this.amount = amount;
        this.affectPlayer = affectPlayer;
    }

    public boolean update() {
        // de-spawn
        if (--timeOut < 1) {
            return true;
        }

        if (affectPlayer) {
            // damage player, then de-spawn
            if (Physics.rectrect(rect, Main.player.rect)) {
                Main.player.hp--;
                timeOut = 0;
                Sounds.play("playerhit" + Utils.rand(0, 1));
            }
        } else {
            // check all entities
            for (int i = 0; i < Entity.entities.size(); i++) {
                // make sure they haven't been hit already
                if (entitiesAffected[0] == i || entitiesAffected[1] == i || entitiesAffected[2] == i) {
                    continue;
                }
                // if it can be damaged and is in contact
                Entity e = Entity.entities.get(i);
                if (e.damageable) {
                    if (Physics.rectrect(rect, e.rect)) {
                        // play hit sound
                        if (e.neutral) {
                            Sounds.play("desk" + Utils.rand(0, 1));
                        } else {
                            Sounds.play("enemyhit" + Utils.rand(0, 2));
                        }
                        // deal damage
                        e.hp -= amount;
                        // track what was hit
                        entitiesAffected[entitiesAffectedIndex] = i;
                        // if hit 3 entities, de-spawn
                        if (++entitiesAffectedIndex > 2) {
                            timeOut = 0;
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void draw() {
        Draw.rectOutline(rect);
    }

    public static void updateAll() {
        for (int i = 0; i < damages.size(); i++) {
            if (damages.get(i).update()) {
                damages.remove(i);
            }
        }
    }

    public static void drawAll() {
        if (Utils.debugMode) {
            Draw.setColor(Color.RED);
            for (int i = 0; i < damages.size(); i++) {
                damages.get(i).draw();
            }
        }
    }
}
