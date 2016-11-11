package nb8384.cs371m.shaketilyoudrop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity
        implements ActivityLauncher.ActivityLauncherListener,
        ShopPlayerInfoController.ShopPlayerControllerListener,
        ShopUpgradeInfoController.ShopUpgradeControllerListener {

    PlayerInfo playerInfo;
    UpgradeList upgradeList;
    StorePageUI storeUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        playerInfo = (PlayerInfo) intent.getSerializableExtra("PlayerInfo");

        UpgradeList temp = (UpgradeList) intent.getSerializableExtra("UpgradeList");
        if (temp != null)
            upgradeList = temp;
        else
            upgradeList = new UpgradeList();

        UpgradesAdapter adapter = new UpgradesAdapter(getApplicationContext(), upgradeList);
        upgradeList.registerController(adapter);

        storeUI = new StorePageUI(this, adapter);

        storeUI.setPlayerControllerListener(this);
        storeUI.setUpgradeControllerListener(this);
        storeUI.setActivityLauncherListener(this);

        playerInfo.setPlayerInfoController(storeUI);
        upgradeList.registerController(storeUI);

    }

    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        intent.putExtra("UpgradeList", upgradeList);
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
