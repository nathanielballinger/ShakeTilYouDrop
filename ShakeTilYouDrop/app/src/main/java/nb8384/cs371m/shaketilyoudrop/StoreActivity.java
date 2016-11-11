package nb8384.cs371m.shaketilyoudrop;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity
        implements ActivityLauncher.ActivityLauncherListener,
        ShopPlayerInfoController.ShopPlayerControllerListener,
        ShopUpgradeInfoController.ShopUpgradeControllerListener {

    PlayerInfo playerInfo;
    AvailableUpgrades availableUpgrades;
    StorePageUI storeUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        playerInfo = (PlayerInfo) intent.getSerializableExtra("PlayerInfo");

        AvailableUpgrades temp = (AvailableUpgrades) intent.getSerializableExtra("AvailableUpgrades");
        if (temp != null)
            availableUpgrades = temp;
        else
            availableUpgrades = new AvailableUpgrades();

        UpgradesAdapter adapter = new UpgradesAdapter(getApplicationContext(), availableUpgrades);
        availableUpgrades.registerController(adapter);

        storeUI = new StorePageUI(this, adapter);

        storeUI.setPlayerControllerListener(this);
        storeUI.setUpgradeControllerListener(this);
        storeUI.setActivityLauncherListener(this);

        playerInfo.setPlayerInfoController(storeUI);
        availableUpgrades.registerController(storeUI);

    }

    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        intent.putExtra("AvailableUpgrades", availableUpgrades);
        startActivity(intent);

    }

    @Override
    public boolean onAttemptPurchase(Upgrade upgrade) {
        boolean success = playerInfo.getNumCoins() >= upgrade.getPrice();
        if (success)
            playerInfo.purchase(upgrade);
        else
            Toast.makeText(getApplicationContext(), "Not enough coins!", Toast.LENGTH_SHORT).show();

        return success;
    }

    @Override
    public void purchase(Upgrade upgrade) {
        upgrade.purchase();
    }
}
