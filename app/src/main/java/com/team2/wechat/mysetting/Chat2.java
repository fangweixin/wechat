package com.team2.wechat.mysetting;

import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.team2.wechat.R;

public class Chat2 extends AppCompatActivity {
    ToggleButton Button8,Button9;
    boolean ischecked8,ischecked9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        //命名冲突
        Button8=findViewById(R.id.Button8);
        Button9=findViewById(R.id.Button9);
        ((ImageView)findViewById(R.id.chat2_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {


            Button8.setOnCheckedChangeListener(checked8);
            Button9.setOnCheckedChangeListener(checked9);
            SharedPreferences sharedPreferences=this.getSharedPreferences("status",MODE_PRIVATE);
            ischecked8=sharedPreferences.getBoolean("button8",true);
            ischecked9=sharedPreferences.getBoolean("button9",true);
            Button8.setChecked(ischecked8);
            Button9.setChecked(ischecked9);
            Button8.setBackgroundDrawable(ischecked8?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            Button9.setBackgroundDrawable(ischecked9?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        } catch (Exception e) {

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private CompoundButton.OnCheckedChangeListener checked8= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button8.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked8=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked9=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button9.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked9=b;
        }
    };
    @Override
    protected void onStop(){
        SharedPreferences sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("button8",ischecked8);
        editor.putBoolean("button9",ischecked9);
        editor.apply();
        super.onStop();
    }
}
