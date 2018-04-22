package com.example.jeffchang.transmission;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_qk;
    private TextView tv_ap;
    private View include_login_ap;
    private View include_login_qk;
    private View include_verification;
    private Button btn_phone;
    private TextView et_phone;
    private Button btn_confirm_qk;
    private TextView tv_phone_qk;
    private EditText et_code_qk;
    private EditText et_phone_ap;
    private EditText et_passwd;
    private Button btn_login;
    private TextView tv_forget;
    private TextView tv_register;
    final static String TAG = "hhh";
    private String phone = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_ap = findViewById(R.id.tv_ap);
        tv_qk = findViewById(R.id.tv_qk);
        btn_phone = findViewById(R.id.btn_phone);
        include_login_ap = findViewById(R.id.include_login_ap);
        include_login_qk = findViewById(R.id.include_login_qk);
        include_verification = findViewById(R.id.include_verification);
        et_phone = findViewById(R.id.et_phone);
        btn_confirm_qk = findViewById(R.id.btn_confirm_qk);
        tv_phone_qk = findViewById(R.id.tv_phone_qk);
        et_code_qk = findViewById(R.id.et_code_qk);
        et_phone_ap = findViewById(R.id.et_phone_ap);
        et_passwd = findViewById(R.id.et_passwd);
        btn_login = findViewById(R.id.btn_login);
        tv_forget = findViewById(R.id.tv_forget);
        tv_register = findViewById(R.id.tv_register);
        tv_ap.setOnClickListener(this);
        tv_qk.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_confirm_qk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_qk:
                //点击快速登录
                tv_qk.setTextColor(0xff000000);
                tv_ap.setTextColor(0xff808080);
                include_login_qk.setVisibility(View.VISIBLE);
                include_login_ap.setVisibility(View.GONE);
                include_verification.setVisibility(View.GONE);
                break;
            case R.id.tv_ap:
                //点击密码登录
                tv_qk.setTextColor(0xff808080);
                tv_ap.setTextColor(0xff000000);
                include_login_qk.setVisibility(View.GONE);
                include_login_ap.setVisibility(View.VISIBLE);
                include_verification.setVisibility(View.GONE);
                break;
            case R.id.btn_phone:
                //点击获取验证码
                phone = et_phone.getText().toString().trim();
                sendCode("86",phone);
                tv_phone_qk.setText("验证码已发送至"+phone);
                break;
            case R.id.btn_confirm_qk:
                //点击提交
                String code = et_code_qk.getText().toString().trim();
                submitCode("86",phone,code);
                break;
            case R.id.tv_forget:
                Intent intent = new Intent(LoginActivity.this,resetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                sendCodeWithUI(getApplicationContext());
                default:break;
        }
    }

    public void sendCodeWithUI(Context context) {
        RegisterPage page = new RegisterPage();
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else{
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(context);
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
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
                            include_login_qk.setVisibility(View.GONE);
                            include_verification.setVisibility(View.VISIBLE);
                        }
                    });

                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"有问题哦",Toast.LENGTH_LONG).show();
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
        Log.d(TAG, phone + "   " + code);
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
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
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
