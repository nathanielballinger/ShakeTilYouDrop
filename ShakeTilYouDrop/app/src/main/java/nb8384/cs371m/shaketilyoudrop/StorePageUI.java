package nb8384.cs371m.shaketilyoudrop;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Nathaniel on 11/9/2016.
 */

public class StorePageUI implements ShopPlayerInfoController, ActivityLauncher {

    private ActivityLauncherListener activityLauncherListener;
    private ControllerListener controllerListener;

    private Button purchaseButton, backToGameButton, addCoinButton;
    private TextView numCoins;

    public StorePageUI(AppCompatActivity activity) {
        findViewsById(activity);
        initViewValues(activity);
        setViewListeners(activity);
    }

    private void findViewsById(AppCompatActivity activity) {
        purchaseButton = (Button) activity.findViewById(R.id.purchaseTest);
        backToGameButton = (Button) activity.findViewById(R.id.backToGame);
        numCoins = (TextView) activity.findViewById(R.id.numCoinsEditable);
        addCoinButton = (Button) activity.findViewById(R.id.addCoinButton);

    }

    private void initViewValues(AppCompatActivity activity) {

    }

    private void setViewListeners(AppCompatActivity activity) {
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerListener.onAttemptPurchase(1);
            }
        });

        backToGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLauncherListener.launchActivity(MainActivity.class);
            }
        });

        addCoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerListener.onAttemptPurchase(-1);
            }
        });
    }

    @Override
    public void setActivityLauncherListener(ActivityLauncherListener listener) {
        activityLauncherListener = listener;
    }

    @Override
    public void setControllerListener(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    @Override
    public void onPlayerInfoUpdate(PlayerInfo playerInfo) {
        numCoins.setText(Integer.toString(playerInfo.getNumCoins()));
    }
}
