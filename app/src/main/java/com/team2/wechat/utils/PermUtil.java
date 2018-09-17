package com.team2.wechat.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by wjsay on 12/22/2017.
 */

public class PermUtil {
    public static boolean checkPerm(Context context, String permName) {
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                permName);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

}
