package nb8384.cs371m.shaketilyoudrop;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


/**
 * Created by Nathaniel on 10/10/2016.
 */

public class MainGameUI
        implements ShakeListener.OnShakeListener, MainGamePlayerInfoController, ActivityController {


    private ActivityControllerListener activityControllerListener;
    private MainPlayerControllerListener controllerListener;

    private TextView userText, timeText, numShakesText, timeStaticText, numShakesStaticText;
    private TextView currCoinsStaticText, currCoinsText, totalCoinsStaticText, totalCoinsText;
    private ImageView shaker;
    private Button reset, store, profile;
    private DisplayMetrics dimensions;
    private MediaPlayer shakerSound, coinSound;
    private int oldCoins;
    private int currCoins;

    public MainGameUI(Activity activity) {

        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(Activity activity) {
        userText = (TextView) activity.findViewById(R.id.userText);
        timeText = (TextView) activity.findViewById(R.id.timeText);
        numShakesText = (TextView) activity.findViewById(R.id.numShakesText);
        timeStaticText = (TextView) activity.findViewById(R.id.timeStaticText);
        numShakesStaticText = (TextView) activity.findViewById(R.id.numShakesStaticText);

        currCoinsStaticText = (TextView) activity.findViewById(R.id.currCoinsStaticText);
        currCoinsText = (TextView) activity.findViewById(R.id.currCoinsText);
        totalCoinsStaticText = (TextView) activity.findViewById(R.id.totalCoinsStaticText);
        totalCoinsText = (TextView) activity.findViewById(R.id.totalCoinsText);

        shaker = (ImageView) activity.findViewById(R.id.shakerView);
        shakerSound =  MediaPlayer.create(activity.getApplicationContext(), R.raw.shake);
        coinSound = MediaPlayer.create(activity.getApplicationContext(), R.raw.collect_coin);

        reset = (Button) activity.findViewById(R.id.resetButton);
        store = (Button) activity.findViewById(R.id.storeButton);
        profile = (Button) activity.findViewById(R.id.profileButton);
    }

    private void initViewValues(Activity activity) {
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
        profile.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);


        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        numShakesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        timeStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        numShakesStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        currCoinsStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        currCoinsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        totalCoinsStaticText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
        totalCoinsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);
    }

    private void setViewListeners(final Activity activity) {
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerListener.onShakeCountReset();
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityControllerListener.launchActivity(StoreActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityControllerListener.launchActivity(ProfileActivity.class);
            }
        });

    }

    @Override
    public void onPlayerInfoInit(PlayerInfo playerInfo) {
        onPlayerInfoUpdate(playerInfo);
    }

    @Override
    public void onPlayerInfoUpdate(PlayerInfo playerInfo) {
        userText.setText(playerInfo.getUserName());
        long time = playerInfo.getTimePlayed();
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        String timeString = String.format("%02d:%02d:%02d", hour, minute, second);
        timeText.setText(timeString);
        numShakesText.setText(Integer.toString(playerInfo.getNumPoints()));
        currCoinsText.setText(Integer.toString(playerInfo.getNumCoins()));
        totalCoinsText.setText(Integer.toString(playerInfo.getNumTotalCoins()));
        userText.setText(playerInfo.getUserName());
        oldCoins = playerInfo.getOldCoins();
        currCoins = playerInfo.getNumCoins();
        if(currCoins > oldCoins){
            coinSound.start();
            oldCoins = currCoins;
            playerInfo.setOldCoins(oldCoins);
        }
    }

    @Override
    public void setPlayerControllerListener(MainPlayerControllerListener listener) {
        controllerListener = listener;
    }

    public void setActivityControllerListener(ActivityControllerListener listener) {
        activityControllerListener = listener;
    }

    @Override
    public void onShake(SensorEvent evt) {
        // animate shaker image, play sounds, etc. here
        shakerSound.start();
        YoYo.with(Techniques.Tada).duration(700).playOn(shaker);
        controllerListener.onShake();
    }
}
