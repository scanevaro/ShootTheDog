package com.rareFrog.birdhunt;

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
    public static int UISHOTSWIDTH = 29;
    public static int UISHOTSHEIGHT = 21;
    public static int UISCOREWIDTH = 53;
    public static int UISCOREHEIGHT = 21;

    public static Skin skin;

    public static TextureAtlas atlas, items;

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
    public static TextureRegion uiScore;
    public static TextureRegion presentRound;
    public static TextureRegion ui3Shots;
    public static TextureRegion ui2Shots;
    public static TextureRegion ui1Shots;
    public static TextureRegion uiWhiteDuck;
    public static TextureRegion uiRedDuck;
    public static TextureRegion uiYellowDuck;
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
    public static TextureRegion pauseButton;
    public static TextureRegion soundIconUp;
    public static TextureRegion soundIconDown;
    public static TextureRegion play1ButtonUp;
    public static TextureRegion play1ButtonDown;
    public static TextureRegion play2ButtonUp;
    public static TextureRegion play2ButtonDown;
    public static TextureRegion aboutButtonUp;
    public static TextureRegion aboutButtonDown;
    public static TextureRegion closeButtonDown;
    public static TextureRegion closeButtonUp;
    public static TextureRegion leaderboardsButtonUp;
    public static TextureRegion leaderboardsButtonDown;
    public static TextureRegion achievementsButtonUp;
    public static TextureRegion achievementsButtonDown;
    public static TextureRegion loginButtonUp;
    public static TextureRegion loginButtonDown;
    public static TextureRegion configButtonUp, configButtonDown;

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

    public static BitmapFont font, fontBig;

    public static Music menuIntro;
    public static Music startRound;
    public static Music endRound;
    public static Music gameOver1;
    public static Music gameOver2;
    public static Music background;
    public static Music dogShotMusic;

    public static Sound shoot;
    public static Sound dogBark;
    public static Sound miss;
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

    public static void load() {
        loadSkin();

        loadAtlas();

        loadTextures();

        loadSounds();
    }

    private static void loadSkin() {
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
        pauseButton = items.findRegion("pauseButton");

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
        uiYellowDuck = items.findRegion("uiYellowDuck");
        dogDuckFound = items.findRegion("dog1Duck");
        dogDucksFound = items.findRegion("dog2Ducks");
        soundIconUp = items.findRegion("soundIconUp");
        soundIconDown = items.findRegion("soundIconDown");
        play1ButtonUp = items.findRegion("play1ButtonUp");
        play1ButtonDown = items.findRegion("play1ButtonDown");
        play2ButtonUp = items.findRegion("play2ButtonUp");
        play2ButtonDown = items.findRegion("play2ButtonDown");
        aboutButtonUp = items.findRegion("aboutButtonUp");
        aboutButtonDown = items.findRegion("aboutButtonDown");
        closeButtonUp = items.findRegion("closeButtonUp");
        closeButtonDown = items.findRegion("closeButtonDown");
        leaderboardsButtonUp = items.findRegion("leaderboards0");
        leaderboardsButtonDown = items.findRegion("leaderboards0");
        achievementsButtonUp = items.findRegion("achievements0");
        achievementsButtonDown = items.findRegion("achievements1");
        loginButtonUp = items.findRegion("login0");
        loginButtonDown = items.findRegion("login0");
        configButtonUp = new TextureRegion(new Texture(Gdx.files.internal("data/images/config.png")));
        configButtonDown = new TextureRegion(new Texture(Gdx.files.internal("data/images/config.png")));
    }

    private static void loadSounds() {
        menuIntro = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/DuckHunt.mp3"));
        menuIntro.setVolume(0.5f);
        startRound = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/start_round.mp3"));
        startRound.setVolume(0.5f);
        endRound = Gdx.audio.newMusic((Gdx.files.internal("data/sounds/end_round.mp3")));
        endRound.setVolume(0.5f);
        gameOver1 = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/gameOver1.mp3"));
        gameOver1.setVolume(0.5f);
        gameOver2 = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/gameOver2.mp3"));
        gameOver2.setVolume(0.5f);
        background = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/music/background1.wav"));
        background.setVolume(0.5f);
        dogShotMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/shotTheDog.mp3"));
        dogShotMusic.setVolume(0.5f);

        shoot = Gdx.audio.newSound(Gdx.files.internal("data/sounds/blast.mp3"));
        dogBark = Gdx.audio.newSound(Gdx.files.internal("data/sounds/bark.mp3"));
        miss = Gdx.audio.newSound(Gdx.files.internal("data/sounds/miss.mp3"));
        dogLaughingSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/laugh.mp3"));
        duckFoundSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/end_duck_round.mp3"));
        hitGround = Gdx.audio.newSound(Gdx.files.internal("data/sounds/drop.mp3"));
        duckFallingSnd = Gdx.audio.newSound(Gdx.files.internal("data/sounds/duck_falling.mp3"));
        cuak = Gdx.audio.newSound(Gdx.files.internal("data/sounds/cuak.mp3"));
        perfect = Gdx.audio.newSound(Gdx.files.internal("data/sounds/perfect.mp3"));
        outOfBullets = Gdx.audio.newSound(Gdx.files.internal("data/sounds/outOfBullets.mp3"));
        pauseClicked = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pauseClick.mp3"));
        pauseClosed = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pauseClose.mp3"));
        duckShot = Gdx.audio.newSound(Gdx.files.internal("data/sounds/duckShot.mp3"));
        dogShotSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/dogShot.mp3"));

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
        duckFoundSnd.dispose();
        hitGround.dispose();
        duckFallingSnd.dispose();
        cuak.dispose();
        perfect.dispose();
        outOfBullets.dispose();
        for (int i = 0; i < movingDucksArray.length; i++) movingDucksArray[i].dispose();
    }
}