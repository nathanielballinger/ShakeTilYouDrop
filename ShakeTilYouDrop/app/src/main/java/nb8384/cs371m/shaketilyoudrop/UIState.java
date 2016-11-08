package nb8384.cs371m.shaketilyoudrop;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nathaniel on 10/10/2016.
 */

public class UIState extends MainActivity{
    private Map<Integer, View> uiViews;
    public UIState() {
        uiViews = new HashMap<Integer, View>();
    }

    public void addView(View view) {
        uiViews.put(view.getId(), view);
    }

    public void shake() {
        TextView numShakesText = (TextView) uiViews.get(R.id.numShakesText);
        int oldNumShakes;
        try {
            oldNumShakes = Integer.parseInt(numShakesText.getText().toString());
        }
        catch (NumberFormatException e) {
            oldNumShakes = 0;
        }
        ((TextView)uiViews.get(R.id.numShakesText)).setText(Integer.toString(++oldNumShakes));
    }
}
