package com.example.jeffchang.transmission;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button btn;
        btn = findViewById(R.id.btn_quit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = PreferenceManager
                        .getDefaultSharedPreferences(SettingsActivity.this)
                        .edit();
                editor.remove("phone");
                editor.apply();
                editor.commit();
                finish();
            }
        });
    }
}
