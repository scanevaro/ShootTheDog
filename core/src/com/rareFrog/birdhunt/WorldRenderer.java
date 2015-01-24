package com.rareFrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rareFrog.birdhunt.entities.Dog;
import com.rareFrog.birdhunt.entities.Duck;

public class WorldRenderer extends Actor {

    private World world;
    public OrthographicCamera gameCam;
    private SpriteBatch batch;
    private float horizontalPosition = 0;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.world = world;
        this.gameCam = new OrthographicCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        this.gameCam.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        this.batch = batch;
    }

    public void setHorizontalPosition(float x) {
        this.horizontalPosition = -x;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        clearScreen();

        gameCam.update();

        batch.setProjectionMatrix(gameCam.combined);
        batch.enableBlending();

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
        }
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
        batch.draw(Assets.backgroundBackRegion, Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
        batch.draw(Assets.backgroundBackRegion, gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
        batch.draw(Assets.backgroundBackRegion, -Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
    }

    private void renderBackground() {
        batch.draw(Assets.backgroundRegion, Game.VIRTUAL_WIDTH + gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
        batch.draw(Assets.backgroundRegion, gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
        batch.draw(Assets.backgroundRegion, Game.VIRTUAL_WIDTH - gameCam.position.x - (Game.VIRTUAL_WIDTH / 2) - horizontalPosition,
                gameCam.position.y - Game.VIRTUAL_HEIGHT / 2, Game.VIRTUAL_WIDTH,
                Game.VIRTUAL_HEIGHT);
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
}