package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements ActivityLauncher.ActivityLauncherListener,
        MainGamePlayerInfoController.MainPlayerControllerListener {

    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener shakeListener;
    private MainGameUI gameUI;
    private PlayerInfo playerInfo;
    private AvailableUpgrades availableUpgrades;

    String username = "Test Username"; //Dummy Username!!!!!!! Change later
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gameUI = new MainGameUI(this);
        gameUI.setActivityLauncherListener(this);
        gameUI.setPlayerControllerListener(this);

        playerInfo = new PlayerInfo(username);

        shakeListener = new ShakeListener(getApplicationContext());
        shakeListener.setOnShakeListener(gameUI);



        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (motionSensor == null)
            Log.i(TAG, "Motion sensor not found!");

    }

    public Runnable realTime = new Runnable(){

        @Override
        public void run() {
            // call playerInfo.addToTimePlayer(long timeInMilliSeconds)
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(shakeListener, motionSensor, SensorManager.SENSOR_DELAY_GAME);

        PlayerInfo newPlayerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");
        if (newPlayerInfo != null)
            playerInfo = newPlayerInfo;
        playerInfo.setPlayerInfoController(gameUI);

        AvailableUpgrades newAvailableUpgrades =
                (AvailableUpgrades) getIntent().getSerializableExtra("AvailableUpgrades");
        if (newAvailableUpgrades != null)
            availableUpgrades = newAvailableUpgrades;


        Toast.makeText(getApplicationContext(), "Num Coins is " + playerInfo.getNumCoins(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(shakeListener);
        playerInfo.unregisterPlayerInfoController();
    }


    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        intent.putExtra("AvailableUpgrades", availableUpgrades);
        startActivity(intent);
    }

    @Override
    public void onShakeCountReset() {
        playerInfo.resetRound();
    }

    @Override
    public void onShake() {
        playerInfo.shake();
    }


}
