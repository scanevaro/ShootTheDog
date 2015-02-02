package com.rareFrog.birdhunt.entities;

import com.rareFrog.birdhunt.Assets;

/**
 * Created by scanevaro on 30/01/2015.
 */
public class Squirrel extends Animal {
    public Squirrel() {
        super();
        bounds.set(0, 0, 32, 32);
    }

    @Override
    public void update(float delta) {
        switch (state) {
            case INSTANTIATE:
                position.set(0, 0);
                setState(STANDING);
                break;
            case STANDING:
                texture = Assets.squirrel;
                break;
        }
    }

    @Override
    public void hit() {

    }
}