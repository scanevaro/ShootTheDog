package com.rareFrog.game.android;

import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.rareFrog.game.Game;
import com.rareFrog.game.interfaces.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, ActionResolver {
    GameHelper gameHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Game(this), config);

        if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        gameHelper.setup(this);
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        gameHelper.onActivityResult(request, response, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public boolean getSignedInGPGS() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void loginGPGS() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ex) {
        }
    }

    @Override
    public void submitScoreGPGS(int score) {
        Games.Leaderboards.submitScore(gameHelper.getApiClient(), /*leaderboard id*/ "CgkI6qzFw40CEAIQBw", score);
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
    }

    @Override
    public void getLeaderboardGPGS() {
        if (gameHelper.isSignedIn())
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), /*leaderboard id*/ "CgkI6qzFw40CEAIQBw"), 100);
        else if (!gameHelper.isConnecting())
            loginGPGS();
    }

    @Override
    public void getAchievementsGPGS() {
        if (gameHelper.isSignedIn())
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
        else if (!gameHelper.isConnecting())
            loginGPGS();
    }

    @Override
    public void onSignInFailed() {
        //TODO
    }

    @Override
    public void onSignInSucceeded() {
        //TODO
    }
}