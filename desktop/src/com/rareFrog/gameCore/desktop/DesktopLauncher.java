package com.rareFrog.gameCore.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rareFrog.gameCore.Game;
import com.rareFrog.gameCore.classes.IDesktopRequestHandler;
import com.rareFrog.gameCore.desktop.classes.ActionResolverDesktop;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 320;
        new LwjglApplication(new Game(new ActionResolverDesktop(), new IDesktopRequestHandler()), config);
    }
}
