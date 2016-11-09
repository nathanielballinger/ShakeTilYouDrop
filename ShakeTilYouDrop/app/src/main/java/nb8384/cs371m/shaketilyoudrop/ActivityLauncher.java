package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public interface ActivityLauncher {

    interface ActivityLauncherListener {
        void launchActivity(Class<? extends AppCompatActivity> activityClass);
    }

    void setActivityLauncherListener(ActivityLauncherListener listener);
}
