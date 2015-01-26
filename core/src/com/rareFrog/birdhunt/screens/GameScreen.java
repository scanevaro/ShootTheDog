package com.rareFrog.birdhunt.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rareFrog.birdhunt.*;
import com.rareFrog.birdhunt.entities.Dog;
import com.rareFrog.birdhunt.entities.Duck;
import com.rareFrog.birdhunt.input.Controls;
import com.rareFrog.birdhunt.input.GameInputProcessor;
import com.rareFrog.birdhunt.spriteobjects.BulletCasing;

public class GameScreen extends AbstractScreen {

    private final int GAME_READY = 0;
    private final int GAME_RUNNING = 1;
    private final int GAME_OVER_1 = 2;
    private final int GAME_OVER_2 = 3;

    private Game game;

    private int state;
    private float stateTime;
    private OrthographicCamera guiCam;
    private SpriteBatch batch;
    public World world;
    private WorldRenderer renderer;
    public static int round;
    public static int shots;
    private int x;
    private int lastScore;
    private String scoreString;
    private int gameMode;
    public int score, multiplier;
    public Controls controls;

    private Sprite multiplierSprite;
    private TweenManager tweenManager;
    /**
     * Widgets
     */
    private ImageButton pauseButton;
    private Label compass;

    public GameScreen(Game game, int gameMode) {
        controls = new Controls();
        this.game = game;
        this.gameMode = gameMode;
        controls.calibrate();
        round = 1;
        guiCam = new OrthographicCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        guiCam.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch);
        world = new World(game, this, gameMode);
        renderer = new WorldRenderer(batch, world);
        world.setWorldRenderer(renderer);
        world.setStage(stage);
        stage.addActor(renderer);

        setWidgets();
        addListeners();

        if (Settings.soundEnabled) Assets.startRound.play();

        state = GAME_READY;
        stateTime = 0;
        shots = 3;
        lastScore = 0;
        scoreString = "000000";

        game.dialogOpen = false;

        /**Set input processor*/
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GameInputProcessor(game)));
    }

    private void setWidgets() {
        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.imageUp = new TextureRegionDrawable(Assets.pauseButton);
        pauseButton = new ImageButton(pauseStyle);
        pauseButton.setSize(21, 21);
        pauseButton.setPosition(Game.VIRTUAL_WIDTH - pauseButton.getWidth() - 5, Game.VIRTUAL_HEIGHT - pauseButton.getHeight() - 5);
        stage.addActor(pauseButton);

        compass = new Label("", Assets.skin);
        compass.setPosition(0, 128 + 5);
        stage.addActor(compass);

        {/**Multiplier Tween*/
            tweenManager = new TweenManager();
            Tween.registerAccessor(Sprite.class, new SpriteAccessor());
            multiplierSprite = new Sprite(Assets.multiplier1);
            multiplierSprite.setSize(64, 64);
            multiplierSprite.setPosition(0, Game.VIRTUAL_HEIGHT - multiplierSprite.getHeight());

            Tween.to(multiplierSprite, SpriteAccessor.SCALE_XY, 0.6f)
                    .ease(Back.IN)
                    .target(0.9f, 0.9f)
                    .repeatYoyo(-1, 0.0f)
                    .start(tweenManager);
        }
    }

    private void addListeners() {
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogs.update(game.screen);
            }
        });
    }

    public void update(float delta) {
        if (delta > 0.1f)
            delta = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            // case ROUND_START
            // case COUNT_DUCKS
            // case NEXT_ROUND
            case GAME_RUNNING:
                updateRunning();
                break;
            case GAME_OVER_1:
                updateGameOver1();
                break;
            case GAME_OVER_2:
                updateGameOver2();
                break;
        }

        if (!game.dialogOpen) {
            tweenManager.update(delta);
            world.update(delta);
            stage.act();
        }
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

    private void updateRunning() {
        switch (world.state) {
            case World.WORLD_STATE_RUNNING:
                if (!game.dialogOpen && Gdx.input.justTouched()) {
                    if (shots > 0) {
                        if (Settings.soundEnabled) Assets.shoot.play();
                        shots--;
                        world.bulletCasings.add(new BulletCasing());
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
                game.requestHandler.showAds(true);
                break;
        }

        updateScore();
    }

    private void updateScore() {
        if (score != lastScore) {
            lastScore = score;

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
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            game.requestHandler.showInterstitial(true);
        }
    }

    public void draw() {
        stage.draw();

        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);
        batch.enableBlending();
        batch.begin();

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

        {/**Multiplier*/
            multiplierSprite.draw(batch);
            switch (multiplier) {
                case 1:
                    multiplierSprite.setRegion(Assets.multiplier1);
                    break;
                case 2:
                    multiplierSprite.setRegion(Assets.multiplier2);
                    break;
                case 3:
                    multiplierSprite.setRegion(Assets.multiplier3);
                    break;
                case 4:
                    multiplierSprite.setRegion(Assets.multiplier4);
                    break;
                case 5:
                    multiplierSprite.setRegion(Assets.multiplier5);
                    break;
                case 6:
                    multiplierSprite.setRegion(Assets.multiplier6);
                    break;
                case 7:
                    multiplierSprite.setRegion(Assets.multiplier7);
                    break;
                case 8:
                    multiplierSprite.setRegion(Assets.multiplier8);
                    break;
                case 9:
                    multiplierSprite.setRegion(Assets.multiplier9);
                    break;
                case 10:
                    multiplierSprite.setRegion(Assets.multiplier10);
                    break;
            }
        }


        batch.end();
        //P for processed, R for raw and C for calibrated
        controls.update((float) Math.toDegrees(this.game.inputInterface.getRotation()[0]));
        renderer.setHorizontalPosition(controls.getCalibratedValue());
        compass.setText("P: " + (int) controls.getAzimuthValue() + " R: " + (int) controls.getRawValue() + " C: " + (int) controls.getCalibratedValue());
        // renderer.gameCam.position.x = controls.getCalibratedValue() * 10 + 240;
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

        batch.draw(
                texture,
                40,
                20,
                Assets.UISHOTSWIDTH + Assets.UISHOTSWIDTH / 2,
                Assets.UISHOTSHEIGHT + Assets.UISHOTSHEIGHT / 2);

//        batch.draw(
//                Assets.uiDucksRound,
//                480 / 2 - Assets.uiDucksRound.getRegionWidth() / 2 - 30,
//                20,
//                Assets.uiDucksRound.getRegionWidth() * 2 - Assets.uiDucksRound.getRegionWidth() / 2,
//                Assets.uiDucksRound.getRegionHeight() + Assets.uiDucksRound.getRegionHeight() / 2);

        x = -88;
        for (int i = 0; i < world.ducks.size(); i++) {
            TextureRegion uiDuck = world.ducks.get(i).uiTexture;
            if (uiDuck != null) {
                batch.draw(
                        uiDuck,
                        Game.VIRTUAL_WIDTH / 2 - 22 + x,
                        21,
                        22,
                        22);
            }

            x += 22;
        }

        batch.draw(
                Assets.uiScore,
                480 - 100,
                20,
                Assets.UISCOREWIDTH + Assets.UISCOREWIDTH / 2,
                Assets.UISCOREHEIGHT + Assets.UISCOREHEIGHT / 2);

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(0.59f, 0.59f);
        Assets.font.draw(batch, scoreString, 480 - 95, 48);

        batch.draw(Assets.presentFlyAway, 41, 54, 41, 15);

        Assets.font.setColor(0.4f, 0.8f, 0.2f, 1);
        Assets.font.draw(batch, "R " + String.valueOf(round), 48, 66);
    }

    private void presentReady() {
        if (!game.dialogOpen) {
            batch.draw(
                    Assets.presentRound,
                    Game.VIRTUAL_WIDTH / 2 - Assets.presentRound.getRegionWidth(),
                    Game.VIRTUAL_HEIGHT / 2 + 30,
                    Assets.presentRound.getRegionWidth() + Assets.presentRound.getRegionWidth(),
                    Assets.presentRound.getRegionHeight() + Assets.presentRound.getRegionHeight());

            Assets.font.setColor(Color.WHITE);
            Assets.font.setScale(0.5f, 0.5f);
            Assets.font.draw(batch, "Round", Game.VIRTUAL_WIDTH / 2 - Assets.presentRound.getRegionWidth() / 2 - 10, Game.VIRTUAL_HEIGHT / 2 + 64);
            Assets.font.draw(batch, String.valueOf(round), Game.VIRTUAL_WIDTH / 2 - Assets.font.getSpaceWidth() + 4, Game.VIRTUAL_HEIGHT / 2 + 45);
        }
    }

    private void presentRunning() {
        if (world.ducks.get(world.duckCount).state == Duck.DUCK_STATE_FLY_AWAY) {
            batch.draw(Assets.presentFlyAway,
                    480 / 2 - Assets.presentFlyAway.getRegionWidth(),
                    320 / 2 + 30, Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth(),
                    Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

            Assets.font.setColor(Color.WHITE);
            Assets.font.setScale(0.45f, 0.5f);
            Assets.font.draw(batch, "FLY AWAY", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 15, Game.VIRTUAL_HEIGHT / 2 + 45);
        }

        if (world.state == World.WORLD_STATE_PERFECT_ROUND) {
            if (stateTime < 5)
                presentRoundEnd();

            if (stateTime > 5) {
                batch.draw(
                        Assets.presentFlyAway,
                        Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth(),
                        Game.VIRTUAL_HEIGHT / 2 + 30, Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth(),
                        Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

                Assets.font.setColor(Color.WHITE);
                Assets.font.setScale(0.45f, 0.5f);
                Assets.font.draw(batch, "Perfect", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 15, Game.VIRTUAL_HEIGHT / 2 + 45);
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
        batch.draw(Assets.presentFlyAway, 480 / 2 - Assets.presentFlyAway.getRegionWidth() - 5, 320 / 2 + 30,
                Assets.presentFlyAway.getRegionWidth() + Assets.presentFlyAway.getRegionWidth() + 12,
                Assets.presentFlyAway.getRegionHeight() + Assets.presentFlyAway.getRegionHeight());

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(0.45f, 0.5f);
        Assets.font.draw(batch, "GAME OVER", Game.VIRTUAL_WIDTH / 2 - Assets.presentFlyAway.getRegionWidth() / 2 - 20, Game.VIRTUAL_HEIGHT / 2 + 45);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();

        if (!game.dialogOpen) stateTime += delta;
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
        stage.dispose();
    }
}