package com.shinhan.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    GridView gridView;
    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        adapter = new SingerAdapter();

        adapter.addItem(new SingerItem("소녀시대",R.drawable.singer));
    }
}
