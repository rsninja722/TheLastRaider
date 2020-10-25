package main;

/**
 * James N
 * 2020.10.22
 * The Last Raider
 */

import engine.*;
import main.map.LoadMap;

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

        // LoopManager.startLoops(this);
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
