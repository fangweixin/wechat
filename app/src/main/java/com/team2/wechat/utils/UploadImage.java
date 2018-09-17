package com.team2.wechat.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by wjsay on 12/19/2017.
 * 异步同步机制
 */

public class UploadImage extends AsyncTask<Void, Void, Void> {
    Bitmap image;
    String name;


    public UploadImage(Bitmap image, String name) {
        //new UploadImage(image, name).execute();
        this.image = image;
        this.name = name;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        ArrayList<NameValuePair> dataToSend = new ArrayList<>();
        dataToSend.add(new BasicNameValuePair("image", encodedImage));
        dataToSend.add(new BasicNameValuePair("name", name));
        //dataToSend.add(new BasicNameValuePair("uploadtext", "user_send_hello_to_server"));
        HttpParams httpRequestParams = getHttpRequestParas();
        HttpClient client = new DefaultHttpClient(httpRequestParams);
        HttpPost post = new HttpPost("http://139.199.38.177/php/SavePicture04.php");
        //HttpPost post = new HttpPost("http://139.199.38.177/php/writetext.php");
        try {
            post.setEntity(new UrlEncodedFormEntity(dataToSend));
            client.execute(post);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Toast.makeText(new Activity().getApplicationContext(),
        //        "Image Upload", Toast.LENGTH_SHORT).show();
    }

    private HttpParams getHttpRequestParas() {
        HttpParams httpRequestParas = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParas, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParas, 1000 * 30);
        return httpRequestParas;
    }
}