package main.ui;

import java.util.ArrayList;

import engine.audio.Sounds;
import main.Main;
import main.Options;

// buttons and slider for options menu

public class OptionsUI {

    public static ArrayList<Component> components = new ArrayList<Component>();

    public static void generate() {
        components.add(new Button(60, 30, 0.1f, 0.9f, "Back", 2, OptionsUI::back));
        components.add(new Button(200, 30, 0.3f, 0.3f, "Graphics: " + (Options.quality ? "yes" : "no"), 2, OptionsUI::toggleQuality));
        components.add(new Button(200, 30, 0.7f, 0.3f, "Controls: " + (Options.useMotionControl ? "motion" : "click"), 2, OptionsUI::toggleInput));
        components.add(new Slider(100, 30, 0.5f, 0.5f, "Volume", OptionsUI::setVolume));
    }

    public static void toggleQuality() {
        Options.quality = !Options.quality;
        components.get(1).text = "Graphics: " + (Options.quality ? "yes" : "no");
    }

    public static void toggleInput() {
        Options.useMotionControl = !Options.useMotionControl;
        components.get(2).text = "Controls: " + (Options.useMotionControl ? "motion" : "click");
    }

    public static void back() {
        Main.state = Options.lastState;
    }

    public static void setVolume() {
        Sounds.globalVolume(components.get(3).percent);
    }
}
