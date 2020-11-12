package main;

/**
 * James N
 * 2020.10.22
 * The Last Raider
 * 
 * top down zombie survival game, focusing on melee combat
 * map made with a modified version of an editor for a different game
 * map and code by James
 * audio and most art assets, and map renders by Nathan Desjardins
 * 
 * Code Highlights
 * parts of the code that were more difficult for me:
 * A* path finding (/util/AStar.java, /util/Node.java)
 * Entity system, especially movable entities (/entities/moveable)
 * Gesture base attack system, it does not work that well, but it was new to me (/entities/movable/combat/Player.java) 
 * 
 * Debug
 * press f3 to enter debug mode
 * this will show fps, player info, and hitboxes
 * pressing v will toggle collision for the player
 * 
 * Story
 * The school has been infected with zombies overnight. They seem to have some generic airborne disease causing there zombification.
 * You, as the last combat ready Beal Raider must clear out the school of any zombies. The source of the infection seems to be coming 
 * from the gym, make your way down there and find out what is happening.
 * 
 * How To Play
 * 
 * to go between floors, walk to the top/bottom of the staircase you would normally 
 * 
 * movement
 *      wasd - move
 *      space - dash in current direction
 * 
 * escape - pause open and options
 *
 * combat
 * click mode
 *      lmb - swipe
 *      rmb - jab
 *      hold lmb and rmb - spin
 * motion mode
 *      rmb - turn towards mouse
 *      hold lmb and drag a
 *          line away from player - jab
 *          line perpendicular to player - swipe
 *          circle - spin
 *
 * TODO
 * death permanence
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
import main.ui.GameOver;
import main.ui.MainMenu;
import main.ui.OptionsUI;

import java.awt.Color;
import java.util.ArrayList;

public class Main extends GameJava {

    // possible game states
    public enum State {
        TITLE, CUTSCENE, TRANSITION, PLAYING, GAMEOVER, OPTIONS;
    }

    public static State state = State.TITLE;
    public static State lastState = State.CUTSCENE;

    public static boolean showHitboxes = false;

    public static Entity player;

    // level file names
    static String[] levels = { "basement", "first", "second", "third" };

    // level to show
    public static int drawLevel = 0;
    // level to use in update
    public static int level = 0;

    // which stair to spawn at
    public static int stairsID = 9;

    // counter for fade in/out
    public static int transitionTime = 21;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super(800, 600, 60, 60);

        // adjust sound volume
        Sounds.globalVolume(0.9f);
        Sounds.adjustGain("swordswoosh", 0.9f);
        Sounds.adjustGain("desk0", 0.8f);
        Sounds.adjustGain("desk1", 0.8f);
        Sounds.adjustGain("walk0", 0.9f);
        Sounds.adjustGain("walk1", 0.95f);
        Sounds.adjustGain("walk2", 0.95f);

        // create UI
        MainMenu.generate();
        GameOver.generate();
        OptionsUI.generate();

        // create entity list for each level
        for (int i = 0; i < 4; i++) {
            Entity.entitiesList.add(new ArrayList<Entity>());
        }
        // set current entity list to third floor
        Entity.entities = Entity.entitiesList.get(3);

        // create player
        player = new Player(1160, 1530, 12, 12);

        // set which level to show
        drawLevel = 3;

        LoopManager.startLoops(this);
    }

    // queues a level to be loaded, and transitioned to 
    public static void loadLevel(int lvl) {
        // clear non permanent array lists 
        Door.doorList = new ArrayList<Door>();
        Stairs.stairsList = new ArrayList<Stairs>();

        // set entity aray list
        Entity.entities.remove(player);
        Entity.entities = Entity.entitiesList.get(lvl);
        Entity.entities.add(player);

        level = lvl;

        LoadMap.loadMap(baseDirectory + directoryChar + "levels" + directoryChar + levels[level] + ".txt");

        // make sure the image is not redrawn next time
        Map.loaded[level] = true;
    }

    @Override
    public void update() {
        Music.update();
        lastState = state;
        switch (state) {
            case TITLE:
                Component.updateAll(MainMenu.components);
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                // fade in and out
                if (transitionTime > 0) {
                    transitionTime--;
                    if (transitionTime == 20) {
                        // switch level when screen is black
                        drawLevel = level;
                        loadLevel(level);
                        Camera.centerOn((int) player.rect.x, (int) player.rect.y);
                    }

                } else {
                    transitionTime = 40;
                    state = State.PLAYING;
                }
                break;
            case PLAYING:
                Music.dontChange = false;
                Entity.updateAll();
                Stairs.updateAll();
                Door.updateAll();
                Damage.updateAll();
                break;
            case GAMEOVER:
                Component.updateAll(GameOver.components);
                break;
            case OPTIONS:
                Component.updateAll(OptionsUI.components);
                if (Input.keyClick(KeyCodes.ESCAPE)) {
                    OptionsUI.back();
                }
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
                // black background
                Draw.setColor(new Color(0, 0, 0));
                Draw.rect(-Camera.x + gw / 2, -Camera.y + gh / 2, gw / (int) Camera.zoom, gh / (int) Camera.zoom);

                // find size of current floor
                int w = 1920;
                int h = 2400;
                if (Map.levelImgs[level] != null) {
                    w = Map.levelImgs[level].getWidth();
                    h = Map.levelImgs[level].getHeight();
                }

                // rendered level base
                if (Options.quality) {
                    Draw.image(Map.levelImgs[drawLevel], w / 2, h / 2, w, h);
                }

                Door.drawAll();

                for (int i = 0; i < Entity.entities.size(); i++) {
                    Entity.entities.get(i).draw();
                }

                Stairs.drawAll();
                Damage.drawAll();

                // rendered level lighting
                if (Options.quality) {
                    Draw.image(drawLevel + "top", w / 2, h / 2);
                }
                break;
            case GAMEOVER:
                break;
            case OPTIONS:
                break;
        }

    }

    @Override
    public void absoluteDraw() {
        switch (state) {
            case TITLE:
                // background color
                Draw.setColor(new Color(20, 20, 20));
                Draw.rect(gw / 2, gh / 2, gw, gh);

                // background image with enemy running across
                int scale = (int) Math.ceil(gw / 400) + 1;
                Draw.image("menuBack", gw / 2, gh / 2, 0, scale);
                Draw.image("lightWalk" + Math.round(updateCount / 5) % 4, ((int) updateCount % (gw) - 100) * scale, gh / 2 - scale * 25, 0, scale);

                // buttons
                Component.drawAll(MainMenu.components);
                break;
            case CUTSCENE:
                break;
            case TRANSITION:
                // fade in/out
                if (transitionTime > 20) {
                    Draw.setColor(new Color(0, 0, 0, (int) Utils.mapRange(transitionTime, 20, 40, 255, 0)));
                } else {
                    Draw.setColor(new Color(0, 0, 0, (int) Utils.mapRange(20 - transitionTime, 0, 20, 255, 0)));
                }
                Draw.rect(gw / 2, gh / 2, gw, gh);
                break;
            case PLAYING:
                break;
            case GAMEOVER:
                // background color
                Draw.setColor(new Color(20, 20, 20));
                Draw.rect(gw / 2, gh / 2, gw, gh);

                // buttons
                Component.drawAll(GameOver.components);
                break;
            case OPTIONS:
                // background color
                Draw.setColor(new Color(20, 20, 20));
                Draw.rect(gw / 2, gh / 2, gw, gh);

                // options title
                Draw.setColor(Color.WHITE);
                Draw.setFontSize(4);
                Draw.text("Options", gw / 2 - 50, 50);

                // buttons
                Component.drawAll(OptionsUI.components);
                break;
        }
    }
}
