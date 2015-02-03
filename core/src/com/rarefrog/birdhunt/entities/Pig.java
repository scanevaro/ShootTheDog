package com.rarefrog.birdhunt.entities;

import com.rarefrog.birdhunt.Assets;

/**
 * Created by scanevaro on 30/01/2015.
 */
public class Pig extends Animal{
    @Override
    public void update(float delta) {
        switch (state) {
            case INSTANTIATE:
                position.set(0, 0);
                setState(STANDING);
                break;
            case STANDING:
                texture = Assets.pig;
                break;
        }
    }

    @Override
    public void hit() {

    }
}
