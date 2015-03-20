package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by Elmar on 3/18/2015.
 */
public class ControlSelect extends AbstractScreen {
    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private int gamemode;
    private int leftSelected = -1;
    private int rightSelected = -1;
    private ControlActor controlActor;
    private ShapeRenderer shapeRenderer;

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
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
