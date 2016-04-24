package rhtds.ruralhealthtasdirectoryservices;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by John on 24/04/2016.
 */
public class Utils {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
