package com.rarefrog.birdhunt.spriteobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rarefrog.birdhunt.Assets;

/**
 * Created by Andreas on 1/26/2015.
 */
public class Viewmodel {

    private Texture texture;
    private float x, y;
    private final float WIDTH = 179F;
    private final float HEIGHT = 124F;

    public Viewmodel(){
        texture = Assets.viewmodel;
        x = 100F;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, WIDTH, HEIGHT);

    }

}
