package com.team2.wechat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.team2.wechat.MainActivity;
import com.team2.wechat.bean.Result;
import com.team2.wechat.comments.MomentItem;
import com.team2.wechat.database.CloudDB;
import com.team2.wechat.database.TABLEfriends;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.user.UserInfo;
import com.team2.wechat.values.ConstValuses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by wjsay on 12/21/2017.
 */

public class DBUtils {
    private static final String TAG = "DBUtils";

    //返回数据库连接
    private static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            //根据数据库的URL 用户名 密码  来连接数据库
            // String ip = "139.199.38.177";//文的腾讯云服务器公网IP（北京）
            String ip = "172.17.0.2"; // mariadb in docker
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, "weixin", "soft02");
        } catch (SQLException ex) {
            Log.e(TAG, " SQL语句执行错误");
        } catch (ClassNotFoundException ex) {
            Log.e(TAG, " 驱动加载错误");
        }
        return conn;
    }

    public static HashMap<String, Object>
        getUserInfoByPhoneNumber(String phone_number) {
        HashMap<String, Object> map = new HashMap<>();
        Connection conn = getConnection("weixin");
        try {
            Statement st = conn.createStatement();
            String sql = "select * from user where telephone = " + phone_number;
            ResultSet res = st.executeQuery(sql);
            if(res == null) {
                map = null;
            }
            else {
                Log.d(TAG, " 其实读取到了数据");
                res.next();
                    Log.d(TAG, TABLEuser.id + " " + res.getString(TABLEuser.id));
                    Log.d(TAG, TABLEuser.ip + " " + res.getString(TABLEuser.ip));
                    Log.d(TAG, TABLEuser.address + " " + res.getString(TABLEuser.address));
                    Log.d(TAG, TABLEuser.birthday + " " + res.getString(TABLEuser.birthday));
                    Log.d(TAG, TABLEuser.gender + " " + res.getString(TABLEuser.gender));
                    Log.d(TAG, TABLEuser.headUrl + " " + res.getString(TABLEuser.headUrl));
                    Log.d(TAG, TABLEuser.problem + " " + res.getString(TABLEuser.problem));
                    Log.d(TAG, TABLEuser.name + " " + res.getString(TABLEuser.name));
                    Log.d(TAG, TABLEuser.telephone + " " + res.getString(TABLEuser.telephone));
                    new DownloadImage(phone_number).execute();

                    //我的天，res.getString()用成了findColumn
//                    map.put(TABLEuser.id, res.getString(TABLEuser.id));
//                    map.put(TABLEuser.ip, res.getString(TABLEuser.ip));
//                    map.put(TABLEuser.address, res.getString(TABLEuser.address));
//                    map.put(TABLEuser.birthday, res.getString(TABLEuser.birthday));
//                    map.put(TABLEuser.gender, res.getString(TABLEuser.gender));
//                    map.put(TABLEuser.headUrl, res.getString(TABLEuser.headUrl));
//                    map.put(TABLEuser.problem, res.getString(TABLEuser.problem));
//                    map.put(TABLEuser.name, res.getString(TABLEuser.name));
//                    map.put(TABLEuser.telephone, res.getString(TABLEuser.telephone));
                    //2017年12月22日10:40:46 准备重构。便于动态修改数据库
                    int cnt = res.getMetaData().getColumnCount();
                    for (int i = 1; i <= cnt; ++i) {
                        String field = res.getMetaData().getColumnName(i);
                        map.put(field, res.getString(field));
                    }
                    conn.close();
                    st.close();
                    res.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d(TAG, " 数据库连接异常");
            //map = null;
        }
        return map;
    }
    public static boolean writeUserInfoByPhoneNumber2LocalFromTel (Context context, String phoneNumber) {
        final String filename = "userprofile";
        //Log.d(TAG, " 执行到更新本地文件");
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(
                    filename, Context.MODE_PRIVATE).edit();
            HashMap<String, Object> map = DBUtils.getUserInfoByPhoneNumber(phoneNumber);
            for (String key: map.keySet()) {
                //值为空的，都没写进文件
                editor.putString(key, (String)map.get(key));
            }
            editor.putBoolean("exit", true);
            editor.apply();
            Log.d(TAG, " 访问文件正常");
            return true;
        } catch (Exception e) {
            //Log.d(TAG, " 额，好像发生了异常。。。");
            return false;
        }
//        try {
//            //可能发生异常
//            SharedPreferences pref = context.getSharedPreferences(ConstValuses.DATAFILE, MODE_PRIVATE);
//            boolean logState = pref.getBoolean(ConstValuses.LOGSTATE, false);
//            Log.d(TAG, " 获取登录状态正常");
//            return logState;
//        } catch (Exception e) {
//            Log.d(TAG, " 获取登录状态异常");
//            return false;
//        }
    }
    public static Set<String> getFriendSetFromCloudDB(String tel) {
        Set<String> friend_set = new HashSet<String>();
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement(); //传来的参数电话号码并未用到
            String id = DBUtils.getUserIdFromCloudDBByPhoneNumber(tel);
            String sql = "select * from friends where frdfg = 1 and A = " + id;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
//                int cnt = rs.getMetaData().getColumnCount();
//                for (int i = 1; i <= cnt; ++i) {
//                    String ColumnNameStr = rs.getMetaData().getColumnName(i);
//                    map.put(ColumnNameStr, rs.getString(ColumnNameStr));
//                }
                friend_set.add(rs.getString(TABLEfriends.B));
            }
            conn.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friend_set;
    }
    public static boolean isFriendAlready(String tel_a_str, String tel_b_str) {
        boolean is_friend_already_bool = false;
        Set<String> friend_set = null;
        friend_set = getFriendSetFromCloudDB(tel_a_str);
        String id_of_b = getUserIdFromCloudDBByPhoneNumber(tel_b_str);
        is_friend_already_bool = friend_set.contains(id_of_b);
        return is_friend_already_bool;
    }
    public static String getUserIdFromCloudDBByPhoneNumber(String tel) {
        HashMap<String, Object> map = null;
        map = getUserInfoByPhoneNumber(tel);
        if (map != null) {
            return map.get(TABLEuser.id).toString();
        }
        return null;
    }
    public static boolean insert2TableFriends(String A, String B) {
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "insert into " + CloudDB.TABLE_FRIENDS + " (A, B, frdfg) values(" + A + ", " + B + ", 0)";
            Log.d(TAG, "insert2TableFriends: " + sql);
            st.execute(sql);
            conn.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean insert2TableFriends(String A, String B, int frdfg) {
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "insert into " + CloudDB.TABLE_FRIENDS
                    + " (A, B, frdfg) values(" + A + ", " + B + ", " + (frdfg == 0 ? 0 : 1) + ")";
            Log.d(TAG, "insert2TableFriends: " + sql);
            st.execute(sql);
            conn.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean update2TableFriends(String A, String B, int frdfg) {
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "update " + CloudDB.TABLE_FRIENDS +" set "+ TABLEfriends.frdfg +" = " + (frdfg == 0 ? 0 : 1) +
                    " where A = " + A + " and B = " + B;
            Log.d(TAG, "insert2TableFriends: " + sql);
            st.execute(sql);
            conn.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static HashMap<String, String> getUserInfoFromCloudDBById(String id) {
        HashMap<String, String> map = new HashMap<>();
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "select * from user where id = " + id;
            ResultSet rs = st.executeQuery(sql);
            int cnt = rs.getMetaData().getColumnCount();
            rs.next();
            for (int i = 1; i <= cnt; ++i) {
                String fieldName = rs.getMetaData().getColumnName(i);
                String value = rs.getString(fieldName);
                map.put(fieldName, value);
            }
            conn.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public static Set<String> getNewFriendsIDFromCDBbyMyID (String id) {
        Set<String> set = new HashSet<>();
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "select * from friends where B = " + id + " and frdfg = 0;";
            ResultSet res = st.executeQuery(sql);
            if(res != null) {
                while(res.next()) {
                    set.add(res.getString(TABLEfriends.A));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    public static void insert2moments(String id, String msg) {
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "insert into comments (userid, content) values('" + id + "', '" + msg + "')";
            st.execute(sql);
            st.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<MomentItem> getMoments() {
        ArrayList<MomentItem> momentItems = new ArrayList<>();
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "select * from comments";
            ResultSet res = st.executeQuery(sql);
            while(res.next()) {
                MomentItem item = new MomentItem();
                HashMap<String, String> map = getUserInfoFromCloudDBById(res.getString("userid"));
                String name = map.get(TABLEuser.name);
                item.setUserName(name);
                Log.d(TAG, "getMoments: " +  name);
                item.setMomentText(res.getString("content"));
                String tel = map.get(TABLEuser.telephone);
                new DownloadImage(tel).execute();
                Log.d(TAG, "getMoments: " + tel);
                Bitmap bitmap = BitmapFactory.decodeFile(
                        UserInfo.LocalPritruesProfile + tel + ".JPG");
                item.setUserAvatar(bitmap);
                try {
                    new DownloadImage(map.get("id") +"moments").execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeFile(
                        UserInfo.LocalPritruesProfile + map.get("id") + "moments.JPG");
                item.setMomentImage(bitmap);
                momentItems.add(item);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(momentItems);
        return momentItems;
    }
    public static void updateIp(String tel, String ip) {
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "update user set ip = '" + ip + "' where telephone = '" + tel + "'";
            st.execute(sql);
            st.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getUserIpById(String id) {
        String ip = "192.168.0.1";
        Connection conn = getConnection(CloudDB.DBNAME);
        try {
            Statement st = conn.createStatement();
            String sql = "select ip from user where id = " + id;
            ResultSet res = st.executeQuery(sql);
            while(res.next()) {
                ip = res.getString(TABLEuser.ip);
            }
            res.close();
            st.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
