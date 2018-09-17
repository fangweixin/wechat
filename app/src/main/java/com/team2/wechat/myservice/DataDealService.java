package com.team2.wechat.myservice;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import com.team2.wechat.MainActivity;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.LocalDBUtils;
import com.team2.wechat.utils.UserTools;
import com.team2.wechat.values.ConstValuses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataDealService extends Service {
    public DataDealService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = LocalDBUtils.getSQLiteDatabase();
                HashMap<String, String> map = new HashMap<>();
                String tel = intent.getStringExtra("tel");
                //String id = UserTools.getUserInfoFromPref(MainActivity.context, TABLEuser.id);
                String id = DBUtils.getUserIdFromCloudDBByPhoneNumber(tel);
                Set<String> set = DBUtils.getNewFriendsIDFromCDBbyMyID(id);
                if(set.size() > 0) {
                    for (String it : set) {
                        map = DBUtils.getUserInfoFromCloudDBById(it);
                        ContentValues values = new ContentValues();
                        for (String key : map.keySet()) {
                            values.put(key, map.get(key));
                        }
                        try {
                            db.insert(ConstValuses.LOCAL_DB_NEWUSER, null, values);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
