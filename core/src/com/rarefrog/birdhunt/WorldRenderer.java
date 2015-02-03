package com.rarefrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rarefrog.birdhunt.entities.Dog;
import com.rarefrog.birdhunt.entities.Duck;
import com.rarefrog.birdhunt.levels.GreenMeadows;
import com.rarefrog.birdhunt.levels.Level;

public class WorldRenderer extends Actor {

    private World world;
    public OrthographicCamera gameCam;
    private SpriteBatch batch;
    private float horizontalPosition = 0;
    private Level level;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.world = world;
        this.gameCam = new OrthographicCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        this.gameCam.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        this.batch = batch;
        level = new GreenMeadows(gameCam);
    }

    public void setHorizontalPosition(float x) {
        level.update(-x);
        horizontalPosition = -x;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        clearScreen();

        gameCam.update();

        batch.setProjectionMatrix(gameCam.combined);
        batch.enableBlending();
        level.drawAndUpdateSpecifics(Gdx.graphics.getDeltaTime(), (SpriteBatch) batch);
        renderBackgroundBack();
        if (world.dog.state == Dog.DOG_STATE_WALKING
                || world.dog.state == Dog.DOG_STATE_FOUND
                || world.dog.state == Dog.DOG_STATE_JUMPING
                || world.dog.state == Dog.DOG_STATE_WALKING_NEW_ROUND) {
            renderBackground();
            renderObjects();
        } else {
            renderObjects();
            renderBackground();
            renderAnimals();
        }
        renderSpriteObjects((SpriteBatch) batch);
    }

    private void renderSpriteObjects(SpriteBatch batch) {
//        world.viewmodel.render(batch);
//        for (BulletCasing b : world.bulletCasings) {
//            b.render(batch);
//        }
    }

    // also draws the background color
    private void clearScreen() {
        if (world.gameMode == World.GAME_MODE_1) {
            if (!(world.ducks.get(world.duckCount).state == Duck.DUCK_STATE_FLY_AWAY))
                Gdx.gl.glClearColor(0.392156f, 0.686274f, 1, 1);
            else
                Gdx.gl.glClearColor(1, 0.823529f, 0.3764705f, 1);
        } else {
            if (!(world.ducks.get(world.duckCount).state == Duck.DUCK_STATE_FLY_AWAY)
                    || !(world.ducks.get(world.duckCount + 1).state == Duck.DUCK_STATE_FLY_AWAY))
                Gdx.gl.glClearColor(0.392156f, 0.686274f, 1, 1);
            else
                Gdx.gl.glClearColor(1, 0.823529f, 0.3764705f, 1);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderBackgroundBack() {
        level.drawBackgrounds(batch);
    }

    private void renderBackground() {
        level.drawForeGround(batch);
    }

    private void renderObjects() {
        renderDog();
        renderDucks();
    }

    private void renderDog() {
        if (world.dog.texture != null)
            batch.draw(world.dog.texture, world.dog.position.x - horizontalPosition, world.dog.position.y, Dog.DOG_WIDTH, Dog.DOG_HEIGHT);
    }

    private void renderDucks() {
        int len = world.ducks.size();
        for (int i = 0; i < len; i++) {
            Duck duck = world.ducks.get(i);

            TextureRegion texture = duck.texture;

            if (texture != null)
                if (duck.velocity.x >= 0)
                    batch.draw(texture, duck.bounds.x - horizontalPosition, duck.bounds.y, Duck.DUCK_WIDTH, Duck.DUCK_HEIGHT);
                else
                    batch.draw(texture, duck.bounds.x + Duck.DUCK_WIDTH - horizontalPosition, duck.bounds.y, -Duck.DUCK_WIDTH, Duck.DUCK_HEIGHT);
        }
    }

    private void renderAnimals() {
        if (world.squirrel.texture != null) {
            TextureRegion texture = world.squirrel.texture;
            Vector2 position = world.squirrel.position;
            Rectangle bounds = world.squirrel.bounds;

            batch.draw(texture, position.x - horizontalPosition, position.y, bounds.width, bounds.height);
        }

        if (world.monkey.texture != null) {
            TextureRegion texture = world.monkey.texture;
            Vector2 position = world.monkey.position;
            Rectangle bounds = world.monkey.bounds;

            batch.draw(texture, position.x - horizontalPosition, position.y, bounds.width, bounds.height);
        }

        if (world.cow.texture != null) {
            TextureRegion texture = world.cow.texture;
            Vector2 position = world.cow.position;
            Rectangle bounds = world.cow.bounds;

            batch.draw(texture, position.x - horizontalPosition, position.y, bounds.width, bounds.height);
        }

        if (world.pig.texture != null) {
            TextureRegion texture = world.pig.texture;
            Vector2 position = world.pig.position;
            Rectangle bounds = world.pig.bounds;

            batch.draw(texture, position.x - horizontalPosition, position.y, bounds.width, bounds.height);
        }

        if (world.bear.texture != null) {
            TextureRegion texture = world.bear.texture;
            Vector2 position = world.bear.position;
            Rectangle bounds = world.bear.bounds;

            batch.draw(texture, position.x - horizontalPosition, position.y, bounds.width, bounds.height);
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}