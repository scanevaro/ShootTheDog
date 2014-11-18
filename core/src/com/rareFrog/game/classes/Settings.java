package com.rareFrog.game.classes;

import com.badlogic.gdx.Gdx;

import java.io.*;

public class Settings {
    public static boolean soundEnabled = true;
    public static int highscoreA = 0;
    public static int highscoreB = 0;
    public final static String file = ".duckhunt";

    public static void load() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(Gdx.files.external(
                    file).read()));

            soundEnabled = Boolean.parseBoolean(in.readLine());
            highscoreA = Integer.parseInt(in.readLine());
            highscoreB = Integer.parseInt(in.readLine());
        } catch (Throwable e) {
            // :( It's ok we have defaults
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void save() {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(
                    file).write(false)));

            out.write(Boolean.toString(soundEnabled));
            out.write(Integer.toString(highscoreA));
            out.write(Integer.toString(highscoreB));
        } catch (Throwable e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
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