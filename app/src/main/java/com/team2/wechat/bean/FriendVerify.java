package com.team2.wechat.bean;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.team2.wechat.R;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.UserTools;

public class FriendVerify extends AppCompatActivity {
    private static final int COMPLETE = 0;
    Bundle data = null;
    ProgressDialog progressDialog;
    String A, B;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            ToastUtils.showToast("发送好友验证完成");
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_verify);
        B = getIntent().getStringExtra("B");
        A = UserTools.getUserInfoFromPref(FriendVerify.this, TABLEuser.id);
        (findViewById(R.id.iv_vertify_back)).setOnClickListener(new OnClickBackListener(FriendVerify.this));
        (findViewById(R.id.btn_send_vertigy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(FriendVerify.this);
                progressDialog.setMessage("发送验证中……");
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.insert2TableFriends(A, B);
                        Message msg = new Message();
                        msg.what = COMPLETE;
                        handler.sendMessage(msg);

                    }
                }).start();
            }
        });
    }
}
