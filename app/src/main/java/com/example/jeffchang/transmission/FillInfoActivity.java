package com.example.jeffchang.transmission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FillInfoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    EditText etBuilding;
    TextView tvAddress;
    EditText etName;
    EditText etPhone;
    ImageButton ibtn;
    ImageButton ibtn_back;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_info);
        btn = findViewById(R.id.btn_fill);
        tvAddress = findViewById(R.id.tv_fill_address);
        etBuilding = findViewById(R.id.et_fill_building);
        etName = findViewById(R.id.et_fill_name);
        etPhone = findViewById(R.id.et_fill_phone);
        ibtn = findViewById(R.id.ibtn_fill);
        ibtn_back = findViewById(R.id.ibtn_backMain);
        tv_title = findViewById(R.id.tv_fill_title);
        ibtn_back.setOnClickListener(this);
        ibtn.setOnClickListener(this);
        btn.setOnClickListener(this);
        Intent intent = getIntent();
        switch (intent.getStringExtra("jiOrSong")){
            case "ji":
                tv_title.setText("完善寄件信息");
                break;
            case "song":
                tv_title.setText("完善取件信息");
                break;
            default:
                Log.e("hhh", "onCreate:  好像没有传过来是寄还是送的消息啊" );
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_fill:
                //先判断是否都填完了
                String address = tvAddress.getText().toString().trim();
                String building = etBuilding.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                if(address.equals("")||building.equals("")||name.equals("")||phone.equals("")){
                    Toast.makeText(getApplicationContext(),"请填写完整",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("address",address);
                    intent.putExtra("building",building);
                    intent.putExtra("name",name);
                    intent.putExtra("phone",phone);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.ibtn_fill:
                Intent intent = new Intent(FillInfoActivity.this,AddressActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.ibtn_backMain:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String address = data.getStringExtra("address");
                    tvAddress.setText(address);
                }
                break;
        }
    }
}
