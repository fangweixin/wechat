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
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.team2.wechat.R;

public class NewMessage extends AppCompatActivity {
    ToggleButton Button1,Button2,Button3,Button4,Button5,Button6;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;
    boolean ischecked1,ischecked2,ischecked3,ischecked4,ischecked5,ischecked6;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        iv_back = (ImageView) findViewById(R.id.newmessage_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearLayout1=findViewById(R.id.LinearLayout1);
        linearLayout2=findViewById(R.id.LinearLayout2);
        linearLayout3=findViewById(R.id.LinearLayout3);
        Button1=findViewById(R.id.Button1);
        Button2=findViewById(R.id.Button2);
        Button3=findViewById(R.id.Button3);
        Button4=findViewById(R.id.Button4);
        Button5=findViewById(R.id.Button5);
        Button6=findViewById(R.id.Button6);
        Button1.setOnCheckedChangeListener(checked1);
        Button2.setOnCheckedChangeListener(checked2);
        Button3.setOnCheckedChangeListener(checked3);
        Button4.setOnCheckedChangeListener(checked4);
        Button5.setOnCheckedChangeListener(checked5);
        Button6.setOnCheckedChangeListener(checked6);

        SharedPreferences sharedPreferences=this.getSharedPreferences("status",MODE_PRIVATE);
        ischecked1=sharedPreferences.getBoolean("button1",true);
        ischecked2=sharedPreferences.getBoolean("button2",true);
        ischecked3=sharedPreferences.getBoolean("button3",true);
        ischecked4=sharedPreferences.getBoolean("button4",true);
        ischecked5=sharedPreferences.getBoolean("button5",true);
        ischecked6=sharedPreferences.getBoolean("button6",true);
        Button1.setChecked(ischecked1);
        Button2.setChecked(ischecked2);
        Button3.setChecked(ischecked3);
        Button4.setChecked(ischecked4);
        Button5.setChecked(ischecked5);
        Button6.setChecked(ischecked6);
        if(ischecked1) {
            Button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);

        }
        else{
            Button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
            linearLayout1.setVisibility(View.INVISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
        }
        Button2.setBackgroundDrawable(ischecked2?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        Button3.setBackgroundDrawable(ischecked3?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        Button4.setBackgroundDrawable(ischecked4?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
        if(ischecked5) {
            Button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
            linearLayout3.setVisibility(View.VISIBLE);
        }
        else{
            Button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
            linearLayout3.setVisibility(View.INVISIBLE);
        }
        Button6.setBackgroundDrawable(ischecked6?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private CompoundButton.OnCheckedChangeListener checked1=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                Button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);

            }
            else{
                Button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
                linearLayout1.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
            }
            ischecked1=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked2=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button2.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked2=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked3=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button3.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked3=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked4=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button4.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked4=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked5=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                Button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.set2));
                linearLayout3.setVisibility(View.VISIBLE);
            }
            else{
                Button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.set));
                linearLayout3.setVisibility(View.GONE);
            }
            ischecked5=b;
        }
    };
    private CompoundButton.OnCheckedChangeListener checked6=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button6.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked6=b;
        }
    };


    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("button1",ischecked1);
        editor.putBoolean("button2",ischecked2);
        editor.putBoolean("button3",ischecked3);
        editor.putBoolean("button4",ischecked4);
        editor.putBoolean("button5",ischecked5);
        editor.putBoolean("button6",ischecked6);
        editor.apply();
        super.onStop();
    }
}
