package com.example.jeffchang.transmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jeffchang.transmission.dao.*;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class resetActivity extends AppCompatActivity {

    private Button btn;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        btn = findViewById(R.id.btn_phone);
        et = findViewById(R.id.et_phone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et.getText().toString().trim();
                //先判断是否存在，然后再获取验证码
                RequestBody requestBody = new FormBody.Builder()
                        .add("tel",phone)
                        .build();
                String url = "";
                HttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().toString();
                    }
                });
            }
        });
    }
}
