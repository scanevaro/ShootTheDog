package com.rareFrog.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rareFrog.game.Game;
import com.rareFrog.game.classes.Assets;
import com.rareFrog.game.classes.Settings;
import com.rareFrog.game.classes.World;

public class MainMenuScreen implements Screen {
    //main
    Game game;
    //screen
    private OrthographicCamera guiCam;
    private Stage stage;
    private SpriteBatch batch;
    //widgets
    private Image titleImage;
    private ImageButton muteButton;
    private ImageButton closeButton;
    private ImageButton play1DuckButton;
    private ImageButton play2DucksButton;
    private ImageButton aboutButton;

    public MainMenuScreen(Game game) {
        this.game = game;

        guiCam = new OrthographicCamera(480, 320);
        guiCam.position.set(480 / 2, 320 / 2, 0);
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);

        setWidgets();
        configureWidgets();
        addListeners();
        setLayout();

        if (Settings.soundEnabled)
            Assets.duckHunt.play();

        Gdx.input.setInputProcessor(stage);
    }

    private void setWidgets() {
        titleImage = new Image(Assets.title);

        ImageButton.ImageButtonStyle muteButtonStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        muteButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.soundIconUp));
        muteButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.soundIconDown));
        muteButtonStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(Assets.soundIconDown));
        muteButton = new ImageButton(muteButtonStyle);

        ImageButton.ImageButtonStyle closeButtonStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        closeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.closeButtonUp));
        closeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.closeButtonDown));
        closeButton = new ImageButton(closeButtonStyle);

        ImageButton.ImageButtonStyle play1DuckStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        play1DuckStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.play1ButtonUp));
        play1DuckStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.play1ButtonDown));
        play1DuckButton = new ImageButton(play1DuckStyle);

        ImageButton.ImageButtonStyle play2DucksStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        play2DucksStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.play2ButtonUp));
        play2DucksStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.play2ButtonDown));
        play2DucksButton = new ImageButton(play2DucksStyle);

        ImageButton.ImageButtonStyle aboutStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        aboutStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.aboutButtonUp));
        aboutStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.aboutButtonDown));
        aboutButton = new ImageButton(aboutStyle);
    }

    private void configureWidgets() {
        if (!Settings.soundEnabled) muteButton.setChecked(true);
    }

    private void addListeners() {
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (muteButton.isChecked()) {
                    Settings.soundEnabled = false;
                    muteButton.setChecked(true);
                } else {
                    Settings.soundEnabled = true;
                    muteButton.setChecked(false);
                }
            }
        });
        play1DuckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, World.GAME_MODE_1));
            }
        });
        play2DucksButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, World.GAME_MODE_2));
            }
        });
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new AboutScreen(game));
            }
        });
    }

    private void setLayout() {
        muteButton.setSize(64, 64);
        muteButton.setPosition(0, 0);
        stage.addActor(muteButton);

        closeButton.setSize(64, 64);
        closeButton.setPosition(Game.VIRTUAL_WIDTH - closeButton.getWidth(), 0);
        stage.addActor(closeButton);

        play1DuckButton.setSize(160, 18);
        play1DuckButton.setPosition(Game.VIRTUAL_WIDTH / 2 - play1DuckButton.getWidth() / 2, 100);
        stage.addActor(play1DuckButton);

        play2DucksButton.setSize(160, 18);
        play2DucksButton.setPosition(Game.VIRTUAL_WIDTH / 2 - play2DucksButton.getWidth() / 2, 68);
        stage.addActor(play2DucksButton);

        aboutButton.setSize(128, 20);
        aboutButton.setPosition(Game.VIRTUAL_WIDTH / 2 - aboutButton.getWidth() / 2, 32);
        stage.addActor(aboutButton);

        titleImage.setSize(256, 128);
        titleImage.setPosition(Game.VIRTUAL_WIDTH / 2 - titleImage.getWidth() / 2, 150);
        stage.addActor(titleImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);

        stage.act();
        stage.draw();

        //highscore
        Assets.font.setScale(0.5f, 0.5f);
        batch.begin();
        Assets.font.draw(batch, "Version 0 1", Game.VIRTUAL_WIDTH / 2 - 50, 10);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        if (!Settings.soundEnabled) muteButton.setChecked(true);
    }

    @Override
    public void dispose() {
    }
}