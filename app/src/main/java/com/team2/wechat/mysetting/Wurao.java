package com.team2.wechat.mysetting;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.team2.wechat.R;

public class Wurao extends AppCompatActivity {
    ToggleButton Button7;
    LinearLayout linearLayout;
    boolean ischecked7;
    ActionBar actionBar;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wurao);
        iv_back = (ImageView)findViewById(R.id.wurao_back);
        iv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayout=findViewById(R.id.time_layout);
        Button7=findViewById(R.id.Button7);
        Button7.setOnCheckedChangeListener(checked7);
        SharedPreferences sharedPreferences=this.getSharedPreferences("status",MODE_PRIVATE);
        ischecked7=sharedPreferences.getBoolean("button7",true);
        Button7.setChecked(ischecked7);
        if(ischecked7) {
            Button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
            linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            Button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
            linearLayout.setVisibility(View.GONE);
        }
    }

    private CompoundButton.OnCheckedChangeListener checked7=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                Button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
                linearLayout.setVisibility(View.VISIBLE);
            }
            else{
                Button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
                linearLayout.setVisibility(View.GONE);
            }
            ischecked7=b;
        }
    };
    @Override
    protected void onStop()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("button7",ischecked7);
        editor.apply();
        super.onStop();
    }
}
