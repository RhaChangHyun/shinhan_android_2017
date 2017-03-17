package com.shinhan.linearlayoutexam;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }
    public void onButtonClicked(View view) {
        ImageView imageView1 = (ImageView)findViewById(R.id.imageview1);
        ImageView imagview2 = (ImageView)findViewById(R.id.imageview2);
        Button button = (Button)view;
        if (button.getText(.toString().equals("BUTTON1")) {
            imageView1.setBackground(R.drawable.a123);
            imageView2.setbackground(R.drawable.a3);
else{
                imageView1.setBackground(R.drawable.a123);
                imageView2.setbackground(R.drawable.a3);
            }
        }
    }



