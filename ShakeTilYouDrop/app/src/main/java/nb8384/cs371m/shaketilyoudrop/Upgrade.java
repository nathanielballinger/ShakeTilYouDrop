package nb8384.cs371m.shaketilyoudrop;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public abstract class Upgrade implements Serializable {
    private transient List<UpgradeInfoController> controllers;
    private String name;
    private int price;

    public Upgrade(String n, int p) {
        name = n;
        price = p;
        controllers = new ArrayList<UpgradeInfoController>();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void registerController(UpgradeInfoController controller) {
        if (controllers == null)
            controllers = new ArrayList<UpgradeInfoController>();
        if (!controllers.contains(controller)) {
            controllers.add(controller);
            controller.onUpgradeInfoUpdate(this);
        }
    }

    public void purchase() {
        price *= 2;

        for (UpgradeInfoController c : controllers) {
            c.onUpgradeInfoUpdate(this);
        }
    }

    public abstract void apply(PlayerInfo playerInfo);
}
