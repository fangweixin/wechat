package com.team2.wechat.mysetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team2.wechat.R;
import com.team2.wechat.user.UserInfo;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.UserTools;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import static com.team2.wechat.utils.ActivityCollector.finishAll;

//by wjsay

public class Setting extends AppCompatActivity {
    private static final String TAG = "Setting";
    ImageView iv_setting_back;
    TextView tv_tuichu;
    TextView tv_wurao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Log.d(TAG, "创建create");
        ActivityCollector.addActivity(this);
        iv_setting_back = (ImageView) findViewById(R.id.myseting_back);
        tv_tuichu = (TextView) findViewById(R.id.setting10);//这命名，不服不行
        tv_wurao = (TextView) findViewById(R.id.setting2);
        iv_setting_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String passwd = "password", name = "name", gender = "gender",
                        telephone = "telephone", address = "address", birthday = "birthday",
                        headUrl = "headUrl", problem = "problem";
                HashMap<String, Object> map = new HashMap<>();
                map.put("password", "");
                map.put("telephone", "");
                map.put("name", "");
                map.put("logState", "false");
                UserTools.writeUserInfo(Setting.this, map);
                ActivityCollector.finishAll();
            }
        });
//        tv_wurao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Setting.this, Wurao.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "开始start");

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

    public void clicksetting1(View view) {
        Intent call_intent=new Intent(Setting.this,NewMessage.class);
        startActivity(call_intent);
    }
    public void clicksetting2(View view) {
        Intent intent=new Intent(this, Wurao.class);
        startActivity(intent);
    }
    public void clicksetting3(View view) {
        Intent intent=new Intent(Setting.this, Chat2.class);
        startActivity(intent);
    }
    public void clicksetting4(View view) {
        Intent intent=new Intent(Setting.this,Private.class);
        startActivity(intent);
    }
    public void clicksetting5(View view) {
        Intent intent=new Intent(Setting.this,Common.class);
        startActivity(intent);
    }
    public void clicksetting6(View view) {
        Intent intent=new Intent(Setting.this,Safe.class);
        startActivity(intent);
    }
    public void clicksetting7(View view) {
        Intent intent=new Intent(Setting.this,About.class);
        startActivity(intent);
    }
    public void clicksetting8(View view) {
        Toast.makeText(Setting.this, "帮助与反馈模块待开发", Toast.LENGTH_SHORT).show();
    }
    public void clicksetting9(View view) {
        Toast.makeText(Setting.this, "插件模块待开发", Toast.LENGTH_SHORT).show();
    }

}
