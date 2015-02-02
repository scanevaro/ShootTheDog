package com.rareFrog.birdhunt.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by scanevaro on 30/01/2015.
 */
public abstract class Animal {
    public static final int IDLE = -1;
    public static final int INSTANTIATE = 0;
    public static final int SHOT = 1;
    public static final int STANDING = 2;

    public Rectangle bounds;
    public Vector2 position;
    public boolean active;
    public int state;
    public TextureRegion texture;

    public Animal() {
        bounds = new Rectangle();
        position = new Vector2();
        active = false;
        state = IDLE;
    }

    public abstract void update(float delta);

    public abstract void hit();

    public boolean isActive() {
        return active;
    }

    public void setState(int state) {
        this.state = state;

        if (state == INSTANTIATE)
            active = true;
        else if (state == SHOT)
            active = false;
    }
}
