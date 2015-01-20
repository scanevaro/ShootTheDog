package com.rareFrog.birdhunt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.rareFrog.birdhunt.interfaces.ActionResolver;
import com.rareFrog.birdhunt.interfaces.IActivityRequestHandler;
import com.rareFrog.birdhunt.screens.AbstractScreen;
import com.rareFrog.birdhunt.screens.SplashScreen;

public class Game implements ApplicationListener {
    public static final float VIRTUAL_WIDTH = 480;
    public static final float VIRTUAL_HEIGHT = 320;
    public static final float VIRTUAL_ASPECT = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;

    public ActionResolver actionResolver;
    public IActivityRequestHandler requestHandler;
    public AbstractScreen screen;
    //    FPSLogger fps;
    public Dialogs dialogs;
    public boolean dialogOpen;

    public Game(ActionResolver actionResolver, IActivityRequestHandler handler) {
        this.actionResolver = actionResolver;
        this.requestHandler = handler;
    }

    @Override
    public void create() {
        dialogs = new Dialogs(this);

        Gdx.input.setCatchBackKey(true);

        Settings.load();
        Assets.load();
        setScreen(new SplashScreen(this));
//        fps = new FPSLogger();
    }

    @Override
    public void render() {
        screen.render(Gdx.graphics.getDeltaTime());
//        fps.log();
    }

    public void setScreen(AbstractScreen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void dispose() {
        Assets.dispose();
        if (screen != null) screen.dispose();
    }
}