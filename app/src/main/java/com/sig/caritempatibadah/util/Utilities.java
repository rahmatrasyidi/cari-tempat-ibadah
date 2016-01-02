package com.sig.caritempatibadah.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Rahmat Rasyidi Hakim on 12/10/2015.
 */
public class Utilities {

    public static boolean isConnectedNetwork(final Activity act, boolean showConfirmation) {
        if (act != null) {
            ConnectivityManager manager = (ConnectivityManager) act.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if ((activeNetwork == null) || !activeNetwork.isConnected()) {
                if (showConfirmation) {
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(act, "No internet connection", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
