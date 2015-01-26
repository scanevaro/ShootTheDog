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
    private final float gForce = 0.07F;
    private TextureRegion texture;
    private Animation animation;
    private Random r;
    private float x, y;
    private final float HEIGHT = 151F / 2.359F; // Both 64x64
    private final float WIDTH = 141F / 2.20F;

    public boolean removed;

    public BulletCasing() {
        x = 300;
        y = 100;
        r = new Random();
        animation = Assets.bulletCasing;
        removed = false;
        reset();
    }

    public void remove(){
        removed = true;
    }

    public void render(SpriteBatch batch){
        if(removed) return;
        texture = animation.getKeyFrame(stateTime, true);
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public void update(float delta){
        if(removed) return;
        stateTime += delta;
        x += hForce;
        y += vForce;
        vForce -= gForce;
        hForce *= 0.95;
        if(stateTime >= Float.MAX_VALUE - 1) stateTime = 0;
        System.out.println("X: " + x + ", Y: " + y);
    }

    public void reset() {
        x = 200;
        y = 200;
        vForce = 2F + r.nextFloat() * 1.5F;
        hForce = 1F + r.nextFloat() * 2F;
    }
}
