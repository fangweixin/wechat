package com.team2.wechat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mysql.jdbc.StringUtils;
import com.team2.wechat.database.TABLEuser;
import com.team2.wechat.myservice.ServiceRegister;
import com.team2.wechat.utils.ActivityCollector;
import com.team2.wechat.utils.Net;
import com.team2.wechat.utils.SerializableHashMap;
import com.team2.wechat.utils.UploadImage;
import com.team2.wechat.utils.UserTools;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText et_Nickname;
    EditText et_PhoneNumber;
    EditText et_Passwd;
    EditText et_Passwd_Again;
    Button userregister;
    ImageView iv_inputProfile;
    View line1, line3, line4, line5;
    ImageView back;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    private static final String TAG = "Register";
    RegisterReceiver receiver;
    Bitmap bitmap; //头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //活动入栈
        ActivityCollector.addActivity(this);
        et_Nickname = (EditText)findViewById(R.id.inputNickname);
        et_PhoneNumber = (EditText)findViewById(R.id.inputPhoneNumber);
        et_Passwd = (EditText)findViewById(R.id.inputPasswd);
        userregister = (Button)findViewById(R.id.userregister);
        iv_inputProfile = (ImageView)findViewById(R.id.inputhead);
        et_Passwd_Again = (EditText) findViewById(R.id.inputPasswd_again);
        picture = (ImageView)findViewById(R.id.inputhead);
        line1 = (View)findViewById(R.id.register_line1);
        line3 = (View)findViewById(R.id.register_line3);
        line4 = (View)findViewById(R.id.register_line4);
        //line5 = (View)findViewById(R.id.register_line5);
        back = (ImageView)findViewById(R.id.register_back);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);//默认头像

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        //获取头像
        iv_inputProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Register.this, SelectProfile.class);
                //startActivity(intent);

                if (ContextCompat.checkSelfPermission(
                        Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            Register.this, new String[] {
                                    Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });

        //输入昵称
        et_Nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                line3.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line4.setBackgroundColor(getResources().getColor(R.color.line_gray));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_Nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                line4.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line3.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
            }
        });
        //输入手机号
        et_PhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line1.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                line4.setBackgroundColor(getResources().getColor(R.color.line_gray));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_PhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                line1.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line4.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
            }
        });
        //输入密码
        et_Passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_Passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                line1.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line3.setBackgroundColor(getResources().getColor(R.color.line_gray));
                line4.setBackgroundColor(getResources().getColor(R.color.line_green));
            }
        });

        //点击注册按钮
        userregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, " 点击注册按钮");
//                if((et_Passwd.getText().toString().trim()).equals(et_Passwd_Again.getText().toString().trim())) {
//
//                }
//                else {
//                    return;
//                }
                if(!Net.isNetworkConnected(Register.this)) {
                    Toast.makeText(Register.this, " 网络受限", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwd = "password", name = "name", gender = "gender",
                        telephone = "telephone", address = "address", birthday = "birthday",
                        headUrl = "headUrl", problem = "problem";
                HashMap<String, Object> map = new HashMap<>();
                map.put("password", et_Passwd.getText().toString().trim());
                map.put("telephone", et_PhoneNumber.getText().toString().trim());
                map.put("name", et_Nickname.getText().toString().trim());
                //新加微信号字段
                map.put(TABLEuser.weixinhao, et_PhoneNumber.getText().toString().trim());
                UserTools.writeUserInfo(Register.this, map);
                SerializableHashMap myMap=new SerializableHashMap();
                myMap.setMap(map);//将hashmap数据添加到封装的myMap中
                Bundle bundle=new Bundle();
                bundle.putSerializable("map", myMap);
                Intent intent = new Intent(Register.this, ServiceRegister.class);
                intent.putExtras(bundle);
                progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("注册中……");
                progressDialog.setCancelable(false);
                progressDialog.show();

                startService(intent);
                Log.d(TAG, "越过启动服务");
                receiver = new RegisterReceiver();
                IntentFilter filter= new IntentFilter();
                filter.addAction("com.team2.wechat.internet.ServiceRegister");
                Register.this.registerReceiver(receiver, filter);
            }
        });

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //定义一个私有的方法改变按钮的颜色
    private void changeButtonColor () {
        if (StringUtils.isNullOrEmpty(et_Nickname.getText().toString().trim())
        || StringUtils.isNullOrEmpty(et_PhoneNumber.getText().toString().trim())
        || StringUtils.isNullOrEmpty(et_Passwd.getText().toString().trim())) {
            userregister.setBackgroundColor(getResources().getColor(R.color.button_color_graygreen));
            userregister.setEnabled(false);
        }
        else {
            userregister.setBackgroundColor(getResources().getColor(R.color.button_color_green));
            userregister.setEnabled(true);
        }

    }

    //广播接收器
    class RegisterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, " 收到注册完成的广播，正在处理");
            Bundle bundle = intent.getExtras();
            boolean registerSuccess = bundle.getBoolean("register_success");
            progressDialog.dismiss();
            if(registerSuccess) {
                Intent intent2app = new Intent(Register.this, MainActivity.class);
                intent2app.putExtra("phone_no", et_PhoneNumber.getText().toString().trim());
                startActivity(intent2app);
                new UploadImage(bitmap, et_PhoneNumber.getText().toString().trim()).execute(); //电话号码作为头像图片名
                ActivityCollector.finishAll();
            }
            else {
                alertDialog = new AlertDialog.Builder(Register.this);
                alertDialog.setTitle("注册失败");
                alertDialog.setMessage("账号" + et_PhoneNumber.getText().toString().trim() + "已存在");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, " 确定");

                    }
                });
                alertDialog.show();
                unregisterReceiver(receiver);
            }
        }
    }

    //选择图片用得到
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private ImageView picture;
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            bitmap = BitmapFactory.decodeFile(imagePath);

            //Log.d(TAG, "图片路径：" + imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}
