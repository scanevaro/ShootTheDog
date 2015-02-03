package com.rarefrog.birdhunt.classes;

import com.rarefrog.birdhunt.interfaces.ActionResolver;

/**
 * Created by scanevaro on 21/11/2014.
 */
public class ActionResolverIOS implements ActionResolver {
    @Override
    public boolean getSignedInGPGS() {
        return false;
    }

    @Override
    public void loginGPGS() {

    }

    @Override
    public void submitScoreGPGS(int score) {

    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {

    }

    @Override
    public void getLeaderboardGPGS() {

    }

    @Override
    public void getAchievementsGPGS() {

    }
}
