package nb8384.cs371m.shaketilyoudrop;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Nathaniel on 10/10/2016.
 */

public class ShakeListener implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;

    private OnShakeListener mListener;

    public interface OnShakeListener
    {
        void onShake();
    }

    public ShakeListener() {

    }

    public void setOnShakeListener(OnShakeListener listener) {
        mListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

        }
    }
}
