package com.team2.wechat;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import com.google.gson.Gson;
import com.team2.bean.ChatMessage;
import com.team2.bean.OppositeMessageNetBean;
import com.team2.bean.ChatMessage.Type;

public class MessageService extends Service {

	private Thread serverThread;
	private ServerSocket serverSocket;
	private SharedPreferences sp;
	private int id;
	private SimpleDateFormat sdf;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		id = sp.getInt("id", 0);

		// �õ�ʱ��ת����
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

		serverThread = new Thread() {

			public void run() {
				try {
					MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(
							getApplication(), id + ".db");
					SQLiteDatabase db = mySqliteOpenHelper
							.getWritableDatabase();
					serverSocket = new ServerSocket(6000);
					while (true) {
						Socket sServer = serverSocket.accept();
						System.out.println("dfkjghfd");
						ObjectInputStream ois = new ObjectInputStream(
								sServer.getInputStream());
						String receiveData = (String) ois.readObject();
						OppositeMessageNetBean oppositeMessage = new Gson()
								.fromJson(receiveData,
										OppositeMessageNetBean.class);
						int opposite = oppositeMessage.getOpposite();
						String content = oppositeMessage.getContent();

						db.execSQL("INSERT INTO message(content,date,opposite,type,status) VALUES('"
								+ content
								+ "',datetime('now','localtime'),"
								+ opposite + ",1,1)");

						if (sp.getInt("contactId", 0) != opposite) {
							Cursor cursor = db.rawQuery(
									"SELECT unreadmessage FROM friends WHERE id="
											+ opposite, null);
							if (cursor.moveToFirst()) {
								
								if (cursor.getInt(0) == -1)
									db.execSQL("UPDATE friends SET unreadmessage=1 WHERE id="
											+ opposite);
								else
									db.execSQL("UPDATE friends SET unreadmessage=unreadmessage+1 WHERE id="
											+ opposite);
							}
							

							Editor edit = sp.edit();
							edit.putInt("unReadMessageTotalCount", sp.getInt("unReadMessageTotalCount", 0)+1);
							edit.commit();
						} 
						db.execSQL("UPDATE friends SET lastmessagetime= datetime('now','localtime') WHERE id="
									+ opposite);
						
						

						Intent receiveMessageIntent = new Intent(
								"RECEIVE_MESSAGE");
						receiveMessageIntent.putExtra("opposite", opposite);
						receiveMessageIntent.putExtra("content", content);
						sendBroadcast(receiveMessageIntent);

						sServer.close();

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		serverThread.start();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
