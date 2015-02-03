package com.rarefrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rarefrog.birdhunt.entities.*;
import com.rarefrog.birdhunt.levels.GreenMeadows;
import com.rarefrog.birdhunt.levels.Level;
import com.rarefrog.birdhunt.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class World {

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

    private Game game;
    private GameScreen gameScreen;

    public final List<Duck> ducks;
    private WorldRenderer worldRenderer;
    public final Dog dog;
    public final Random rand;
    public Stage stage;
    //    public Viewmodel viewmodel;
//    public ArrayList<BulletCasing> bulletCasings;
    public Squirrel squirrel;
    public Monkey monkey;
    public Cow cow;
    public Pig pig;
    public Bear bear;

    public int state;
    public int gameMode;
    public int duckCount;
    public int duckCountRoundEnd;
    public int ducksHit;
    public int ducksDead;
    public int dogShot;
    public float stateTime;
    public boolean checkDucksRoundPause;
    public boolean perfect;

    Vector2 touchPoint;

    private Level level;

    public World(Game game, GameScreen gameScreen, int gameMode) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.ducks = new ArrayList<Duck>(10);
        this.gameMode = gameMode;
        rand = new Random();
        this.touchPoint = new Vector2();

        gameScreen.score = 0;
        gameScreen.multiplier = 1;
        dog = new Dog(0, 60, this);
//        bulletCasings = new ArrayList<BulletCasing>();
//        viewmodel = new Viewmodel();

        level = new GreenMeadows(null);
        instantiateAnimals();
        generateRound();
    }

    private void instantiateAnimals() {
        squirrel = new Squirrel();
        monkey = new Monkey();
        cow = new Cow();
        pig = new Pig();
        bear = new Bear();
    }

    private void generateRound() {
        ducks.clear();

        for (int i = 0; i < 10; i++) {
            //float random = rand.nextFloat() > 0.5f ? Game.VIRTUAL_WIDTH / 2 - 30 : Game.VIRTUAL_WIDTH / 2 + 30;
            float random = -(level.width / 2) + (rand.nextFloat() * (level.width));
            Duck duck = new Duck(level, random, 75f);
            ducks.add(duck);
        }

        duckCount = 0;
        duckCountRoundEnd = 0;
        ducksDead = 0;
        stateTime = 0;
        perfect = true;
        this.state = WORLD_STATE_ROUND_START;

        dogShot = 0;
    }

    public void update(float deltaTime) {
        float touchX = ((Gdx.input.getX() - ((Gdx.graphics.getWidth() - stage.getViewport().getScreenWidth()) / 2)) * (stage.getViewport().getWorldWidth() / stage.getViewport().getScreenWidth())) - gameScreen.controls.getCalibratedValue();
        float touchY = (((Gdx.graphics.getHeight() - Gdx.input.getY()) - ((Gdx.graphics.getHeight() - stage.getViewport().getScreenHeight()) / 2)) * (stage.getViewport().getWorldHeight() / stage.getViewport().getScreenHeight()));
        touchPoint.x = touchX;
        touchPoint.y = touchY;
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
//        for (BulletCasing b : bulletCasings) {
//            b.update(deltaTime);
//        }
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

    private void stateRunning(float delta) {
        if (Assets.background.getVolume() <= 0.5f) Assets.background.setVolume(Assets.background.getVolume() + 0.015f);

        updateDog(delta, ducksHit);
        updateDucks(delta);
        updateAnimals(delta);
        checkCollisions();
        checkStates();
    }

    private void stateRoundPause(float delta) {
        if (checkDucksRoundPause) {
            checkDucksRoundPause();

            if (ducksHit != 0)
                dog.position.x = ducks.get(duckCount).position.x;
            else
                dog.position.x = Game.VIRTUAL_WIDTH / 2 - (Dog.DOG_WIDTH / 2);
        }

        {/**Out of Bullets*/
            if (stateTime <= 1.6f && Gdx.input.justTouched() && gameScreen.shots == 0 && Settings.soundEnabled)
                Assets.outOfBullets.play();
        }

        if (dog.state != Dog.DOG_STATE_SHOT && stateTime > 1.5f && ducksHit > 0 && Assets.background.getVolume() > 0.0f)
            Assets.background.setVolume(Assets.background.getVolume() - 0.015f);

        if (stateTime > 1.6f) {
            updateDog(delta, ducksHit);
            updateAnimals(delta);
            checkDogState();
            checkDogCollision();
        }

        if (duckCount > 9) {
            state = WORLD_STATE_COUNTING_DUCKS;
            stateTime = 0;
            duckCount = 0;
        }
    }

    private void stateRoundEnd() {
        if (Assets.background.getVolume() <= 0.5f) Assets.background.setVolume(Assets.background.getVolume() + 0.015f);

        if (stateTime > 5) {
            newRound();
            state = WORLD_STATE_ROUND_START;
            dog.state = Dog.DOG_STATE_WALKING_NEW_ROUND;
            dog.position.set(Game.VIRTUAL_WIDTH / 2 - Game.VIRTUAL_WIDTH / 4, 60);
        }
    }

    private void statePerfectRound() {
        if (Assets.background.getVolume() <= 0.5f) Assets.background.setVolume(Assets.background.getVolume() + 0.015f);

        if (stateTime > 9) {
            newRound();
            state = WORLD_STATE_ROUND_START;
            dog.state = Dog.DOG_STATE_WALKING_NEW_ROUND;
            dog.position.set(Game.VIRTUAL_WIDTH / 2 - Game.VIRTUAL_WIDTH / 4, 60);
        } else if (stateTime > 5) {
            if (perfect) {
                if (Settings.soundEnabled) Assets.perfect.play();
                gameScreen.score += PERFECT;
                perfect = false;
            }
        }
    }

    private void stateCountingDucks(float deltaTime) {
        if (Assets.background.getVolume() > 0.15f) Assets.background.setVolume(Assets.background.getVolume() - 0.015f);

        updateDucksCounting(deltaTime);

        if (stateTime > 0.325f) {
            /** changed original counting ducks to count the ones that got Hit, so it plays the continuos SFX */
            for (duckCountRoundEnd = duckCountRoundEnd; duckCountRoundEnd < ducks.size(); duckCountRoundEnd++) {
                Duck duck = ducks.get(duckCountRoundEnd);

                if (duck.state == Duck.DUCK_STATE_DEAD) {
                    duck.uiTexture = Assets.uiYellowDuck;

                    if (Settings.soundEnabled) Assets.movingDucksArray[ducksDead].play();

                    ducks.remove(duckCountRoundEnd);
                    ducks.add(0, duck);

                    ducksDead++;
                    duckCountRoundEnd++;
                    stateTime = 0;
                    return;
                }
            }
        }

        if (duckCountRoundEnd >= ducks.size()) {
            stateTime = 0;

            if (ducksDead == 10) {
                state = WORLD_STATE_PERFECT_ROUND;
                if (Settings.soundEnabled) Assets.endRound.play();

                /**ducksgiving*/
                if (ducksDead == 10 && dogShot != 10)
                    game.actionResolver.unlockAchievementGPGS("CgkIm6zBrtUEEAIQAQ");
                /**every last one*/
                else if (ducksDead == 10 && dogShot == 10)
                    game.actionResolver.unlockAchievementGPGS("CgkIm6zBrtUEEAIQBA");

            } else if (ducksDead >= 6) {
                state = WORLD_STATE_ROUND_END;
                if (Settings.soundEnabled) Assets.endRound.play();
            } else {
                if (Assets.background.isPlaying()) Assets.background.stop();
                state = WORLD_STATE_GAME_OVER_1;

                /**inner rage*/
                if (ducksDead == 0 && dogShot == 10) game.actionResolver.unlockAchievementGPGS("CgkIm6zBrtUEEAIQAg");
            }

            /**let it go*/
            if (dogShot == 0) game.actionResolver.unlockAchievementGPGS("CgkIm6zBrtUEEAIQBQ");
        }
    }

    private void updateDucksCounting(float deltaTime) {
        for (int i = 0; i < ducks.size(); i++)
            ducks.get(i).update(deltaTime);
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
        if (dog.state == Dog.DOG_STATE_SHOT)
            if (Assets.background.getVolume() <= 0.5f)
                Assets.background.setVolume(Assets.background.getVolume() + 0.015f);

        if (dog.state == Dog.DOG_STATE_HIDDEN) {
            state = WORLD_STATE_RUNNING;
            checkDucksRoundPause = true;
            gameScreen.shots = 3;
        }
    }

    private void checkDogCollision() {
        if (Gdx.input.justTouched()) {
            if (gameScreen.shots > 0 && dog.bounds.contains(touchPoint.x, touchPoint.y) && dog.stateTime < 3) {
                dog.state = Dog.DOG_STATE_SHOT;
                dog.stateTime = 0;
                if (Settings.soundEnabled) Assets.shoot.play();
                Assets.dogLaughingSnd.stop();
                Assets.duckFoundSnd.stop();
                if (Settings.soundEnabled) Assets.dogShotSound.play();

                dogShot++;
                gameScreen.shots--;
//                bulletCasings.add(new BulletCasing());

            } else if (Gdx.input.justTouched() && gameScreen.shots == 0) {
                if (Settings.soundEnabled) Assets.outOfBullets.play();
            } else if (Gdx.input.justTouched() && gameScreen.shots > 0 && !dog.bounds.contains(touchPoint.x, touchPoint.y)) {
                gameScreen.shots--;
                if (Settings.soundEnabled) Assets.shoot.play();
//                bulletCasings.add(new BulletCasing());
            }
        }
    }

    private void updateDucks(float deltaTime) {
        if (gameMode == GAME_MODE_1) {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_STANDBY)
                ducks.get(duckCount).fly();

            ducks.get(duckCount).update(deltaTime);
        } else {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_STANDBY)
                ducks.get(duckCount).fly();
            if (ducks.get(duckCount + 1).state == Duck.DUCK_STATE_STANDBY)
                ducks.get(duckCount).fly();

            ducks.get(duckCount).update(deltaTime);
            ducks.get(duckCount + 1).update(deltaTime);
        }
    }

    private void updateAnimals(float delta) {
        squirrel.update(delta);
        monkey.update(delta);
        cow.update(delta);
        pig.update(delta);
        bear.update(delta);
    }

    private void checkCollisions() {
        checkDuckCollision();
        checkAnimalsCollision();
    }

    private void checkStates() {
        checkDuckStates();
    }

    private void checkDuckCollision() {
        if (gameMode == GAME_MODE_1) {
            Duck duck = ducks.get(duckCount);
            if (Gdx.input.justTouched()) {
                if (duck.bounds.contains(touchPoint.x, touchPoint.y) && duck.state == Duck.DUCK_STATE_FLYING) {
                    duck.hit();

                    switch (gameScreen.shots) {
                        case 2:
                            gameScreen.multiplier++;
                            gameScreen.score += Duck.SCORE2 * gameScreen.multiplier;
                            break;
                        case 1:
                            gameScreen.score += Duck.SCORE1 * gameScreen.multiplier;
                            break;
                        case 0:
                            if (gameScreen.multiplier != 1)
                                gameScreen.multiplier--;
                            gameScreen.score += Duck.SCORE0 * gameScreen.multiplier;
                            break;
                    }

                    {/**Animals State*/
                        if (gameScreen.shots == 2 && gameScreen.multiplier == 3 && !squirrel.isActive()) {
                            squirrel.setState(Animal.INSTANTIATE);
                            return;
                        } else if (gameScreen.shots == 2 && gameScreen.multiplier == 5 && !monkey.isActive()) {
                            monkey.setState(Animal.INSTANTIATE);
                            return;
                        } else if (gameScreen.shots == 2 && gameScreen.multiplier == 7 && !cow.isActive()) {
                            cow.setState(Animal.INSTANTIATE);
                            return;
                        } else if (gameScreen.shots == 2 && gameScreen.multiplier == 9 && !pig.isActive()) {
                            pig.setState(Animal.INSTANTIATE);
                            return;
                        } else if (gameScreen.shots == 2 && gameScreen.multiplier == 10 && !bear.isActive()) {
                            bear.setState(Animal.INSTANTIATE);
                            return;
                        }
                    }/***/


                } else if (Gdx.input.justTouched() && gameScreen.shots == 0 && duck.state == Duck.DUCK_STATE_FLYING) {
                    duck.flyAway();
                    gameScreen.multiplier = 1;
                } else if (Gdx.input.justTouched() && gameScreen.shots == 0 && Settings.soundEnabled)
                    Assets.outOfBullets.play();
            }
        } else {
            Duck duck = ducks.get(duckCount);
            int shot = 0;
            if (Gdx.input.justTouched() && duck.bounds.contains(touchPoint.x, touchPoint.y) && duck.state == Duck.DUCK_STATE_FLYING) {
                duck.hit();

                //two ducks one stone
                shot++;

                switch (gameScreen.shots) {
                    case 2:
                        gameScreen.multiplier++;
                        gameScreen.score += Duck.SCORE2 * gameScreen.multiplier;
                        break;
                    case 1:
                        gameScreen.score += Duck.SCORE1 * gameScreen.multiplier;
                        break;
                    case 0:
                        if (gameScreen.multiplier != 1)
                            gameScreen.multiplier--;
                        gameScreen.score += Duck.SCORE0 * gameScreen.multiplier;
                        break;
                }

            } else if (Gdx.input.justTouched() && gameScreen.shots == 0 && duck.state == Duck.DUCK_STATE_FLYING) {
                duck.flyAway();
                gameScreen.multiplier = 1;
            } else if (Gdx.input.justTouched() && gameScreen.shots == 0)
                Assets.outOfBullets.play();

            Duck duck2 = ducks.get(duckCount + 1);
            if (Gdx.input.justTouched() && duck2.bounds.contains(touchPoint.x, touchPoint.y) && duck2.state == Duck.DUCK_STATE_FLYING) {
                duck2.hit();

                shot++;

                /**two ducks one stone*/
                if (shot == 2) game.actionResolver.unlockAchievementGPGS("CgkIm6zBrtUEEAIQAw");

                switch (gameScreen.shots) {
                    case 2:
                        gameScreen.multiplier++;
                        gameScreen.score += Duck.SCORE2 * gameScreen.multiplier;
                        break;
                    case 1:
                        gameScreen.score += Duck.SCORE1 * gameScreen.multiplier;
                        break;
                    case 0:
                        if (gameScreen.multiplier != 1)
                            gameScreen.multiplier--;
                        gameScreen.score += Duck.SCORE0 * gameScreen.multiplier;
                        break;
                }

            } else if (Gdx.input.justTouched() && gameScreen.shots == 0 && duck2.state == Duck.DUCK_STATE_FLYING) {
                duck2.flyAway();
                gameScreen.multiplier = 1;
            }
        }
    }

    private void checkAnimalsCollision() {
        if (Gdx.input.justTouched()) {
            if (squirrel.bounds.contains(touchPoint.x, touchPoint.y)) {
                squirrel.hit();
                return;
            } else if (monkey.bounds.contains(touchPoint.x, touchPoint.y)) {
                monkey.hit();
                return;
            } else if (cow.bounds.contains(touchPoint.x, touchPoint.y)) {
                cow.hit();
                return;
            } else if (pig.bounds.contains(touchPoint.x, touchPoint.y)) {
                pig.hit();
                return;
            } else if (bear.bounds.contains(touchPoint.x, touchPoint.y)) {
                bear.hit();
                return;
            }
        }
    }

    private void checkDuckStates() {
        if (gameMode == GAME_MODE_1) {
            if (ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD || ducks.get(duckCount).state == Duck.DUCK_STATE_GONE) {
                state = WORLD_STATE_ROUND_PAUSE;

                if (ducks.get(duckCount).state == Duck.DUCK_STATE_GONE)
                    gameScreen.multiplier = 1;
            }
        } else if ((ducks.get(duckCount).state == Duck.DUCK_STATE_DEAD || ducks.get(duckCount).state == Duck.DUCK_STATE_GONE) &&
                (ducks.get(duckCount + 1).state == Duck.DUCK_STATE_DEAD || ducks.get(duckCount + 1).state == Duck.DUCK_STATE_GONE)) {
            state = WORLD_STATE_ROUND_PAUSE;

            if (ducks.get(duckCount).state == Duck.DUCK_STATE_GONE || ducks.get(duckCount + 1).state == Duck.DUCK_STATE_GONE)
                gameScreen.multiplier = 1;
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
        gameScreen.round++;
    }

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
        worldRenderer.setLevel(this.level);
        level.setOrthographicCamera(worldRenderer.gameCam);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Can be used if World extends actor
     * so can add the listener and catch the click
     * instead of using Gdx.input.justTouched(),
     * and it also catches the real position
     */
    private class WorldClickListener extends ClickListener {
        @Override
        public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {

        }
    }
}