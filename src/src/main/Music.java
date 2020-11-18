package main;

import engine.Utils;
import engine.audio.Sounds;

public class Music {

    // if a song is playing
    static boolean playing = false;

    // all tracks and there length in game frames 
    static String[] tracks = { "ambientFire", "battleLoop", "bellChimeClose", "bellChimeDistant", "conflictLong",
            "deepRise", "endlessDowner" };
    static int[] trackTimes = { 96 * 60, 43 * 60, 14 * 60, 18 * 60, 143 * 60, 43 * 60, 64 * 60 };

    // indexes of tracks
    static int activeTrack = -1;

    // time a song started playing at, in frames
    static long lastPlayTime;

    // time to wait between playing songs, in frames 
    static int timeToWait;

    // if the music should not be changing
    static public boolean dontChange = false;

    static public double volume = 1.0;

    public static void update() {

        // when playing starts, play a random track
        if (Main.state == Main.State.PLAYING && Main.lastState != Main.state && !dontChange) {
            play(Utils.rand(0, 6));
        }

        // play endless downer for all menus
        if (Main.state != Main.State.PLAYING && Main.lastState != Main.state && Main.lastState != Main.State.TRANSITION && Main.state != Main.State.TRANSITION) {
            play(6);
        }

        // keep playing tracks 
        if (Main.state == Main.State.PLAYING) {
            // if the track is over
            if (Main.updateCount - lastPlayTime > trackTimes[activeTrack]) {
                // set a time from 3-45 seconds to wait between tracks
                if (timeToWait == 0) {
                    timeToWait = Utils.rand(180, 2700);
                } else {
                    // when done waiting, play another track
                    if (--timeToWait == 0) {
                        play(Utils.rand(0, 6));
                    }
                }
            }
        }

    }

    public static void play(int index) {
        if (index != activeTrack) {
            activeTrack = index;

            // set gain
            Sounds.adjustGain(tracks[index], 0.9f * (float)volume );

            // stop all tracks
            for (int i = 0; i < 7; i++) {
                Sounds.stop(tracks[i]);
            }

            // play current track
            Sounds.play(tracks[index]);

            playing = true;
            lastPlayTime = Main.updateCount;
            timeToWait = 0;
        }
    }
}
