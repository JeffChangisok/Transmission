package com.example.jeffchang.transmission;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.brioal.swipemenu.view.SwipeMenu;
import com.example.jeffchang.transmission.dao.MyMenuItem;
import com.example.jeffchang.transmission.dao.adapter.MenuItemAdapter;
import com.example.jeffchang.transmission.dao.TypeBean;
import com.example.jeffchang.transmission.utils.Util;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeMenu mMainSwipemenu;
    private LinearLayout ll_ji;
    private LinearLayout ll_song;
    private TextView tv_ji;
    private TextView tv_song;
    private TextView tv_ji_1;
    private TextView tv_ji_2;
    private TextView tv_song_1;
    private TextView tv_song_2;
    private TextView tv_weight;
    private ImageButton ibtn_type;
    private ImageButton ibtn_weight;
    private ImageButton ibtn_time;
    private ArrayList<TypeBean> weightList = new ArrayList<>();
    private List<MyMenuItem> itemList = new ArrayList<>();
    final static String TAG = "hhh";
    String address_ji;
    String building_ji;
    String name_ji;
    String phone_ji;
    String address_song;
    String building_song;
    String name_song;
    String phone_song;
    String type_name;
    String note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btn_openMenu;
        mMainSwipemenu = findViewById(R.id.main_swipemenu);
        btn_openMenu = findViewById(R.id.ibtn_openMenu);
        ll_ji = findViewById(R.id.ll_ji);
        ll_song = findViewById(R.id.ll_song);
        tv_ji = findViewById(R.id.tv_ji);
        tv_song = findViewById(R.id.tv_song);
        tv_ji_1 = findViewById(R.id.tv_ji_1);
        tv_ji_2 = findViewById(R.id.tv_ji_2);
        tv_song_1 = findViewById(R.id.tv_song_1);
        tv_song_2 = findViewById(R.id.tv_song_2);
        tv_weight = findViewById(R.id.tv_weight);
        ibtn_type = findViewById(R.id.ibtn_type);
        ibtn_weight = findViewById(R.id.ibtn_weight);
        ibtn_time = findViewById(R.id.ibtn_time);
        final NavigationView navView = findViewById(R.id.nav_view);
        /*个位代表的旋转动画效果序号,十位代表透明度动画效果,
          千位代表缩放动画效果,万位代表位移动画效果,
          各个位置的起始都是1
         */
        mMainSwipemenu.setStyleCode(1221);
        mMainSwipemenu.setStartAlpha(0.7f);
        mMainSwipemenu.setStartScale(0.7f);
        btn_openMenu.setOnClickListener(this);
        ibtn_type.setOnClickListener(this);
        ibtn_weight.setOnClickListener(this);
        ibtn_time.setOnClickListener(this);
        /*
          侧滑菜单
         */
        initData();
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
                        Intent intent1 = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent1);
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
            case R.id.tv_price_introduce:
                Intent intent = new Intent(MainActivity.this,PriceIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.ibtn_type:
                Intent intent1 = new Intent(MainActivity.this,TypeActivity.class);
                startActivityForResult(intent1,3);
                break;
            case R.id.ibtn_weight:
                Util.alertBottomWheelOption(MainActivity.this, weightList, new Util.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int position) {
                        tv_weight.setText(weightList.get(position).getName());
                        Log.d(TAG, "onClick: ..."+weightList.get(position).getName()+"...");
                    }
                });
                break;
            case R.id.ibtn_time:
                break;
        }
    }

    public void onAddressClick(View v){
        Intent intent = new Intent(MainActivity.this,FillInfoActivity.class);
        switch (v.getId()){
            case R.id.ll_big_ji:
                intent.putExtra("jiOrSong","ji");
                startActivityForResult(intent,1);
                break;
            case R.id.ll_big_song:
                intent.putExtra("jiOrSong","song");
                startActivityForResult(intent,2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    address_ji = data.getStringExtra("address");
                    building_ji = data.getStringExtra("building");
                    name_ji = data.getStringExtra("name");
                    phone_ji = data.getStringExtra("phone");
                    tv_ji.setVisibility(View.GONE);
                    tv_ji_1.setText(address_ji+" "+building_ji);
                    tv_ji_2.setText(name_ji+" "+phone_ji);
                    ll_ji.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    address_song = data.getStringExtra("address");
                    building_song = data.getStringExtra("building");
                    name_song = data.getStringExtra("name");
                    phone_song = data.getStringExtra("phone");
                    tv_song.setVisibility(View.GONE);
                    ll_song.setVisibility(View.VISIBLE);
                    tv_song_1.setText(address_song+""+building_song);
                    tv_song_2.setText(name_song+" "+phone_song);
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){

                }
                break;
        }
    }

    private void initData(){
        /*
         初始化侧滑菜单显示数据
         */
        itemList.add(new MyMenuItem("个人资料",R.mipmap.ic_personal));
        itemList.add(new MyMenuItem("订单中心",R.mipmap.ic_order));
        itemList.add(new MyMenuItem("我的钱包",R.mipmap.ic_wallet));
        itemList.add(new MyMenuItem("设置",R.mipmap.ic_setting));
        /*
         初始化重量数据
         */
        weightList.add(new TypeBean(0,"5公斤以下"));
        for(int i = 1;i < 20;i++){
            weightList.add(new TypeBean(i,i+"公斤"));
        }
    }

}
