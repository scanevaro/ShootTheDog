package com.batman.birdhunt.interfaces;

/**
 * Created by scanevaro on 21/11/2014.
 */
public interface ActionResolver {
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();
}
