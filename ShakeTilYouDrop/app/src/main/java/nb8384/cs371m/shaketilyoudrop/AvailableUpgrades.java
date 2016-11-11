package nb8384.cs371m.shaketilyoudrop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 11/11/2016.
 * Use this class to get the list of available upgrades
 * Wraps a List of Upgrades for the purpose of Serialization
 */

public class AvailableUpgrades implements Serializable {
    private List<Upgrade> availableUpgrades;

    public AvailableUpgrades() {
        availableUpgrades = new ArrayList<Upgrade>();

        // add more upgrades here as needed
        availableUpgrades.add(new ShakeMultiplierUpgrade("Multiply Points per Shake by 2", 1, 2));
    }

    public List<Upgrade> getList() {
        return availableUpgrades;
    }

    public void registerController(UpgradeInfoController controller) {
        for (Upgrade upgrade : availableUpgrades)
            upgrade.registerController(controller);
    }

}
