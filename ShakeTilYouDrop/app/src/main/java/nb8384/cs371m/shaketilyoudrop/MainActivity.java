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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeListener.OnShakeListener {

    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener shakeListener;
    private UIState uiState;
    String username = "Test Username"; //Dummy Username!!!!!!! Change later
    TextView userText, timeText;
    ImageView shaker;
    Button reset, store;


    DisplayMetrics dimensions;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int screenHeight = dimensions.heightPixels;
        int screenWidth = dimensions.widthPixels;

        int userSize = (int) (screenWidth * 0.025);
        userText = (TextView) findViewById(R.id.userText);
        userText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);
        userText.setText(username);

        int shakerHeight = (int) (screenHeight * 0.5);
        int shakerWidgth = (int) (screenWidth * 0.5);
        shaker = (ImageView) findViewById(R.id.shakerView);
        shaker.getLayoutParams().height = shakerHeight;
        shaker.getLayoutParams().width = shakerWidgth;

        reset = (Button) findViewById(R.id.resetButton);
        store = (Button) findViewById(R.id.storeButton);
        reset.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);
        store.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize);

        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, userSize/2);

        uiState = new UIState();
        uiState.addView(findViewById(R.id.numShakesText));


        shakeListener = new ShakeListener(getApplicationContext());
        shakeListener.setOnShakeListener(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (motionSensor == null)
            Log.i(TAG, "Motion sensor not found!");

    }

    public Runnable realTime = new Runnable(){

        @Override
        public void run() {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(shakeListener, motionSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(shakeListener);
    }

    @Override
    public void onShake(SensorEvent event) {
        uiState.shake();
    }
}
