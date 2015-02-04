package com.rarefrog.birdhunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static int UISHOTSWIDTH = 29;
    public static int UISHOTSHEIGHT = 21;
    public static int UISCOREWIDTH = 53;
    public static int UISCOREHEIGHT = 21;

    public static boolean loaded;

    public static AssetManager assetManager;

    public static Skin skin;

    public static TextureAtlas items;

//    public static Texture viewmodel;

    public static TextureRegion cloud[];
    public static TextureRegion backgroundRegion;
    public static TextureRegion backgroundBackRegion;
    public static TextureRegion duckHit;
    public static TextureRegion title;
    public static TextureRegion gameMode1;
    public static TextureRegion gameMode2;
    public static TextureRegion menuCursor;
    public static TextureRegion dogFound;
    public static TextureRegion uiShot;
    public static TextureRegion uiDucksRound;
    public static TextureRegion ui3Shots;
    public static TextureRegion ui2Shots;
    public static TextureRegion ui1Shots;
    public static TextureRegion uiWhiteDuck;
    public static TextureRegion uiRedDuck;
    public static TextureRegion uiYellowDuck;
    public static TextureRegion dogDuckFound;
    public static TextureRegion dogDucksFound;
    public static TextureRegion dialog;
    public static TextureRegion ui0Shots;
    public static TextureRegion duckFallingBlack;
    public static TextureRegion duckFallingRed;
    public static TextureRegion duckHitBlack;
    public static TextureRegion duckHitRed;
    public static TextureRegion birdHitYellow;
    public static TextureRegion pauseButton;
    public static TextureRegion soundIconUp, soundIconDown;
    public static TextureRegion play1ButtonUp, play1ButtonDown;
    public static TextureRegion play2ButtonUp, play2ButtonDown;
    public static TextureRegion aboutIconUp, aboutIconDown;
    public static TextureRegion closeIconUp, closeIconDown;
    public static TextureRegion loginButtonUp, loginButtonDown;
    public static TextureRegion configButtonUp, configButtonDown;
    public static TextureRegion quitButtonUp, quitButtonDown;
    public static TextureRegion confirmButtonUp, confirmButtonDown;
    public static TextureRegion backIconUp, backIconDown;
    public static TextureRegion multiplier1, multiplier2, multiplier3, multiplier4, multiplier5, multiplier6, multiplier7, multiplier8, multiplier9, multiplier10;
    public static TextureRegion squirrel, monkey, cow, pig, bear;
    public static TextureRegion leaderboardsUp, leaderboardsDown, achievementsUp, achievementsDown;
    public static TextureRegion logo, chrislogo;

    public static Animation duckFlyRightBlack;
    public static Animation duckFlyRightRed;
    public static Animation duckFlyTopBlack;
    public static Animation duckFlyTopRed;
    public static Animation duckFlyUpBlack;
    public static Animation duckFlyUpRed;
    public static Animation birdFlyRightYellow;
    public static Animation birdFlyUpYellow;
    public static Animation birdFlyFallingYellow;
    public static Animation dogWalking;
    public static Animation dogJumping;
    public static Animation dogLaughing;
    public static Animation uiDucks;
    public static Animation dogShot;
    public static Animation bulletCasing;

    public static BitmapFont font, fontBig;

    public static Music menuIntro;
    public static Music startRound;
    public static Music endRound;
    public static Music gameOver1;
    public static Music gameOver2;
    public static Music background;

    public static Sound shoot;
    public static Sound dogBark;
    public static Sound flapLong;
    public static Sound flapNormal;
    public static Sound flapShort;
    public static Sound dogLaughingSnd;
    public static Sound duckFoundSnd;
    public static Sound hitGround;
    public static Sound duckFallingSnd;
    public static Sound cuak;
    public static Sound[] movingDucksArray;
    public static Sound perfect;
    public static Sound outOfBullets;
    public static Sound pauseClicked;
    public static Sound pauseClosed;
    public static Sound duckShot;
    public static Sound dogShotSound;

    public Assets() {
        assetManager = new AssetManager();
        loaded = false;
    }

    public static void load() {
        assetManager.load("data/loading.pack", TextureAtlas.class);
        assetManager.finishLoading();

        loadAtlas();
        loadSounds();
    }

    private static void loadAtlas() {
        assetManager.load("data/items.pack", TextureAtlas.class);
    }

    private static void loadSounds() {
        assetManager.load("data/sounds/DuckHunt.mp3", Music.class);
        assetManager.load("data/sounds/start_round.mp3", Music.class);
        assetManager.load("data/sounds/end_round.mp3", Music.class);
        assetManager.load("data/sounds/gameOver1.mp3", Music.class);
        assetManager.load("data/sounds/gameOver2.mp3", Music.class);
        assetManager.load("data/sounds/music/background1.wav", Music.class);

        assetManager.load("data/sounds/blast.mp3", Sound.class);
        assetManager.load("data/sounds/bark.mp3", Sound.class);
        assetManager.load("data/sounds/flapLong.mp3", Sound.class);
        assetManager.load("data/sounds/flapNormal.mp3", Sound.class);
        assetManager.load("data/sounds/flapShort.mp3", Sound.class);
        assetManager.load("data/sounds/laugh.mp3", Sound.class);
        assetManager.load("data/sounds/end_duck_round.mp3", Sound.class);
        assetManager.load("data/sounds/drop.mp3", Sound.class);
        assetManager.load("data/sounds/duck_falling.mp3", Sound.class);
        assetManager.load("data/sounds/cuak.mp3", Sound.class);
        assetManager.load("data/sounds/perfect.mp3", Sound.class);
        assetManager.load("data/sounds/outOfBullets.mp3", Sound.class);
        assetManager.load("data/sounds/pauseClick.mp3", Sound.class);
        assetManager.load("data/sounds/pauseClose.mp3", Sound.class);
        assetManager.load("data/sounds/duckShot.mp3", Sound.class);
        assetManager.load("data/sounds/dogShot.mp3", Sound.class);

        assetManager.load("data/sounds/countingDuck0.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck1.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck2.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck3.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck4.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck5.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck6.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck7.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck8.mp3", Sound.class);
        assetManager.load("data/sounds/countingDuck9.mp3", Sound.class);
    }

    public static void set() {
        setSkin();
        setAtlas();
        setTextures();
        setSounds();
    }

    private static void setSkin() {
        skin = new Skin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/comic.ttf"));
        font = generator.generateFont(40);
        fontBig = generator.generateFont(65);

        skin.add("default-font", font, BitmapFont.class);
        skin.add("big-font", fontBig, BitmapFont.class);

        generator.dispose();

        FileHandle fileHandle = Gdx.files.internal("data/items.json");
        FileHandle atlasFile = fileHandle.sibling("items.pack");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    private static void setAtlas() {
        items = assetManager.get("data/items.pack");
    }

    private static void setTextures() {
        backgroundRegion = items.findRegion("grass1440");
        backgroundBackRegion = items.findRegion("wallpaper1440");
//        viewmodel = new Texture(Gdx.files.internal("data/images/viewmodel.png"));
        bulletCasing = new Animation(0.5f,
                (items.findRegion("cart1")),
                (items.findRegion("cart2")),
                (items.findRegion("cart3")),
                (items.findRegion("cart4")),
                (items.findRegion("cart5")));
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
        duckFlyRightBlack = new Animation(0.2f,
                (items.findRegion("duckFlyRightBlack1")),
                (items.findRegion("duckFlyRightBlack2")),
                (items.findRegion("duckFlyRightBlack3")));
        duckFlyRightRed = new Animation(0.2f,
                (items.findRegion("duckFlyRightRed1")),
                (items.findRegion("duckFlyRightRed2")),
                (items.findRegion("duckFlyRightRed3")));
        duckFlyTopBlack = new Animation(0.2f,
                (items.findRegion("duckFlyTopBlack1")),
                (items.findRegion("duckFlyTopBlack2")),
                (items.findRegion("duckFlyTopBlack3")));
        duckFlyTopRed = new Animation(0.2f,
                (items.findRegion("duckFlyTopRed1")),
                (items.findRegion("duckFlyTopRed2")),
                (items.findRegion("duckFlyTopRed3")));
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
                items.findRegion("dogShot0"),
                items.findRegion("dogShot1"));
        birdFlyRightYellow = new Animation(0.2f,
                items.findRegion("bird11"),
                items.findRegion("bird12"),
                items.findRegion("bird13"),
                items.findRegion("bird12"),
                items.findRegion("bird14"),
                items.findRegion("bird15"),
                items.findRegion("bird16"));
        birdFlyUpYellow = new Animation(0.2f,
                items.findRegion("bird17"),
                items.findRegion("bird18"),
                items.findRegion("bird19"));
        birdFlyFallingYellow = new Animation(0.2f,
                items.findRegion("bird111"),
                items.findRegion("bird112"));

        cloud = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            cloud[i] = items.findRegion("cloud" + (i + 1));
        }
        duckFallingBlack = items.findRegion("duckFallingBlack");
        duckFallingRed = items.findRegion("duckFallingRed");
        duckHitBlack = items.findRegion("duckHitBlack");
        duckHitRed = items.findRegion("duckHitRed");
        birdHitYellow = items.findRegion("bird110");
        pauseButton = items.findRegion("pauseButton");

        ui0Shots = items.findRegion("ui0Shots1");
        duckHit = items.findRegion("duckHit");
        title = items.findRegion("title");
        gameMode1 = items.findRegion("gameMode1");
        gameMode2 = items.findRegion("gameMode2");
        menuCursor = items.findRegion("menuCursor");
        uiShot = items.findRegion("uiShot");
        uiDucksRound = items.findRegion("uiDucksRound");
        dialog = items.findRegion("pauseDialog");
        ui3Shots = items.findRegion("ui3Shots");
        ui2Shots = items.findRegion("ui2Shots");
        ui1Shots = items.findRegion("ui1Shots");
        uiWhiteDuck = items.findRegion("uiWhiteDuck");
        uiRedDuck = items.findRegion("uiRedDuck");
        uiYellowDuck = items.findRegion("uiYellowDuck");
        dogDuckFound = items.findRegion("dog1Duck");
        dogDucksFound = items.findRegion("dog2Ducks");
        soundIconUp = items.findRegion("soundIconUp");
        soundIconDown = items.findRegion("soundIconDown");
        play1ButtonUp = items.findRegion("play1ButtonUp");
        play1ButtonDown = items.findRegion("play1ButtonDown");
        play2ButtonUp = items.findRegion("play2ButtonUp");
        play2ButtonDown = items.findRegion("play2ButtonDown");
        aboutIconUp = items.findRegion("aboutIconUp");
        aboutIconDown = items.findRegion("aboutIconDown");
        closeIconUp = items.findRegion("closeIconUp");
        closeIconDown = items.findRegion("closeIconDown");
        loginButtonUp = items.findRegion("loginButtonUp");
        loginButtonDown = items.findRegion("loginButtonDown");
        configButtonUp = items.findRegion("configIconUp");
        configButtonDown = items.findRegion("configIconDown");
        backIconUp = items.findRegion("backIconUp");
        backIconDown = items.findRegion("backIconDown");
        quitButtonUp = items.findRegion("quitIconUp");
        quitButtonDown = items.findRegion("quitIconDown");
        confirmButtonUp = items.findRegion("confirmIconUp");
        confirmButtonDown = items.findRegion("confirmIconDown");
        multiplier1 = items.findRegion("multiplier1");
        multiplier2 = items.findRegion("multiplier2");
        multiplier3 = items.findRegion("multiplier3");
        multiplier4 = items.findRegion("multiplier4");
        multiplier5 = items.findRegion("multiplier5");
        multiplier6 = items.findRegion("multiplier6");
        multiplier7 = items.findRegion("multiplier7");
        multiplier8 = items.findRegion("multiplier8");
        multiplier9 = items.findRegion("multiplier9");
        multiplier10 = items.findRegion("multiplier10");
        squirrel = items.findRegion("squirrel");
        monkey = items.findRegion("monkey");
        cow = items.findRegion("cow");
        pig = items.findRegion("pig");
        bear = items.findRegion("bear");
        achievementsDown = items.findRegion("achievementsDown");
        achievementsUp = items.findRegion("achievementsUp");
        leaderboardsDown = items.findRegion("leaderboardsDown");
        leaderboardsUp = items.findRegion("leaderboardsUp");
        logo = items.findRegion("logo");
        chrislogo = items.findRegion("chrisportermusic");
    }

    private static void setSounds() {
        menuIntro = assetManager.get("data/sounds/DuckHunt.mp3");
        menuIntro.setVolume(0.5f);
        startRound = assetManager.get("data/sounds/start_round.mp3");
        startRound.setVolume(0.5f);
        endRound = assetManager.get("data/sounds/end_round.mp3");
        endRound.setVolume(0.5f);
        gameOver1 = assetManager.get("data/sounds/gameOver1.mp3");
        gameOver1.setVolume(0.5f);
        gameOver2 = assetManager.get("data/sounds/gameOver2.mp3");
        gameOver2.setVolume(0.5f);
        background = assetManager.get("data/sounds/music/background1.wav");
        background.setVolume(0.5f);

        shoot = assetManager.get("data/sounds/blast.mp3");
        dogBark = assetManager.get("data/sounds/bark.mp3");
        flapLong = assetManager.get("data/sounds/flapLong.mp3");
        flapNormal = assetManager.get("data/sounds/flapNormal.mp3");
        flapShort = assetManager.get("data/sounds/flapShort.mp3");
        dogLaughingSnd = assetManager.get("data/sounds/laugh.mp3");
        duckFoundSnd = assetManager.get("data/sounds/end_duck_round.mp3");
        hitGround = assetManager.get("data/sounds/drop.mp3");
        duckFallingSnd = assetManager.get("data/sounds/duck_falling.mp3");
        cuak = assetManager.get("data/sounds/cuak.mp3");
        perfect = assetManager.get("data/sounds/perfect.mp3");
        outOfBullets = assetManager.get("data/sounds/outOfBullets.mp3");
        pauseClicked = assetManager.get("data/sounds/pauseClick.mp3");
        pauseClosed = assetManager.get("data/sounds/pauseClose.mp3");
        duckShot = assetManager.get("data/sounds/duckShot.mp3");
        dogShotSound = assetManager.get("data/sounds/dogShot.mp3");

        movingDucksArray = new Sound[10];
        movingDucksArray[0] = assetManager.get("data/sounds/countingDuck0.mp3");
        movingDucksArray[1] = assetManager.get("data/sounds/countingDuck1.mp3");
        movingDucksArray[2] = assetManager.get("data/sounds/countingDuck2.mp3");
        movingDucksArray[3] = assetManager.get("data/sounds/countingDuck3.mp3");
        movingDucksArray[4] = assetManager.get("data/sounds/countingDuck4.mp3");
        movingDucksArray[5] = assetManager.get("data/sounds/countingDuck5.mp3");
        movingDucksArray[6] = assetManager.get("data/sounds/countingDuck6.mp3");
        movingDucksArray[7] = assetManager.get("data/sounds/countingDuck7.mp3");
        movingDucksArray[8] = assetManager.get("data/sounds/countingDuck8.mp3");
        movingDucksArray[9] = assetManager.get("data/sounds/countingDuck9.mp3");
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
        flapLong.dispose();
        flapNormal.dispose();
        flapShort.dispose();
        dogLaughingSnd.dispose();
        duckFoundSnd.dispose();
        hitGround.dispose();
        duckFallingSnd.dispose();
        cuak.dispose();
        perfect.dispose();
        outOfBullets.dispose();
        for (int i = 0; i < movingDucksArray.length; i++) movingDucksArray[i].dispose();

        assetManager.dispose();
    }
}