package main;

/**
 * James N
 * 2020.10.22
 * The Last Raider
 * 
 * TODO
 * death permanence
 * music
 * death
 * dark rooms
 * bow
 * blocking
 * boss
 * health/arrow pickup 
 */

import engine.*;
import engine.audio.Sounds;
import engine.drawing.Camera;
import engine.drawing.Draw;
import main.entities.Damage;
import main.entities.Entity;
import main.entities.moveable.combat.Player;
import main.entities.moveable.map.Door;
import main.entities.moveable.map.Stairs;
import main.map.LoadMap;
import main.map.Map;
import main.ui.Component;
import main.ui.MainMenu;

import java.awt.Color;
import java.util.ArrayList;


public class Main extends GameJava {

    public enum State {
        TITLE, CUTSCENE, TRANSITION, PLAYING
    }

    public static State state = State.TITLE;

    public static boolean showHitboxes = false;

    public static Entity player;

    static String[] levels = {"basement", "first", "second", "third"};

    public static int drawLevel = 0;
    public static int level = 0;

    public static int stairsID = 9;
    
    public static int transitionTime = 21;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super(800, 600, 60, 60);

        Sounds.globalVolume(0.9f);
        Sounds.adjustGain("swordswoosh", 0.9f);
        Sounds.adjustGain("desk0", 0.8f);
        Sounds.adjustGain("desk1", 0.8f);
        Sounds.adjustGain("walk0", 0.9f);
        Sounds.adjustGain("walk1", 0.95f);
        Sounds.adjustGain("walk2", 0.95f);

        MainMenu.generate();

        for(int i = 0;i<4;i++) {
            Entity.entitiesList.add(new ArrayList<Entity>());
        }
        Entity.entities = Entity.entitiesList.get(3);

        player = new Player(1160, 1530, 12, 12);

        drawLevel = 3;

        LoopManager.startLoops(this);
    }

    public static void loadLevel(int lvl) {
        Door.doorList = new ArrayList<Door>();
        Stairs.stairsList = new ArrayList<Stairs>();
        Entity.entities.remove(player);
        Entity.entities = Entity.entitiesList.get(lvl);
        Entity.entities.add(player);

        level = lvl;

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + levels[level] + ".txt");
        Map.loaded[level] = true;
    }

    @Override
    public void update() {
        switch (state) {
            case TITLE:
                Component.updateAll(MainMenu.components);
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                if(transitionTime > 0) {
                    transitionTime--;
                    if(transitionTime == 20) {
                        drawLevel = level;
                        loadLevel(level);
                        Camera.centerOn((int)player.rect.x,(int)player.rect.y);
                    }
                    
                } else {
                    transitionTime = 40;
                    state = State.PLAYING;
                }
                break;
            case PLAYING:
                Entity.updateAll();
                Stairs.updateAll();
                Door.updateAll();
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
            case PLAYING:
                Draw.setColor(new Color(30, 30, 30));
                Draw.rect(-Camera.x + gw / 2, -Camera.y + gh / 2, gw / (int) Camera.zoom, gh / (int) Camera.zoom);

                int w = 1920;
                int h = 2400;
                if(Map.levelImgs[level] != null) {
                    w = Map.levelImgs[level].getWidth();
                    h = Map.levelImgs[level].getHeight();
                }
                // Draw.image(Map.img, w / 2, h / 2, w, h);
                // Draw.image(drawLevel+"bottom", w / 2, h / 2);
                Draw.image(Map.levelImgs[drawLevel],w / 2, h / 2,w,h);
                
                Door.drawAll();

                for (int i = 0; i < Entity.entities.size(); i++) {
                    Entity.entities.get(i).draw();
                }

                Stairs.drawAll();
                Damage.drawAll();

                Draw.image(drawLevel+"top", w / 2, h / 2);
                break;
        }

    }

    @Override
    public void absoluteDraw() {
        switch (state) {
            case TITLE:
            Draw.setColor(new Color(20,20,20));
                Draw.rect(gw/2, gh/2, gw, gh);
                Component.drawAll(MainMenu.components);
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                if(transitionTime > 20) {
                    Draw.setColor(new Color(0,0,0, (int)Utils.mapRange(transitionTime, 20, 40, 255, 0)));
                } else {
                    Draw.setColor(new Color(0,0,0, (int)Utils.mapRange(20-transitionTime, 0, 20, 255, 0)));
                }
                Draw.rect(gw/2, gh/2, gw, gh);
                break;
            case PLAYING:
                break;
        }
    }
}
