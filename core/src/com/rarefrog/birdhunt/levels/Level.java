package com.rarefrog.birdhunt.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rarefrog.birdhunt.Game;

/**
 * Created by Elmar on 1/22/2015.
 */
public abstract class Level {
    public int tiles;
    public int width;
    public int height;
    public float horPosition;
    //protected TextureRegion foreGround[];
    protected TextureRegion foreGround;
    //protected TextureRegion backGround[];
    protected TextureRegion backGround;
    protected OrthographicCamera gameCam;

    public Level(OrthographicCamera gameCam, int width, int height, int tiles) {
        this.gameCam = gameCam;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public void update(float horPosition) {
        this.horPosition = horPosition;
    }

    public void drawForeGround(SpriteBatch spriteBatch) {
        spriteBatch.draw(foreGround,
                -Game.VIRTUAL_WIDTH - horPosition, gameCam.position.y - Game.VIRTUAL_HEIGHT / 2,
                Game.VIRTUAL_WIDTH * 3,
                Game.VIRTUAL_HEIGHT);
    }

    public void drawBackgrounds(SpriteBatch spriteBatch) {
        //float xPosition = Game.VIRTUAL_WIDTH * i - Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horPosition
        spriteBatch.draw(backGround,
                -Game.VIRTUAL_WIDTH - horPosition, gameCam.position.y - Game.VIRTUAL_HEIGHT / 2,
                Game.VIRTUAL_WIDTH * 3,
                Game.VIRTUAL_HEIGHT);
    }

    public void drawAndUpdateSpecifics(float deltaT, SpriteBatch spriteBatch) {

    }

    public void setOrthographicCamera(OrthographicCamera orthographicCamera) {
        this.gameCam = orthographicCamera;
    }
}
