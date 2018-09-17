package com.team2.wechat.utils;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjsay on 2017/12/10.
 */

public class ActivityCollector {
    //存储Activity的List
    public static List<Activity> activities = new ArrayList<Activity>();

    //添加Activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移出Activity
    public static void removeActivity(Activity activity) {
        activity.finish();
        activities.remove(activity);
    }

    //销毁所有Activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
