package com.rareFrog.birdhunt.screens;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rareFrog.birdhunt.Game;
import com.rareFrog.birdhunt.classes.Assets;
import com.rareFrog.birdhunt.classes.SpriteAccessor;

/**
 * Created by scanevaro on 18/11/2014.
 */
public class SplashScreen implements Screen {
    private static final int PX_PER_METER = 400;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final SpriteBatch batch = new SpriteBatch();
    private final TweenManager tweenManager = new TweenManager();
    private final TweenCallback callback;
    private final Sprite universal;
    private final Sprite tween;
    private final Sprite engine;
    private final Sprite logo;
    private final Sprite strip;
    private final Sprite powered;
    private final Sprite gdx;
    private final Sprite veil;
    private final TextureRegion gdxTex;

    public SplashScreen(final Game game) {
        this.callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MainMenuScreen(game));
            }
        };

        Tween.setWaypointsLimit(10);
        Tween.setCombinedAttributesLimit(3);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        TextureAtlas atlas = Assets.atlas;
        universal = atlas.createSprite("universal");
        tween = atlas.createSprite("tween");
        engine = atlas.createSprite("engine");
        logo = atlas.createSprite("logo");
        strip = atlas.createSprite("white");
        powered = atlas.createSprite("powered");
        gdx = atlas.createSprite("gdxblur");
        veil = atlas.createSprite("white");
        gdxTex = atlas.findRegion("gdx");

        float wpw = 1f;
        float wph = wpw * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        camera.viewportWidth = wpw;
        camera.viewportHeight = wph;
        camera.update();

        Gdx.input.setInputProcessor(inputProcessor);

        Sprite[] sprites = new Sprite[]{universal, tween, engine, logo, powered, gdx};
        for (Sprite sp : sprites) {
            sp.setSize(sp.getWidth() / PX_PER_METER, sp.getHeight() / PX_PER_METER);
            sp.setOrigin(sp.getWidth() / 2, sp.getHeight() / 2);
        }

        universal.setPosition(-0.325f, 0.028f);
        tween.setPosition(-0.320f, -0.066f);
        engine.setPosition(0.020f, -0.087f);
        logo.setPosition(0.238f, 0.022f);

        strip.setSize(wpw, wph);
        strip.setOrigin(wpw / 2, wph / 2);
        strip.setPosition(-wpw / 2, -wph / 2);

        powered.setPosition(-0.278f, -0.025f);
        gdx.setPosition(0.068f, -0.077f);

        veil.setSize(wpw, wph);
        veil.setPosition(-wpw / 2, -wph / 2);
        veil.setColor(1, 1, 1, 0);

        Timeline.createSequence()
//                .push(Tween.set(tween, SpriteAccessor.POS_XY).targetRelative(-1, 0))
//                .push(Tween.set(engine, SpriteAccessor.POS_XY).targetRelative(1, 0))
//                .push(Tween.set(universal, SpriteAccessor.POS_XY).targetRelative(0, 0.5f))
//                .push(Tween.set(logo, SpriteAccessor.SCALE_XY).target(7, 7))
//                .push(Tween.set(logo, SpriteAccessor.OPACITY).target(0))
                .push(Tween.set(strip, SpriteAccessor.SCALE_XY).target(1, 0))
                .push(Tween.set(powered, SpriteAccessor.OPACITY).target(0))
                .push(Tween.set(gdx, SpriteAccessor.OPACITY).target(0))
//
                .pushPause(0.5f)
                .push(Tween.to(strip, SpriteAccessor.SCALE_XY, 0.8f).target(1, 0.6f).ease(Back.OUT))
//                .push(Tween.to(tween, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Quart.OUT))
//                .push(Tween.to(engine, SpriteAccessor.POS_XY, 0.5f).targetRelative(-1, 0).ease(Quart.OUT))
//                .push(Tween.to(universal, SpriteAccessor.POS_XY, 0.6f).targetRelative(0, -0.5f).ease(Quint.OUT))
//                .pushPause(-0.3f)
//                .beginParallel()
//                .push(Tween.set(logo, SpriteAccessor.OPACITY).target(1))
//                .push(Tween.to(logo, SpriteAccessor.SCALE_XY, 0.5f).target(1, 1).ease(Back.OUT))
//                .end()
//                .push(Tween.to(strip, SpriteAccessor.SCALE_XY, 0.5f).target(1, 1).ease(Back.IN))
//                .pushPause(0.3f)
//                .beginParallel()
//                .push(Tween.to(tween, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//                .push(Tween.to(engine, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//                .push(Tween.to(universal, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//                .push(Tween.to(logo, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//                .end()

                .pushPause(-0.3f)
                .push(Tween.to(powered, SpriteAccessor.OPACITY, 0.3f).target(1))
                .beginParallel()
                .push(Tween.to(gdx, SpriteAccessor.OPACITY, 1.5f).target(1).ease(Cubic.IN))
                .push(Tween.to(gdx, SpriteAccessor.ROTATION, 2.0f).target(360 * 15).ease(Quad.OUT))
                .end()
                .pushPause(0.3f)
                .push(Tween.to(gdx, SpriteAccessor.SCALE_XY, 0.6f).waypoint(1.6f, 0.4f).target(1.2f, 1.2f).ease(Cubic.OUT))
                .pushPause(0.3f)
                .beginParallel()
                .push(Tween.to(powered, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
                .push(Tween.to(gdx, SpriteAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
                .end()
                .pushPause(0.3f)

                .setCallback(callback)
                .start(tweenManager);
    }

    @Override
    public void resize(int width, int height) {

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

    public void dispose() {
        tweenManager.killAll();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        tweenManager.update(delta);

        if (gdx.getRotation() > 360 * 15 - 20) gdx.setRegion(gdxTex);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        strip.draw(batch);
//        universal.draw(batch);
//        tween.draw(batch);
//        engine.draw(batch);
//        logo.draw(batch);
        powered.draw(batch);
        gdx.draw(batch);
        if (veil.getColor().a > 0.1f) veil.draw(batch);
        batch.end();
    }

    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            Tween.to(veil, SpriteAccessor.OPACITY, 0.7f)
                    .target(1)
                    .setCallback(callback)
                    .start(tweenManager);
            return true;
        }
    };
}