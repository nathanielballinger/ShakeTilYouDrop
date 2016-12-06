package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements ActivityController.ActivityControllerListener,
        MainGamePlayerInfoController.MainPlayerControllerListener {

    public static final int REQUEST_CODE = 1;

    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener shakeListener;
    private MainGameUI gameUI;
    private PlayerInfo playerInfo;
    private UpgradeList upgradeList;
    private long time = 0;
    Handler handle;

    String username = "Default Username"; //Dummy Username!!!!!!! Change later
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handle = new Handler();
        handle.postDelayed(realTime, 1000);

        gameUI = new MainGameUI(this);
        gameUI.setActivityControllerListener(this);
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
            time = 1000;
            playerInfo.addToTimePlayed(time);
            // call playerInfo.addToTimePlayer(long timeInMilliSeconds)
            handle.postDelayed(realTime, 1000);
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        initActivityFromIntent(getIntent());

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
        intent.putExtra("UpgradeList", upgradeList);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void finishActivity() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            initActivityFromIntent(data);
        }
    }

    @Override
    public void onShakeCountReset() {
        playerInfo.reset();
    }

    @Override
    public void onShake() {
        playerInfo.shake();
    }

    @Override
    public void onBackPressed() {
        // save instance data to Firebase before closing app
        super.onBackPressed();
    }

    private void initActivityFromIntent(Intent data) {
        mSensorManager.registerListener(shakeListener, motionSensor, SensorManager.SENSOR_DELAY_GAME);

        PlayerInfo newPlayerInfo = (PlayerInfo) data.getSerializableExtra("PlayerInfo");
        if (newPlayerInfo != null)
            playerInfo = newPlayerInfo;
        playerInfo.setPlayerInfoController(gameUI);

        UpgradeList newUpgradeList =
                (UpgradeList) data.getSerializableExtra("UpgradeList");
        if (newUpgradeList != null)
            upgradeList = newUpgradeList;
    }


}
