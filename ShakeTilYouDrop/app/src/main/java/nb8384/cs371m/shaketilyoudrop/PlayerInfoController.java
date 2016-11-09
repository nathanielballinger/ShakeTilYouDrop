package nb8384.cs371m.shaketilyoudrop;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface PlayerInfoController {

    interface PlayerInfoControllerListener {
        void onShakeCountReset();
        void onShake();
    }

    void updateUI(PlayerInfo playerInfo);
    void setPlayerInfoControllerListener(PlayerInfoControllerListener listener);

}
