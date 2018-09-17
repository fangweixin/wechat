package com.team2.wechat.comments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.team2.wechat.R;
import com.team2.wechat.Register;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.user.UserInfo;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.FileUtils;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.UserTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.team2.wechat.Register.CHOOSE_PHOTO;

public class MomentsActivity extends AppCompatActivity {
    private ArrayList<MomentItem> momentItems = new ArrayList<>();
    MyAdapter adapter;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0) {
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();
        ToastUtils.showLongToast("加载中，请稍后……");
        initList();
         adapter = new MyAdapter(momentItems);
        RecyclerView recyclerView = findViewById(R.id.Moments_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(MomentsActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.Moments_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.Moments_camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //秦文杰修改
                //Toast.makeText(MomentsActivity.this, "正在全力开发中...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MomentsActivity.this, SendMoments.class);
                startActivity(intent);


            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.Moments_swipe_fresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorDodgeBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MomentsActivity.this, "正在刷新", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }
    public void initList(){
        Uri imageUir = FileUtils.getImageUriFromPath(UserTools.getUserInfoFromPref(this, TABLEuser.telephone));
        Resources resources = getResources();
        MomentItem item = new MomentItem(null, null, null, null,
                BitmapFactory.decodeResource(resources, R.drawable.playboy_jianbian_update1),
                BitmapFactory.decodeResource(resources, R.drawable.head));
        item = new MomentItem(null, null, null, null,
                BitmapFactory.decodeResource(resources, R.drawable.droid),
                BitmapFactory.decodeFile(
                        UserInfo.LocalPritruesProfile + UserTools.getUserInfoFromPref(this, TABLEuser.telephone) + ".JPG"));
        item.setMomentTopName(UserTools.getUserInfoFromPref(this, TABLEuser.name));
        momentItems.add(item);
        //adapter.notifyDataSetChanged();
        String text = "天天都需要你爱\n" +
                "我的心思由你猜\n" +
                "I love you\n" +
                "我就是要你让我每天都精彩\n" +
                "天天把它挂嘴边\n" +
                "《不得不爱》音乐曲谱\n" +
                "《不得不爱》音乐曲谱\n" +
                "到底什么是真爱\n" +
                "I love you\n" +
                "到底有几分说得比想像更快\n" +
                "是我们感情丰富太慷慨";

//        for(int i = 0; i < 7; i++){
//            item = new MomentItem("Eric " + i, text, BitmapFactory.decodeResource(resources, R.drawable.head),
//                    BitmapFactory.decodeResource(resources, R.drawable.lion_in_the_sun), null, null);
//            momentItems.add(item);
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                momentItems.addAll(DBUtils.getMoments());
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();
    }

}
