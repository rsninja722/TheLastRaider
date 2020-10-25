package main;

/**
 * James N
 * 2020.10.22
 * The Last Raider
 */

import engine.*;
import engine.drawing.Camera;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Rect;
import main.entities.Entity;
import main.entities.map.Wall;
import main.map.LoadMap;
import main.map.Map;

import java.awt.Color;


public class Main extends GameJava {

    enum State {
        TITLE, CUTSCENE, TRANSITION, PLAYING
    }

    State state = State.TITLE;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super(800, 600, 60, 60);

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + "second.txt");
        Camera.centerOn(1000,1000);

        LoopManager.startLoops(this);
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
                break;
        }

        if (Input.keyDown(KeyCodes.W)) {
            Camera.y += 5;
        }
        if (Input.keyDown(KeyCodes.A)) {
            Camera.x += 5;
        }
        if (Input.keyDown(KeyCodes.S)) {
            Camera.y -= 5;
        }
        if (Input.keyDown(KeyCodes.D)) {
            Camera.x -= 5;
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
        Draw.image(Map.img, w/2, h/2, w, h);

        for(int i=0;i<Entity.entities.size();i++) {
            Entity.entities.get(i).draw();
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

    public static Entity colliding(Rect r) {

        for(int i=0;i<Map.walls.length;i++) {
            Rect rect = Map.walls[i].rect;
            if(! (rect.x == r.x && rect.y == r.y)) {
                continue;
            }

            if(Physics.rectrect(rect, r)) {
                return Entity.class.cast(Map.walls[i]);
            }
        }

        return null;
    } 
}
