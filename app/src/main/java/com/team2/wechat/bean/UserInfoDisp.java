package com.team2.wechat.bean;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team2.wechat.ChatActivity;
import com.team2.wechat.MainActivity;
import com.team2.wechat.R;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.listener.OnClickViewUnDevelopListener;
import com.team2.wechat.user.LocalTableUser;
import com.team2.wechat.user.UserInfo;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.SerializableHashMap;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.UserTools;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class UserInfoDisp extends AppCompatActivity {
    ImageView iv_more_info_back;
    ImageView iv_more_info_menu;
    Button btn_add_user;
    TextView tv_set_name;
    Button btn_send_msg;
    HashMap<String, Object> map;
    TextView tv_user_more_info_name2;
    TextView tv_user_more_info_name;
    ImageView iv_user_more_info_profile2;
    ImageView iv_user_more_info_profile;
    TextView tv_wexinhao;
    LinearLayout ll_already_friend;
    LinearLayout ll_not_friend_yet;
    Bundle data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_disp);
        data = getIntent().getExtras();
        SerializableHashMap mymap = new SerializableHashMap();
        mymap = (SerializableHashMap)data.get("info_map");
        ll_already_friend = findViewById(R.id.ll_already_friend);
        ll_not_friend_yet = findViewById(R.id.ll_not_friend_yet);
        map = mymap.getMap();
        {  //从云端查询是否已是好友
            boolean is_friend_already = false;
            if(!is_friend_already) {
                ll_already_friend.setVisibility(View.INVISIBLE);
                ll_not_friend_yet.setVisibility(View.VISIBLE);
            }
            else {
                ll_already_friend.setVisibility(View.VISIBLE);
                ll_not_friend_yet.setVisibility(View.INVISIBLE);
            }

        }
        tv_wexinhao = findViewById(R.id.tv_wexinhao);
        tv_wexinhao.setText(map.get(TABLEuser.weixinhao).toString());
        iv_user_more_info_profile2 = findViewById(R.id.iv_user_more_info_profile2);
        iv_user_more_info_profile = findViewById(R.id.iv_user_more_info_profile);
        tv_user_more_info_name2 = findViewById(R.id.tv_user_more_info_name2);
        tv_user_more_info_name = findViewById(R.id.tv_user_more_info_name);
        ActivityCollector.addActivity(this);
        findViewById(R.id.user_info_disp_ll2).setOnClickListener(new OnClickViewUnDevelopListener());
        findViewById(R.id.tv_user_info_more).setOnClickListener(new OnClickViewUnDevelopListener());
        findViewById(R.id.rl_more_info2).setOnClickListener(new OnClickViewUnDevelopListener());
        iv_more_info_back = findViewById(R.id.iv_more_info_back);
        iv_more_info_back.setOnClickListener(new OnClickBackListener(this));
        iv_more_info_menu = findViewById(R.id.iv_more_info_menu);
        iv_more_info_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("设置备注及标签");
            }
        });
        btn_add_user = findViewById(R.id.btn_add_user);
        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成之后再个人信息界面
                //ToastUtils.showShortToast("添加好友");
                Intent intent = new Intent(UserInfoDisp.this, FriendVerify.class);
                intent.putExtra("B", map.get(TABLEuser.id).toString()); //B的id
                startActivity(intent);
            }
        });
        btn_send_msg = findViewById(R.id.btn_send_msg);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoDisp.this, ChatActivity.class);
                startActivity(intent);
                for (int i = 1; i <= 3; ++i) {
                    ActivityCollector.removeActivity(ActivityCollector.activities.get(ActivityCollector.activities.size() - 1));
                }
            }
        });
        tv_set_name = findViewById(R.id.tv_set_name);
        tv_set_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("设置备注标签");
            }
        });
    }

    private static final String TAG = "UserInfoDisp";
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 摧毁");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "开始start");
        tv_user_more_info_name2.setText(map.get(TABLEuser.name).toString());
        tv_user_more_info_name.setText(map.get(TABLEuser.name).toString());
        File path = new File(UserInfo.LocalPritruesProfile, map.get(TABLEuser.telephone) + ".JPG");
        iv_user_more_info_profile2.setImageURI(Uri.fromFile(path));
        iv_user_more_info_profile.setImageURI(Uri.fromFile(path));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "暂停pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "重新开始restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "继续resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "停止stop");
    }
}
