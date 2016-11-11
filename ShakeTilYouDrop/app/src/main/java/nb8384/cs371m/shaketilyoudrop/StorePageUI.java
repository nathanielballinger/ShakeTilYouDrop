package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public class StorePageUI
        implements ShopPlayerInfoController, ShopUpgradeInfoController,
        ActivityLauncher, UpgradesAdapter.UpgradesAdapterListener {

    private ActivityLauncherListener activityLauncherListener;
    private ShopPlayerControllerListener playerListener;
    private ShopUpgradeControllerListener upgradeListener;

    private RecyclerView rvUpgrades;
    private UpgradesAdapter adapter;
    private Button backToGameButton;
    private TextView numCoins;

    public StorePageUI(AppCompatActivity activity, UpgradesAdapter upgradesAdapter) {
        adapter = upgradesAdapter;
        adapter.setAdapterListener(this);
        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        rvUpgrades = (RecyclerView) activity.findViewById(R.id.rvUpgrades);
        backToGameButton = (Button) activity.findViewById(R.id.backToGame);
        numCoins = (TextView) activity.findViewById(R.id.numCoinsEditable);

    }

    private void initViewValues(AppCompatActivity activity) {
        rvUpgrades.setAdapter(adapter);
        rvUpgrades.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void setViewListeners(AppCompatActivity activity) {

        backToGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLauncherListener.launchActivity(MainActivity.class);
            }
        });

    }

    @Override
    public void setActivityLauncherListener(ActivityLauncherListener listener) {
        activityLauncherListener = listener;
    }

    @Override
    public void setPlayerControllerListener(ShopPlayerControllerListener controllerListener) {
        playerListener = controllerListener;
    }

    @Override
    public void setUpgradeControllerListener(ShopUpgradeControllerListener controllerListener) {
        upgradeListener = controllerListener;
    }

    @Override
    public void onPlayerInfoInit(PlayerInfo playerInfo) {
        onPlayerInfoUpdate(playerInfo);
    }

    @Override
    public void onPlayerInfoUpdate(PlayerInfo playerInfo) {
        numCoins.setText(Integer.toString(playerInfo.getNumCoins()));
    }

    @Override
    public void onUpgradeInfoUpdate(Upgrade upgrade) {
        // upgrade state was changed, already taken care of in adapter
        // use this to update any other UI elements besides the RecyclerView
    }

    @Override
    public void onPurchaseButtonPressed(Upgrade upgrade) {
        if (playerListener.onAttemptPurchase(upgrade))
            upgradeListener.purchase(upgrade);
    }
}
