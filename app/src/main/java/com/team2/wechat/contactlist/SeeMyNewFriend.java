package com.team2.wechat.contactlist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team2.wechat.R;
import com.team2.wechat.bean.AddUser;
import com.team2.wechat.bean.UserSearch;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.FileUtils;
import com.team2.wechat.utils.LocalDBUtils;
import com.team2.wechat.utils.PermUtil;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.UserTools;

import org.w3c.dom.Text;

public class SeeMyNewFriend extends AppCompatActivity {
    private static final String TAG = "SeeMyNewFriend";
    LinearLayout ll_my_new_friend_vertify;
    Intent intent = new Intent();
    private final int ADDUSEROK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_new_friend);
        findViewById(R.id.tv_add_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMyNewFriend.this, AddUser.class);
                startActivity(intent);
            }

        });
        findViewById(R.id.iv_see_my_new_friend_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.rl_see_my_friend_add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMyNewFriend.this, UserSearch.class);
                startActivity(intent);
            }
        });
        //新开线程，利用本地数据库，优化相应时间
        String sql = "select * from newuser";
        Cursor cursor = LocalDBUtils.getQueryResult(sql, null);
        ll_my_new_friend_vertify = findViewById(R.id.ll_my_new_friend_vertify);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                final String A = UserTools.getUserInfoFromPref(this, "id");
                final String B = cursor.getString(cursor.getColumnIndex(TABLEuser.id));
                final String name = cursor.getString(cursor.getColumnIndex(TABLEuser.name));
                final String tel = cursor.getString(cursor.getColumnIndex(TABLEuser.telephone));
                RelativeLayout relativeLayout = (RelativeLayout)LayoutInflater.from(this)
                        .inflate(R.layout.layout_my_new_friend_vertify_apply, ll_my_new_friend_vertify, false);
                ((TextView)relativeLayout.findViewById(R.id.tv_newuser_name)).setText(name);
                ((ImageView)relativeLayout.findViewById(R.id.iv_new_apply_friend_profile))
                        .setImageURI(FileUtils.getImageUriFromPath(tel));
                final Button btn_agree = relativeLayout.findViewById(R.id.btn_agree);
                btn_agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            String a_id = A, b_id = B;
                            String phone = tel;
                            @Override
                            public void run() {
                                //修改云端数据库
                                DBUtils.update2TableFriends(a_id, b_id, 1);
                                DBUtils.update2TableFriends(b_id, a_id, 1);
                                SQLiteDatabase db = LocalDBUtils.getSQLiteDatabase();
                                //同时更新本地数据库
                                db.execSQL("delete from newuser where id = " + b_id);
                                Log.d(TAG, "run: " + a_id + "\t" + b_id);
                            }
                        }).start();
                        intent.putExtra("id", B);
                        intent.putExtra("tel", tel);
                        intent.putExtra("name", name);
                        btn_agree.setText("已添加");
                        btn_agree.setTextColor(getResources().getColor(R.color.gray));
                        btn_agree.setBackgroundColor(getResources().getColor(R.color.touming));
                    }
                });
                ll_my_new_friend_vertify.addView(relativeLayout, 0);
            }
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();//不能要super()
        Log.d(TAG, "onBackPressed: " + intent.getStringExtra("id"));
        Log.d(TAG, "onBackPressed: " + intent.getStringExtra("name"));
        Log.d(TAG, "onBackPressed: " + intent.getStringExtra("tel"));
        setResult(RESULT_OK, intent);
        finish();
    }
}
