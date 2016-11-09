package nb8384.cs371m.shaketilyoudrop;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface ShopPlayerInfoController extends PlayerInfoController {
    interface ControllerListener {
        void onAttemptPurchase(int coins);
    }

    void setControllerListener(ControllerListener listener);
}
