package com.mc.tshirt.tshirtsize.ds;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by ninaad on 5/1/18.
 */
public class Utilities {

    public static boolean isNetworkAvailable(Context mContext) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//.getActiveNetworkInfo();
        if (mNetworkInfo == null || !mNetworkInfo.isConnectedOrConnecting())
            Toast.makeText(mContext, "Network Not Available", Toast.LENGTH_SHORT).show();
        return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
    }

}
