package nb8384.cs371m.shaketilyoudrop;

import android.media.Image;

/**
 * Created by Nathaniel on 11/10/2016.
 */

public class ShakeMultiplierUpgrade extends Upgrade {

    private int multiplier;

    public ShakeMultiplierUpgrade(String n, int p, int multiplier) {
        super(n, p);
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
