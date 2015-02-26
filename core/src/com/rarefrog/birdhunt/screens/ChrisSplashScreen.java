package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.entities.ChrisSplashActor;

/**
 * Created by scanevaro on 09/12/2014.
 */
public class ChrisSplashScreen extends AbstractScreen {
    private static final float DURATION = 3.0f; //Duration of the SplashScreen
    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private ChrisSplashActor splashSprite;
    private Timer.Task timer;

    public ChrisSplashScreen(Game game) {
        this.game = game;
    }

    private void setActors() {
        splashSprite = new ChrisSplashActor();
    }

    private void configureActors() {
        splashSprite.setColor(1, 1, 1, 0);

        splashSprite.addAction(Actions.delay(2.5f - splashSprite.stateTime, Actions.fadeOut(0.5f)));
    }

    private void setListeners() {
        splashSprite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                timer.cancel();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Timer.schedule(timer = new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, DURATION);
    }

    private void setLayout() {
        stage.addActor(splashSprite);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        if (batch == null) batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);

        Gdx.input.setInputProcessor(stage);

        setActors();
        configureActors();
        setListeners();
        setLayout();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}