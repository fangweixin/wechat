package com.team2.wechat.mysetting;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.team2.wechat.R;

public class Common extends AppCompatActivity {
    ToggleButton Button15;
    boolean ischecked15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        Button15=findViewById(R.id.Button15);
        Button15.setOnCheckedChangeListener(checked15);
        SharedPreferences sharedPreferences=this.getSharedPreferences("status",MODE_PRIVATE);
        ischecked15=sharedPreferences.getBoolean("button15",false);
        Button15.setChecked(ischecked15);
        Button15.setBackgroundDrawable(ischecked15?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private CompoundButton.OnCheckedChangeListener checked15=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Button15.setBackgroundDrawable(b?getResources().getDrawable(R.drawable.set2):getResources().getDrawable(R.drawable.set));
            ischecked15=b;
        }
    };
    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("button15",ischecked15);
        editor.apply();
        super.onStop();
    }
}

