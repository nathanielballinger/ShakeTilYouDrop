package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeListener.OnShakeListener {

    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener shakeListener;
    private UIState uiState;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiState = new UIState();
        uiState.addView(findViewById(R.id.numShakesText));

        shakeListener = new ShakeListener(getApplicationContext());
        shakeListener.setOnShakeListener(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (motionSensor == null)
            Log.i(TAG, "Motion sensor not found!");

    }

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
