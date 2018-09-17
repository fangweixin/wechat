package com.team2.wechat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.values.ConstValuses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class Utils {
    /**
     * 工具类
     */
    private static final String TAG = "Utils";
    public static int getRunTimes(Context context) {
        //getSharedPreferences必须依赖上下文
        SharedPreferences pref = context.getSharedPreferences(ConstValuses.DATAFILE, MODE_PRIVATE);
        int runTims = pref.getInt(ConstValuses.RUNTIMES, 0);
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ConstValuses.DATAFILE, Context.MODE_WORLD_WRITEABLE).edit();//RunTime为文件名
        editor.putInt(ConstValuses.RUNTIMES, runTims + 1);
        editor.apply();
        return runTims;
    }
    //获取登录状态
    public static boolean getLogState(Context context) {
        //Log.d(TAG, "获取登录状态");
        try {
            //可能发生异常
            SharedPreferences pref = context.getSharedPreferences(ConstValuses.DATAFILE, MODE_PRIVATE);
            boolean logState = pref.getBoolean(ConstValuses.LOGSTATE, false);
            //Log.d(TAG, " 获取登录状态正常");
            return logState;
        } catch (Exception e) {
            //Log.d(TAG, " 获取登录状态异常");
            return false;
        }

    }
    //为登录状态赋值
    public static void setLogState(Context context, boolean state) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ConstValuses.DATAFILE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(ConstValuses.LOGSTATE, state);
        editor.apply();
    }
    //获取用户密码
    public static String getUserPasswordByPhoneNumber(String PhoneNumber){
        Connection conn = getConnection("weixin");
        if(conn != null) {
            Log.d(TAG, " 数据库连接成功");
        }
        else {
            Log.d(TAG, " 数据库连接失败");
        }
        String res = "";
        try {
            //创建Statement，负责执行SQL语句
            Statement st = conn.createStatement();
            String sql = "select * from user where telephone = " + PhoneNumber;
            Log.d(TAG, "执行的SQL语句为：" + sql);
            ResultSet rs = st.executeQuery(sql);
            Log.d(TAG, " SQL语句被处理完成");
            if(rs != null) {
                Log.d(TAG, " 查询结果非空");
                while(rs.next()) {
                    res = rs.getString("password");
                }
                Log.d(TAG, " 查询到的密码为：" + res);
            }
            else {
                Log.d(TAG, " 查询结果为空");
            }
            conn.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            Log.d(TAG, " 执行SQL语句时发生异常");
            return "";
        }
        catch (Exception e) {
            Log.d(TAG, " 访问数据库时Close异常");
        }
        return res;
    }
    //返回数据库连接
    private static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            //根据数据库的URL 用户名 密码  来连接数据库
            String ip = "139.199.38.177";//文的腾讯云服务器公网IP（北京）
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName, "weixin", "soft02");
        } catch (SQLException ex) {
            Log.e(TAG, " SQL语句执行错误");
        } catch (ClassNotFoundException ex) {
            Log.e(TAG, " 驱动加载错误");
        }
        return conn;
    }
    //保存注册信息
    public static boolean writeProfile2DB(HashMap<String, Object> map) {
        Connection conn = getConnection("weixin");
        if(conn != null) {
            Log.d(TAG, " 数据库连接成功");
        }
        else {
            Log.d(TAG, " 数据库连接失败");
            return false;
        }
        String passwd = null, name = null, gender = null,
                telephone = null, address = null, birthday = null, headUrl = null, problem = null;
        passwd = (String)map.get("password"); name = (String)map.get("name"); gender = (String)map.get("gender");
                telephone = (String)map.get("telephone"); address = (String)map.get("address");
                birthday = (String)map.get("birthday");
                headUrl = (String)map.get("headUrl"); problem = (String)map.get("problem");
                String weixinhao_str = (String)map.get(TABLEuser.weixinhao);
        try {
            //创建Statement，负责执行SQL语句
            Statement st = conn.createStatement();
            String sql = "INSERT INTO `user` (password, name, gender, telephone" +
                    ", address, birthday, headUrl, problem, weixinhao)" +
                    " VALUES ('" + passwd  + "', '" + name + "', '" +
                    gender + "', '" + telephone + "', '" + address +
                    "', '" + birthday + "', '" + headUrl +  "', '" + problem +   "', '" + weixinhao_str + "')";
            Log.d(TAG, "执行的SQL语句为：" + sql);
            st.execute(sql); //实型sql语句
            Log.d(TAG, " SQL语句被处理完成");
            conn.close();
            st.close();
        } catch (SQLException e) {
            Log.d(TAG, " 执行SQL语句时发生异常");
            return false;
        }
        catch (Exception e) {
            Log.d(TAG, " 访问数据库时Close异常");
        }
        return true;
    }

}