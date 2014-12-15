package com.rareFrog.birdhunt.classes;

import com.rareFrog.birdhunt.interfaces.IActivityRequestHandler;

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
