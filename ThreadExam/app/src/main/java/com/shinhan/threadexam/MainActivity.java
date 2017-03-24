package com.shinhan.threadexam;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ProgressHandler progressHandler = new ProgressHandler();
    Handler handler = new Handler();
    ProgressRunnable progressRunnable = new ProgressRunnable();
    boolean isRunning = false;
    ProgressBar progressBar1, progressBar2,progressBar3,progressBar4;
    TextView textView1, textView2,textView3,textView4;
    class ProgressTask extends AsyncTask<Integer, Integer,Integer> {
        private int value = 0;
        @Override
        protected  Integer doInBackground(Integer...params) {//ui점근하면 안됨
            for (int i = 0; i < 10 && isRunning; i++) {
                value+=10;
                publishProgress(value); //onProgressUpdate 호출
                try { Thread.sleep(2000); }
                catch (Exception e) {e.printStackTrace(); }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {//백그라운드 작업전에 호출
            super.onPreExecute();
            value = 0; progressBar4.setProgress(value); //프로그레스초기화
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textView4.setText("Done" );
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView4.setText("Working..."+values[0]);
        }
    }
    public class ProgressHandler extends Handler{ // 스레드대신 메인 ui에 접근
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar1.incrementProgressBy(5);//1초에 한번식 5초
            if (progressBar1.getProgress() == progressBar1.getMax()) {
                textView1.setText("Done");
            } else {
                textView1.setText("Working..."+progressBar1.getProgress());
            }

        }
    }
    public class ProgressRunnable implements Runnable{ //스레드대신 메인 ui접근

        @Override
        public void run() {
            progressBar2.incrementProgressBy(5);
            if(progressBar2.getProgress() == progressBar2.getMax()) {
                textView2.setText("Done");
            } else {
                textView2.setText("Working..."+progressBar2.getProgress());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView)findViewById(R.id.textview1);
        textView2 = (TextView)findViewById(R.id.textview2);
        textView3 = (TextView)findViewById(R.id.textview3);
        textView4 = (TextView)findViewById(R.id.textview4);
        progressBar1 = (ProgressBar)findViewById(R.id.progress1);
        progressBar2 = (ProgressBar)findViewById(R.id.progress2);
        progressBar3 = (ProgressBar)findViewById(R.id.progress3);
        progressBar4 = (ProgressBar)findViewById(R.id.progress4);

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar1.setProgress(0); progressBar2.setProgress(0);
        Thread thread = new Thread(new Runnable() {//스레드 정의
            @Override
            public void run() {//스레드에서 실행되는 영역 (메인 ui접근 불가)
                try {
                    for (int i = 0; i < 20 && isRunning; i++) {
                        Thread.sleep(1000);
                        //1.핸들러를 이용한 메시지 전송방법
                        Message msg = progressHandler.obtainMessage();
                        progressHandler.sendMessage(msg);
                        //2. Runnnable객체를 실행하는 방법
                        handler.post(progressRunnable);

                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
