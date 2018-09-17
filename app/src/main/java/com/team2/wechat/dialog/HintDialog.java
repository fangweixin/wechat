package com.team2.wechat.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;

import java.util.List;

/**
 * Created by wjsay on 2017/11/24.
 */

public class HintDialog  {
    AlertDialog.Builder alertDialog;
    public HintDialog (Context context, String msg) {
        alertDialog = new AlertDialog.Builder(context); //对话框需要上下文
        alertDialog.setMessage(msg);
    }
    AlertDialog.Builder getAlertDialog(Context context, String msg) {
        return alertDialog;
    }
}
