package br.ufc.ubicomp.mihealth.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vladymirbezerra on 20/04/15.
 */
public class NetManager {

    public static boolean isOnline(Context context) {
        NetworkInfo activeNetwork = NetManager.netInfo(context);
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static boolean isWifi(Context context) {
        return NetManager.netInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static NetworkInfo netInfo(Context context) {
        return ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }
}
