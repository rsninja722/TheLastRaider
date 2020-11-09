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
    int timeOut = 20;
    int[] entitiesAffected = {0, 0, 0};
    int entitiesAffectedIndex = 0;
    boolean affectPlayer;

    public Damage(double x, double y, int w, int h, int amount, boolean affectPlayer) {
        rect = new Rect(x, y, w, h);
        this.amount = amount;
        this.affectPlayer = affectPlayer;
    }

    public boolean update() {
        if (--timeOut < 1) {
            return true;
        }

        if (affectPlayer) {
            if (Physics.rectrect(rect, Main.player.rect)) {
                Main.player.hp--;
            }
        } else {
            for (int i = 0; i < Entity.entities.size(); i++) {
                if (entitiesAffected[0] == i || entitiesAffected[1] == i || entitiesAffected[2] == i) {
                    continue;
                }
                Entity e = Entity.entities.get(i);
                if (e.damageable) {
                    if (Physics.rectrect(rect, e.rect)) {
                        Sounds.play("bonk");
                        e.hp -= amount;
                        entitiesAffected[entitiesAffectedIndex] = i;
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

    public static void updateAll() {
        for (int i = 0; i < damages.size(); i++) {
            if (damages.get(i).update()) {
                damages.remove(i);
            }
        }
    }

    public static void drawAll() {
        if(Utils.debugMode) {
            Draw.setColor(Color.RED);
            for (int i = 0; i < damages.size(); i++) {
                damages.get(i).draw();
            }
        }
    }

    public void draw() {
        Draw.rectOutline(rect);
    }
}
