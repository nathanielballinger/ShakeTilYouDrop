package nb8384.cs371m.shaketilyoudrop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public class PlayerInfo implements java.io.Serializable {
    private transient PlayerInfoController controller;
    private String userName;
    private int numPoints;
    private int numCoins;
    private int totalCoins;
    private long timePlayed;
    private int pointsPerShake;
    private List<Upgrade> upgrades;

    public static final int POINTS_PER_COIN = 100;

    //upgrades
    // multiplier for shake:coin
    // shakes-for-you upgrade
    public PlayerInfo(String userName) {
        upgrades = new ArrayList<Upgrade>();
        this.userName = userName;
        numPoints = 0;
        numCoins = 0;
        totalCoins = 0;
        timePlayed = 0;
        pointsPerShake = 1;
    }

    public void setPlayerInfoController(PlayerInfoController controller) {
        this.controller = controller;
        this.controller.onPlayerInfoUpdate(this);
    }

    public void unregisterPlayerInfoController() {
        controller = null;
    }

    /**
     * increments total number of points and number of coins based on POINTS_PER_COIN
     */
    public void shake(){
        int oldNumPoints = numPoints;
        numPoints += pointsPerShake;

        addNewCoins(oldNumPoints, numPoints);

        if (controller != null)
            controller.onPlayerInfoUpdate(this);

    }

    public void purchase(Upgrade upgrade) {
        // add whatever I purchase to inventory
        upgrades.add(upgrade);
        upgrade.apply(this);
        numCoins -= upgrade.getPrice();

        if(controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public void addToTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
        if (controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public void resetRound() {
        numPoints = 0;
        if (controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public int getNumCoins() {
        return numCoins;
    }

    public int getNumTotalCoins() {
        return totalCoins;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumActiveUpgrades() {
        return upgrades.size();
    }

    public int getPointsPerShake() {
        return pointsPerShake;
    }

    /**
     * multiplies the pointsPerShake into the current pointsPerShake
     * @param shakeMultiplier multiplier (usually of an Upgrade) to be added in
     */
    public void combineIntoMultiplier(int shakeMultiplier) {
        this.pointsPerShake *= shakeMultiplier;
    }


    private void addNewCoins(int oldNumPoints, int newNumPoints) {
        int diff = newNumPoints - oldNumPoints;
        if (diff >= POINTS_PER_COIN)
            numCoins += diff / POINTS_PER_COIN;

        if (newNumPoints % POINTS_PER_COIN < oldNumPoints % POINTS_PER_COIN)
            numCoins++;
    }


}
