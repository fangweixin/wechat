package com.team2.wechat;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.team2.bean.ChatMessage;
import com.team2.bean.ChatMessage.Type;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.ToastUtils;

public class ChatActivity extends Activity {

    private static final String TAG = "ChatActivity";
    private TextView tv_header;
    private ListView lv_body;
    private LinearLayout ll_footer;
    private EditText et_msg;
    private Button btn_send;
    private List<ChatMessage> msgList = new ArrayList<ChatMessage>();
    private BodyAdapter bodyAdapter;
    private String contactName;
    private SharedPreferences sp;
    private int id;
    private SQLiteDatabase db;
    private Cursor historyMessageCursor;
    private SimpleDateFormat sdf;
    private int contactId;
    private OppositeMessageReceiver oppositeMessageReceiver;

    // 10.0.2.15
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findView();
        initInterface();

        et_msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() == 0) {
                    btn_send.setBackgroundResource(R.drawable.send_btn_normal);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                btn_send.setBackgroundResource(R.drawable.send_btn_pressed);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    protected void onDestroy() {
        //
        unregisterReceiver(oppositeMessageReceiver);

        Editor edit = sp.edit();
        edit.putInt("contactId", 0);
        edit.commit();
        super.onDestroy();
    }

    /**
     *
     *
     * @param v
     */
    public void onSendClick(View v) {

        final String sendMsgContent = et_msg.getText().toString();
        if (TextUtils.isEmpty(sendMsgContent)) {
            return;
        }

        //
        db.execSQL("INSERT INTO message(content,date,opposite,type,status) VALUES('"
                + sendMsgContent
                + "',datetime('now','localtime'),"
                + contactId
                + ",0,0)");

        //
        db.execSQL("UPDATE friends SET lastmessagetime= datetime('now','localtime') WHERE id="
                + contactId);

        //
        Intent receiveMessageIntent = new Intent("SEND_MESSAGE");
        receiveMessageIntent.putExtra("opposite", contactId);
        receiveMessageIntent.putExtra("content", sendMsgContent);
        sendBroadcast(receiveMessageIntent);

        ChatMessage sendMessage = new ChatMessage();
        sendMessage.setType(Type.SEND);
        sendMessage.setDate(sdf.format(new Date()));
        sendMessage.setContent(sendMsgContent);
        bodyAdapter.add(sendMessage);
        lv_body.setSelection(bodyAdapter.getCount() - 1);
        et_msg.setText("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = DBUtils.getUserIpById("" + contactId);
                //Log.d(TAG, "onSendClick: " + ip);
                try {
                    Socket cServer = new Socket(ip, 6000);
                    ObjectOutputStream oos = new ObjectOutputStream(
                            cServer.getOutputStream());
                    System.out.println("bb");
                    oos.writeObject("{\"opposite\":" + id
                            + ",\"content\":\""
                            + sendMsgContent + "\"}");
                    oos.flush();
                    cServer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("http://139.199.38.177/php/getip.php?id=" + contactId,
//                new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers,
//                                          byte[] responseBody) {
//                        Log.d(TAG, "onSuccess: " + "发送消息成功");
//                        try {
//                            //ip
//                            final String ip = new String(responseBody, "utf-8");
//                            if(ip.equals("")||ip.equals("0")) return;
//
//                            /**
//                             *
//                             */
//                            new Thread() {
//                                public void run() {
//                                    try {
//                                        System.out.println("aa");
//                                        System.out.println(ip);
//                                        Socket cServer = new Socket(ip, 6000);
//                                        ObjectOutputStream oos = new ObjectOutputStream(
//                                                cServer.getOutputStream());
//                                        System.out.println("bb");
//                                        oos.writeObject("{\"opposite\":" + id
//                                                + ",\"content\":\""
//                                                + sendMsgContent + "\"}");
//                                        oos.flush();
//                                        cServer.close();
//
//                                    } catch (Exception e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                        System.out.println("ooo");
//                                    }
//                                }
//                            }.start();
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers,
//                                          byte[] responseBody, Throwable error) {
//                        Log.d(TAG, "onFailure: " + "发送消息失败");
//                        System.out.println("adx");
//                    }
//
//                });
//
    }

    private void findView() {

        tv_header = (TextView) findViewById(R.id.tv_header);
        lv_body = (ListView) findViewById(R.id.lv_body);
        ll_footer = (LinearLayout) findViewById(R.id.ll_footer);
        et_msg = (EditText) findViewById(R.id.et_msg);
        btn_send = (Button) findViewById(R.id.btn_send);
    }

    private void initInterface() {


        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);


        Intent intent = getIntent();
        contactName = intent.getStringExtra("contactName");
        contactId = intent.getIntExtra("contactId", 0);
        tv_header.setText(contactName);


        sp = getSharedPreferences("config", MODE_PRIVATE);
        id = sp.getInt("id", 0);

        // contactId
        Editor edit = sp.edit();
        edit.putInt("contactId", contactId);
        edit.commit();


        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(
                getApplication(), id + ".db");
        db = mySqliteOpenHelper.getWritableDatabase();


        db.execSQL("UPDATE message set status=0 WHERE opposite=" + contactId
                + " AND status=1");
        historyMessageCursor = db.rawQuery(
                "SELECT content,date,type FROM message WHERE opposite="
                        + contactId + " ORDER BY date asc", null);
        bodyAdapter = new BodyAdapter();
        System.out.println(historyMessageCursor.getCount());
        while (historyMessageCursor.moveToNext()) {
            System.out.println(33333);
            String content = historyMessageCursor.getString(0);
            String date = historyMessageCursor.getString(1);
            int type = historyMessageCursor.getInt(2);

            ChatMessage historyMessage = new ChatMessage();
            if (type == 0)
                historyMessage.setType(Type.SEND);
            else
                historyMessage.setType(Type.RECEIVE);
            historyMessage.setDate(date);
            historyMessage.setContent(content);
            bodyAdapter.add(historyMessage);
        }
        System.out.println(55555);
        lv_body.setAdapter(bodyAdapter);
        lv_body.setSelection(bodyAdapter.getCount() - 1);

        // TODO
        //

        //
        oppositeMessageReceiver = new OppositeMessageReceiver();
        IntentFilter filter = new IntentFilter("RECEIVE_MESSAGE");
        registerReceiver(oppositeMessageReceiver, filter);

    }

    public class BodyAdapter extends BaseAdapter {

        public void add(ChatMessage msg) {
            msgList.add(msg);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public ChatMessage getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (msgList.get(position).getType() == Type.SEND) {
                return 1;
            }
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ChatMessage chatMessage = getItem(position);
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                if (getItemViewType(position) == 1) {
                    convertView = View.inflate(ChatActivity.this,
                            R.layout.item_send_msg, null);
                    holder.date = (TextView) convertView
                            .findViewById(R.id.tv_send_msg_date);
                    holder.text = (TextView) convertView
                            .findViewById(R.id.tv_send_msg_text);
                } else {
                    convertView = View.inflate(ChatActivity.this,
                            R.layout.item_receive_msg, null);
                    holder.date = (TextView) convertView
                            .findViewById(R.id.tv_receive_msg_date);
                    holder.text = (TextView) convertView
                            .findViewById(R.id.tv_receive_msg_text);
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //
            // TODO
            holder.date.setText(chatMessage.getDate());
            holder.text.setText(chatMessage.getContent());

            return convertView;
        }

        private final class ViewHolder {
            TextView date;
            TextView text;
        }

    }

    public class OppositeMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (sp.getInt("contactId", 0) == intent.getIntExtra("opposite", -1)) {
                ChatMessage receiveMessage = new ChatMessage();
                receiveMessage.setType(Type.RECEIVE);
                receiveMessage.setDate(sdf.format(new Date()));
                receiveMessage.setContent(intent.getStringExtra("content"));
                bodyAdapter.add(receiveMessage);
                lv_body.setSelection(bodyAdapter.getCount() - 1);
            }

        }

    }

}
