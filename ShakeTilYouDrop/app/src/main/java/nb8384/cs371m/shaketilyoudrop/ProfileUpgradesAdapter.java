package nb8384.cs371m.shaketilyoudrop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wongk_000 on 12/6/2016.
 */

public class ProfileUpgradesAdapter extends RecyclerView.Adapter<ProfileUpgradesAdapter.ViewHolder>
    implements UpgradeInfoController{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView upgradeIcon;
        public TextView upgradeName;

        public ViewHolder(View itemView) {
            super(itemView);
            upgradeIcon = (ImageView) itemView.findViewById(R.id.upgradeIconImage);
            upgradeName = (TextView) itemView.findViewById(R.id.upgradeNameText);
        }
    }

    private List<Upgrade> mUpgrades;
    private Context mContext;
    private int layoutResourceID;

    public ProfileUpgradesAdapter(Context context, UpgradeList upgradeList, int layoutResourceID) {
        mContext = context;
        mUpgrades = upgradeList.getList();
        this.layoutResourceID = layoutResourceID;
    }

    @Override
    public void onUpgradeInfoUpdate(Upgrade upgrade) {
        for (int i = 0; i < mUpgrades.size(); ++i) {
            if (mUpgrades.get(i).getName().equals(upgrade.getName()))
                notifyItemChanged(i);
        }
    }

    @Override
    public ProfileUpgradesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View upgradeView = inflater.inflate(layoutResourceID, parent, false);

        return new ViewHolder(upgradeView);
    }

    @Override
    public void onBindViewHolder(ProfileUpgradesAdapter.ViewHolder viewHolder, int position) {
        final Upgrade upgrade = mUpgrades.get(position);
        viewHolder.upgradeName.setText(upgrade.getName());
    }

    @Override
    public int getItemCount() {
        return mUpgrades.size();
    }
}
