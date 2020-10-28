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
import engine.physics.Rect;
import main.entities.Entity;
import main.entities.Wall;
import main.entities.moveable.Moveable;
import main.entities.moveable.combat.Player;
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

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + "first.txt");
        Camera.centerOn(1000,1000);

        Entity.entities.add(new Player(1400, 184, 12, 12));
        Entity.entities.add(new Moveable(1400, 200, 16, 16));
        Entity.entities.add(new Moveable(1400, 220, 16, 16));
        Entity.entities.add(new Moveable(1400, 250, 16, 16));

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
        for(int i=0;i<Entity.entities.size();i++) {
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
        // Draw.image(Map.img, w/2, h/2, w, h);
        Draw.image("b", w/2, h/2);
        
        for(int i=0;i<Entity.entities.size();i++) {
            Entity.entities.get(i).draw();
        }
        
        Draw.image("a", w/2, h/2);
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
