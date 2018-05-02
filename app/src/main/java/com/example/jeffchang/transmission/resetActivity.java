package com.example.jeffchang.transmission;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffchang.transmission.dao.*;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class resetActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "hhh";
    private Button btn_phone;
    private EditText et_phone;
    private EditText et_reset;
    private View include_reset_phone;
    private View include_reset_passwd;
    private View include_reset_verfication;
    private Button btn_reset;
    private Button btn_verification;
    private TextView tv_phone;
    private EditText et_code;
    private String token;
    private String userId;
    final MyClass myClass = new MyClass();

    class MyClass{
        String phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        btn_phone = findViewById(R.id.btn_phone);
        et_phone = findViewById(R.id.et_phone);
        include_reset_phone = findViewById(R.id.include_reset_phone);
        include_reset_passwd = findViewById(R.id.include_reset_passwd);
        include_reset_verfication = findViewById(R.id.include_reset_verfication);
        btn_reset = findViewById(R.id.btn_reset);
        btn_verification = findViewById(R.id.btn_confirm);
        tv_phone = findViewById(R.id.tv_phone);
        et_code = findViewById(R.id.et_code);
        et_reset = findViewById(R.id.et_reset);
        btn_phone.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_verification.setOnClickListener(this);
    }



    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            include_reset_phone.setVisibility(View.GONE);
                            include_reset_verfication.setVisibility(View.VISIBLE);
                            tv_phone.setText("验证码已发送至"+myClass.phone);

                        }
                    });

                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"发送验证码时出现问题",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    include_reset_phone.setVisibility(View.GONE);
                    include_reset_verfication.setVisibility(View.GONE);
                    include_reset_passwd.setVisibility(View.VISIBLE);
                    include_reset_passwd.setVisibility(View.VISIBLE);
                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"不对哦",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_phone:
                phone();break;
            case R.id.btn_confirm:
                String code = et_code.getText().toString().trim();
                submitCode("86",myClass.phone,code);break;
            case R.id.btn_reset:
                reset();
        }
    }

    public void reset(){
        String newPasswd = et_reset.getText().toString().trim();
        String url = "http://yigege.top:8080/portal/user_updateUser.action";
        RequestBody requestBody = new FormBody.Builder()
                .add("token",token)
                .add("userId",userId)
                .add("password",newPasswd)
                .build();
        HttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure:   请求修改密码失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(resetActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
        判断是否存在，再获取验证码
     */
    public void phone(){
        myClass.phone = et_phone.getText().toString().trim();
        RequestBody requestBody = new FormBody.Builder()
                .add("tel",myClass.phone)
                .build();
        String url = "http://yigege.top:8080/portal/user_telIsExist.action";

        HttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure:   请求判断手机号是否存在失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "user_telIsExist返回的信息:"+responseData);
                try{
                    JSONObject jo = new JSONObject(responseData);
                    if(jo.getInt("state")==1){
                        token = jo.getJSONObject("result").getString("token");
                        userId = jo.getJSONObject("result").getString("userId");
                        sendCode("86",myClass.phone);
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"该手机号并未注册",Toast.LENGTH_SHORT);
                            }
                        });
                    }
                }catch (Exception e){
                    Log.d(TAG, "onResponse:  "+e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
