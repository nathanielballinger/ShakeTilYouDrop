package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public class ProfileUI implements
        ProfilePlayerInfoController, ActivityLauncher {

    private ActivityLauncherListener activityLauncherListener;
    private ControllerListener controllerListener;

    private EditText username;
    private RecyclerView rvUpgrades;
    private UpgradesAdapter adapter;
    private TextView upgradesStaticText, upgradesText, shakersStaticText, shakersText;
    private Button backToGame;
    private DisplayMetrics dimensions;

    public ProfileUI(AppCompatActivity activity, UpgradesAdapter adapter) {
        this.adapter = adapter;
        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        username = (EditText) activity.findViewById(R.id.usernameText);
        rvUpgrades = (RecyclerView) activity.findViewById(R.id.upgradeRecyclerView);
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
        rvUpgrades.getLayoutParams().height = listViewHeight;

        upgradesStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        upgradesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        shakersStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        shakersText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);

        rvUpgrades.setAdapter(adapter);
        rvUpgrades.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void setViewListeners(final AppCompatActivity activity) {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
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
