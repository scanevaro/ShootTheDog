package com.rareFrog.gameCore.androidGame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.rareFrog.gameCore.Game;
import com.rareFrog.gameCore.interfaces.ActionResolver;
import com.rareFrog.gameCore.interfaces.IActivityRequestHandler;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, ActionResolver, IActivityRequestHandler {
    GameHelper gameHelper;
    protected AdView adView;
    private InterstitialAd interstitial;

    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new Game(this, this), config);

        if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        gameHelper.setup(this);
        gameHelper.setMaxAutoSignInAttempts(0);

        // Create and setup the AdMob view
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-6543072510381828/6747477399");
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder()/*addTestDevice(AdRequest.DEVICE_ID_EMULATOR).*/.build());

        layout.addView(gameView);

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        layout.addView(adView, adParams);

        setContentView(layout);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6543072510381828/7209978992");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder()/*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)*/.build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
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

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS: {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    protected Handler intHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    if (interstitial.isLoaded())
                        interstitial.show();
                    break;
                }
            }
        }
    };

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }

    @Override
    public void showInterstitial(boolean show) {
//        if (show && interstitial.isLoaded())
//            interstitial.show();
        intHandler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
}