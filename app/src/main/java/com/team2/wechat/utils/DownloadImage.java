package com.team2.wechat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;

import com.team2.wechat.MainActivity;
import com.team2.wechat.user.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by wjsay on 12/21/2017.
 */

public class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
    private static final String TAG = "DownloadImage";
    String name;

    public DownloadImage (String name) {
        this.name = name;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        //小心服务器端改数据
        String url = "http://139.199.38.177/php/PicturesProfile/" + name + ".JPG";
        Log.d(TAG, " RUL " + url);
        //String url = "https://www.baidu.com/img/bd_logo1.png";//百度logo可以下载，自己的东西下载不了，权限问题
        //String url = "http://139.199.38.177/lamp.gif";
        //String url = "http://139.199.38.177/php/pictures/icpc.jpg";
        //教训，服务器段忘了写base64解密语句
        try {
            Log.d(TAG, " 执行了下载任务");
            URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(1000 * 30);
            connection.setReadTimeout(1000 * 30);
            Bitmap image = null;
            image = BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
            if(image != null) {
                Log.d(TAG, " 下载成功");
            }
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 下载失败");
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap != null) {
            //iv_download.setImageBitmap(bitmap);
            //从服务端写入图片至本地SD卡成功，2017年12月22日01:01:30
            //个人信息存在xml文件中userprofile.xml
            try {
                File path = Environment.getExternalStorageDirectory();
                File dir = new File(path + "/team02weixin/");
                dir.mkdir();
                File dir02 = new File(dir + "/PicturesProfile/");//两侧都要加/
                dir02.mkdir();
                File file = new File(dir02, name + ".JPG");
                OutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Log.d(TAG, "写入头像成功");
                } catch (Exception e) {
                    Log.d(TAG, " 写入头像失败");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.d(TAG, " 额，好像发生了异常。。。");
            }
        }
        else {

        }
    }
}

