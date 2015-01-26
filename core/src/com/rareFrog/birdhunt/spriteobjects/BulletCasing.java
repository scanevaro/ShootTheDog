package com.rareFrog.birdhunt.spriteobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rareFrog.birdhunt.Assets;

import java.util.Random;

/**
 * Created by Andreas on 1/24/2015.
 */
public class BulletCasing {

    private float vForce, hForce;
    private float stateTime = 0;
    private final float gForce = 0.0982F;
    private TextureRegion texture;
    private Animation animation;
    private float x, y;
    private final float HEIGHT = 151F / 2.359F; // Both 64x64
    private final float WIDTH = 141F / 2.20F;

    public BulletCasing() {
        x = 200;
        y = 200;
        Random r = new Random();
        vForce = 2 - r.nextFloat() * 2;
        hForce = 1 - r.nextFloat() * 4;
        animation = Assets.bulletCasing;
    }

    public void render(SpriteBatch batch){
        if(texture == null) texture = animation.getKeyFrame(stateTime, true);
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public void update(float delta){
        texture = animation.getKeyFrame(stateTime);
        stateTime += delta;
        x += hForce;
        y += vForce;
        vForce -= gForce;
        hForce *= 0.95;
        if(stateTime >= Float.MAX_VALUE - 1) stateTime = 0;
    }

}
