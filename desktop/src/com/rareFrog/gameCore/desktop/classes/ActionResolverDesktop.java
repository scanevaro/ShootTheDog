package com.rareFrog.gameCore.desktop.classes;

import com.rareFrog.gameCore.interfaces.ActionResolver;

/**
 * Created by scanevaro on 21/11/2014.
 */
public class ActionResolverDesktop implements ActionResolver {
    boolean signedInStateGPGS = false;

    @Override
    public boolean getSignedInGPGS() {
        return signedInStateGPGS;
    }

    @Override
    public void loginGPGS() {
        System.out.println("loginGPGS");
        signedInStateGPGS = true;
    }

    @Override
    public void submitScoreGPGS(int score) {
        System.out.println("submitScoreGPGS " + score);
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {
        System.out.println("unlockAchievement " + achievementId);
    }

    @Override
    public void getLeaderboardGPGS() {
        System.out.println("getLeaderboardGPGS");
    }

    @Override
    public void getAchievementsGPGS() {
        System.out.println("getAchievementsGPGS");
    }
}