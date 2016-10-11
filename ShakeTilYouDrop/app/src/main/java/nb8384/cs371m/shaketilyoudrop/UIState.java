package nb8384.cs371m.shaketilyoudrop;

import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nathaniel on 10/10/2016.
 */

public class UIState {
    private Map<Integer, View> uiViews;
    public UIState() {
        uiViews = new HashMap<Integer, View>();
    }

    public void addView(View view) {
        uiViews.put(view.getId(), view);
    }

    public void addShake() {
        TextView numShakesText = (TextView) uiViews.get(R.id.numShakesText);
        int oldNumShakes = Integer.parseInt(numShakesText.getText().toString());
        ((TextView)uiViews.get(R.id.numShakesText)).setText(Integer.toString(++oldNumShakes));
    }
}
