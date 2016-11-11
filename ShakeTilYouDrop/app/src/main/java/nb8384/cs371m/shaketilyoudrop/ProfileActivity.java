package nb8384.cs371m.shaketilyoudrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public class ProfileActivity extends AppCompatActivity
    implements ActivityLauncher.ActivityLauncherListener,
        ProfilePlayerInfoController.ControllerListener {

    PlayerInfo playerInfo;
    ProfileUI profileUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        playerInfo = (PlayerInfo) intent.getSerializableExtra("PlayerInfo");

        UpgradesAdapter adapter = new UpgradesAdapter(getApplicationContext(), playerInfo.getActiveUpgrades());

        profileUI = new ProfileUI(this, adapter);
        profileUI.setControllerListener(this);
        profileUI.setActivityLauncherListener(this);
        playerInfo.setPlayerInfoController(profileUI);
        profileUI.username.setText(playerInfo.getUserName());
    }

    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        startActivity(intent);
    }

    @Override
    public void editUsername(EditText username){
        if(!(username.getText().toString().isEmpty())){
            playerInfo.changeUsername(username.getText().toString());
        }
        else
            Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlayerInfo newPlayerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");
        if (newPlayerInfo != null)
            playerInfo = newPlayerInfo;
        playerInfo.setPlayerInfoController(profileUI);
        profileUI.username.setText(playerInfo.getUserName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerInfo.unregisterPlayerInfoController();
    }
}
