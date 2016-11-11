package nb8384.cs371m.shaketilyoudrop;

/**
 * Created by Nathaniel on 11/10/2016.
 */

public interface ShopUpgradeInfoController extends UpgradeInfoController {
    interface ShopUpgradeControllerListener {
        void purchase(Upgrade upgrade);
    }

    void setUpgradeControllerListener(ShopUpgradeControllerListener listener);


}
