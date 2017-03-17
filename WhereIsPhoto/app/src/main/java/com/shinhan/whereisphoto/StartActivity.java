package com.shinhan.whereisphoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    public void onButton2Clicked(View view) {
        Intent intent = new Intent(StartActivity.this, PhotoFind.class);
        startActivityForResult(intent, 0);//startActivit
    }
}
