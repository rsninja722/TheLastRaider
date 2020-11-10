package main;

import engine.Utils;
import engine.audio.Sounds;

public class Music {

    static float fadeTime = 0.5f;
    static boolean playing = false;

    static String[] tracks = { "ambientFire", "battleLoop", "bellChimeClose", "bellChimeDistant", "conflictLong", "deepRise", "endlessDowner" };
    static int[] trackTimes = { 96*60, 43*60, 14*60, 18*60,143*60, 43*60, 64*60 };

    static int activeTrack = -1;
    static int lastActiveTrack = -1;

    static long lastPlayTime;

    static int timeToWait;

    static public boolean dontChange = false;

    public static void update() {

        if(Main.state == Main.State.PLAYING && Main.lastState != Main.state && !dontChange) {
            play(Utils.rand(0,6));
        }

        if(Main.state != Main.State.PLAYING && Main.lastState != Main.state && Main.lastState != Main.State.TRANSITION && Main.state != Main.State.TRANSITION) {
            play(6);
        }

        if(Main.state == Main.State.PLAYING) {
            if(Main.updateCount - lastPlayTime > trackTimes[activeTrack]) {
                if(timeToWait == 0) {
                    timeToWait = Utils.rand(180,3000);
                } else {
                    if(--timeToWait == 0) {
                        play(Utils.rand(0,6));
                    }
                }
            } 
        }
         

    }
    public static void play(int index) {
        lastActiveTrack = activeTrack;
        activeTrack = index;
        if(lastActiveTrack != activeTrack) {
            fadeTime = 0.5f;
            Sounds.adjustGain(tracks[index], 0.9f);
            for(int i=0;i<7;i++) {
                Sounds.stop(tracks[i]);
            }
            Sounds.play(tracks[index]);
            playing = true;
            lastPlayTime = Main.updateCount;
            timeToWait = 0;
        }
    }
}
