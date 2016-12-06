package nb8384.cs371m.shaketilyoudrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wongk_000 on 11/11/2016.
 */

public class ProfileActivity extends AppCompatActivity
    implements ActivityLauncher.ActivityLauncherListener,
        ProfilePlayerInfoController.ControllerListener {

    private PlayerInfo playerInfo;
    private ProfileUI profileUI;
    private UpgradeList availableUpgrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        playerInfo = (PlayerInfo) intent.getSerializableExtra("PlayerInfo");

        UpgradeList temp = (UpgradeList) intent.getSerializableExtra("UpgradeList");
        if (temp != null)
            availableUpgrades = temp;
        else {
            availableUpgrades = new UpgradeList();
            availableUpgrades.populateWithAllUpgrades();
        }

        ProfileUpgradesAdapter adapter = new ProfileUpgradesAdapter(getApplicationContext(),
                playerInfo.getActiveUpgrades(), R.layout.item_upgrade_profile);

        profileUI = new ProfileUI(this, adapter);
        profileUI.setControllerListener(this);
        profileUI.setActivityLauncherListener(this);
        playerInfo.setPlayerInfoController(profileUI);
    }

    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        intent.putExtra("UpgradeList", availableUpgrades);
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

        UpgradeList temp = (UpgradeList) getIntent().getSerializableExtra("UpgradeList");
        if (temp != null)
            availableUpgrades = temp;
        else {
            availableUpgrades = new UpgradeList();
            availableUpgrades.populateWithAllUpgrades();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerInfo.unregisterPlayerInfoController();
    }
}
