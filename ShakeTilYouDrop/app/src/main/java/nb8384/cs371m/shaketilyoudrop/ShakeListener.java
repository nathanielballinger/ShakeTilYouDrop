package nb8384.cs371m.shaketilyoudrop;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * Created by Nathaniel on 10/10/2016.
 */

public class ShakeListener implements SensorEventListener {

    // the minimum amount of force that will register as a shake (in m/s^2)
    private static final int FORCE_THRESHOLD = 20;
    // the minimum amount of time in between each shake (in ms)
    private static final int TIME_THRESHOLD = 100;

    private OnShakeListener mListener;
    private long lastUpdate;
    private Context mContext;

    public interface OnShakeListener
    {
        void onShake(SensorEvent event);
    }

    public ShakeListener(Context context) {
        lastUpdate = 0;
        mListener = null;
        mContext = context;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long currTime = System.currentTimeMillis();
            long diffTime = currTime - lastUpdate;
            if(diffTime > TIME_THRESHOLD){
                lastUpdate = currTime;
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];
                double shakeForce = Math.sqrt(x*x + y*y + z*z);
                if(shakeForce > FORCE_THRESHOLD && mListener != null)
                    mListener.onShake(event);
            }
        }
    }
}
