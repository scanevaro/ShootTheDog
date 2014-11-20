package com.rareFrog.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.rareFrog.game.Game;
import com.rareFrog.game.entities.Dog;
import com.rareFrog.game.entities.Duck;
import com.rareFrog.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class World {

    public interface WorldListener {
        public void reload();

        public void shoot();

        public void ducks();
    }

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_ROUND_START = 1;
    public static final int WORLD_STATE_ROUND_PAUSE = 2;
    public static final int WORLD_STATE_ROUND_END = 4;
    public static final int WORLD_STATE_COUNTING_DUCKS = 5;
    public static final int WORLD_STATE_GAME_OVER_1 = 6;
    public static final int WORLD_STATE_GAME_OVER_2 = 7;
    public static final int WORLD_STATE_PERFECT_ROUND = 8;
    public static final int GAME_MODE_1 = 0;
    public static final int GAME_MODE_2 = 1;
    private final int PERFECT = 10000;

    public final List<Duck> ducks;
    public final WorldListener listener;
    private WorldRenderer worldRenderer;
    public final Dog dog;
    public final Random rand;

    public int score;
    public int state;
    public int gameMode;
    public int duckCount;
    public int duckCountRoundEnd;
    public int ducksHit;
    public float stateTime;
    public boolean checkDucksRoundPause;
    public boolean perfect;

    Vector3 touchPoint;

    public World(WorldListener listener, int gameMode) {
        this.ducks = new ArrayList<Duck>(10);
        this.listener = listener;
        this.gameMode = gameMode;
        rand = new Random();
        this.touchPoint = new Vector3();

        this.score = 0;
        dog = new Dog(0, 60, this);

        generateRound();
    }

    private void generateRound() {
        ducks.clear();

        for (int i = 0; i < 10; i++) {
            float random = rand.nextFloat() > 0.5f ? Game.VIRTUAL_WIDTH / 2 - 30 : Game.VIRTUAL_WIDTH / 2 + 30;
            Duck duck = new Duck(random, 75f);
            ducks.add(duck);
        }

        duckCount = 0;
        duckCountRoundEnd = 0;
        stateTime = 0;
        perfect = true;
        this.state = WORLD_STATE_ROUND_START;
    }

    public void update(float deltaTime) {
        switch (state) {
            case WORLD_STATE_ROUND_START:
                stateRoundStart(deltaTime);
                break;
            case WORLD_STATE_RUNNING:
                stateRunning(deltaTime);
                break;
            case WORLD_STATE_ROUND_PAUSE:
                stateRoundPause(deltaTime);
                break;
            case WORLD_STATE_ROUND_END:
                stateRoundEnd();
                break;
            case WORLD_STATE_PERFECT_ROUND:
                statePerfectRound();
                break;
            case WORLD_STATE_COUNTING_DUCKS:
                stateCountingDucks(deltaTime);
                break;
            case WORLD_STATE_GAME_OVER_1:
                stateGameOver1();
                break;
            case WORLD_STATE_GAME_OVER_2:
                stateGameOver2(deltaTime);
                break;
        }

        stateTime += deltaTime;
    }

    private void stateRoundStart(float deltaTime) {
        updateDog(deltaTime, ducksHit);
        checkDogState();
    }

    private void stateNewRound(float deltaTime) {
        updateDog(deltaTime, ducksHit);
        checkDogState();
    }

    private void stateRunning(float deltaTime) {
        updateDog(deltaTime, ducksHit);
        updateDucks(deltaTime);
        checkCollisions();
    }

    private void stateRoundPause(float deltaTime) {
        if (checkDucksRoundPause) {
            checkDucksRoundPause();

            if (ducksHit != 0)
                dog.position.x = ducks.get(duckCount).position.x;
            else
                dog.position.x = Game.VIRTUAL_WIDTH / 2 - (Dog.DOG_WIDTH / 2);
        }

        if (stateTime > 1.6f) {
            updateDog(deltaTime, ducksHit);
            checkDogState();
        }

        if (duckCount > 9) {
            state = WORLD_STATE_COUNTING_DUCKS;
            stateTime = 0;
            duckCount = 0;
        }

    }

    private void stateRoundEnd() {
        if (stateTime > 5) {
            newRound();
            state = WORLD_STATE_ROUND_START;
            dog.state = Dog.DOG_STATE_WALKING_NEW_ROUND;
            dog.position.set(Game.VIRTUAL_WIDTH / 2 - Game.VIRTUAL_WIDTH / 4,
                    1.9f);
        }
    }

    private void statePerfectRound() {
        if (stateTime > 9) {
            newRound();
            state = WORLD_STATE_ROUND_START;
            dog.state = Dog.DOG_STATE_WALKING_NEW_ROUND;
            dog.position.set(Game.VIRTUAL_WIDTH / 2 - Game.VIRTUAL_WIDTH / 4,
                    1.9f);
        } else if (stateTime > 5) {
            if (perfect) {
                Assets.perfect.play();
                score += PERFECT;
                perfect = false;
            }
        }
    }

    private void stateCountingDucks(float deltaTime) {
        if (stateTime > 0.325f) {
            for (duckCountRoundEnd = duckCountRoundEnd; duckCountRoundEnd < ducks
                    .size(); duckCountRoundEnd++) {
                if (ducks.get(duckCountRoundEnd).state == Duck.DUCK_STATE_GONE) {
                    Duck duck = ducks.get(duckCountRoundEnd);
                    for (int x = duckCountRoundEnd; x < ducks.size(); x++) {
                        if (ducks.get(x).state == Duck.DUCK_STATE_DEAD) {
                            ducks.remove(duckCountRoundEnd);
                            ducks.add(duck);
                            if (Settings.soundEnabled) Assets.movingDucksArray.play();
                            stateTime = 0;
                            return;
                        }
                    }
                    duckCountRoundEnd = ducks.size();
                }
            }
        }

        if (duckCountRoundEnd >= ducks.size()) {
            int ducksDead = 0;
            for (int i = 0; i < ducks.size(); i++) {
                if (ducks.get(i).state == Duck.DUCK_STATE_DEAD)
                    ducksDead++;
            }

            stateTime = 0;

            if (ducksDead == 10) {
                state = WORLD_STATE_PERFECT_ROUND;
                if (Settings.soundEnabled) Assets.endRound.play();
            } else if (ducksDead >= 6) {
                state = WORLD_STATE_ROUND_END;
                if (Settings.soundEnabled) Assets.endRound.play();
            } else {
                state = WORLD_STATE_GAME_OVER_1;
            }

        }
    }

    private void stateGameOver1() {
        if (stateTime > 3) {
            state = WORLD_STATE_GAME_OVER_2;
            dog.position.x = Game.VIRTUAL_WIDTH / 2 - (Dog.DOG_WIDTH / 2);
            dog.position.y = 1.7f;
            dog.state = Dog.DOG_STATE_LAUGHING_GAME_OVER;
        }
    }

    private void stateGameOver2(float deltaTime) {
        updateDog(deltaTime, duckCount);
    }

    private void updateDog(float deltaTime, int duckCount) {
        dog.update(deltaTime, duckCount);
    }

    private void checkDogState() {
        if (dog.state == Dog.DOG_STATE_HIDDEN) {
            state = WORLD_STATE_RUNNING;
            checkDucksRoundPause = true;
        }
    }

    private void updateDucks(float deltaTime) {
        if (gameMode == GAME_MODE_1)
            ducks.get(duckCount).update(deltaTime);
        else {
            ducks.get(duckCount).update(deltaTime);
            ducks.get(duckCount + 1).update(deltaTime);
        }
    }

    private void checkCollisions() {
        checkDuckCollision();
        checkDuckStates();
    }

    private void checkDuckCollision() {
        if (gameMode == GAME_MODE_1) {
            Duck duck = ducks.get(duckCount);

            if (Gdx.input.justTouched()) {
                worldRenderer.gameCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (duck.bounds.contains(touchPoint.x, touchPoint.y)
                        && duck.state == Duck.DUCK_STATE_FLYING) {
                    duck.hit();
                    score += Duck.SCORE;
                }
            } else if (Gdx.input.justTouched() && GameScreen.shots == 0
                    && duck.state == Duck.DUCK_STATE_FLYING) {
                duck.state = Duck.DUCK_STATE_FLY_AWAY;
            }
        } else {
            Duck duck = ducks.get(duckCount);
            if (Gdx.input.justTouched()
                    && duck.bounds.contains(touchPoint.x, touchPoint.y)
                    && duck.state == Duck.DUCK_STATE_FLYING) {
                duck.hit();

                score += Duck.SCORE;
            } else if (Gdx.input.justTouched() && GameScreen.shots == 0
                    && duck.state == Duck.DUCK_STATE_FLYING) {
                duck.state = Duck.DUCK_STATE_FLY_AWAY;
            }

            Duck duck2 = ducks.get(duckCount + 1);
            if (Gdx.input.justTouched()
                    && duck2.bounds.contains(touchPoint.x, touchPoint.y)
                    && duck2.state == Duck.DUCK_STATE_FLYING) {
                duck2.hit();

                score += Duck.SCORE;
            } else if (Gdx.input.justTouched() && GameScreen.shots == 0
                    && duck2.state == Duck.DUCK_STATE_FLYING) {
                duck2.state = Duck.DUCK_STATE_FLY_AWAY;
            }
        }
    }

    private void checkDuckStates() {
        if (gameMode == GAME_MODE_1) {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD
                    || ducks.get(duckCount).state == Duck.DUCK_STATE_GONE)
                state = WORLD_STATE_ROUND_PAUSE;
        } else {
            if ((ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD || ducks
                    .get(duckCount).state == Duck.DUCK_STATE_GONE)
                    && (ducks.get(duckCount + 1).state == Duck.DUCK_STATE_DEAD || ducks
                    .get(duckCount + 1).state == Duck.DUCK_STATE_GONE))
                state = WORLD_STATE_ROUND_PAUSE;
        }
    }

    private void checkDucksRoundPause() {
        ducksHit = 0;
        if (gameMode == GAME_MODE_1) {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD) {
                ducksHit++;
                checkDucksRoundPause = false;
            } else if (ducks.get(duckCount).state == Duck.DUCK_STATE_GONE)
                checkDucksRoundPause = false;
        } else {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD)
                ducksHit++;

            if (ducks.get(duckCount + 1).state == Duck.DUCK_STATE_DEAD)
                ducksHit++;

            checkDucksRoundPause = false;
        }

        stateTime = 0;
    }

    private void newRound() {
        Duck.duck_velocity_x += 12;
        Duck.duck_velocity_y += 6;

        generateRound();
        GameScreen.round++;
    }

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }
}