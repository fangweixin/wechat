package com.team2.wechat.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.team2.wechat.MainActivity;

/**
 * Created by wjsay on 2017/12/27.
 */

public class MyProcessDialog {
    ProgressDialog processDialog;
    public MyProcessDialog () {
        processDialog = new ProgressDialog(MainActivity.context);
    }
    public MyProcessDialog (Context context) {
        processDialog = new ProgressDialog(context);
    }

    public ProgressDialog getProcessDialog() {
        processDialog.setCancelable(false);
        return processDialog;
    }
}
