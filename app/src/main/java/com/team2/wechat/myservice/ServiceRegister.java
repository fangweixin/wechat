package com.team2.wechat.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.team2.wechat.utils.SerializableHashMap;
import com.team2.wechat.utils.Utils;

import java.util.HashMap;

/**
 * Created by wjsay on 2017/12/7.
 *  注册时，数据库写入的服务
 */

public class ServiceRegister extends Service {
    boolean RegisterSuccess = false;
    private static final String TAG = "ServiceRegister";
    HashMap<String, Object> map;
    public ServiceRegister() {

    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " 注册服务被创建");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "注册服务开始");
        Bundle bundle = intent.getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("map");
        map = serializableHashMap.getMap();
        String passwd = "password", name = "name", gender = "gender",
                telephone = "telephone", address = "address", birthday = "birthday",
                headUrl = "headUrl", problem = "problem", ip = "ip", weixinhao = "weixinhao";
        //map.put(passwd, intent.getStringExtra(passwd));
        //map.put(name, intent.getStringExtra(name));
        //map.put(telephone, intent.getStringExtra(telephone));
        new Thread(new Runnable() {
            @Override
            public void run() {
                RegisterSuccess = Utils.writeProfile2DB(map);
                //发送注册是否成功的广播
                Intent intent=new Intent();
                Log.d(TAG, " 注册返回结果 " + RegisterSuccess);
                intent.putExtra("register_success", RegisterSuccess);
                intent.setAction("com.team2.wechat.internet.ServiceRegister");
                sendBroadcast(intent);
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
