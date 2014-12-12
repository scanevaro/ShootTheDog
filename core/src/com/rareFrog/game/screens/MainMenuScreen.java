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
    private Image menuBackgroundBack;
    private Image menuBackground;
    private Image titleImage;
    private ImageButton muteButton;
    private ImageButton closeButton;
    private ImageButton play1DuckButton;
    private ImageButton play2DucksButton;
    private ImageButton aboutButton;
    private ImageButton libgdxButton;
    private ImageButton achievementsButton;
    private ImageButton leaderboardsButton;
    private ImageButton loginButton;

    public MainMenuScreen(Game game) {
        this.game = game;

        guiCam = new OrthographicCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        guiCam.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);

        setWidgets();
        configureWidgets();
        addListeners();
        setLayout();

        if (Settings.soundEnabled) Assets.menuIntro.play();

        Gdx.input.setInputProcessor(stage);

        game.actionResolver.loginGPGS();
    }

    private void setWidgets() {
        menuBackgroundBack = new Image(Assets.backgroundBackRegion);
        menuBackground = new Image(Assets.backgroundRegion);
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

        ImageButton.ImageButtonStyle libgdxStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        libgdxStyle.imageUp = new TextureRegionDrawable(Assets.atlas.findRegion("gdx"));
        libgdxButton = new ImageButton(libgdxStyle);

        ImageButton.ImageButtonStyle leaderboardsStyle = new ImageButton.ImageButtonStyle();
        leaderboardsStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.leaderboardsButtonUp));
        leaderboardsStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.leaderboardsButtonDown));
        leaderboardsButton = new ImageButton(leaderboardsStyle);

        ImageButton.ImageButtonStyle achievementsStyle = new ImageButton.ImageButtonStyle();
        achievementsStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.achievementsButtonUp));
        achievementsStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.achievementsButtonDown));
        achievementsButton = new ImageButton(achievementsStyle);

        ImageButton.ImageButtonStyle loginStyle = new ImageButton.ImageButtonStyle();
        loginStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.loginButtonUp));
        loginStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.loginButtonDown));
        loginButton = new ImageButton(loginStyle);
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
                if (Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
            }
        });
        play2DucksButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, World.GAME_MODE_2));
                if (Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
            }
        });
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new AboutScreen(game));
            }
        });
        libgdxButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("libgdx.badlogicgames.com");
            }
        });
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.save();
                Gdx.app.exit();
            }
        });
        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getLeaderboardGPGS();
            }
        });
        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getAchievementsGPGS();
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.loginGPGS();
            }
        });
    }

    private void setLayout() {
        menuBackgroundBack.setSize(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        menuBackgroundBack.setPosition(0, 0);
        stage.addActor(menuBackgroundBack);

        menuBackground.setSize(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        menuBackground.setPosition(0, 0);
        stage.addActor(menuBackground);

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

        libgdxButton.setSize(64, 64);
        libgdxButton.setPosition(Game.VIRTUAL_WIDTH - libgdxButton.getWidth(), Game.VIRTUAL_HEIGHT - libgdxButton.getHeight());
        stage.addActor(libgdxButton);

        titleImage.setSize(256, 128);
        titleImage.setPosition(Game.VIRTUAL_WIDTH / 2 - titleImage.getWidth() / 2, 150);
        stage.addActor(titleImage);

        leaderboardsButton.setSize(64, 64);
        leaderboardsButton.setPosition(Game.VIRTUAL_WIDTH - leaderboardsButton.getWidth(), closeButton.getWidth() + 5);
        stage.addActor(leaderboardsButton);

        loginButton.setSize(64, 64);
        loginButton.setPosition(Game.VIRTUAL_WIDTH - loginButton.getWidth(), closeButton.getWidth() + 5 + leaderboardsButton.getWidth() + 5);
        stage.addActor(loginButton);

        achievementsButton.setSize(64, 64);
        achievementsButton.setPosition(Game.VIRTUAL_WIDTH - achievementsButton.getWidth(), closeButton.getWidth() + 5 + leaderboardsButton.getWidth() + 5 + loginButton.getWidth() + 5);
        stage.addActor(achievementsButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.823529f, 0.3764705f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);

        stage.act();
        stage.draw();

        Assets.font.setScale(0.5f, 0.5f);
        batch.begin();
        Assets.font.draw(batch, "Version 0.1", Game.VIRTUAL_WIDTH / 2 - 50, 20);
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