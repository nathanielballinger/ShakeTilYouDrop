package nb8384.cs371m.shaketilyoudrop;

import android.widget.EditText;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public interface ProfilePlayerInfoController extends PlayerInfoController {
    interface ControllerListener{
        void editUsername(EditText username);
    }
    void setControllerListener(ControllerListener listener);
}
