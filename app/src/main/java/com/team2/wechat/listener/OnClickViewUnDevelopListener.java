package com.team2.wechat.listener;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team2.wechat.utils.ToastUtils;

/**
 * Created by wjsay on 2017/12/27.
 * 通用的点击了未开发控件的信息，为了证明真的
 * 有控件，而不是截图。太帅了
 */

public class OnClickViewUnDevelopListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        String dispmsg = null;
        if(v instanceof Button) {
            dispmsg = ((Button)v).getText().toString().trim();
        }
        else if(v instanceof TextView) {
           dispmsg = ((TextView)v).getText().toString().trim();
        }
        else if(v instanceof LinearLayout) {
            dispmsg = "线性布局";
        }
        else if(v instanceof RelativeLayout) {
            dispmsg = "相关布局";
        }
        ToastUtils.showToast(dispmsg);
    }
}
