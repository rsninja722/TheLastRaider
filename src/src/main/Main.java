package main;

/**
 * James N
 * 2020.10.22
 * The Last Raider
 */

import engine.*;
import engine.drawing.Camera;
import engine.drawing.Draw;
import engine.drawing.Sprites;
import engine.physics.Physics;
import engine.physics.Point;
import engine.physics.Rect;
import main.entities.Damage;
import main.entities.Entity;
import main.entities.Wall;
import main.entities.moveable.Moveable;
import main.entities.moveable.combat.Player;
import main.map.LoadMap;
import main.map.Map;
import main.util.AStar;

import java.awt.Color;
import java.util.ArrayList;

public class Main extends GameJava {

    enum State {
        TITLE, CUTSCENE, TRANSITION, PLAYING
    }

    State state = State.PLAYING;

    public static boolean showHitboxes = false;

    public static Entity player;

    static String[] levels = {"basement", "first", "second", "third"};

    public static int level = 0;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super(800, 600, 60, 60);

        loadLevel(3);

        LoopManager.startLoops(this);
    }

    public static void loadLevel(int lvl) {
        Entity.entities = new ArrayList<Entity>();

        Entity.entities.add(new Player(1160, 1530, 12, 12));
        player = Entity.entities.get(0);

        level = lvl;

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + levels[level] + ".txt");
    }

    @Override
    public void update() {
        switch (state) {
            case TITLE:
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                break;
            case PLAYING:
                Entity.updateAll();
                Damage.updateAll();
                break;
        }

    }

    @Override
    public void draw() {
        switch (state) {
            case TITLE:
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                break;
            case PLAYING:
                Draw.setColor(new Color(30, 30, 30));
                Draw.rect(-Camera.x + gw / 2, -Camera.y + gh / 2, gw / (int) Camera.zoom, gh / (int) Camera.zoom);

                int w = Map.img.getWidth();
                int h = Map.img.getHeight();
                // Draw.image(Map.img, w / 2, h / 2, w, h);
                Draw.image(level+"bottom", w / 2, h / 2);

                for (int i = 0; i < Entity.entities.size(); i++) {
                    Entity.entities.get(i).draw();
                }

                Damage.drawAll();

                Draw.image(level+"top", w / 2, h / 2);
                break;
        }

    }

    @Override
    public void absoluteDraw() {
        switch (state) {
            case TITLE:
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                break;
            case PLAYING:
                break;
        }
    }
}
