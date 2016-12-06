package nb8384.cs371m.shaketilyoudrop;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends FragmentActivity
        implements ActivityController.ActivityControllerListener,
        MainGamePlayerInfoController.MainPlayerControllerListener,
        LoginDialogFragment.LoginDialogListener {

    public static final int REQUEST_CODE = 1;

    private LoginDialogFragment dialogFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SensorManager mSensorManager;
    private Sensor motionSensor;
    private ShakeListener shakeListener;
    private MainGameUI gameUI;
    private PlayerInfo playerInfo;
    private UpgradeList upgradeList;
    private long time = 0;
    private boolean loggedIn = false;
    Handler handle;

    String username = "Default Username"; //Dummy Username!!!!!!! Change later
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handle = new Handler();
        handle.postDelayed(realTime, 1000);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    loggedIn = false;
                }
                else
                    loggedIn = true;
            }
        };

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
        if (!loggedIn) {
            dialogFragment = new LoginDialogFragment();
            dialogFragment.setLoginDialogListener(this);
            mAuth.addAuthStateListener(mAuthListener);
            dialogFragment.show(getFragmentManager(),"FirebaseLogin");
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(shakeListener);
        playerInfo.unregisterPlayerInfoController();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onCreateAccountClick(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(getApplicationContext(),
                                    "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Account Created!", Toast.LENGTH_SHORT).show();
                            dialogFragment.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onLoginClick(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(getApplicationContext(),
                                    "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Login Succesful!", Toast.LENGTH_SHORT).show();
                            dialogFragment.dismiss();
                        }
                    }
                });
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
