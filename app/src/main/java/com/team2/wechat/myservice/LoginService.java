package com.team2.wechat.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.UserTools;
import com.team2.wechat.utils.Utils;

import java.util.HashMap;

public class LoginService extends Service {
    public LoginService() {
    }
    private static final String TAG = "LoginService";
    String telephone;
    String inputPasswd;
    boolean passwdcorrect;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " 服务已被创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, " 服务开始执行");

        telephone = intent.getStringExtra("telephone");
        Log.d(TAG, " " + telephone);
        inputPasswd = intent.getStringExtra("inputPasswd");
        Log.d(TAG, " " + inputPasswd);

        //子线程访问数据库
        new Thread(new Runnable() {
            @Override
            public void run() {

                //String realpasswd = Utils.getUserPasswordByPhoneNumber(telephone);
                HashMap<String, Object> map = DBUtils.getUserInfoByPhoneNumber(telephone);
                if(map == null || map.size() == 0) {
                    passwdcorrect = false;
                    stopSelf();
                    return;
                }
                String realpasswd = map.get(TABLEuser.password).toString();
                Log.d(TAG, " 输入的密码是：" + inputPasswd + "\n真正的密码" + realpasswd);
                if(inputPasswd.equals(realpasswd)) {
                    passwdcorrect = true;
                    UserTools.writeUserInfo(LoginService.this, map);
                }
                else {
                    passwdcorrect = false;
                }
                Log.d(TAG, " 进度条对话框消失前");

                Log.d(TAG, " 进度条对话框消失后" + passwdcorrect);
//                if(passwdcorrect) {
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    //finish();
//                }
//                else {
//                    //子线程中不可也弹出吐司
//                    //Toast.makeText(Login.this, "账号或密码错误，请重新填写",
//                    //        Toast.LENGTH_SHORT).show();
//                }
                Log.d(TAG, " 子线程结束");
                stopSelf();
            }

        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //发送广播
        Intent intent=new Intent();
        intent.putExtra("passwordCorrect", passwdcorrect);
        intent.setAction("com.team2.wechat.internet.LoginService");
        sendBroadcast(intent);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
