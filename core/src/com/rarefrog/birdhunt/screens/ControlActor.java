package com.rarefrog.birdhunt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;

/**
 * Created by Elmar on 3/20/2015.
 */
public class ControlActor extends Actor {
    private TextureRegion texture;
    private ShapeRenderer shapeRenderer;
    public float stateTime;
    private int leftSelected = -1;
    private int rightSelected = -1;


    public ControlActor() {
        texture = Assets.controls;
        shapeRenderer = new ShapeRenderer();

        setActions();

        stateTime = 0;
    }

    private void setActions() {
        SequenceAction secAction = new SequenceAction();
        secAction.addAction(Actions.fadeIn(0.5f));
        addAction(secAction);
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                leftSelected = 1;
                rightSelected = 0;
            } else {
                leftSelected = 0;
                rightSelected = 1;
            }
            System.out.println("l:" + leftSelected + "r:" + rightSelected);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color backGr = new Color(1, 1, 1, 1);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        if (leftSelected == -1) {
            backGr.set(1, 1, 1, 1);
        } else if (leftSelected == 1) {
            backGr.set(1, 0, 0, 1);
        } else {
            backGr.set(0, 1, 0, 1);
        }
        shapeRenderer.setColor(backGr);
        shapeRenderer.rect(0, 0, Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT);
        if (rightSelected == -1) {
            backGr.set(1, 1, 1, 1);
        } else if (rightSelected == 0) {
            backGr.set(1, 0, 0, 1);
        } else {
            backGr.set(0, 1, 0, 1);
        }
        shapeRenderer.setColor(backGr);
        shapeRenderer.rect(Game.VIRTUAL_WIDTH / 2, 0, Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT);
        shapeRenderer.end();
        batch.begin();
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        batch.draw(texture, 0, 0, Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);


    }
}
