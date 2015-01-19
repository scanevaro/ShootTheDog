package com.rareFrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
    public static boolean soundEnabled = true;
    public static int highscoreA = 0;
    public static int highscoreB = 0;
    public final static String file = ".duckhunt";

    public static void load() {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
            highscoreA = Integer.parseInt(strings[1]);
            highscoreB = Integer.parseInt(strings[2]);
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save() {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            filehandle.writeString(Boolean.toString(soundEnabled) + "\n", false);
            filehandle.writeString(Integer.toString(highscoreA) + "\n", true);
            filehandle.writeString(Integer.toString(highscoreB), true);
        } catch (Throwable e) {
        }
    }

    public static void addScoreA(int score) {
        if (highscoreA < score) {
            highscoreA = score;
        }
    }

    public static void addScoreB(int score) {
        if (highscoreB < score) {
            highscoreB = score;
        }
    }
}