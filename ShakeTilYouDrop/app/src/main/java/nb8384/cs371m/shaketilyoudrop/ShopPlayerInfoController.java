package nb8384.cs371m.shaketilyoudrop;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface ShopPlayerInfoController extends PlayerInfoController {
    interface ShopPlayerControllerListener {
        boolean onAttemptPurchase(Upgrade upgrade);
    }

    void setPlayerControllerListener(ShopPlayerControllerListener listener);
}
