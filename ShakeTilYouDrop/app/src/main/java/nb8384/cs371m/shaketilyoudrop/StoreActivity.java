package nb8384.cs371m.shaketilyoudrop;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity
        implements ActivityLauncher.ActivityLauncherListener,
        ShopPlayerInfoController.ControllerListener {

    PlayerInfo playerInfo;
    StorePageUI storeUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        playerInfo = (PlayerInfo) intent.getSerializableExtra("PlayerInfo");

        storeUI = new StorePageUI(this);
        storeUI.setControllerListener(this);
        storeUI.setActivityLauncherListener(this);

        playerInfo.setPlayerInfoController(storeUI);

    }

    @Override
    public void launchActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        intent.putExtra("PlayerInfo", playerInfo);
        startActivity(intent);

    }

    @Override
    public void onAttemptPurchase(int coins) {
        if (playerInfo.getNumCoins() >= coins)
            playerInfo.purchase(coins);
        else
            Toast.makeText(getApplicationContext(), "Not enough coins!", Toast.LENGTH_SHORT).show();
    }
}
