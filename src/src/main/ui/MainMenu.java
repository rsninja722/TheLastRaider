package main.ui;

import java.util.ArrayList;

import main.Main;

public class MainMenu {
    
    public static ArrayList<Component> components = new ArrayList<Component>();

    public static void generate() {
        components.add(new Button(150, 50, 0.5f, 0.4f, "Play", 3, MainMenu::play));
    }

    public static void play() {
        Main.state = Main.State.TRANSITION;
        Main.stairsID = 9;
        Main.level = 3;
    }
}
