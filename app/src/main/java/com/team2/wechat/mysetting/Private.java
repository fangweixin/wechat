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

public class Private extends AppCompatActivity {
    ToggleButton Button10,Button11,Button12,Button13,Button14;
    LinearLayout linearLayout;
    boolean ischecked10,ischecked11,ischecked12,ischecked13,ischecked14;
    //ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        linearLayout=findViewById(R.id.yingsi_layout);
        Button10=findViewById(R.id.Button10);
        Button11=findViewById(R.id.Button11);
        Button12=findViewById(R.id.Button12);
        Button13=findViewById(R.id.Button13);
        Button14=findViewById(R.id.Button14);
        Button10.setOnCheckedChangeListener(checked10);
        Button11.setOnCheckedChangeListener(checked11);
        Button12.setOnCheckedChangeListener(checked12);
        Button13.setOnCheckedChangeListener(checked13);
        Button14.setOnCheckedChangeListener(checked14);
        SharedPreferences sharedPreferences=this.getSharedPreferences("status",MODE_PRIVATE);
        ischecked10=sharedPreferences.getBoolean("button10",true);
        ischecked11=sharedPreferences.getBoolean("button11",true);
        ischecked12=sharedPreferences.getBoolean("button12",true);
        ischecked13=sharedPreferences.getBoolean("button13",true);
        ischecked14=sharedPreferences.getBoolean("button14",true);
        Button10.setChecked(ischecked10);
        Button11.setChecked(ischecked11);
        Button12.setChecked(ischecked12);
        Button13.setChecked(ischecked13);
        Button14.setChecked(ischecked14);
        ((ImageView)(findViewById(R.id.private_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(ischecked13){
            Button13.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
            linearLayout.setVisibility(View.VISIBLE);
        }
        else {
            Button13.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
            linearLayout.setVisibility(View.GONE);
        }
        Button10.setBackgroundDrawable(ischecked10?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        Button11.setBackgroundDrawable(ischecked11?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        Button12.setBackgroundDrawable(ischecked12?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        Button14.setBackgroundDrawable(ischecked14?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private CompoundButton.OnCheckedChangeListener checked10=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button10.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked10=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked11=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button11.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked11=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked12=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button12.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked12=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked13=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                Button13.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
                linearLayout.setVisibility(View.VISIBLE);
            }
            else {
                Button13.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
                linearLayout.setVisibility(View.GONE);
            }
            ischecked13=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked14=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button14.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked14=b;
        }
    };
    @Override
    protected void onStop(){

        SharedPreferences sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("button10",ischecked10);
        editor.putBoolean("button11",ischecked11);
        editor.putBoolean("button12",ischecked12);
        editor.putBoolean("button13",ischecked13);
        editor.putBoolean("button14",ischecked14);
        editor.apply();
        super.onStop();
    }
}
