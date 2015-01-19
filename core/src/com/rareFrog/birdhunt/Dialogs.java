package com.rareFrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
        } else {
            game.dialogOpen = false;
            remove();
        }
    }

    private void build(AbstractScreen screen) {

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
                window.setPosition(Game.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2);
                screen.stage.addActor(window);
                return;
            }

            window = new Window("Pause", Assets.skin);

            TextButton noButton = new TextButton("Resume", Assets.skin);
            noButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.dialogOpen = false;
                    window.remove();
                }
            });
            noButton.setPosition(noButton.getWidth() / 4, noButton.getHeight() / 2);

            //add to dialog
            window.addActor(noButton);

            TextButton backButton = new TextButton("Main Menu", Assets.skin);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    /**
                     * Go Back and show Interstitial Ad
                     * game.setScreen(new MainMenuScreen());
                     */
                    Gdx.app.exit();
                }
            });
            backButton.setPosition(noButton.getWidth() / 4 + 20 + noButton.getWidth(), backButton.getHeight() / 2);
            //add to dialog
            window.addActor(backButton);

            window.setSize(noButton.getWidth() / 4 + 20 + backButton.getWidth() / 4 + noButton.getWidth() + backButton.getWidth(), backButton.getHeight() * 2.5f);
            window.setPosition(Game.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2);

            /**Add to screen stage*/
            screen.stage.addActor(window);

            return;
        }
    }

    private void remove() {
        if (window != null)
            window.remove();
    }
}