package com.shinhan.whereisphoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButton2Clicked(View view) {
        Intent intent = new  Intent(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }
}
