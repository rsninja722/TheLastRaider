package main.ui;

import java.util.ArrayList;

import engine.Utils;
import main.Main;
import main.Music;
import main.Options;

public class MainMenu {
    
    public static ArrayList<Component> components = new ArrayList<Component>();

    public static void generate() {
        components.add(new Button(150, 50, 0.5f, 0.4f, "Play", 3, MainMenu::play));
        components.add(new Button(150, 50, 0.5f, 0.6f, "Options", 3, Options::enterOptions));
    }

    public static void play() {
        Main.state = Main.State.TRANSITION;
        Main.stairsID = 9;
        Main.level = 3;
        Music.play(Utils.rand(0,5));
    }
}
