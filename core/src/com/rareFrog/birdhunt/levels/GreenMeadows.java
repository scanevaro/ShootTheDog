package com.rareFrog.birdhunt.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rareFrog.birdhunt.Assets;
import com.rareFrog.birdhunt.Game;

import java.util.Random;

/**
 * Created by Elmar on 24-1-2015.
 */
public class GreenMeadows extends Level {
    public static final int GREEN_MEADOWS_TILES = 3;
    private float scaleX = 1;
    private float scaleY = 1;
    private Sprite clouds[] = new Sprite[20];

    public GreenMeadows(OrthographicCamera gameCam) {
        super(gameCam, 0, 0, GREEN_MEADOWS_TILES);
        for (int i = 0; i < GREEN_MEADOWS_TILES; i++) {
            backGround[i] = Assets.backgroundBackRegion;
            foreGround[i] = Assets.backgroundRegion;
        }
        scaleX = Game.VIRTUAL_WIDTH / backGround[0].getRegionWidth();
        scaleY = Game.VIRTUAL_HEIGHT / backGround[0].getRegionHeight();
        float xPosition = gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horPosition;
        Random random = new Random();
        float scaleMultiplier = 0;
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Sprite(Assets.cloud[i % 3]);
            scaleMultiplier = 0.8f * random.nextFloat() + 0.2f;
            clouds[i].setPosition(-Game.VIRTUAL_WIDTH + random.nextFloat() * Game.VIRTUAL_WIDTH * 2, random.nextFloat() * Game.VIRTUAL_HEIGHT/2 + Game.VIRTUAL_HEIGHT/2);
            clouds[i].setScale(scaleX * scaleMultiplier, scaleY * scaleMultiplier);

        }
    }

    @Override
    public void drawAndUpdateSpecifics(float deltaT, SpriteBatch spriteBatch) {
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].draw(spriteBatch);
            clouds[i].setX(clouds[i].getX() + clouds[i].getScaleX() * 2);
            if (clouds[i].getX() > Game.VIRTUAL_WIDTH) {
                clouds[i].setX(-Game.VIRTUAL_WIDTH - (clouds[i].getWidth() * clouds[i].getScaleX()));
            }
        }
    }
}
