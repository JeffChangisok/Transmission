package com.example.jeffchang.transmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll;
    Button btn;
    ImageButton iBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ll = findViewById(R.id.ll_personal_address);
        btn = findViewById(R.id.btn_personal);
        iBtn = findViewById(R.id.ibtn_personal);
        ll.setOnClickListener(this);
        btn.setOnClickListener(this);
        iBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_personal:
                finish();
                break;
            case R.id.btn_personal:
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_personal_address:
                //启动地址选择
                break;
        }
    }
}
