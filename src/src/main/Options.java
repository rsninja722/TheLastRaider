package main;

import main.Main.State;

public class Options {
    public static boolean useMotionControl = false;
    public static boolean quality = true;
    
    public static State lastState;

    public static void enterOptions() {
        lastState = Main.state;
        Main.state = State.OPTIONS;
    }
}
