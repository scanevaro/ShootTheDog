package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.World;
import com.rarefrog.birdhunt.input.GameInputProcessor;

/**
 * Created by Elmar on 3/18/2015.
 */
public class ControlSelect extends AbstractScreen {
    private Game game;
    private SpriteBatch batch;
    private int gamemode;
    private ControlActor controlActor;

    public ControlSelect(Game game, int gamemode) {
        this.game = game;
        this.gamemode = gamemode;

    }

    public void launchGame() {
        if (Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
        game.setScreen(new GameScreen(game, gamemode));
    }

    @Override
    public void show() {
        if (batch == null) batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);
        controlActor = new ControlActor();
        stage.addActor(controlActor);
        setInputProcessor();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GameInputProcessor(game));;
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();

        Gdx.gl.glClearColor(1, 1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }
}
