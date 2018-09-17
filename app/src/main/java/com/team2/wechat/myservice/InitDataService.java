package com.team2.wechat.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.team2.wechat.utils.DBUtils;

public class InitDataService extends Service {
    private static final String TAG = "InitDataService";
    public InitDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " 服务启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " 服务终止");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, " 服务开始");
        final String phone_num = intent.getStringExtra("tel");
        Log.d(TAG, " phone_no" + phone_num);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从远程服务器下载个人信息及头像到到本地
                DBUtils.writeUserInfoByPhoneNumber2LocalFromTel(InitDataService.this, phone_num);
                //Utils.getLogState(InitDataService.this);//测试
            }


        }).start();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
