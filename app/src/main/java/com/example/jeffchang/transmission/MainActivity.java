package com.example.jeffchang.transmission;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.brioal.swipemenu.view.SwipeMenu;

public class MainActivity extends AppCompatActivity {

    private SwipeMenu mMainSwipemenu;
    final static String TAG = "hhh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainSwipemenu = findViewById(R.id.main_swipemenu);
        final NavigationView navView = findViewById(R.id.nav_view);
        /*个位代表的旋转动画效果序号,十位代表透明度动画效果,
          千位代表缩放动画效果,万位代表位移动画效果,
          各个位置的起始都是1
         */
        mMainSwipemenu.setStyleCode(1221);
        mMainSwipemenu.setStartAlpha(0.7f);
        mMainSwipemenu.setStartScale(0.7f);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "onNavigationItemSelected:   "+item.getItemId());
                switch(item.getItemId()){
                    case R.id.nav_personal:
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
        });
    }
}
