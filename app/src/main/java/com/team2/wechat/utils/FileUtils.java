package com.team2.wechat.utils;

import android.content.Context;
import android.net.Uri;

import com.team2.wechat.MainActivity;
import com.team2.wechat.user.UserInfo;

import java.io.File;

/**
 * Created by wjsay on 12/22/2017.
 */

public class FileUtils {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri id2uri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + MainActivity.context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
    public static Uri getImageUriFromPath(String tel) {
        File path = new File(UserInfo.LocalPritruesProfile, tel + ".JPG");
        return Uri.fromFile(path);
    }
}
