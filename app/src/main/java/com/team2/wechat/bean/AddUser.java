package com.team2.wechat.bean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team2.wechat.R;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.listener.OnClickViewUnDevelopListener;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.UserTools;

public class AddUser extends AppCompatActivity {
    ImageView iv_add_user_back;
    private int AddUserCommitSuicide = 1;
    TextView tv_myweixinhao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ActivityCollector.addActivity(this);
        iv_add_user_back = findViewById(R.id.iv_add_user_back);
        tv_myweixinhao = findViewById(R.id.tv_myweixinhao);
        tv_myweixinhao.setText("我的微信号:" + UserTools.getUserInfoFromPref(this, TABLEuser.weixinhao));
        ((LinearLayout)findViewById(R.id.ll_add_user_1)).setOnClickListener(new OnClickViewUnDevelopListener());
        ((LinearLayout)findViewById(R.id.ll_add_user_2)).setOnClickListener(new OnClickViewUnDevelopListener());
        ((LinearLayout)findViewById(R.id.ll_add_user_3)).setOnClickListener(new OnClickViewUnDevelopListener());
        ((LinearLayout)findViewById(R.id.ll_add_user_4)).setOnClickListener(new OnClickViewUnDevelopListener());
        ((LinearLayout)findViewById(R.id.ll_add_user_5)).setOnClickListener(new OnClickViewUnDevelopListener());
        iv_add_user_back.setOnClickListener(new OnClickBackListener(this));
        ((RelativeLayout)findViewById(R.id.rl_add_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUser.this, UserSearch.class);
                startActivity(intent);
            }
        });
    }


}
