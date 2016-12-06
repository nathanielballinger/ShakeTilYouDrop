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
import android.widget.TextView;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public class ProfileUI implements
        ProfilePlayerInfoController, ActivityController {

    private ActivityControllerListener activityControllerListener;
    private ControllerListener controllerListener;

    private EditText username;
    private RecyclerView rvUpgrades;
    private ProfileUpgradesAdapter adapter;
    private TextView upgradesStaticText, shakersStaticText, shakersText;
    private Button backToGame;
    private DisplayMetrics dimensions;

    public ProfileUI(AppCompatActivity activity, ProfileUpgradesAdapter adapter) {
        this.adapter = adapter;
        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        username = (EditText) activity.findViewById(R.id.usernameText);
        rvUpgrades = (RecyclerView) activity.findViewById(R.id.upgradeRecyclerView);
        upgradesStaticText = (TextView) activity.findViewById(R.id.upgradesStaticText);
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

        int listViewHeight = (int) (screenHeight * 0.25);
        rvUpgrades.getLayoutParams().height = listViewHeight;

        upgradesStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
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
                activityControllerListener.finishActivity();
            }
        });
    }

    public void setActivityControllerListener(ActivityControllerListener listener) {
        activityControllerListener = listener;
    }

    @Override
    public void onPlayerInfoInit(PlayerInfo playerInfo) {
        username.setText(playerInfo.getUserName());
    }

    @Override
    public void onPlayerInfoUpdate(PlayerInfo playerInfo) {

    }

    @Override
    public void setControllerListener(ControllerListener listener) {
        this.controllerListener = listener;
    }
}
