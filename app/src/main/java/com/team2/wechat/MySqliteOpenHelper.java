package com.team2.wechat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context, String name) {
		super(context, name, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table message(id integer primary key autoincrement,content varchar(255) not null,date datetime not null,opposite integer(10) not null,type tinyint(1) not null,status tinyint(1) not null)");
		db.execSQL("create table friends(id integer primary key,name varchar(255),telephone char(11) not null,weixinhao varchar(30),visible tinyint(1) not null,unreadmessage int default 0,lastmessagetime datetime)");
		db.execSQL("create index message_date_index on message(date)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
