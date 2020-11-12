package main.entities.moveable.map;

import java.util.ArrayList;

import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Point;
import main.Main;
import main.Music;
import main.entities.Entity;

import java.awt.Color;

public class Stairs extends Entity {

    public static ArrayList<Stairs> stairsList = new ArrayList<Stairs>();

    int id;
    int floor;
    boolean disabled = true;

    public Stairs(double x, double y, int w, int h, int id, int floor) {
        super(x, y, w, h);
        this.id = id;
        this.floor = floor;
    }

    @Override
    public boolean update() {
        if (disabled) {
            // wait until player has left stairs to re-enable
            if (!Physics.rectrect(rect, Main.player.rect)) {
                disabled = false;
            }
        } else {
            // if player steps onto stair, set to transition to the linked floor
            if (Physics.rectrect(rect, Main.player.rect)) {
                Main.stairsID = id;
                Main.level = floor;
                Main.state = Main.State.TRANSITION;
                Music.dontChange = true;
            }
        }

        return false;
    }

    @Override
    public void draw() {
        if (Utils.debugMode) {
            if (disabled) {
                Draw.setColor(Color.MAGENTA);
            } else {
                Draw.setColor(Color.YELLOW);
            }
            Draw.rectOutline(rect);
        }
    }

    public static void updateAll() {
        for (int i = 0; i < stairsList.size(); i++) {
            stairsList.get(i).update();
        }
    }

    public static void drawAll() {
        for (int i = 0; i < stairsList.size(); i++) {
            stairsList.get(i).draw();
        }
    }

    // returns the position of the stair with the current ID
    public static Point getSpawnPos() {
        for (int i = 0; i < stairsList.size(); i++) {
            Stairs s = stairsList.get(i);
            if (s.id == Main.stairsID) {
                s.disabled = true;
                return new Point(s.rect.x, s.rect.y);
            }
        }

        return new Point(1160, 1530);
    }

}
