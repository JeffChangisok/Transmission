package com.example.jeffchang.transmission;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jeffchang.transmission.dao.adapter.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends AppCompatActivity implements TypeAdapter.RecyItemOnClick{

    private List<String> typeList = new ArrayList<>();
    private TypeAdapter adapter;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private Button button;
    private String type = "生活用品";
    private String note = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initData();
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TypeAdapter(typeList,TypeActivity.this);
        adapter.setRecyItemOnClick(this);
        recyclerView.setAdapter(adapter);
        imageButton = findViewById(R.id.ibtn_backMain);
        button = findViewById(R.id.btn_type);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onItemOnClick(View view, int index) {
        for (int i = 0; i < typeList.size(); i++) {
            recyclerView.getChildAt(i).setBackgroundResource(R.drawable.border1);
        }
        recyclerView.getChildAt(index).setBackgroundResource(R.drawable.border2);
        type = typeList.get(index);
    }

    private void initData(){
        typeList.add("电子产品");
        typeList.add("生活用品");
        typeList.add("食品");
        typeList.add("文件");
        typeList.add("鲜花");
        typeList.add("证件");
        typeList.add("其他");
    }
}
