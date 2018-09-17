package com.team2.wechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.team2.wechat.user.UserInfo;
import com.team2.wechat.values.ConstValuses;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by wjsay on 2017/12/9.
 */

public class UserTools {
    /**
     * 写入用户信息
     */
    public static boolean writeUserInfo (Context context, HashMap<String, Object> map) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ConstValuses.DATAFILE, Context.MODE_PRIVATE).edit();
        for (String key: map.keySet()) {
            editor.putString(key, (String)(map.get(key)));
        }

        editor.apply();

        return true;
    }

    /**
     *从本地datafile.xml读出用户信息
     */
    public static HashMap<String, Object> readUserInfo(Context context) {
        SharedPreferences pref = context.getSharedPreferences(ConstValuses.DATAFILE, MODE_PRIVATE);
        String passwd = pref.getString("password", null);
        String telephone = pref.getString("telephone", null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", passwd);
        map.put("telephone", telephone);
        return map;
    }
    /**
     * 从本地userprofile.xml读取用户信息
     */
    public static String getUserInfoFromPref(Context context, String what) {
        SharedPreferences pref = context.getSharedPreferences(UserInfo.userprofile, MODE_PRIVATE);
        return pref.getString(what, null);
    }

}
