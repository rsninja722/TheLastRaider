package main;

import main.Main.State;

public class Options {
    // should attacks be done by drawing patters
    public static boolean useMotionControl = false;

    // graphics quality (only 2 options)
    public static boolean quality = true;

    // state before entering options
    public static State lastState;

    // open options menu, and remember where to go when closed
    public static void enterOptions() {
        lastState = Main.state;
        Main.state = State.OPTIONS;
    }
}
