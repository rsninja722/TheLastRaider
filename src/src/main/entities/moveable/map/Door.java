package main.entities.moveable.map;

import java.awt.Color;
import java.util.ArrayList;

import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Rect;
import main.Options;
import main.entities.Entity;
import main.entities.Wall;

public class Door {

    Rect rect;
    int rotation;

    // frames before closing
    int timeOut = 0;

    boolean open = false;
    boolean openable;

    public static ArrayList<Door> doorList = new ArrayList<Door>();

    public Door(double x, double y, int w, int h, boolean openable, int rotation) {
        rect = new Rect(x, y, w, h);
        this.openable = openable;
        this.rotation = rotation;

        // if door cannot be opened, create a wall that fits it
        if (!openable) {
            switch (rotation) {
                case 0:
                    Entity.entities.add(new Wall(rect.x, rect.y - 6, 16, 4));
                    break;
                case 1:
                    Entity.entities.add(new Wall(rect.x + 6, rect.y, 4, 16));
                    break;
                case 2:
                    Entity.entities.add(new Wall(rect.x, rect.y + 6, 16, 4));
                    break;
                case 3:
                    Entity.entities.add(new Wall(rect.x - 6, rect.y, 4, 16));
                    break;
            }
        }
    }

    public boolean update() {
        if (openable) {
            if (timeOut == 0) {
                // close if there is no entity on door
                open = false;
                for (int i = 0; i < Entity.entities.size(); i++) {
                    Entity e = Entity.entities.get(i);
                    // if there is an entity, open for 1 second
                    if (e.moveable) {
                        if (Physics.rectrect(rect, e.rect)) {
                            open = true;
                            timeOut = 60;
                        }
                    }
                }
            } else {
                timeOut--;
            }
        }

        return false;
    }

    public void draw() {
        if (Options.quality) {
            Draw.image("door" + (openable ? 0 : 1), (int) rect.x, (int) rect.y, rotation * Math.PI / 2.0 - (open ? Math.PI / 2.0 : 0), 1.0);
        }

        if (Utils.debugMode || (!Options.quality && !openable)) {
            Draw.setColor(Color.LIGHT_GRAY);
            Draw.rectOutline(rect);
        }
    }

    public static void updateAll() {
        for (int i = 0; i < doorList.size(); i++) {
            doorList.get(i).update();
        }
    }

    public static void drawAll() {
        for (int i = 0; i < doorList.size(); i++) {
            doorList.get(i).draw();
        }
    }
}
