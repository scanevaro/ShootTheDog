package com.rareFrog.birdhunt;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Elmar on 1/22/2015.
 */
public abstract class Level {
    public int width;
    public int height;
    public float horPosition;
    protected TextureRegion foreGround;
    private OrthographicCamera gameCam;

    public Level(OrthographicCamera gameCam, int width, int height) {
        this.width = width;
        this.height = height;
        this.gameCam = gameCam;
    }

    public void update(float horPosition) {
        this.horPosition = horPosition;
    }

    public void drawForeGround(SpriteBatch spriteBatch) {
        if (foreGround != null) {
            spriteBatch.draw(foreGround, Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horPosition,
                    gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                    Game.VIRTUAL_HEIGHT);
        }
    }

    public abstract void drawBackgrounds(SpriteBatch spriteBatch);
}
