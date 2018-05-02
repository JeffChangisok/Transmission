package com.example.jeffchang.transmission;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.brioal.swipemenu.view.SwipeMenu;
import com.example.jeffchang.transmission.dao.MyMenuItem;
import com.example.jeffchang.transmission.dao.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeMenu mMainSwipemenu;
    private ImageButton btn_openMenu;
    private List<MyMenuItem> itemList = new ArrayList<>();
    final static String TAG = "hhh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainSwipemenu = findViewById(R.id.main_swipemenu);
        btn_openMenu = findViewById(R.id.ibtn_openMenu);
        final NavigationView navView = findViewById(R.id.nav_view);
        /*个位代表的旋转动画效果序号,十位代表透明度动画效果,
          千位代表缩放动画效果,万位代表位移动画效果,
          各个位置的起始都是1
         */
        mMainSwipemenu.setStyleCode(1221);
        mMainSwipemenu.setStartAlpha(0.7f);
        mMainSwipemenu.setStartScale(0.7f);
        btn_openMenu.setOnClickListener(this);
        /*
          侧滑菜单
         */
        initMenuItem();
        MenuItemAdapter adapter = new MenuItemAdapter(MainActivity.this,
                R.layout.list_item_menu,itemList);
        ListView listView = findViewById(R.id.lv_menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
        /*
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "onNavigationItemSelected:   "+item.getItemId());
                switch(item.getItemId()){
                    case R.id.nav_personal:
                        Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_order:
                        break;
                    case R.id.nav_wallet:
                        break;
                    case R.id.nav_setting:
                        break;
                }
                return true;
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_openMenu:
                mMainSwipemenu.showMenu();
                break;
        }
    }

    private void initMenuItem(){
        itemList.add(new MyMenuItem("个人资料",R.mipmap.ic_personal));
        itemList.add(new MyMenuItem("订单中心",R.mipmap.ic_order));
        itemList.add(new MyMenuItem("我的钱包",R.mipmap.ic_wallet));
        itemList.add(new MyMenuItem("设置",R.mipmap.ic_setting));
    }

}
