package com.team2.wechat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wjsay on 2018/1/1.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDatabaseHelper";
    /**
     * 该user表包含用户的好友信息
     */
    private static final String CREATE_TABLE_USER_SQL = "create table user("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "password text, "
            + "gender text, "
            + "telephone text, "
            + "address text, "
            + "birthday text, "
            + "headUrl text, "
            + "problem text, "
            + "ip text, "
            + "weixinhao text"
            + ")";
    private static final String CREATE_TABLE_NEWUSER_SQL = "create table newuser("
            + "id integer primary key, "
            + "name text, "
            + "password text, "
            + "gender text, "
            + "telephone text, "
            + "address text, "
            + "birthday text, "
            + "headUrl text, "
            + "problem text, "
            + "ip text, "
            + "weixinhao text"
            + ")";
    private Context mContext;

    public MyDatabaseHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_SQL); //创建MyDatabaseHelper时就会创建带有该表的数据库，数据库名由构造方法给出
        db.execSQL(CREATE_TABLE_NEWUSER_SQL);
        Toast.makeText(mContext, "数据库创建成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: " + "数据库对象创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists newuser");
        onCreate(db);
    }
}
