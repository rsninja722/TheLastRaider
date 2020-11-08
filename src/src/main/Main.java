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

    State state = State.TITLE;

    public static boolean showHitboxes = false;

    public static Entity player;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super(800, 600, 60, 60);

        Entity.entities.add(new Player(1400, 184, 12, 12));
        player = Entity.entities.get(0);

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + "second.txt");
        Camera.centerOn(1000, 1000);

        LoopManager.startLoops(this);
    }

    @Override
    public void update() {
        Utils.putInDebugMenu("m - toggle hitboxes", "on:" + showHitboxes);
        if (Input.keyClick(KeyCodes.M)) {
            showHitboxes = (showHitboxes ? false : true);
        }
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
        for (int i = 0; i < Entity.entities.size(); i++) {
            Entity.entities.get(i).update();
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
                break;
        }

        Draw.setColor(new Color(30, 30, 30));
        Draw.rect(-Camera.x + gw / 2, -Camera.y + gh / 2, gw / (int) Camera.zoom, gh / (int) Camera.zoom);

        int w = Map.img.getWidth();
        int h = Map.img.getHeight();
        Draw.image(Map.img, w / 2, h / 2, w, h);
        // Draw.image("b", w/2, h/2); 

        for (int i = 0; i < Entity.entities.size(); i++) {
            Entity.entities.get(i).draw();
        }

        // Draw.setColor(Color.WHITE);
        // if (path != null) {
        //     for (int i = 0; i < path.size(); i++) {
        //         if (path != null) {
        //             Draw.rectOutline((int) path.get(i).x, (int) path.get(i).y, 16, 16);
        //         }
        //     }
        // }
        // Draw.image("a", w/2, h/2);
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
