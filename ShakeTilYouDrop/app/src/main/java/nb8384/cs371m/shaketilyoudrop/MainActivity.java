package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ShakeListener.OnShakeListener {

    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener sensorListener;
    private UIState uiState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiState = new UIState();
        uiState.addView(findViewById(R.id.numShakesText));

        sensorListener = new ShakeListener();
        sensorListener.setOnShakeListener(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorListener, motionSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorListener);
    }

    @Override
    public void onShake() {
        uiState.addShake();
    }
}
