package com.rareFrog.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rareFrog.game.Game;
import com.rareFrog.game.classes.Assets;
import com.rareFrog.game.classes.Settings;
import com.rareFrog.game.classes.World;
import com.rareFrog.game.classes.WorldRenderer;
import com.rareFrog.game.entities.Dog;
import com.rareFrog.game.entities.Duck;

public class GameScreen implements Screen {

    private final int GAME_READY = 0;
    private final int GAME_RUNNING = 1;
    private final int GAME_OVER_1 = 2;
    private final int GAME_OVER_2 = 3;

    private Game game;

    private int state;
    private float stateTime;
    private OrthographicCamera guiCam;
    private Stage stage;
    private SpriteBatch batcher;
    private World world;
    private World.WorldListener worldListener;
    private WorldRenderer renderer;
    public static int round;
    public static int shots;
    private int x;
    private int lastScore;
    private String scoreString;
    private int gameMode;

    public GameScreen(Game game, int gameMode) {
        this.game = game;
        this.gameMode = gameMode;

        round = 1;
        guiCam = new OrthographicCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        guiCam.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        batcher = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batcher);
        worldListener = new World.WorldListener() {
            @Override
            public void reload() {
            }

            @Override
            public void shoot() {
                if (Settings.soundEnabled) Assets.shoot.play();
            }

            @Override
            public void ducks() {
            }
        };
        world = new World(worldListener, game, gameMode);
        renderer = new WorldRenderer(batcher, world);
        world.setWorldRenderer(renderer);
        world.setStage(stage);
        stage.addActor(renderer);

//        if (Settings.soundEnabled) Assets.startRound.play();
        if (Settings.soundEnabled) {
            Assets.startRound.play();
//            Assets.background.play();
//            Assets.background.setLooping(true);
        }

        state = GAME_READY;
        stateTime = 0;
        shots = 3;
        lastScore = 0;
        scoreString = "000000";

        Gdx.input.setInputProcessor(stage);
    }

    public void update(float deltaTime) {
        if (deltaTime > 0.1f)
            deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            // case ROUND_START
            // case COUNT_DUCKS
            // case NEXT_ROUND
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_OVER_1:
                updateGameOver1();
                break;
            case GAME_OVER_2:
                updateGameOver2();
                break;
        }

        world.update(deltaTime);
        stage.act();
    }

    private void updateReady() {
//        if (Settings.soundEnabled) {
//            if (!Assets.startRound.isPlaying() && !Assets.background.isPlaying()) {
//                Assets.background.play();
//                Assets.background.setLooping(true);
//            }
//        }

        if (world.dog.state == Dog.DOG_STATE_HIDDEN) {
            state = GAME_RUNNING;

            if (Settings.soundEnabled) {
                Assets.background.play();
                Assets.background.setLooping(true);
            }
        }
    }

    private void updateRunning(float deltaTime) {
        switch (world.state) {
            case World.WORLD_STATE_RUNNING:
                if (Gdx.input.justTouched()) {
                    if (shots > 0) {
                        if (Settings.soundEnabled) Assets.shoot.play();

                        shots--;
                    }
                }
                break;
            case World.WORLD_STATE_ROUND_PAUSE:
                stateTime = 0;
                break;
            case World.WORLD_STATE_COUNTING_DUCKS:
                stateTime = 0;
                break;
            case World.WORLD_STATE_ROUND_START:
                state = GAME_READY;
                break;
            case World.WORLD_STATE_GAME_OVER_1:
                stateTime = 0;
                state = GAME_OVER_1;
                if (Settings.soundEnabled) Assets.gameOver1.play();
                break;
        }

        updateScore();
    }

    private void updateScore() {
        if (world.score != lastScore) {
            lastScore = world.score;

            if (String.valueOf(lastScore).length() == 3)
                scoreString = "000" + String.valueOf(lastScore);
            else if (String.valueOf(lastScore).length() == 4)
                scoreString = "00" + String.valueOf(lastScore);
            else if (String.valueOf(lastScore).length() == 5)
                scoreString = "0" + String.valueOf(lastScore);
            else
                scoreString = String.valueOf(lastScore);
        }
    }

    private void updateGameOver1() {
        if (stateTime > 3) {
            state = GAME_OVER_2;

            if (Assets.background.isPlaying()) Assets.background.stop();
            if (Settings.soundEnabled) Assets.gameOver2.play();

            if (gameMode == World.GAME_MODE_1)
                Settings.addScoreA(lastScore);
            else
                Settings.addScoreB(lastScore);
            Settings.save();
        }
    }

    private void updateGameOver2() {
        if (Gdx.input.justTouched()) game.setScreen(new MainMenuScreen(game));
    }

    public void draw() {
//        renderer.render();
        stage.draw();

        guiCam.update();
        batcher.setProjectionMatrix(guiCam.combined);
        batcher.enableBlending();
        batcher.begin();

        drawUI();

        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            // case ROUND_START
            // case COUNT_DUCKS
            // case NEXT_ROUND
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_OVER_1:
            case GAME_OVER_2:
                presentGameOver();
                break;
        }

        batcher.end();
    }

    private void drawUI() {
        TextureRegion texture = null;
        switch (shots) {
            case 3:
                texture = Assets.ui3Shots;
                break;
            case 2:
                texture = Assets.ui2Shots;
                break;
            case 1:
                texture = Assets.ui1Shots;
                break;
            default:
                texture = Assets.ui0Shots;
                break;
        }

        batcher.draw(
                texture,
                40,
                20,
                Assets.UISHOTSWIDTH + Assets.UISHOTSWIDTH / 2,
                Assets.UISHOTSHEIGHT + Assets.UISHOTSHEIGHT / 2);
        batcher.draw(
                Assets.uiDucksRound,
                480 / 2 - Assets.uiDucksRound.getRegionWidth() / 2 - 30,
                20,
                Assets.uiDucksRound.getRegionWidth() * 2 - Assets.uiDucksRound.getRegionWidth() / 2,
                Assets.uiDucksRound.getRegionHeight() + Assets.uiDucksRound.getRegionHeight() / 2);

        x = 0;
        for (int i = 0; i < world.ducks.size(); i++) {
            TextureRegion uiDuck = world.ducks.get(i).uiTexture;
            if (uiDuck != null) {
                batcher.draw(
                        uiDuck,
                        480 / 2 - Assets.uiWhiteDuck.getRegionWidth() - 29 + x,
                        21, Assets.uiWhiteDuck.getRegionWidth() * 2 - Assets.uiWhiteDuck.getRegionWidth() / 2,
                        Assets.uiWhiteDuck.getRegionHeight() + Assets.uiWhiteDuck.getRegionHeight() / 2);
            }

            x += 12;
        }

        batcher.draw(
                Assets.uiScore,
                480 - 100,
                20,
                Assets.UISCOREWIDTH + Assets.UISCOREWIDTH / 2,
                Assets.UISCOREHEIGHT + Assets.UISCOREHEIGHT / 2);

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(0.59f, 0.59f);
        Assets.font.draw(batcher, scoreString, 480 - 95, 48);

        batcher.draw(Assets.presentFlyAway, 41, 54, 41, 15);

        Assets.font.setColor(0.4f, 0.8f, 0.2f, 1);
        Assets.font.draw(batcher, "R " + String.valueOf(round), 48, 66);
    }

    private void presentReady() {
        batcher.draw(
                Assets.presentRound,
                480 / 2 - Assets.presentRound.getRegionWidth(),
                320 / 2 + 30,
                Assets.presentRound.getRegionWidth() + Assets.presentRound.getRegionWidth(),
                Assets.presentRound.getRegionHeight() + Assets.presentRound.getRegionHeight());

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(0.5f, 0.5f);
        Assets.font.draw(batcher, "Round", Game.VIRTUAL_WIDTH / 2 - Assets.presentRound.getRegionWidth() / 2 - 10, Game.VIRTUAL_HEIGHT / 2 + 64);
        Assets.font.draw(batcher, String.valueOf(round), Game.VIRTUAL_WIDTH / 2 - Assets.font.getSpaceWidth() + 4, Game.VIRTUAL_HEIGHT / 2 + 45);
    }

    private void presentRunning() {
        if (world.ducks.get(world.duckCount).state == Duck.DUCK_STATE_FLY_AWAY) {
            batcher.draw(Assets.presentFlyAway,
                    480 / 2 - Assets.presentFlyAway.getRegionWidth(),
                    320 / 2 + 30, Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth(),
                    Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

            Assets.font.setColor(Color.WHITE);
            Assets.font.setScale(0.45f, 0.5f);
            Assets.font.draw(batcher, "FLY AWAY", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 15, Game.VIRTUAL_HEIGHT / 2 + 45);
        }

        if (world.state == World.WORLD_STATE_PERFECT_ROUND) {
            if (stateTime < 5)
                presentRoundEnd();

            if (stateTime > 5) {
                batcher.draw(
                        Assets.presentFlyAway,
                        Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth(),
                        Game.VIRTUAL_HEIGHT / 2 + 30, Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth(),
                        Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

                Assets.font.setColor(Color.WHITE);
                Assets.font.setScale(0.45f, 0.5f);
                Assets.font.draw(batcher, "Perfect", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 15, Game.VIRTUAL_HEIGHT / 2 + 45);
            }
        }

        if (world.state == World.WORLD_STATE_ROUND_END)
            presentRoundEnd();
    }

    private void presentRoundEnd() {
        for (int i = 0; i < world.ducks.size(); i++) {
            if (world.ducks.get(i).state == Duck.DUCK_STATE_DEAD) {
                world.ducks.get(i).uiTexture = Assets.uiDucks.getKeyFrame(
                        stateTime, true);
            }
        }
    }

    private void presentGameOver() {
        batcher.draw(Assets.presentFlyAway, 480 / 2 - Assets.presentFlyAway.getRegionWidth() - 5, 320 / 2 + 30,
                Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth() + 12,
                Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(0.45f, 0.5f);
        Assets.font.draw(batcher, "GAME OVER", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 20, Game.VIRTUAL_HEIGHT / 2 + 45);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();

        stateTime += delta;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}