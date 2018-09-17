package com.team2.wechat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.BroadcastReceiver;
import com.team2.bean.MsgItem;
//以上是亚鹏的修改
import android.content.SharedPreferences.Editor;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//秦文杰修改
import com.mysql.jdbc.StringUtils;
import com.team2.wechat.bean.AddUser;
import com.team2.wechat.bean.TopMenuSearch;
import com.team2.wechat.comments.MomentsActivity;
import com.team2.wechat.contactlist.AddressAdapter;
import com.team2.wechat.contactlist.AddressItem;
import com.team2.wechat.contactlist.SeeMyNewFriend;
import com.team2.wechat.database.MyDatabaseHelper;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.myservice.DataDealService;
import com.team2.wechat.myservice.InitDataService;
import com.team2.wechat.listener.MyMenuItem;
import com.team2.wechat.listener.OnClickViewUnDevelopListener;
import com.team2.wechat.listener.TopRightMenu;
import com.team2.wechat.mysetting.Setting;
import com.team2.wechat.user.UserInfo;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.DBUtils;
import com.team2.wechat.utils.DownloadImage;
import com.team2.wechat.utils.FileUtils;
import com.team2.wechat.utils.LocalDBUtils;
import com.team2.wechat.utils.NetUtils;
import com.team2.wechat.utils.ToastUtils;
import com.team2.wechat.utils.UserTools;
import com.team2.wechat.utils.Utils;
import com.team2.wechat.values.ConstValuses;


public class MainActivity extends AppCompatActivity {
    public static Context context;//MainActivity级别的context
    private static final String TAG = "MainActivity";
    private TextView txt_title;
    private ViewPager vp_body;
    private LinearLayout main_bottom;
    private RelativeLayout re_weixin;
    private ImageView ib_weixin;
    private TextView tv_weixin;
    private TextView unread_msg_number;
    private RelativeLayout re_contact_list;
    private ImageView ib_contact_list;
    private TextView tv_contact_list;
    private TextView unread_address_number;
    private RelativeLayout re_find;
    private ImageView ib_find;
    private TextView tv_find;
    private TextView unread_find_number;
    private RelativeLayout re_profile;
    private ImageView ib_profile;
    private TextView tv_profile;
    private ArrayList<LinearLayout> bodys;//存储底部导航栏对应的四个页面（微信，联系人，发现，我）
    //private SharedPreferences sp;// 通过sp进行本地数据存取
    private int preBodyPage;// 上次在导航栏的哪个页面

    private final int[] bodyRes = new int[] {
            R.layout.activity_main_body_weixin,
            R.layout.activity_main_body_contact_list,
            R.layout.activity_main_body_find,
            R.layout.activity_main_body_profile };

    private ImageView[] iv_bottom;
    private TextView[] tv_bottom;
    private static final int bodyPageTotal = 4;
    private ListView lv_body_weixin_msg;
    private ArrayList<MsgItem> msgItemList;
    private LvAdapter lvAdapter;
    private int initBodyPage;
    private static int WEIXIN = 0, CONTACT_LIST = 1, FIND = 2, PROFILE = 3;

    //亚鹏
    private SharedPreferences sp;
    private int id;
    private SQLiteDatabase db;
    private ChangeMsgItemReceiver changeMsgItemReceiver;

    //=============秦文杰=================
    RelativeLayout comments;
    ProgressDialog progressDialog;
    private static final int GETFRIENDSDATADONE = 0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GETFRIENDSDATADONE:
                    initBodyContact(); //初始化联系人界面
                    progressDialog.dismiss();
                    break;
            }
            return false;
        }
    });
    //========我界面
    RelativeLayout rl_profile_setting;
    ImageView iv_user_profile;
    private String user_phone_num;
    ImageView iv_more_menu;
    HashMap<String, String> map = new HashMap<>();
    //发现界面
    RelativeLayout rl_little_process;
    private TopRightMenu mTopRightMenu;
    private ImageView iv_top_search;
    //联系人界面
    ListView lvaddress;
    private List<AddressItem> addressList = new ArrayList<AddressItem>();
    AddressItem addressItem;
    AddressAdapter adapter;
    public String telephoenStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, " 创建create");
        context = this;
        ActivityCollector.finishAll();
        ActivityCollector.addActivity(this);
        updateInfo();
        initAddressList();
        findView();
        initLocalData();
        bodys = new ArrayList<LinearLayout>();
        for (int i = 0; i < bodyPageTotal; i++) {
            bodys.add((LinearLayout) View.inflate(this, bodyRes[i], null));
        }
        vp_body.setAdapter(new vpAdapter());
        vp_body.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                iv_bottom[preBodyPage].setSelected(false);
                tv_bottom[preBodyPage].setTextColor(Color.parseColor("#9a9a9a"));
                iv_bottom[position].setSelected(true);
                tv_bottom[position].setTextColor(Color.rgb(70, 192, 27));
                preBodyPage = position;
            }
            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
        if (initBodyPage == 0) {
            iv_bottom[initBodyPage].setSelected(true);
            tv_bottom[initBodyPage].setTextColor(getResources().getColor(R.color.line_green));
        } else {
            vp_body.setCurrentItem(initBodyPage, false);
        }
        preBodyPage = initBodyPage;

        initBodyWeixin();//张亚鹏
        initBodyFind(); //by qinwenjie
        initBodyProfile(); //
        //initBodyContact(); //初始化联系人界面，放到了其他地方。因为它耗时呀
        //添加修改 2018-1-12 ==========================================================
        Intent messageServiceIntent = new Intent(getApplication(),
                MessageService.class);
        startService(messageServiceIntent);
    }


    /**
     * 从本地存储初始化数据，--张亚鹏
     */
    private void initLocalData() {
        //亚鹏的
        sp = getSharedPreferences("config", MODE_PRIVATE);
        preBodyPage = initBodyPage = sp.getInt("bodyPage", 0);
        id = sp.getInt("id", 0);
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(
                getApplication(), id + ".db");
        db = mySqliteOpenHelper.getWritableDatabase();
        /**
         * 创建本地数据库，秦文杰 2017-12-28，没成功，2018-1-1再次创建。
         * 每次修改数据库，记得改版本号
         */
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "weixin.db", null, ConstValuses.LOACL_DB_VERSION);
        dbHelper.getWritableDatabase();
        Intent intent = new Intent(this, DataDealService.class);
        intent.putExtra("tel", telephoenStr);
        startService(intent);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("初次登录，初始化数据中……");
        progressDialog.setCancelable(false);
        //progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> set = DBUtils.getFriendSetFromCloudDB(user_phone_num);
                //set保存好友id
                if (set != null) {
                    boolean firstItem = true;
                    for (String it : set) {
                        Log.d(TAG, "run: " + it);
                        map = DBUtils.getUserInfoFromCloudDBById(it);
                        //==========
                        //new DownloadImage(map.get(TABLEuser.telephone)).execute();
                        File path = new File(UserInfo.LocalPritruesProfile, map.get(TABLEuser.telephone) + ".JPG");
                        addressItem = new AddressItem(View.INVISIBLE, null,
                                Uri.fromFile(path), map.get(TABLEuser.name));
                        addressItem.setUserid(it);
                        if(firstItem) {
                            addressItem.setTitleVisible(View.VISIBLE);
                            addressItem.setTitleName("我的好友");
                        }
                        firstItem = false;
                        addressList.add(addressItem);
                    }
                }
                Message msg = new Message();
                msg.what = GETFRIENDSDATADONE;
                handler.sendMessage(msg);
            }
        }).start();

        Utils.setLogState(MainActivity.this, true);
        iv_more_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, " 点击了more_menu");
                try {
                    mTopRightMenu = new TopRightMenu(MainActivity.this);
                    List<MyMenuItem> menuItems = new ArrayList<>();
                    menuItems.add(new MyMenuItem(R.mipmap.multichat, "发起群聊"));
                    menuItems.add(new MyMenuItem(R.mipmap.addmember, "添加好友"));
                    menuItems.add(new MyMenuItem(R.mipmap.qr_scan, "扫一扫"));
                    mTopRightMenu
                            .setHeight(800)     //默认高度480
                            .setWidth(600)      //默认宽度wrap_content
                            .dimBackground(false)           //背景变暗，默认为true
                            .needAnimationStyle(true)   //显示动画，默认为true
                            .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                            .addMenuList(menuItems)
                            .addMenuItem(new MyMenuItem(R.mipmap.facetoface, "面对面快传"))
                            .addMenuItem(new MyMenuItem(R.mipmap.pay, "收付款"))
                            .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                                @Override
                                public void onMenuItemClick(int position) {
                                    Intent intent = null;
                                    switch (position) {
                                        case 1: //添加好友
                                            intent = new Intent(MainActivity.this, AddUser.class);
                                            break;
                                        default:
                                            Toast.makeText(MainActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                    if(intent != null) {
                                        startActivity(intent);
                                    }
                                }
                            })
                            .showAsDropDown(iv_more_menu, -300, -60);//-255, 0
                } catch (Exception e) {
                    Log.e(TAG, " 捕获到异常");
                    e.printStackTrace();
                }

            }
        });
        iv_top_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TopMenuSearch.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 找到各个控件
     */
    private void findView() {

        txt_title = (TextView) findViewById(R.id.txt_title);

        vp_body =  findViewById(R.id.vp_body);

        main_bottom = (LinearLayout) findViewById(R.id.main_bottom);
        re_weixin = (RelativeLayout) findViewById(R.id.re_weixin);
        ib_weixin = (ImageView) findViewById(R.id.ib_weixin);
        tv_weixin = (TextView) findViewById(R.id.tv_weixin);
        unread_msg_number = (TextView) findViewById(R.id.unread_msg_number);
        re_contact_list = (RelativeLayout) findViewById(R.id.re_contact_list);
        ib_contact_list = (ImageView) findViewById(R.id.ib_contact_list);
        tv_contact_list = (TextView) findViewById(R.id.tv_contact_list);
        unread_address_number = (TextView) findViewById(R.id.unread_address_number);
        re_find = (RelativeLayout) findViewById(R.id.re_find);
        ib_find = (ImageView) findViewById(R.id.ib_find);
        tv_find = (TextView) findViewById(R.id.tv_find);
        unread_find_number = (TextView) findViewById(R.id.unread_find_number);
        re_profile = (RelativeLayout) findViewById(R.id.re_profile);
        ib_profile = (ImageView) findViewById(R.id.ib_profile);
        tv_profile = (TextView) findViewById(R.id.tv_profile);

        iv_bottom = new ImageView[] { ib_weixin, ib_contact_list, ib_find,
                ib_profile };
        tv_bottom = new TextView[] { tv_weixin, tv_contact_list, tv_find,
                tv_profile };
        //wjsay
        iv_more_menu = (ImageView)(findViewById(R.id.layout_bar).findViewById(R.id.iv_more_menu));
        iv_top_search = (ImageView)(findViewById(R.id.layout_bar).findViewById(R.id.iv_top_search));

    }
    /**
     *
     * 定义ViewPager的适配器
     *
     */
    private class vpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bodys.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout ll_body = bodys.get(position);
            container.addView(ll_body);
            return ll_body;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    /**
     * 响应底部导航栏的点击事件
     *
     * @param v
     */
    public void onTabClicked(View v) {
        switch (v.getId()) {
            case R.id.re_weixin:

                vp_body.setCurrentItem(0, false);
                break;

            case R.id.re_contact_list:

                vp_body.setCurrentItem(1, false);
                break;

            case R.id.re_find:

                vp_body.setCurrentItem(2, false);
                break;

            case R.id.re_profile:
                initBodyProfile();

                vp_body.setCurrentItem(3, false);
                break;
        }
    }
    /**
     * 初始化导航栏第一个页面（微信消息页面）
     */
    private void initBodyWeixin() {
        msgItemList = new ArrayList<MsgItem>();
        //if(msgItemList.isEmpty())
            //ToastUtils.showLongToast("加载数据中，请稍后……");
        Cursor messageContactCursor = db
                .rawQuery(
                        "SELECT id,name,unreadmessage,lastmessagetime FROM friends WHERE unreadmessage>=0 ORDER BY lastmessagetime desc",
                        null);

        while (messageContactCursor.moveToNext()) {

            int contactId = messageContactCursor.getInt(0);
            String contactName = messageContactCursor.getString(1);
            int unReadMessage = messageContactCursor.getInt(2);
            MsgItem msgItemBean = new MsgItem();
            msgItemBean.setContactIcon("");
            msgItemBean.setUnReadMessage(unReadMessage);
            msgItemBean.setContactId(contactId);
            msgItemBean.setContactName(contactName);
            msgItemBean.setMsgNotDisturb(false);
            Cursor lastMessageCursor = db.rawQuery(
                    "SELECT date,content FROM message WHERE opposite="
                            + contactId + " ORDER BY date desc limit 1", null);
            if (lastMessageCursor.moveToFirst()) {
                msgItemBean.setMsgTime(lastMessageCursor.getString(0)
                        .substring(11, 16));
                msgItemBean.setMsgSimpleContent(lastMessageCursor.getString(1));
            } else {
                msgItemBean.setMsgTime("");
                msgItemBean.setMsgSimpleContent("");
            }

            msgItemList.add(msgItemBean);
        }

        lv_body_weixin_msg = (ListView) bodys.get(0).findViewById(
                R.id.lv_body_weixin_msg);
        lvAdapter = new LvAdapter();
        lv_body_weixin_msg.setAdapter(lvAdapter);
        lv_body_weixin_msg.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int contactId = msgItemList.get(position).getContactId();


                msgItemList.get(position).setUnReadMessage(0);
                lvAdapter.update();


                Cursor cursor = db.rawQuery(
                        "SELECT unreadmessage FROM friends WHERE id="
                                + contactId, null);

                if (cursor.moveToFirst()&&cursor.getInt(0)>0) {
                    int count = sp.getInt("unReadMessageTotalCount", 0)
                            - cursor.getInt(0);
                    updateUnReadMessage(count);
                    Editor edit = sp.edit();
                    edit.putInt("unReadMessageTotalCount", count);
                    edit.commit();
                }
                db.execSQL("UPDATE friends SET unreadmessage=0 WHERE id="
                        + contactId);


                Intent intent = new Intent(MainActivity.this,
                        ChatActivity.class);
                intent.putExtra("contactId", contactId);
                intent.putExtra("contactName", msgItemList.get(position)
                        .getContactName());
                startActivity(intent);
            }
        });


        changeMsgItemReceiver = new ChangeMsgItemReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("RECEIVE_MESSAGE");
        filter.addAction("SEND_MESSAGE");
        registerReceiver(changeMsgItemReceiver, filter);


        updateUnReadMessage(sp.getInt("unReadMessageTotalCount", 0));

    }

    /**
     * 初始化发现界面的id
     */
    private void initBodyFind () {
        //填充控件id
        //==============秦文杰=================
        comments = bodys.get(2).findViewById(R.id.comments);
        comments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, Comments.class);
                //Intent intent = new Intent(MainActivity.this, TestActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(MainActivity.this, MomentsActivity.class);
                startActivity(intent);

            }
        });
        rl_little_process = bodys.get(FIND).findViewById(R.id.rl_little_process);
        rl_little_process.setOnClickListener(new OnClickViewUnDevelopListener());
    }
    /**
     * 初始化个人信息界面的id
     */
    private  void initBodyProfile() {
        rl_profile_setting = bodys.get(3).findViewById(R.id.profile_setting);
        rl_profile_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2setting = new Intent(MainActivity.this, Setting.class);
                startActivity(intent2setting);
            }
        });
        //从本地区取照片
        try {
            iv_user_profile = bodys.get(PROFILE).findViewById(R.id.iv_user_avatar);
            if(user_phone_num == null) {
                user_phone_num = UserTools.getUserInfoFromPref(MainActivity.this, TABLEuser.telephone);
            }
            if(user_phone_num == null) {
                throw  new Exception();
            }
            File path = new File(UserInfo.LocalPritruesProfile, user_phone_num + ".JPG");
            iv_user_profile.setImageURI(Uri.fromFile(path));
        } catch (Exception e) {
            Log.d(TAG, " 加载头像时抛出异常");
            /**
             * 2017年12月22日10:15:16动态获取头像成功
             */
            rl_profile_setting = bodys.get(PROFILE).findViewById(R.id.re_profile);
            rl_profile_setting.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    initBodyProfile();
                }
            });
        }
        //从本地后去用户信息，但是这个信息是刚从云端抓取的
        TextView tv_user_nickname = bodys.get(PROFILE).findViewById(R.id.tv_user_nickname);
        tv_user_nickname.setText(UserTools.getUserInfoFromPref(MainActivity.this, TABLEuser.name));
        TextView tv_user_weixinhao = bodys.get(PROFILE).findViewById(R.id.tv_user_weixinhao);
        //我们默认微信号就是手机号，可以进一步更改。我天，数据库忘了设计微信号
        String weixinhao = UserTools.getUserInfoFromPref(MainActivity.this, TABLEuser.weixinhao);
        if(StringUtils.isNullOrEmpty(weixinhao)) {
            weixinhao = UserTools.getUserInfoFromPref(MainActivity.this, TABLEuser.telephone);
        }
        tv_user_weixinhao.setText("微信号：" + weixinhao);

    }
    private static final int GETNEWUSER = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case GETNEWUSER:
                    ToastUtils.showToast("执行回调方法");
                    Log.d(TAG, "onActivityResult: Activity的回调方法");
                    String b_id = data.getStringExtra("id");
                    String tel = data.getStringExtra("tel");
                    String name = data.getStringExtra("name");
                    Log.d(TAG, "onActivityResult: b_id" + b_id + " tel:" + tel + " name:" + name);
                    addressItem = new AddressItem(View.INVISIBLE, null,
                            FileUtils.getImageUriFromPath(tel), name);
                    addressItem.setUserid(b_id);
                    if(name == null) {

                    }
                    else {
                        addressList.add(addressItem);
                        adapter.notifyDataSetChanged();
                    }
                    //initBodyContact();
                    break;

            }
        }
    }


    void initBodyContact() {
        lvaddress = bodys.get(CONTACT_LIST).findViewById(R.id.lv_address);
        adapter = new AddressAdapter(MainActivity.this, R.layout.layout_address_item, addressList);
        lvaddress.setAdapter(adapter);
        lvaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, SeeMyNewFriend.class);
                        startActivityForResult(intent, GETNEWUSER);
                        break;
                    case 1:
                        //startActivity(intent);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        AddressItem address =addressList.get(position);
                        ToastUtils.showToast("" + address.getUserid());
                        break;
                }
            }
        });
    }
    private void initAddressList() {
        AddressItem address = new AddressItem(View.INVISIBLE, null,
                FileUtils.id2uri(R.drawable.new_friends), "新的朋友");
        address.setUserid("#0");
        addressList.add(address);
        address = new AddressItem(View.INVISIBLE, null,
                FileUtils.id2uri(R.drawable.group_chat), "群聊");
        address.setUserid("#1");
        addressList.add(address);
        address = new AddressItem(View.INVISIBLE, null,
                FileUtils.id2uri(R.drawable.flag), "标签");
        address.setUserid("#2");
        addressList.add(address);
        address = new AddressItem(View.INVISIBLE, null,
                FileUtils.id2uri(R.drawable.public_no), "公众号");
        address.setUserid("#3");
        addressList.add(address);

    }
    /**
     *
     * 微信消息适配器
     *
     */
    private class LvAdapter extends BaseAdapter {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);

        public void update() {
            notifyDataSetChanged();
        }

        public void toFirst(int contactId, String lastMessage) {

            boolean flag = true;
            for (MsgItem msgItemBean : msgItemList) {
                if (contactId == msgItemBean.getContactId()) {
                    msgItemList.remove(msgItemBean);
                    msgItemList.add(0, msgItemBean);
                    msgItemBean.setMsgSimpleContent(lastMessage);
                    msgItemBean.setMsgTime(sdf.format(new Date()));
                    Cursor cursor = db.rawQuery(
                            "SELECT unreadmessage FROM friends WHERE id="
                                    + contactId, null);
                    if (cursor.moveToFirst())
                        msgItemBean.setUnReadMessage(cursor.getInt(0));
                    flag = false;
                    break;
                }
            }
            if (flag) {
                MsgItem msgItemBean = new MsgItem();
                msgItemBean.setContactIcon("");
                msgItemBean.setContactId(contactId);
                Cursor cursor = db.rawQuery(
                        "SELECT name FROM friends WHERE id=" + contactId, null);
                if (cursor.moveToFirst()) {
                    msgItemBean.setContactName(cursor.getString(0));
                } else {
                    msgItemBean.setContactName("");
                }
                msgItemBean.setMsgNotDisturb(false);
                msgItemBean.setMsgSimpleContent(lastMessage);
                msgItemBean.setMsgTime(sdf.format(new Date()));
                msgItemBean.setUnReadMessage(1);
                msgItemList.add(0, msgItemBean);
            }

            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return msgItemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MsgItemViewHolder holder;
            if (convertView == null) {
                holder = new MsgItemViewHolder();
                convertView = View.inflate(MainActivity.this,
                        R.layout.activity_main_body_weixin_msg_item, null);
                holder.iv_contact_icon = (ImageView) convertView
                        .findViewById(R.id.iv_contact_icon);
                holder.tv_contact_name = (TextView) convertView
                        .findViewById(R.id.tv_contact_name);
                holder.tv_msg_simple_content = (TextView) convertView
                        .findViewById(R.id.tv_msg_simple_content);
                holder.tv_msg_time = (TextView) convertView
                        .findViewById(R.id.tv_msg_time);
                holder.iv_msg_not_disturb = (ImageView) convertView
                        .findViewById(R.id.iv_msg_not_disturb);
                holder.tv_unread_message_number = (TextView) convertView
                        .findViewById(R.id.tv_unread_message_number);
                convertView.setTag(holder);

            } else {
                holder = (MsgItemViewHolder) convertView.getTag();
            }

            MsgItem msgItemBean = msgItemList.get(position);
            holder.iv_contact_icon.setImageResource(R.drawable.ic_launcher);
            holder.tv_contact_name.setText(msgItemBean.getContactName());
            holder.tv_msg_simple_content.setText(msgItemBean
                    .getMsgSimpleContent());
            holder.tv_msg_time.setText(msgItemBean.getMsgTime());

            int unReadMessage = msgItemBean.getUnReadMessage();
            if (unReadMessage > 0 && unReadMessage < 100) {
                holder.tv_unread_message_number.setText(String
                        .valueOf(unReadMessage));
                holder.tv_unread_message_number.setVisibility(View.VISIBLE);
            } else if (unReadMessage >= 100) {
                holder.tv_unread_message_number.setText(99 + "+");
                holder.tv_unread_message_number.setVisibility(View.VISIBLE);
            } else
                holder.tv_unread_message_number.setVisibility(View.INVISIBLE);
            if (msgItemBean.isMsgNotDisturb()) {
                holder.iv_msg_not_disturb
                        .setImageResource(R.drawable.ic_launcher);
            }

            return convertView;
        }

        private class MsgItemViewHolder {
            public ImageView iv_contact_icon;
            public TextView tv_contact_name;
            public TextView tv_msg_simple_content;
            public TextView tv_msg_time;
            public TextView tv_unread_message_number;
            public ImageView iv_msg_not_disturb;
        }

    }

    private void updateInfo() {
        Intent initDataService = new Intent(MainActivity.this, InitDataService.class);
        user_phone_num = getIntent().getStringExtra("phone_no");
        telephoenStr = user_phone_num;
//        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        }, 1);
        initDataService.putExtra("tel", user_phone_num);
        if(user_phone_num == null) {
            //优先使用活动传递来的数据
            user_phone_num = UserTools.getUserInfoFromPref(MainActivity.this, TABLEuser.telephone);
            telephoenStr = user_phone_num;

        }
        if(user_phone_num != null ) {
            new Thread(new Runnable() {
                                    @Override
                    public void run() {
                        String ip = NetUtils.getIPAddress(MainActivity.this);
                        DBUtils.updateIp(user_phone_num, ip);
                    }
                }).start();
            //若活动没有出传递数据，使用本地缓存
            startService(initDataService); //耗时数据初始化
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(changeMsgItemReceiver);

        Editor edit = sp.edit();
        edit.putInt("bodyPage", preBodyPage);
        edit.commit();
        SQLiteDatabase db = LocalDBUtils.getSQLiteDatabase();
        Log.d(TAG, "onDestroy: 退出");
        db.execSQL("delete from user");
        db.execSQL("delete from newuser");
        super.onDestroy();
    }

    //亚鹏的
    public class ChangeMsgItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int opposite = intent.getIntExtra("opposite", -1);
            String content = intent.getStringExtra("content");
            lvAdapter.toFirst(opposite, content);
            updateUnReadMessage(sp.getInt("unReadMessageTotalCount", 0));
        }

    }
    private void updateUnReadMessage(int count) {

        if (count >= 100) {
            unread_msg_number.setText(99 + "+");
            unread_msg_number.setVisibility(View.VISIBLE);
        } else if (count > 0 && count < 100) {
            unread_msg_number.setText(count + "");
            unread_msg_number.setVisibility(View.VISIBLE);
        } else {
            unread_msg_number.setVisibility(View.INVISIBLE);
        }

    }

//    @Override
//    public void onBackPressed() {
//
//    }
}
