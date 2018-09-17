package com.team2.wechat.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by wjsay on 2017/12/25.
 * 点击返回到上一个活动，结束当前活动
 */

public class OnClickBackListener implements View.OnClickListener {
    private  Activity activity;
    public OnClickBackListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }
}
