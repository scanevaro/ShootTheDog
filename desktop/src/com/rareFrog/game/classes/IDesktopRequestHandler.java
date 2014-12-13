package com.rareFrog.game.classes;

import com.rareFrog.game.interfaces.IActivityRequestHandler;

/**
 * Created by scanevaro on 12/12/2014.
 */
public class IDesktopRequestHandler implements IActivityRequestHandler {
    @Override
    public void showAds(boolean show) {
        System.out.println("Show Ads called");
    }

    @Override
    public void showInterstitial(boolean show) {
        System.out.println("Show Interstitial called");
    }
}
