package com.shinhan.whereisphoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linear1 = (LinearLayout) findViewById(R.id.linear1);

        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.icon);
        linear1.addView(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.icon);
        linear1.addView(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.icon);
        linear1.addView(iv3);




    }
    public void onButton2Clicked(View view) {
        Intent intent = new  Intent(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }
}
