package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface ActivityController {

    interface ActivityControllerListener {
        void launchActivity(Class<? extends AppCompatActivity> activityClass);
        void finishActivity();
    }

    void setActivityControllerListener(ActivityControllerListener listener);
}
