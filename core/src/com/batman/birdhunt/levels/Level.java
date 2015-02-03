package com.batman.birdhunt.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.batman.birdhunt.Game;

/**
 * Created by Elmar on 1/22/2015.
 */
public abstract class Level {
    public int tiles;
    public int width;
    public int height;
    public float horPosition;
    protected TextureRegion foreGround[];
    protected TextureRegion backGround[];
    protected OrthographicCamera gameCam;

    public Level(OrthographicCamera gameCam, int width, int height, int tiles) {
        this.gameCam = gameCam;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        foreGround = new TextureRegion[tiles];
        backGround = new TextureRegion[tiles];
    }

    public void update(float horPosition) {
        this.horPosition = horPosition;
    }

    public void drawForeGround(SpriteBatch spriteBatch) {
        for (int i = 0; i < tiles; i++) {
            float xPosition = Game.VIRTUAL_WIDTH * i - Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horPosition;
            spriteBatch.draw(foreGround[i],
                    xPosition,
                    gameCam.position.y - Game.VIRTUAL_HEIGHT / 2,
                    Game.VIRTUAL_WIDTH,
                    Game.VIRTUAL_HEIGHT);
        }
    }

    public void drawBackgrounds(SpriteBatch spriteBatch) {
        for (int i = 0; i < tiles; i++) {
            float xPosition = Game.VIRTUAL_WIDTH * i - Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horPosition;
            spriteBatch.draw(backGround[i],
                    xPosition,
                    gameCam.position.y - Game.VIRTUAL_HEIGHT / 2,
                    Game.VIRTUAL_WIDTH,
                    Game.VIRTUAL_HEIGHT);
        }
    }

    public void drawAndUpdateSpecifics(float deltaT, SpriteBatch spriteBatch) {

    }

    public void setOrthographicCamera(OrthographicCamera orthographicCamera) {
        this.gameCam = orthographicCamera;
    }
}
