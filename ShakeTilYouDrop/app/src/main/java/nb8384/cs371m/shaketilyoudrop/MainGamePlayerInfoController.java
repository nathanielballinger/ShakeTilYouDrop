package nb8384.cs371m.shaketilyoudrop;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface MainGamePlayerInfoController extends PlayerInfoController {
    interface ControllerListener {
        void onShakeCountReset();
        void onShake();
    }

    void setControllerListener(ControllerListener listener);
}
