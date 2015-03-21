package com.rarefrog.birdhunt.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.rarefrog.birdhunt.Assets;
import com.rarefrog.birdhunt.Game;

/**
 * Created by scanevaro on 09/12/2014.
 */
public class ChrisSplashActor extends Actor {
    private TextureRegion texture;
    public float stateTime;

    public ChrisSplashActor() {
        texture = Assets.chrislogo;
        setSize(600,600);
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
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);

        batch.draw(texture, 0, 0, Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
    }
}