package com.rareFrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rareFrog.birdhunt.screens.AbstractScreen;
import com.rareFrog.birdhunt.screens.GameScreen;
import com.rareFrog.birdhunt.screens.MainMenuScreen;

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

    private void buildMenuDialog(final AbstractScreen screen) {
        if (menuDialog != null) {
            menuDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - menuDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - menuDialog.getHeight() / 2);
            screen.stage.addActor(menuDialog);
            return;
        }

        menuDialog = new Window("Quit", Assets.skin.get("pauseDialog", Window.WindowStyle.class));
        menuDialog.setWidth(256);
        menuDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - menuDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - menuDialog.getHeight() / 2);

        ImageButton.ImageButtonStyle confirmStyle = new ImageButton.ImageButtonStyle();
        confirmStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.confirmButtonUp));
        confirmStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.confirmButtonDown));
        final ImageButton confirmButton = new ImageButton(confirmStyle);
        confirmButton.setSize(64, 64);
        confirmButton.setPosition(menuDialog.getWidth() / 4 - confirmButton.getWidth() / 2, 0);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        menuDialog.addActor(confirmButton);

        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.closeIconUp));
        closeStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.closeIconDown));
        final ImageButton closeButton = new ImageButton(closeStyle);
        closeButton.setSize(64, 64);
        closeButton.setPosition(menuDialog.getWidth() / 4 * 3 - closeButton.getWidth() / 2, 0);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogOpen = false;
                menuDialog.remove();
                if (Settings.soundEnabled) Assets.pauseClosed.play();
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
        gameDialog.setWidth(256);
        gameDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - gameDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - gameDialog.getHeight() / 2);

        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.closeIconUp));
        closeStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.closeIconDown));
        final ImageButton closeButton = new ImageButton(closeStyle);
        closeButton.setSize(48, 48);
        closeButton.setPosition(gameDialog.getWidth() / 4 - closeButton.getWidth() / 2, 20);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogOpen = false;
                if (Settings.soundEnabled) Assets.pauseClosed.play();
                gameDialog.remove();
            }
        });
        gameDialog.addActor(closeButton);

        ImageButton.ImageButtonStyle muteStyle = new ImageButton.ImageButtonStyle();
        muteStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.soundIconUp));
        muteStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(Assets.soundIconDown));
        final ImageButton muteButton = new ImageButton(muteStyle);
        muteButton.setSize(64, 64);
        muteButton.setPosition(5, gameDialog.getHeight() / 2 - muteButton.getHeight() / 2 + 10);
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
        });
        gameDialog.addActor(muteButton);

        ImageButton.ImageButtonStyle compassStyle = new ImageButton.ImageButtonStyle();
        compassStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.crosshairIconUp));
        compassStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.crosshairIconDown));
        final ImageButton compassButton = new ImageButton(compassStyle);
        compassButton.setSize(64, 64);
        compassButton.setPosition(gameDialog.getWidth() / 4, gameDialog.getHeight() / 2 - compassButton.getHeight() / 2 + 10);
        compassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        gameDialog.addActor(compassButton);

        ImageButton.ImageButtonStyle touchStyle = new ImageButton.ImageButtonStyle();
        touchStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.touchButtonUp));
        touchStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.touchButtonDown));
        final ImageButton touchButton = new ImageButton(touchStyle);
        touchButton.setSize(64, 64);
        touchButton.setPosition(gameDialog.getWidth() / 4 * 3 - touchButton.getWidth(), gameDialog.getHeight() / 2 - touchButton.getHeight() / 2 + 10);
        touchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        gameDialog.addActor(touchButton);

        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.backIconUp));
        backStyle.imageDown = new TextureRegionDrawable(new TextureRegion(Assets.backIconDown));
        final ImageButton backButton = new ImageButton(backStyle);
        backButton.setSize(64, 64);
        backButton.setPosition(gameDialog.getWidth() - backButton.getWidth(), gameDialog.getHeight() / 2 - backButton.getHeight() / 2 + 10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        gameDialog.addActor(backButton);

        screen.stage.addActor(gameDialog);
    }
}