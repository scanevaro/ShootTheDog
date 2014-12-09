package com.rareFrog.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static Skin skin;

    public static TextureAtlas atlas;
    public static TextureAtlas items;

    public static TextureRegion backgroundRegion;
    public static TextureRegion backgroundBackRegion;
    public static TextureRegion pause;
    public static TextureRegion ready;
    public static TextureRegion duckHit;
    public static TextureRegion title;
    public static TextureRegion gameMode1;
    public static TextureRegion gameMode2;
    public static TextureRegion menuCursor;
    public static TextureRegion dogFound;
    public static TextureRegion uiShot;
    public static TextureRegion uiDucksRound;
    public static TextureRegion uiScore;
    public static TextureRegion presentRound;
    public static TextureRegion ui3Shots;
    public static TextureRegion ui2Shots;
    public static TextureRegion ui1Shots;
    public static TextureRegion uiWhiteDuck;
    public static TextureRegion uiRedDuck;
    public static TextureRegion dogDuckFound;
    public static TextureRegion dogDucksFound;
    public static TextureRegion presentFlyAway;
    public static TextureRegion ui0Shots;
    public static TextureRegion duckFallingBlue;
    public static TextureRegion duckFallingBlack;
    public static TextureRegion duckFallingRed;
    public static TextureRegion duckHitBlue;
    public static TextureRegion duckHitBlack;
    public static TextureRegion duckHitRed;

    //    public static Texture menuBackground;
    public static Texture soundIconUp;
    public static Texture soundIconDown;
    public static Texture play1ButtonUp;
    public static Texture play1ButtonDown;
    public static Texture play2ButtonUp;
    public static Texture play2ButtonDown;
    public static Texture aboutButtonUp;
    public static Texture aboutButtonDown;
    public static Texture closeButtonDown;
    public static Texture closeButtonUp;

    public static Animation duckFlyRightBlue;
    public static Animation duckFlyRightBlack;
    public static Animation duckFlyRightRed;
    public static Animation duckFlyTopBlue;
    public static Animation duckFlyTopBlack;
    public static Animation duckFlyTopRed;
    public static Animation duckFlyUpBlue;
    public static Animation duckFlyUpBlack;
    public static Animation duckFlyUpRed;
    public static Animation dogWalking;
    public static Animation dogJumping;
    public static Animation dogLaughing;
    public static Animation uiDucks;
    public static Animation dogShot;

    public static BitmapFont font;

    public static Music menuIntro;
    public static Music startRound;
    public static Music endRound;
    public static Music gameOver1;
    public static Music gameOver2;
    public static Music background;

    public static Sound shoot;
    public static Sound dogBark;
    public static Sound miss;
    public static Sound dogLaughingSnd;
    public static Sound dogDuckFoundSnd;
    public static Sound hitGround;
    public static Sound duckFallingSnd;
    public static Sound cuak;
    public static Sound[] movingDucksArray;
    public static Sound perfect;
    public static Sound outOfBullets;

    public static void load() {
        loadSkin();

        loadAtlas();

        loadTextures();

        loadFont();

        loadSounds();
    }

    private static void loadSkin() {
        skin = new Skin();
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    private static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/wonder.ttf"));
        font = generator.generateFont(20);

        skin.add("gameFont", font, BitmapFont.class);

        generator.dispose();
    }

    private static void loadAtlas() {
        atlas = new TextureAtlas(Gdx.files.internal("data/splash/pack"));
        items = new TextureAtlas(Gdx.files.internal("data/items.pack"),
                Gdx.files.internal("data"));
    }

    private static void loadTextures() {
        backgroundRegion = items.findRegion("grass1280");
        backgroundBackRegion = items.findRegion("wallpaper1280");
        dogWalking = new Animation(0.15f,
                (items.findRegion("dogWalking1")),
                (items.findRegion("dogWalking2")),
                (items.findRegion("dogWalking3")));
        dogFound = items.findRegion("dogFound");
        dogJumping = new Animation(0.8f,
                (items.findRegion("dogJump1")),
                (items.findRegion("dogJump2")));
        dogLaughing = new Animation(0.1f,
                (items.findRegion("dogLaugh1")),
                (items.findRegion("dogLaugh2")));
        duckFlyRightBlue = new Animation(0.2f,
                (items.findRegion("duckFlyRightBlue1")),
                (items.findRegion("duckFlyRightBlue2")),
                (items.findRegion("duckFlyRightBlue3")));
        duckFlyRightBlack = new Animation(0.2f,
                (items.findRegion("duckFlyRightBlack1")),
                (items.findRegion("duckFlyRightBlack2")),
                (items.findRegion("duckFlyRightBlack3")));
        duckFlyRightRed = new Animation(0.2f,
                (items.findRegion("duckFlyRightRed1")),
                (items.findRegion("duckFlyRightRed2")),
                (items.findRegion("duckFlyRightRed3")));
        duckFlyTopBlue = new Animation(0.2f,
                (items.findRegion("duckFlyTopBlue1")),
                (items.findRegion("duckFlyTopBlue2")),
                (items.findRegion("duckFlyTopBlue3")));
        duckFlyTopBlack = new Animation(0.2f,
                (items.findRegion("duckFlyTopBlack1")),
                (items.findRegion("duckFlyTopBlack2")),
                (items.findRegion("duckFlyTopBlack3")));
        duckFlyTopRed = new Animation(0.2f,
                (items.findRegion("duckFlyTopRed1")),
                (items.findRegion("duckFlyTopRed2")),
                (items.findRegion("duckFlyTopRed3")));
        duckFlyUpBlue = new Animation(0.2f,
                (items.findRegion("duckFlyUpBlue1")),
                (items.findRegion("duckFlyUpBlue2")),
                (items.findRegion("duckFlyUpBlue3")));
        duckFlyUpBlack = new Animation(0.2f,
                (items.findRegion("duckFlyUpBlack1")),
                (items.findRegion("duckFlyUpBlack2")),
                (items.findRegion("duckFlyUpBlack3")));
        duckFlyUpRed = new Animation(0.2f,
                (items.findRegion("duckFlyUpRed1")),
                (items.findRegion("duckFlyUpRed2")),
                (items.findRegion("duckFlyUpRed3")));
        uiDucks = new Animation(0.2f,
                (items.findRegion("uiWhiteDuck")),
                (items.findRegion("uiRedDuck")));
        dogShot = new Animation(0.2f,
                new TextureRegion(new Texture(Gdx.files.internal("data/dogShot0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("data/dogShot1.png"))));

        duckFallingBlue = items.findRegion("duckFallingBlue");
        duckFallingBlack = items.findRegion("duckFallingBlack");
        duckFallingRed = items.findRegion("duckFallingRed");
        duckHitBlue = items.findRegion("duckHitBlue");
        duckHitBlack = items.findRegion("duckHitBlack");
        duckHitRed = items.findRegion("duckHitRed");

        ui0Shots = items.findRegion("ui0Shots1");
        duckHit = items.findRegion("duckHit");
        title = items.findRegion("title");
        gameMode1 = items.findRegion("gameMode1");
        gameMode2 = items.findRegion("gameMode2");
        menuCursor = items.findRegion("menuCursor");
        uiShot = items.findRegion("uiShot");
        uiDucksRound = items.findRegion("uiDucksRound");
        uiScore = items.findRegion("uiScore");
        presentRound = items.findRegion("presentRound");
        presentFlyAway = items.findRegion("presentFlyAway");
        ui3Shots = items.findRegion("ui3Shots");
        ui2Shots = items.findRegion("ui2Shots");
        ui1Shots = items.findRegion("ui1Shots");
        uiWhiteDuck = items.findRegion("uiWhiteDuck");
        uiRedDuck = items.findRegion("uiRedDuck");
        dogDuckFound = items.findRegion("dog1Duck");
        dogDucksFound = items.findRegion("dog2Ducks");

//        menuBackground = new Texture(Gdx.files.internal("data/images/menuBackground.png"));
        soundIconUp = new Texture(Gdx.files.internal("data/images/soundIconUp.png"));
        soundIconDown = new Texture(Gdx.files.internal("data/images/soundIconDown.png"));
        play1ButtonUp = new Texture(Gdx.files.internal("data/images/play1ButtonUp.png"));
        play1ButtonDown = new Texture(Gdx.files.internal("data/images/play1ButtonDown.png"));
        play2ButtonUp = new Texture(Gdx.files.internal("data/images/play2ButtonUp.png"));
        play2ButtonDown = new Texture(Gdx.files.internal("data/images/play2ButtonDown.png"));
        aboutButtonUp = new Texture(Gdx.files.internal("data/images/aboutButtonUp.png"));
        aboutButtonDown = new Texture(Gdx.files.internal("data/images/aboutButtonDown.png"));
        closeButtonUp = new Texture(Gdx.files.internal("data/images/closeButtonUp.png"));
        closeButtonDown = new Texture(Gdx.files.internal("data/images/closeButtonDown.png"));
    }

    private static void loadSounds() {
        menuIntro = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/DuckHunt.mp3"));
        menuIntro.setLooping(false);
        menuIntro.setVolume(0.5f);
        startRound = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/start_round.mp3"));
        endRound = Gdx.audio.newMusic((Gdx.files.internal("data/sounds/end_round.mp3")));
        gameOver1 = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/gameOver1.mp3"));
        gameOver2 = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/gameOver2.mp3"));
        background = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/music/background1.mp3"));

        shoot = Gdx.audio.newSound(Gdx.files.internal("data/sounds/blast.mp3"));
        dogBark = Gdx.audio.newSound(Gdx.files.internal("data/sounds/bark.mp3"));
        miss = Gdx.audio.newSound(Gdx.files.internal("data/sounds/miss.mp3"));
        dogLaughingSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/laugh.mp3"));
        dogDuckFoundSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/end_duck_round.mp3"));
        hitGround = Gdx.audio.newSound(Gdx.files.internal("data/sounds/drop.mp3"));
        duckFallingSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/duck_falling.mp3"));
        cuak = Gdx.audio.newSound(Gdx.files.internal("data/sounds/cuak.mp3"));
        perfect = Gdx.audio.newSound(Gdx.files.internal("data/sounds/perfect.mp3"));
        outOfBullets = Gdx.audio.newSound(Gdx.files.internal("data/sounds/outOfBullets.mp3"));

        movingDucksArray = new Sound[10];
        movingDucksArray[0] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck0.mp3"));
        movingDucksArray[1] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck1.mp3"));
        movingDucksArray[2] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck2.mp3"));
        movingDucksArray[3] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck3.mp3"));
        movingDucksArray[4] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck4.mp3"));
        movingDucksArray[5] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck5.mp3"));
        movingDucksArray[6] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck6.mp3"));
        movingDucksArray[7] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck7.mp3"));
        movingDucksArray[8] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck8.mp3"));
        movingDucksArray[9] = Gdx.audio.newSound(Gdx.files.internal("data/sounds/countingDuck9.mp3"));

    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled)
            sound.play(1);
    }

    public static void dispose() {
        //skin
        skin.dispose();
        //atlas
        items.dispose();
        //music
        menuIntro.dispose();
        startRound.dispose();
        endRound.dispose();
        gameOver1.dispose();
        //sound
        shoot.dispose();
        dogBark.dispose();
        miss.dispose();
        dogLaughingSnd.dispose();
        dogDuckFoundSnd.dispose();
        hitGround.dispose();
        duckFallingSnd.dispose();
        cuak.dispose();
        perfect.dispose();
        outOfBullets.dispose();
        for (int i = 0; i < movingDucksArray.length; i++) movingDucksArray[i].dispose();
        //textures
        soundIconUp.dispose();
        soundIconDown.dispose();
        play1ButtonUp.dispose();
        play1ButtonDown.dispose();
        play2ButtonUp.dispose();
        play2ButtonDown.dispose();
        aboutButtonUp.dispose();
        aboutButtonDown.dispose();
        closeButtonUp.dispose();
        closeButtonDown.dispose();
    }

    public static Skin getSkin() {
        return skin;
    }
}