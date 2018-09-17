package com.team2.wechat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.team2.wechat.R;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.PermUtil;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    Button login;
    Button register;
    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //加入活动栈
        ActivityCollector.addActivity(this);
        //获取登录状态
        boolean isLogIn = Utils.getLogState(this);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
        if(!PermUtil.checkPerm(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //ToastUtils.showShortToast("对不起，没有该权限，软件无法正常运行");
            Toast.makeText(SplashActivity.this, "对不起，没有该权限，软件无法正常运行",
                    Toast.LENGTH_SHORT).show();
            //ActivityCollector.finishAll();
        }
        //Log.v(TAG, " 登录状态" + isLogIn);
        if(isLogIn) {
            //主界面（微信，通讯录，发现，我）
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            ActivityCollector.finishAll();
        }
        else {
            //进入登录注册界面
            FrameLayout start_layout = (FrameLayout) findViewById(R.id.start_layout);
            start_layout.setVisibility(View.VISIBLE);
            login = (Button)findViewById(R.id.button_login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(SplashActivity.this, Login.class);
                    //重构一下
                    //intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            register = (Button)findViewById(R.id.button_register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(SplashActivity.this, Register.class);
                    startActivity(intent);
                    Log.d(TAG, " 启动注册Activity");
                }
            });

        }


    }
}
