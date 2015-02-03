package com.batman.birdhunt.entities;

import com.batman.birdhunt.Assets;

/**
 * Created by scanevaro on 30/01/2015.
 */
public class Cow extends Animal{
    @Override
    public void update(float delta) {
        switch (state) {
            case INSTANTIATE:
                position.set(0, 0);
                setState(STANDING);
                break;
            case STANDING:
                texture = Assets.cow;
                break;
        }
    }

    @Override
    public void hit() {

    }
}
