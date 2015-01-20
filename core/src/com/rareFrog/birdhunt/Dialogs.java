package com.rareFrog.birdhunt;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rareFrog.birdhunt.screens.AbstractScreen;
import com.rareFrog.birdhunt.screens.GameScreen;
import com.rareFrog.birdhunt.screens.MainMenuScreen;
import com.rareFrog.birdhunt.screens.SplashScreen;

/**
 * Created by scanevaro on 14/01/2015.
 */
public class Dialogs {
    public Window window;
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
            remove();
            if (Settings.soundEnabled) Assets.pauseClosed.play();
        }
    }

    private void build(final AbstractScreen screen) {

        if (screen instanceof SplashScreen) {
            window = new Dialog("Quit Game?", Assets.skin);

            screen.stage.addActor(window);

            return;
        }

        if (screen instanceof MainMenuScreen) {
            window = new Window("Config", Assets.skin);

            //TODO

            screen.stage.addActor(window);

            return;
        }

        if (screen instanceof GameScreen) {
            if (window != null) {
                window.setPosition(Game.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - window.getHeight() / 2);
                screen.stage.addActor(window);
                return;
            }

            window = new Window("Pause", Assets.skin.get("pauseDialog", Window.WindowStyle.class));
            window.setWidth(256);
            window.setPosition(Game.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - window.getHeight() / 2);

            final TextButton closeDialogButton = new TextButton("Resume", Assets.skin.get("button", TextButton.TextButtonStyle.class));
            closeDialogButton.setSize(52, 15);
            closeDialogButton.setPosition(window.getWidth() / 2 - closeDialogButton.getWidth() / 2, 20);
            closeDialogButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.dialogOpen = false;
                    if (Settings.soundEnabled) Assets.pauseClosed.play();
                    window.remove();
                }
            });
            window.addActor(closeDialogButton);

            ImageButton.ImageButtonStyle muteStyle = new ImageButton.ImageButtonStyle();
            muteStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.soundIconUp));
            muteStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(Assets.soundIconDown));
            final ImageButton muteButton = new ImageButton(muteStyle);
            muteButton.setSize(64, 64);
            muteButton.setPosition(20, window.getHeight() / 2 - muteButton.getHeight() / 2);
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
            window.addActor(muteButton);

            screen.stage.addActor(window);

            return;
        }
    }

    private void remove() {
        if (window != null)
            window.remove();
    }
}