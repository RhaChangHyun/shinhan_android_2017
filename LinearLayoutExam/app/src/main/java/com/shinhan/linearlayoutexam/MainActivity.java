package com.shinhan.linearlayoutexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButton1Clecked(View view) {
        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        Intent.putExtra("String", string);
    }
}
