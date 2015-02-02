package com.rareFrog.birdhunt.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rareFrog.birdhunt.*;
import com.rareFrog.birdhunt.levels.Level;

import java.util.Random;

public class Duck extends DynamicGameObject {
    private Level level;

    public static final int BLACK_DUCK = 1;
    public static final int RED_DUCK = 2;
    public static final int YELLOW_BIRD = 3;

    public static final int DUCK_STATE_FLYING = 0;
    public static final int DUCK_STATE_HIT = 1;
    public static final int DUCK_STATE_FALLING = 2;
    public static final int DUCK_STATE_STANDBY = 3;
    public static final int DUCK_STATE_DEAD = 4;
    public static final int DUCK_STATE_FLY_AWAY = 6;
    public static final int DUCK_STATE_GONE = 7;
    public static final float DUCK_GRAVITY = -9.5f;
    public static final float DUCK_WIDTH = 38;
    public static final float DUCK_HEIGHT = 38;

    public static final int SCORE2 = 500;
    public static final int SCORE1 = 250;
    public static final int SCORE0 = 100;

    public static float duck_velocity_y = 90;
    public static float duck_velocity_x = 45;

    public int type;
    public TextureRegion texture;
    public TextureRegion uiTexture;
    public Integer state;
    public float uiStateTime;
    public float stateTime;
    private float lastTimeSaved;
    private float lastTimeSaved2;
    private Random rand;
    private int frames;
    private long soundID;
    private float fallingVolume;

    public Duck(Level level, float x, float y) {
        super(x, y, DUCK_WIDTH, DUCK_HEIGHT);
        this.level = level;
        state = DUCK_STATE_STANDBY;
        velocity.set(duck_velocity_x, duck_velocity_y);
        stateTime = 0;
        uiStateTime = 0;
        lastTimeSaved = 0;
        lastTimeSaved2 = 0;
        soundID = -1;
        rand = new Random();
        uiTexture = Assets.uiWhiteDuck;

        if (rand.nextFloat() > 0.5f)
            type = YELLOW_BIRD;
        else if (rand.nextFloat() > 0.5f)
            type = BLACK_DUCK;
        else
            type = RED_DUCK;
    }

    public void update(float deltaTime) {

        switch (state) {
            case DUCK_STATE_STANDBY:
                uiTexture = Assets.uiWhiteDuck;
                texture = null;
                break;
            case DUCK_STATE_FLYING:
                stateFlying(deltaTime);
                uiStateFlying(deltaTime);
                checkStateTime();
                break;
            case DUCK_STATE_HIT:
                stateHit();
                break;
            case DUCK_STATE_FALLING:
                stateFalling(deltaTime);
                break;
            case DUCK_STATE_DEAD:
                // uiTexture = Assets.uiRedDuck;
                break;
            case DUCK_STATE_FLY_AWAY:
                stateFlyAway(deltaTime);
                break;
            case DUCK_STATE_GONE:
                stateGone();
                break;
        }

        stateTime += deltaTime;
    }

    private void stateFlying(float deltaTime) {
        texture = DucksTextures.getFlyingTexture(stateTime, type, velocity);

        if (position.y < 75)
            velocity.y = Math.abs(velocity.y);
        position.add(velocity.x * deltaTime * 2, velocity.y * deltaTime * 2);
        bounds.x = position.x;
        bounds.y = position.y;

        if (rand.nextFloat() > 0.998f)
            velocity.x = -velocity.x;

        if (rand.nextFloat() > 0.998f)
            velocity.x = 0;

        if (rand.nextFloat() > 0.99f)
            velocity.x = duck_velocity_x;
        else if (rand.nextFloat() > 0.99f)
            velocity.x = -duck_velocity_x;

        if (rand.nextFloat() > 0.999f)
            velocity.y = -velocity.y;

        if (rand.nextFloat() > 0.999f)
            if (rand.nextFloat() > 0.99f)
                velocity.y = duck_velocity_y;
            else
                velocity.y = -duck_velocity_y;
        else if (rand.nextFloat() > 0.99f)
            if (rand.nextFloat() > 0.99f)
                velocity.y = duck_velocity_y / 3;
            else
                velocity.y = -duck_velocity_y / 3;
        else if (rand.nextFloat() > 0.99f)
            if (rand.nextFloat() > 0.99f)
                velocity.y = duck_velocity_y / 2;
            else
                velocity.y = -duck_velocity_y / 2;

        if (position.x < -(level.width / 2)) { //DUCK_WIDTH / 2) {
            position.x = DUCK_WIDTH / 2;
            velocity.x = duck_velocity_y;
            velocity.y = duck_velocity_y * rand.nextFloat();
        }

        if (position.x > (level.width / 2)) {//Game.VIRTUAL_WIDTH - DUCK_WIDTH / 2) {
            position.x = Game.VIRTUAL_WIDTH - DUCK_WIDTH / 2;
            velocity.x = -duck_velocity_y;
            velocity.y = duck_velocity_y * rand.nextFloat();
        }

        if (position.y < DUCK_WIDTH / 2) {
            position.y = DUCK_HEIGHT / 2;
            velocity.x = duck_velocity_y;
            velocity.y = duck_velocity_y * rand.nextFloat();
        }

        if (position.y > Game.VIRTUAL_HEIGHT - DUCK_HEIGHT / 2) {
            position.y = Game.VIRTUAL_HEIGHT - DUCK_HEIGHT / 2;
            velocity.x = -duck_velocity_y;
            float topBot = rand.nextFloat() > 0.5f ? 1 : -1;
            velocity.y = duck_velocity_y * rand.nextFloat() * topBot;
        }

//        if (stateTime > 0.125f) {
//            if ((stateTime - lastTimeSaved) >= 0.125f) {
//                if (Settings.soundEnabled) Assets.flapLong.play();
//                lastTimeSaved = stateTime;
//            }
//        }

        if (stateTime > 1.3f) {
            if ((stateTime - lastTimeSaved2) >= 2f) {
                if (Settings.soundEnabled) Assets.cuak.play();
                lastTimeSaved2 = stateTime;
            }
        }
    }

    private void uiStateFlying(float deltaTime) {
        if (uiStateTime < 1.5f)
            uiTexture = null;
        else if (uiStateTime < 3)
            uiTexture = Assets.uiWhiteDuck;
        else
            uiStateTime = 0;

        uiStateTime += deltaTime;
    }

    private void checkStateTime() {
        if (stateTime > 8f) {
            flyAway();
        }
    }

    public void flyAway() {
        state = DUCK_STATE_FLY_AWAY;
        stateTime = 0;
        stopFlap();
        return;
    }

    public void hit() {
        velocity.set(0, 0);
        state = Duck.DUCK_STATE_HIT;
        stateTime = 0;

        if (Settings.soundEnabled) Assets.duckShot.play();
        stopFlap();
    }

    private void stateFalling(float deltaTime) {
        Assets.duckFoundSnd.setVolume(soundID, fallingVolume -= 0.05f);

        frames++;
        if (frames > 4) {
            texture = DucksTextures.getFallingTexture(stateTime, type, true);
            frames = 0;
        } else
            texture = DucksTextures.getFallingTexture(stateTime, type, false);

        velocity.add(0, DUCK_GRAVITY);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x;
        bounds.y = position.y;

        if (position.y < 50f) {
            state = DUCK_STATE_DEAD;
            Assets.duckFallingSnd.stop(soundID);
            if (Settings.soundEnabled) Assets.hitGround.play();
        }
    }

    private void stateHit() {
        texture = DucksTextures.getHitTexture(type);
        uiTexture = Assets.uiRedDuck;

        if (stateTime > 1.0f) {
            state = DUCK_STATE_FALLING;
            velocity.set(0, DUCK_GRAVITY);

            if (Settings.soundEnabled) {
                soundID = Assets.duckFallingSnd.play();
                Assets.duckFoundSnd.setVolume(soundID, fallingVolume = 1);
            }
        }
    }

    private void stateFlyAway(float deltaTime) {
        texture = DucksTextures.getUpTexture(type, stateTime);

        position.add(0, deltaTime * 135);
        bounds.x = position.x;
        bounds.y = position.y;

//        if (stateTime > 0.125f)
//            if ((stateTime - lastTimeSaved) >= 0.125f) {
//                if (Settings.soundEnabled) Assets.flapLong.play();
//                lastTimeSaved = stateTime;
//            }

        if (position.y > Game.VIRTUAL_HEIGHT + DUCK_HEIGHT)
            state = DUCK_STATE_GONE;

        uiTexture = Assets.uiWhiteDuck;
    }

    private void stateGone() {
        uiTexture = Assets.uiWhiteDuck;
    }

    public String toString() {
        return "[" + bounds.x + ", " + bounds.y + "]";
    }

    private void playFlap() {
        if (Settings.soundEnabled)
            switch (type) {
                case YELLOW_BIRD:
                    Assets.flapLong.loop();
                    break;
                case BLACK_DUCK:
                    Assets.flapNormal.loop();
                    break;
                case RED_DUCK:
                    Assets.flapShort.loop();
                    break;
            }
    }

    private void stopFlap() {
        switch (type) {
            case YELLOW_BIRD:
                Assets.flapLong.stop();
                break;
            case BLACK_DUCK:
                Assets.flapNormal.stop();
                break;
            case RED_DUCK:
                Assets.flapShort.stop();
                break;
        }
    }

    public void fly() {
        state = Duck.DUCK_STATE_FLYING;
        playFlap();
    }
}