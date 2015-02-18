package com.rarefrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rarefrog.birdhunt.screens.AboutScreen;
import com.rarefrog.birdhunt.screens.AbstractScreen;
import com.rarefrog.birdhunt.screens.GameScreen;
import com.rarefrog.birdhunt.screens.MainMenuScreen;

/**
 * Created by scanevaro on 14/01/2015.
 */
public class Dialogs {
    public Window menuDialog, gameDialog;
    private Game game;

    public Dialogs(Game game) {
        this.game = game;
    }

    public void update(AbstractScreen screen) {
        if (screen instanceof AboutScreen)
            game.setScreen(new MainMenuScreen(game));

        if (!game.dialogOpen) {
            game.dialogOpen = true;
            build(screen);
            if (Settings.soundEnabled) Assets.pauseClicked.play();
        } else {
            game.dialogOpen = false;
            remove(screen);
            if (Settings.soundEnabled) Assets.pauseClosed.play();
        }
    }

    private void build(final AbstractScreen screen) {
        if (screen instanceof MainMenuScreen) {
            buildMenuDialog(screen);
            return;
        }

        if (screen instanceof GameScreen) {
            buildGameDialog(screen);
            return;
        }
    }

    private void remove(AbstractScreen screen) {
        if (screen instanceof MainMenuScreen)
            if (menuDialog != null)
                menuDialog.remove();

        if (screen instanceof GameScreen)
            if (gameDialog != null)
                gameDialog.remove();
    }

    public void buildMenuDialog(final AbstractScreen screen) {
        if (((MainMenuScreen) screen).configDialog != null) ((MainMenuScreen) screen).configDialog.remove();

        if (menuDialog != null) {
            menuDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - menuDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - menuDialog.getHeight() / 2);
            screen.stage.addActor(menuDialog);
            return;
        }

        menuDialog = new Window("Quit", Assets.skin.get("pauseDialog", Window.WindowStyle.class));
        menuDialog.setWidth(256);
        menuDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - menuDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - menuDialog.getHeight() / 2);

        Label label = new Label("Are you sure you want to Quit?", Assets.skin);
        label.setPosition(45, menuDialog.getHeight() / 2 + 25);
        menuDialog.addActor(label);

        ImageButton.ImageButtonStyle confirmStyle = new ImageButton.ImageButtonStyle();
        confirmStyle.imageUp = new TextureRegionDrawable(Assets.confirmButtonUp);
        final ImageButton confirmButton = new ImageButton(confirmStyle);
        confirmButton.setSize(64, 64);
        confirmButton.setPosition(menuDialog.getWidth() / 4 - confirmButton.getWidth() / 2, 20);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                confirmButton.getImage().setOrigin(Align.center);
                confirmButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                confirmButton.getImage().setScale(1.0f);
            }
        });
        menuDialog.addActor(confirmButton);

        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = new TextureRegionDrawable(Assets.closeIconUp);
        final ImageButton closeButton = new ImageButton(closeStyle);
        closeButton.setSize(64, 64);
        closeButton.setPosition(menuDialog.getWidth() / 4 * 3 - closeButton.getWidth() / 2, 20);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogOpen = false;
                menuDialog.remove();
                if (Settings.soundEnabled) Assets.pauseClosed.play();
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
        menuDialog.addActor(closeButton);

        screen.stage.addActor(menuDialog);
    }

    private void buildGameDialog(final AbstractScreen screen) {
        if (gameDialog != null) {
            gameDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - gameDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - gameDialog.getHeight() / 2);
            screen.stage.addActor(gameDialog);
            return;
        }

        gameDialog = new Window("Pause", Assets.skin.get("pauseDialog", Window.WindowStyle.class));
        gameDialog.setWidth(160);
        gameDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - gameDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - gameDialog.getHeight() / 2);

        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = new TextureRegionDrawable(Assets.closeIconUp);
        final ImageButton closeButton = new ImageButton(closeStyle);
        closeButton.setSize(48, 48);
        closeButton.setPosition(gameDialog.getWidth() / 2 - closeButton.getWidth() / 2, 0);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogOpen = false;
                if (Settings.soundEnabled) Assets.pauseClosed.play();
                gameDialog.remove();
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
        gameDialog.addActor(closeButton);

        ImageButton.ImageButtonStyle muteStyle = new ImageButton.ImageButtonStyle();
        muteStyle.imageUp = new TextureRegionDrawable(Assets.soundIconUp);
        muteStyle.imageChecked = new TextureRegionDrawable(Assets.soundIconDown);
        final ImageButton muteButton = new ImageButton(muteStyle);
        muteButton.setSize(64, 64);
        muteButton.setPosition(gameDialog.getWidth() / 2 - muteButton.getWidth() - 5, gameDialog.getHeight() / 2 - muteButton.getHeight() / 2 + 10);
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

                if (!Settings.soundEnabled) Assets.background.pause();
                else if (((GameScreen) screen).world.state != World.WORLD_STATE_ROUND_START)
                    Assets.background.play();

                if (!Settings.soundEnabled && Assets.startRound.isPlaying()) Assets.startRound.stop();
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
        gameDialog.addActor(muteButton);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.imageUp = new TextureRegionDrawable(Assets.backIconUp);
        final ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.setSize(64, 64);
        homeButton.setPosition(gameDialog.getWidth() / 2 + 5, gameDialog.getHeight() / 2 - homeButton.getHeight() / 2 + 10);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Assets.startRound.isPlaying()) Assets.startRound.stop();
                if (Assets.background.isPlaying()) Assets.background.stop();
                game.setScreen(new MainMenuScreen(game));
                game.requestHandler.showInterstitial(true);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                homeButton.getImage().setOrigin(Align.center);
                homeButton.getImage().setScale(1.5f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                homeButton.getImage().setScale(1.0f);
            }
        });
        gameDialog.addActor(homeButton);

        screen.stage.addActor(gameDialog);
    }
}