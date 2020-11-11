package main.ui;

import java.util.ArrayList;

import main.Main;
import main.entities.Entity;
import main.entities.moveable.combat.Player;

// buttons for game over menu

public class GameOver {

    public static ArrayList<Component> components = new ArrayList<Component>();

    public static void generate() {
        components.add(new Button(200, 75, 0.5f, 0.5f, "Play Again", 3, GameOver::playAgain));
    }

    public static void playAgain() {
        Main.state = Main.State.TRANSITION;
        Main.stairsID = 9;
        Main.level = 3;

        for (int i = 0; i < 4; i++) {
            Entity.entitiesList.add(new ArrayList<Entity>());
        }
        Entity.entities = Entity.entitiesList.get(3);

        Main.player = new Player(1160, 1530, 12, 12);

        Main.drawLevel = 3;
    }
}
