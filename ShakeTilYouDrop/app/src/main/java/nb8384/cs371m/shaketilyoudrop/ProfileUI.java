package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public class ProfileUI implements ProfilePlayerInfoController, ActivityLauncher{

    private ActivityLauncherListener activityLauncherListener;
    private ControllerListener controllerListener;

    EditText username;
    ListView multipliers;
    TextView upgradesStaticText, upgradesText, shakersStaticText, shakersText;
    Button backToGame;
    DisplayMetrics dimensions;

    public ProfileUI(AppCompatActivity activity) {
        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        username = (EditText) activity.findViewById(R.id.usernameText);
        multipliers = (ListView) activity.findViewById(R.id.multListView);
        upgradesStaticText = (TextView) activity.findViewById(R.id.upgradesStaticText);
        upgradesText = (TextView) activity.findViewById(R.id.upgradesText);
        shakersStaticText = (TextView) activity.findViewById(R.id.shakersStaticText);
        shakersText = (TextView) activity.findViewById(R.id.shakersText);
        backToGame = (Button) activity.findViewById(R.id.backToGameButton);
    }

    private void initViewValues(AppCompatActivity activity) {
        dimensions = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int screenHeight = dimensions.heightPixels;
        int screenWidth = dimensions.widthPixels;
        int userSize = (int) (screenWidth * 0.025);

        username.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);
        backToGame.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);

        int listViewHeight = (int) (screenHeight * 0.5);
        multipliers.getLayoutParams().height = listViewHeight;

        upgradesStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        upgradesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        shakersStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        shakersText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
    }

    private void setViewListeners(final AppCompatActivity activity) {
        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                controllerListener.editUsername(username);
            }
        });

        backToGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                activityLauncherListener.launchActivity(MainActivity.class);
            }
        });
    }

    @Override
    public void setActivityLauncherListener(ActivityLauncherListener listener) {
        activityLauncherListener = listener;
    }

    @Override
    public void onPlayerInfoUpdate(PlayerInfo playerInfo) {
        username.setText(playerInfo.getUserName());
    }

    @Override
    public void setControllerListener(ControllerListener listener) {
        this.controllerListener = listener;
    }
}