package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.input.GameInputProcessor;

/**
 * Created by scanevaro on 02/02/2015.
 */
public class AboutScreen extends AbstractScreen {
    private Game game;

    public AboutScreen(Game game) {
        super();
        this.game = game;
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), new SpriteBatch());

        setWidgets();
        setInputProcessor();
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GameInputProcessor(game));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void setWidgets() {
        Image backgroundBack = new Image(Assets.backgroundBackRegion);
        backgroundBack.setSize(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        stage.addActor(backgroundBack);

        Image background = new Image(Assets.backgroundRegion);
        background.setSize(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        stage.addActor(background);

        Image logo = new Image(Assets.logo);
        logo.setSize(128, 128);
        logo.setPosition(5, Game.VIRTUAL_HEIGHT - 15 - logo.getHeight());
        stage.addActor(logo);

        Image chrislogo = new Image(Assets.chrislogo);
        chrislogo.setSize(128, 96);
        chrislogo.setPosition(Game.VIRTUAL_WIDTH - 5 - chrislogo.getWidth(), 5);
        stage.addActor(chrislogo);

        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.imageUp = new TextureRegionDrawable(Assets.backIconUp);
        backStyle.imageUp.setMinWidth(64);
        backStyle.imageUp.setMinHeight(64);
        final ImageButton backButton = new ImageButton(backStyle);
        backButton.setPosition(5, 5);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                backButton.getImage().setOrigin(Align.center);
                backButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                backButton.getImage().setScale(1.0f);
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.823529f, 0.3764705f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        stage.getBatch().begin();
        Assets.font.setScale(0.9f);
        Assets.font.draw(stage.getBatch(), "Deeep Games - 2015", 138, Game.VIRTUAL_HEIGHT - 45);
        Assets.font.setScale(0.7f);
        Assets.font.draw(stage.getBatch(), "www.deeepgames.com", 138, Game.VIRTUAL_HEIGHT - 85);
        Assets.font.draw(stage.getBatch(), "Art by Alessandro Galluci - alegallucci.rei@gmail.com", 5, Game.VIRTUAL_HEIGHT / 2 + 5);
        Assets.font.draw(stage.getBatch(), "PR by Michael Phelps - kamata@pixel-virtuosa.com", 5, Game.VIRTUAL_HEIGHT / 2 - 25);
        Assets.font.draw(stage.getBatch(), "Music and Sound Effects", 64 + 35, Game.VIRTUAL_HEIGHT / 2 - 60);
        Assets.font.draw(stage.getBatch(), "by Christopher Porter", 64 + 48, Game.VIRTUAL_HEIGHT / 2 - 90);
        Assets.font.draw(stage.getBatch(), "chrisportermusic1@gmail.com", 64 + 24, Game.VIRTUAL_HEIGHT / 2 - 125);
        stage.getBatch().end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}