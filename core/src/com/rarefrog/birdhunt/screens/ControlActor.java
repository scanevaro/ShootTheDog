package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.input.Controls;

/**
 * Created by Elmar on 3/20/2015.
 */
public class ControlActor extends Actor {
    public ClickListener clickListener;
    private TextureRegion texture;
    private ShapeRenderer shapeRenderer;
    private int leftSelected = -1;
    private int rightSelected = -1;
    private Game game;
    private int playStyle;
    private float timer = 0;
    private boolean time = false;


    public ControlActor(Game game, int playStyle) {
        texture = Assets.controls;
        shapeRenderer = new ShapeRenderer();
        this.playStyle = playStyle;
        this.game = game;

        setActions();
        setWidth(Game.VIRTUAL_WIDTH);
        setHeight(Game.VIRTUAL_HEIGHT);
    }

    private void setActions() {

        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(x + "  " + y);
                if (!time) {
                    if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                        if (leftSelected == 1) {
                            Controls.touchMovement = false;
                            addAction(Actions.fadeOut(3));
                            timer = 0;
                            time = true;
                        }
                        leftSelected = 1;
                        rightSelected = 0;
                    } else {
                        if (rightSelected == 1) {
                            Controls.touchMovement = true;
                            addAction(Actions.fadeOut(3));
                            timer = 0;
                            time = true;
                        }
                        leftSelected = 0;
                        rightSelected = 1;
                    }
                    System.out.println("l:" + leftSelected + "r:" + rightSelected);
                }
            }
        };
        addListener(clickListener);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color backGr1 = new Color(1, 1, 1, 1);
        Color backGr2 = new Color(1, 1, 1, 1);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        if (time) {
            timer += Gdx.graphics.getDeltaTime();
            if (timer >= 3) {
                game.setScreen(new GameScreen(game, playStyle));
            }
        }
        parentAlpha = 1f - (timer / 3f);
        if (leftSelected == -1) {
            backGr1.set(1, 1, 1, parentAlpha);
        } else if (leftSelected == 0) {
            backGr1.set(0.9059f * parentAlpha, 0.2431f * parentAlpha, 0.3921f * parentAlpha, parentAlpha);
        } else {
            backGr1.set(0.3725f * parentAlpha, 0.6824f *parentAlpha, 0.0745f * parentAlpha, parentAlpha);
        }

        shapeRenderer.setColor(backGr1);
        shapeRenderer.rect(0, 0, Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT);
        if (rightSelected == -1) {
            backGr2.set(1, 1, 1, parentAlpha);
        } else if (rightSelected == 0) {
            backGr2.set(0.9059f * parentAlpha, 0.2431f * parentAlpha, 0.3921f * parentAlpha, parentAlpha);
        } else {
            backGr2.set(0.3725f * parentAlpha, 0.6824f *parentAlpha, 0.0745f * parentAlpha, parentAlpha);
        }
        shapeRenderer.setColor(backGr2);
        shapeRenderer.rect(Game.VIRTUAL_WIDTH / 2, 0, Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT);
        shapeRenderer.end();
        batch.begin();
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        batch.draw(texture, 0, 0, Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);


    }


}
