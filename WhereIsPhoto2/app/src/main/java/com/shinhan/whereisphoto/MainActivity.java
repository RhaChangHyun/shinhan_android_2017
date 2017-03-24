package com.shinhan.whereisphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public class MainActivity extends Activity {

        ListView listView;


        @Override
        protected void onResume() {
            super.onResume();

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            listView = (ListView)this.findViewById(R.id.listView);

            ArrayList<String> items = new ArrayList<>();
            items.add("icon");
            items.add("icon1");
            items.add("icon2");
            items.add("icon3");

            CustomAdapter adapter = new CustomAdapter(this, 0, items);
            listView.setAdapter(adapter);
        }

        private class CustomAdapter extends ArrayAdapter<String> {
            private ArrayList<String> items;

            public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
                super(context, textViewResourceId, objects);
                this.items = objects;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.listview_item, null);
                }

                // ImageView 인스턴스
                ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

                // 리스트뷰의 아이템에 이미지를 변경한다.
                if("Seoul".equals(items.get(position)))
                    imageView.setImageResource(R.drawable.seoul);
                else if("Busan".equals(items.get(position)))
                    imageView.setImageResource(R.drawable.busan);
                else if("Daegu".equals(items.get(position)))
                    imageView.setImageResource(R.drawable.daegu);
                else if("Jeju".equals(items.get(position)))
                    imageView.setImageResource(R.drawable.jeju);


                TextView textView = (TextView)v.findViewById(R.id.textView);
                textView.setText(items.get(position));

                final String text = items.get(position);
                Button button = (Button)v.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                });

                return v;
            }
        }
    }

    public void onButton2Clicked(View view) {
        Intent intent = new  Intent(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }
}
