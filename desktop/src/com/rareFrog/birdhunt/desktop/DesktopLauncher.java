package com.rareFrog.birdhunt.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rareFrog.birdhunt.Game;
import com.rareFrog.birdhunt.classes.IDesktopRequestHandler;
import com.rareFrog.birdhunt.desktop.classes.ActionResolverDesktop;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) Game.VIRTUAL_WIDTH;
        config.height = (int) Game.VIRTUAL_HEIGHT;
        new LwjglApplication(new Game(new ActionResolverDesktop(), new IDesktopRequestHandler()), config);
    }
}
