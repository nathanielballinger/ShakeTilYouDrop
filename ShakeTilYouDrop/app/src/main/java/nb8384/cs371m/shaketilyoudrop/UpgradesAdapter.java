package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Nathaniel on 11/10/2016.
 */

public class UpgradesAdapter extends RecyclerView.Adapter<UpgradesAdapter.ViewHolder>
        implements UpgradeInfoController {

    public interface UpgradesAdapterListener {
        void onPurchaseButtonPressed(Upgrade upgrade);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView upgradeIcon;
        public TextView upgradeName;
        public Button purchaseButton;

        public ViewHolder(View itemView) {
            super(itemView);

            upgradeIcon = (ImageView) itemView.findViewById(R.id.upgradeIconImage);
            upgradeName = (TextView) itemView.findViewById(R.id.upgradeNameText);
            purchaseButton = (Button) itemView.findViewById(R.id.purchaseButton);
        }
    }



    private List<Upgrade> mUpgrades;
    private Context mContext;
    private UpgradesAdapterListener listener;
    private int layoutResourceID;

    public UpgradesAdapter(Context context, UpgradeList upgradeList, int layoutResourceID) {
        mContext = context;
        mUpgrades = upgradeList.getList();
        this.layoutResourceID = layoutResourceID;
    }


    public void setAdapterListener(UpgradesAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onUpgradeInfoUpdate(Upgrade upgrade) {
        for (int i = 0; i < mUpgrades.size(); ++i) {
            if (mUpgrades.get(i).getName().equals(upgrade.getName()))
                notifyItemChanged(i);
        }
    }

    @Override
    public UpgradesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View upgradeView = inflater.inflate(layoutResourceID, parent, false);

        return new ViewHolder(upgradeView);
    }

    @Override
    public void onBindViewHolder(UpgradesAdapter.ViewHolder viewHolder, int position) {
        final Upgrade upgrade = mUpgrades.get(position);
        viewHolder.upgradeName.setText(upgrade.getName());

        if (viewHolder.purchaseButton != null) {

            viewHolder.purchaseButton.setText(upgrade.getPrice() + " coins");


            viewHolder.purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onPurchaseButtonPressed(upgrade);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mUpgrades.size();
    }


}
