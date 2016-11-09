package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nathaniel on 10/10/2016.
 */

public class MainGameUI
        implements ShakeListener.OnShakeListener, PlayerInfoController, ActivityLauncher {


    private ActivityLauncherListener activityLauncherListener;
    private PlayerInfoControllerListener playerInfoControllerListener;

    private TextView userText, timeText, numShakesText;
    private ImageView shaker;
    private Button reset, store;
    private DisplayMetrics dimensions;

    public MainGameUI(AppCompatActivity activity) {

        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        userText = (TextView) activity.findViewById(R.id.userText);
        timeText = (TextView) activity.findViewById(R.id.timeText);
        numShakesText = (TextView) activity.findViewById(R.id.numShakesText);

        shaker = (ImageView) activity.findViewById(R.id.shakerView);

        reset = (Button) activity.findViewById(R.id.resetButton);
        store = (Button) activity.findViewById(R.id.storeButton);
    }

    private void initViewValues(AppCompatActivity activity) {
        dimensions = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int screenHeight = dimensions.heightPixels;
        int screenWidth = dimensions.widthPixels;
        int userSize = (int) (screenWidth * 0.025);

        userText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);


        int shakerHeight = (int) (screenHeight * 0.5);
        int shakerWidgth = (int) (screenWidth * 0.5);
        shaker.getLayoutParams().height = shakerHeight;
        shaker.getLayoutParams().width = shakerWidgth;

        reset.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);
        store.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);

        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
    }

    private void setViewListeners(AppCompatActivity activity) {
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerInfoControllerListener.onShakeCountReset();
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLauncherListener.launchActivity(StoreActivity.class);
            }
        });


    }

    @Override
    public void updateUI(PlayerInfo playerInfo) {
        userText.setText(playerInfo.getUserName());
        timeText.setText(Long.toString(playerInfo.getTimePlayed()));
        numShakesText.setText(Integer.toString(playerInfo.getNumShakes()));

    }

    @Override
    public void setPlayerInfoControllerListener(PlayerInfoControllerListener listener) {
        playerInfoControllerListener = listener;
    }

    @Override
    public void setActivityLauncherListener(ActivityLauncherListener listener) {
        activityLauncherListener = listener;
    }

    @Override
    public void onShake(SensorEvent evt) {
        // animate shaker image, play sounds, etc. here

        playerInfoControllerListener.onShake();
    }
}
