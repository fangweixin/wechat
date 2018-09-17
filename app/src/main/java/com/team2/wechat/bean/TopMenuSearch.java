package com.team2.wechat.bean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.team2.wechat.R;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.utils.ActivityCollector;

public class TopMenuSearch extends AppCompatActivity {
    ImageView iv_search_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_menu_search);
        ActivityCollector.addActivity(this);
        iv_search_back = findViewById(R.id.iv_search_back);
        iv_search_back.setOnClickListener(new OnClickBackListener(this));
    }
}
