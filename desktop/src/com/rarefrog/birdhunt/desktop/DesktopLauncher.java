package com.rarefrog.birdhunt.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.classes.IDesktopRequestHandler;
import com.rarefrog.birdhunt.desktop.classes.ActionResolverDesktop;
import com.rarefrog.birdhunt.interfaces.InputInterface;

public class DesktopLauncher implements InputInterface {
    private float rotation = 0;
    private boolean increment = false;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) Game.VIRTUAL_WIDTH*3;
        config.height = (int) Game.VIRTUAL_HEIGHT*2;
        new LwjglApplication(new Game(new ActionResolverDesktop(), new IDesktopRequestHandler(), new DesktopLauncher()), config);
    }

    @Override
    public float[] getRotation() {
        return new float[]{(float) Math.toRadians(rotation), (float) Math.toRadians(rotation), (float) Math.toRadians(rotation)};
    }
}
