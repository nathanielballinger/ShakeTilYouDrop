package nb8384.cs371m.shaketilyoudrop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public class PlayerInfo implements java.io.Serializable {
    private PlayerInfoController controller;
    private String userName;
    private int numTotalShakes;
    private int numShakes;
    private int numCoins;
    private long timePlayed;

    public PlayerInfo(String userName, PlayerInfoController controller) {
        this.controller = controller;
        this.userName = userName;
        numTotalShakes = 0;
        numShakes = 0;
        numCoins = 0;
        timePlayed = 0;
    }

    /**
     * increments total number of shakes and number of coins every X shakes
     */
    public void shake(){
        numShakes++;
        numTotalShakes++;
        if (numShakes % 100 == 0)
            numCoins++;

        controller.updateUI(this);

    }

    public void addToTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
        controller.updateUI(this);
    }

    public void resetRound() {
        numShakes = 0;
        controller.updateUI(this);
    }

    public int getNumCoins() {
        return numCoins;
    }

    public int getNumTotalShakes() {
        return numTotalShakes;
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
