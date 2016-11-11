package nb8384.cs371m.shaketilyoudrop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public class PlayerInfo implements java.io.Serializable {
    private transient PlayerInfoController controller;
    private String userName;
    private int numShakes;
    private int numCoins;
    private int totalCoins;
    private long timePlayed;
    private List<Upgrade> upgrades;

    //upgrades
    // multiplier for shake:coin
    // shakes-for-you upgrade
    public PlayerInfo(String userName) {
        this.userName = userName;
        numShakes = 0;
        numCoins = 0;
        totalCoins = 0;
        timePlayed = 0;
    }

    public void setPlayerInfoController(PlayerInfoController controller) {
        this.controller = controller;
        this.controller.onPlayerInfoUpdate(this);
    }

    public void unregisterPlayerInfoController() {
        controller = null;
    }

    /**
     * increments total number of shakes and number of coins every X shakes
     */
    public void shake(){
        numShakes++;
        if (numShakes % 100 == 0) {
            numCoins++;
            totalCoins++;
        }

        if (controller != null)
            controller.onPlayerInfoUpdate(this);

    }

    public void purchase(int coins) {
        // add whatever I purchase to inventory

        numCoins -= coins;

        if(controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public void addToTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
        if (controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public void resetRound() {
        numShakes = 0;
        if (controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public void changeUsername(String username){
        userName = username;
        if (controller != null)
            controller.onPlayerInfoUpdate(this);
    }

    public int getNumCoins() {
        return numCoins;
    }

    public int getNumTotalCoins() {
        return totalCoins;
    }

    public int getNumShakes() {
        return numShakes;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public String getUserName() {
        return userName;
    }
}
