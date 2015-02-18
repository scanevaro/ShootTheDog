package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.Settings;
import com.rarefrog.birdhunt.World;
import com.rarefrog.birdhunt.input.GameInputProcessor;


public class MainMenuScreen extends AbstractScreen {
    /**
     * main
     */
    Game game;
    /**
     * screen
     */
    private SpriteBatch batch;
    /**
     * widgets
     */
    private Image menuBackgroundBack;
    private Image menuBackground;
    private Image titleImage;
    private ImageButton play1DuckButton, play2DucksButton, configButton, loginButton;
    public Window configDialog;
    private Label deeepLabel;

    public MainMenuScreen(Game game) {
        this.game = game;

        Assets.font.setColor(1, 1, 1, 1);
        Assets.font.setScale(0.4f);

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);

        setWidgets();
        configureWidgets();
        addListeners();
        setLayout();
        prepareConfigDialog();

        if (Settings.soundEnabled) Assets.menuIntro.play();

        setInputProcessor();

        game.requestHandler.showAds(true);
    }

    private void setWidgets() {
        menuBackgroundBack = new Image(Assets.backgroundBackRegion);
        menuBackground = new Image(Assets.backgroundRegion);
        titleImage = new Image(Assets.title);

        ImageButton.ImageButtonStyle play1DuckStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        play1DuckStyle.imageUp = new TextureRegionDrawable(Assets.play1ButtonUp);
        play1DuckStyle.imageUp.setMinWidth(192);
        play1DuckStyle.imageUp.setMinHeight(64);
        play1DuckButton = new ImageButton(play1DuckStyle);

        ImageButton.ImageButtonStyle play2DucksStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        play2DucksStyle.imageUp = new TextureRegionDrawable(Assets.play2ButtonUp);
        play2DucksStyle.imageUp.setMinWidth(192);
        play2DucksStyle.imageUp.setMinHeight(64);
        play2DucksButton = new ImageButton(play2DucksStyle);

        ImageButton.ImageButtonStyle loginStyle = new ImageButton.ImageButtonStyle();
        loginStyle.imageUp = new TextureRegionDrawable(Assets.loginButtonUp);
        loginButton = new ImageButton(loginStyle);

        ImageButton.ImageButtonStyle configStyle = new ImageButton.ImageButtonStyle(/*Assets.getSkin().get(Button.ButtonStyle.class)*/);
        configStyle.imageUp = new TextureRegionDrawable(Assets.configButtonUp);
        configButton = new ImageButton(configStyle);

        deeepLabel = new Label("Deeep Games 2014/2015 - Version: 0.9.0", Assets.skin);
    }

    private void configureWidgets() {
    }

    private void addListeners() {
        play1DuckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.requestHandler.showAds(false);
                game.setScreen(new GameScreen(game, World.GAME_MODE_1));
                if (Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                play1DuckButton.getImage().setOrigin(Align.center);
                play1DuckButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                play1DuckButton.getImage().setScale(1.0f);
            }
        });
        play2DucksButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.requestHandler.showAds(false);
                game.setScreen(new GameScreen(game, World.GAME_MODE_2));
                if (Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                play2DucksButton.getImage().setOrigin(Align.center);
                play2DucksButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                play2DucksButton.getImage().setScale(1.0f);
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.loginGPGS();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                loginButton.getImage().setOrigin(Align.center);
                loginButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                loginButton.getImage().setScale(1.0f);
            }
        });
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.dialogs.menuDialog != null) game.dialogs.menuDialog.remove();

                if (!configDialog.isVisible()) {
                    if (Settings.soundEnabled) Assets.pauseClicked.play();
                    stage.addActor(configDialog);
                    configDialog.setVisible(true);
                } else {
                    if (Settings.soundEnabled) Assets.pauseClosed.play();
                    configDialog.remove();
                    configDialog.setVisible(false);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                configButton.getImage().setOrigin(Align.center);
                configButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                configButton.getImage().setScale(1.0f);
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

        play1DuckButton.setPosition(Game.VIRTUAL_WIDTH / 2 - Game.VIRTUAL_WIDTH / 4 - play1DuckButton.getWidth() / 2, 68);
        stage.addActor(play1DuckButton);

        play2DucksButton.setPosition(Game.VIRTUAL_WIDTH / 4 * 3 - play2DucksButton.getWidth() / 2, 68);
        stage.addActor(play2DucksButton);

        titleImage.setSize(256, 128);
        titleImage.setPosition(Game.VIRTUAL_WIDTH / 2 - titleImage.getWidth() / 2, 150);
        stage.addActor(titleImage);

        configButton.setSize(32, 32);
        configButton.setPosition(Game.VIRTUAL_WIDTH - configButton.getWidth(), 2);
        stage.addActor(configButton);

        loginButton.setSize(32, 32);
        loginButton.setPosition(Game.VIRTUAL_WIDTH - loginButton.getWidth() - configButton.getWidth() - 2, 2);
        stage.addActor(loginButton);

        deeepLabel.setPosition(Game.VIRTUAL_WIDTH / 2 - deeepLabel.getWidth() / 2, 5);
        stage.addActor(deeepLabel);
    }

    private void prepareConfigDialog() {
        configDialog = new Window("Config", Assets.skin.get("pauseDialog", Window.WindowStyle.class));
        configDialog.setWidth(256);
        configDialog.setHeight(196);
        configDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - configDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - configDialog.getHeight() / 2);
        configDialog.setVisible(false);

        //TODO Change it to CLOSE ICON
        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = new TextureRegionDrawable(Assets.closeIconUp);
        final ImageButton closeButton = new ImageButton(closeStyle);
        closeButton.setSize(48, 48);
        closeButton.setPosition(configDialog.getWidth() / 2 - closeButton.getWidth() / 2, 10);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogOpen = false;
                if (Settings.soundEnabled) Assets.pauseClosed.play();
                configDialog.remove();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                closeButton.getImage().setOrigin(Align.center);
                closeButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                closeButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(closeButton);

        ImageButton.ImageButtonStyle muteStyle = new ImageButton.ImageButtonStyle();
        muteStyle.imageUp = new TextureRegionDrawable(Assets.soundIconUp);
        muteStyle.imageChecked = new TextureRegionDrawable(Assets.soundIconDown);
        final ImageButton muteButton = new ImageButton(muteStyle);
        muteButton.setSize(64, 64);
        muteButton.setPosition(20, configDialog.getHeight() / 2 + 15);
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

                if (!Settings.soundEnabled && Assets.menuIntro.isPlaying()) Assets.menuIntro.stop();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                muteButton.getImage().setOrigin(Align.center);
                muteButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                muteButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(muteButton);

        ImageButton.ImageButtonStyle aboutStyle = new ImageButton.ImageButtonStyle();
        aboutStyle.imageUp = new TextureRegionDrawable(Assets.aboutIconUp);
        final ImageButton aboutButton = new ImageButton(aboutStyle);
        aboutButton.setSize(64, 64);
        aboutButton.setPosition(20 + muteButton.getWidth() + 5, configDialog.getHeight() / 2 + 15);
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AboutScreen(game));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                aboutButton.getImage().setOrigin(Align.center);
                aboutButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                aboutButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(aboutButton);

        ImageButton.ImageButtonStyle quitStyle = new ImageButton.ImageButtonStyle();
        quitStyle.imageUp = new TextureRegionDrawable(Assets.quitButtonUp);
        final ImageButton quitButton = new ImageButton(quitStyle);
        quitButton.setSize(64, 64);
        quitButton.setPosition(20 + muteButton.getWidth() + 5 + aboutButton.getWidth() + 5, configDialog.getHeight() / 2 + 15);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                configDialog.remove();
                game.dialogs.buildMenuDialog(game.screen);
                if (Settings.soundEnabled) Assets.pauseClicked.play();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                quitButton.getImage().setOrigin(Align.center);
                quitButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                quitButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(quitButton);

        ImageButton.ImageButtonStyle leaderboardsStyle = new ImageButton.ImageButtonStyle();
        leaderboardsStyle.imageUp = new TextureRegionDrawable(Assets.leaderboardsUp);
        final ImageButton leaderboardsButton = new ImageButton(leaderboardsStyle);
        leaderboardsButton.setSize(64, 64);
        leaderboardsButton.setPosition(configDialog.getWidth() / 2 - 15 - 64, 32 + 10);
        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getLeaderboardGPGS();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                leaderboardsButton.getImage().setOrigin(Align.center);
                leaderboardsButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                leaderboardsButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(leaderboardsButton);

        ImageButton.ImageButtonStyle achievementsStyle = new ImageButton.ImageButtonStyle();
        achievementsStyle.imageUp = new TextureRegionDrawable(Assets.achievementsUp);
        final ImageButton achievementsButton = new ImageButton(achievementsStyle);
        achievementsButton.setSize(64, 64);
        achievementsButton.setPosition(configDialog.getWidth() / 2 + 15, 32 + 10);
        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getAchievementsGPGS();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                achievementsButton.getImage().setOrigin(Align.center);
                achievementsButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                achievementsButton.getImage().setScale(1.0f);
            }
        });
        configDialog.addActor(achievementsButton);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GameInputProcessor(game));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.823529f, 0.3764705f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}