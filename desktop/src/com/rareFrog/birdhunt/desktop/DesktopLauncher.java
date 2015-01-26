package com.rareFrog.birdhunt.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rareFrog.birdhunt.Game;
import com.rareFrog.birdhunt.classes.IDesktopRequestHandler;
import com.rareFrog.birdhunt.desktop.classes.ActionResolverDesktop;
import com.rareFrog.birdhunt.interfaces.InputInterface;

public class DesktopLauncher implements InputInterface {
    private float rotation = 0;
    private boolean increment = false;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) Game.VIRTUAL_WIDTH;
        config.height = (int) Game.VIRTUAL_HEIGHT;
        new LwjglApplication(new Game(new ActionResolverDesktop(), new IDesktopRequestHandler(), new DesktopLauncher()), config);
    }

    @Override
    public float[] getRotation() {
        if (increment)
            rotation += 0.25f;
        else
            rotation -= 0.25f;
        if (rotation < -30) {
            increment = true;
        }
        if (rotation > 30) {
            increment = false;
        }

        return new float[]{(float) Math.toRadians(rotation), (float) Math.toRadians(rotation), (float) Math.toRadians(rotation)};
    }
}
