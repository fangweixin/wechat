package com.team2.wechat.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team2.wechat.MainActivity;
import com.team2.wechat.database.MyDatabaseHelper;
import com.team2.wechat.values.ConstValuses;

/**
 * Created by wjsay on 2018/1/1.
 */

public class LocalDBUtils {
    public static SQLiteDatabase getSQLiteDatabase(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.context, ConstValuses.LOCAL_DB_NAME, null,
                ConstValuses.LOACL_DB_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db;
    }
    public static Cursor getQueryResult(String sql, String[] str) {
        SQLiteDatabase db = LocalDBUtils.getSQLiteDatabase();
        Cursor cursor = db.rawQuery(sql, str);
        return cursor;
    }
}
