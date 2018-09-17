package com.team2.wechat.bean;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mysql.jdbc.StringUtils;
import com.team2.wechat.R;
import android.app.ProgressDialog;
import com.team2.wechat.listener.OnClickBackListener;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.SerializableHashMap;

import java.util.HashMap;

//AddUser -> user_search ; 准备到UserInfoDisp
public class UserSearch extends AppCompatActivity {
    private final int Exixt = 1, Not_Exist = 0;
    private static final String TAG = "UserSearch";
    private String UserInput;
    EditText et_user_search;
    RelativeLayout rl_search_result;
    TextView tv_user_input;
    ImageView iv_search_user_back;
    RelativeLayout rl_search_result_null;
    TextView tv_user_input_content;
    HashMap<String, Object> map = null;
    ProgressDialog progressDialog;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progressDialog.dismiss();
            switch (msg.what) {
                case Exixt:
                    rl_search_result_null.setVisibility(View.INVISIBLE);
                    rl_search_result.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(UserSearch.this, UserInfoDisp.class);
                    Bundle bundle = new Bundle();
                    SerializableHashMap mymap = new SerializableHashMap();
                    mymap.setMap(map);
                    bundle.putSerializable("info_map", mymap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case Not_Exist:
                    tv_user_input_content.setText(et_user_search.getText());
                    rl_search_result_null.setVisibility(View.VISIBLE);
                    rl_search_result.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        ActivityCollector.addActivity(this);

        iv_search_user_back = findViewById(R.id.iv_search_user_back);
        iv_search_user_back.setOnClickListener(new OnClickBackListener(this));
        et_user_search = (EditText)findViewById(R.id.et_user_search);
        rl_search_result_null = findViewById(R.id.rl_search_result_null);
        tv_user_input_content = findViewById(R.id.tv_user_input_content);
        rl_search_result = findViewById(R.id.rl_search_result);
        et_user_search.setFocusable(true);
        et_user_search.setFocusableInTouchMode(true);
        et_user_search.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        rl_search_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_input_str = et_user_search.getText().toString().trim();
                //可根据手机号或微信号搜索，算了，目前只能通过手机号查询用户
                progressDialog = new ProgressDialog(UserSearch.this);
                Log.d(TAG, "onClick: 点击按钮");
                progressDialog.setMessage("正在查找联系人……");
                Log.d(TAG, "onClick: 设置信息");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //yes, make it. 第一次使用handler传递消息成功
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map = DBUtils.getUserInfoByPhoneNumber(user_input_str);
                        Message msg = new Message();
                        if(map != null && map.size() > 0) {
                            msg.what = Exixt;
                        }
                        else {
                            msg.what = Not_Exist;
                        }
                        handler.sendMessage(msg);
                    }

                }).start();

            }
        });
        tv_user_input = findViewById(R.id.tv_user_input);
        et_user_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean blank = isBlank_UesrSearchText();
                //重新输入，需要让rl_search_result_null不可见
                rl_search_result_null.setVisibility(View.INVISIBLE);
                if(blank) {
                    rl_search_result.setVisibility(View.INVISIBLE);
                }
                else {
                    rl_search_result.setVisibility(View.VISIBLE);
                    tv_user_input.setText(UserInput);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private boolean isBlank_UesrSearchText() {
        UserInput = et_user_search.getText().toString().trim();
        boolean state = StringUtils.isNullOrEmpty(UserInput);
        return state;
    }
}
