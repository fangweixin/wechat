package com.team2.wechat;

import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team2.bean.FriendInformationNetBean;
import com.team2.wechat.utils.NetUtils;

public class LoginActivity extends Activity {
    LinearLayout mobile_login_hint_layout;
    ImageView iv_return;
    ImageView iv_menu;
    Button login;
    EditText phonenumber;
    EditText passwd;
    boolean passwdcorrect = false;
    boolean inner;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    private SharedPreferences sp;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirstLogin = sp.getBoolean("isFirstLogin", true);
        if (!isFirstLogin) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
//		ActivityCollector.addActivity(this);
//		// Log.d(TAG, " debug01");

        mobile_login_hint_layout = (LinearLayout) findViewById(R.id.mobile_login_hint);
        iv_return = (ImageView) findViewById(R.id.returnButton);
        iv_menu = (ImageView) findViewById(R.id.menuButton);
        login = (Button) findViewById(R.id.userlogin);
        phonenumber = (EditText) findViewById(R.id.PhoneNumber);
        passwd = (EditText) findViewById(R.id.password);
        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile_login_hint_layout != null
                        && mobile_login_hint_layout.getVisibility() == View.VISIBLE)
                    mobile_login_hint_layout.setVisibility(View.GONE);
            }
        });
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {

                if (!TextUtils.isEmpty(phonenumber.getText().toString().trim())
                        && !TextUtils.isEmpty(passwd.getText().toString()
                        .trim())) {
                    login.setBackgroundColor(getResources().getColor(
                            R.color.button_color_green));
                    login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(phonenumber.getText().toString().trim())) {
                    login.setBackgroundColor(Color.parseColor("#A2E08D"));
                    login.setEnabled(false);
                }
            }
        });
        passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b
                        && mobile_login_hint_layout != null
                        && mobile_login_hint_layout.getVisibility() == View.VISIBLE) {
                    mobile_login_hint_layout.setVisibility(View.GONE);
                    // Log.d(TAG, " debug01");
                }
            }
        });
        passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {
                if (!TextUtils.isEmpty(phonenumber.getText().toString().trim())
                        && !TextUtils.isEmpty(passwd.getText().toString()
                        .trim())) {
                    login.setBackgroundColor(getResources().getColor(
                            R.color.button_color_green));
                    login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(passwd.getText().toString().trim())) {
                    login.setBackgroundColor(Color.parseColor("#A2E08D"));
                    login.setEnabled(false);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String PhoneNumber = phonenumber.getText().toString()
                        .trim();
                final String inputPasswd = passwd.getText().toString().trim();
                if (TextUtils.isEmpty(PhoneNumber)
                        || TextUtils.isEmpty(inputPasswd)) {
                    return;
                }
                final ProgressDialog loginProgressDialog = new ProgressDialog(
                        LoginActivity.this);
                loginProgressDialog.setMessage("正在加载中...");
                loginProgressDialog.show();

                /**
                 * 进行登录并上传本机IP
                 */

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("telephone", PhoneNumber);
                params.put("password", inputPasswd);
                String ip = NetUtils.getIPAddress(LoginActivity.this);
                params.put("ip", ip);
                Log.d(TAG, "onClick: 开始执行post");
                client.post ("http://139.199.38.177/php/login.php", params,
                        new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode,
                                                  Header[] headers, byte[] responseBody) {
                                Log.d(TAG, "onSuccess: 成功");
                                loginProgressDialog.dismiss();
                                try {
                                    String responseData = new String(
                                            responseBody, "utf-8");
                                    if (!responseData.equals("0")) {// 登录成功
                                        /**
                                         * 存储好友信息（id，name，telephone，weixinhao，
                                         * visible）
                                         */
                                        List<FriendInformationNetBean> friendList = new Gson()
                                                .fromJson(
                                                        responseData,
                                                        new TypeToken<List<FriendInformationNetBean>>() {
                                                        }.getType());
                                        FriendInformationNetBean me = friendList
                                                .get(0);
                                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(
                                                getApplication(), me.getId()
                                                + ".db");
                                        // TODO 需要判断此用户数据库是否已经存在，若存在则不插入好友记录
                                        SQLiteDatabase db = mySqliteOpenHelper
                                                .getWritableDatabase();

                                        StringBuffer sql = new StringBuffer(
                                                "REPLACE INTO friends(id,name,telephone,weixinhao,visible) VALUES");
                                        for (int i = 1; i < friendList.size(); i++) {
                                            FriendInformationNetBean friend = friendList
                                                    .get(i);
                                            sql.append("(" + friend.getId()
                                                    + ",'" + friend.getName()
                                                    + "','"
                                                    + friend.getTelephone()
                                                    + "','"
                                                    + friend.getWeixinhao()
                                                    + "',"
                                                    + friend.getVisible() + "),");
                                        }
                                        sql.deleteCharAt(sql.length()-1);
                                        db.execSQL(sql.toString());


                                        // 存储用户帐户密码，改变首次登录标记
                                        Editor edit = sp.edit();
                                        edit.putBoolean("isFirstLogin", false);
                                        edit.putString("telephone", PhoneNumber);
                                        edit.putString("password", inputPasswd);
                                        edit.putInt("id", me.getId());
                                        edit.putString("name", me.getName());
                                        edit.putString("weixinhao", me.getWeixinhao());
                                        edit.putInt("unReadMessageTotalCount", 0);
                                        edit.commit();

                                        // 跳转到mainActivity
                                        Intent intent = new Intent(
                                                LoginActivity.this,
                                                MainActivity.class);
                                        intent.putExtra("phone_no", phonenumber.getText().toString().trim());
                                        startActivity(intent);

                                        // 关闭登录界面
                                        finish();
                                    } else {// 登录失败
                                        Toast.makeText(LoginActivity.this,
                                                "用户名或密码错误", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    // TODO Auto-generated catch block

                                }

                            }

                            @Override
                            public void onFailure(int statusCode,
                                                  Header[] headers, byte[] responseBody,
                                                  Throwable error) {
                                Log.d(TAG, "onFailure: 失败");
                                loginProgressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "网络连接不可用，请稍后重试", Toast.LENGTH_SHORT)
                                        .show();// TODO
                            }
                        });
                Log.d(TAG, "onClick: post执行完毕");
            }
        });
    }
}
