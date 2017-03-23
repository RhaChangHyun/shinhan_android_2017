package com.shinhan.googlenewsexam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private static String rssUrl =
            "http://api.sbs.co.kr/xml/news/rss.jsp?pmDiv=entertainment";

    class RSSNewsItem {
        String title;
        String link;
        String description;
        String pubDate;
        String author;
        String category;

        RSSNewsItem(String title, String link, String description, String pubDate,
                    String author, String category) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
            this.author = author;
            this.category = category;
        }
    }

    ArrayList<RSSNewsItem> newsItemList = new ArrayList<RSSNewsItem>();//동적배열ㅣ
    RSSListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsItemList.add(new RSSNewsItem("제목2", "https://m.naver.com",
                "설명1", "날짜1", "작성자1", "카테고리1"));
        newsItemList.add(new RSSNewsItem("제목2", "https://m.naver.com",
                "설명2", "날짜2", "작성자2", "카테고리2"));
        newsItemList.add(new RSSNewsItem("제목3", "https://m.naver.com",
                "설명3", "날짜3", "작성자3", "카테고리3"));
        ListView listView = (ListView) findViewById(R.id.listview);
        listAdapter = new RSSListAdapter(MainActivity.this);
        listView.setAdapter(listAdapter);
        EditText editText = (EditText) findViewById(R.id.input01);
        editText.setText(rssUrl);
    }

    class RSSListAdapter extends ArrayAdapter {
        public RSSListAdapter(Context context) {
            super(context, R.layout.listitem, newsItemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView != null) {
                view = convertView;
            } else {
                LayoutInflater inflater = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listitem, null, true);
            }  //데이터를 받아서 넣는다.
            TextView dataItem01 = (TextView) view.findViewById(R.id.dataItem01);
            TextView dataItem02 = (TextView) view.findViewById(R.id.dataItem02);
            TextView dataItem03 = (TextView) view.findViewById(R.id.dataItem03);
            WebView dataItem04 = (WebView) view.findViewById(R.id.dataItem04);
            dataItem01.setText(newsItemList.get(position).title);
            dataItem02.setText(newsItemList.get(position).pubDate);
            dataItem03.setText(newsItemList.get(position).category);
            //dataItem04.loadUrl(newsItemList.get(position).description);
            dataItem04.loadData(newsItemList.get(position).description,
                    "text/html; charset=utf-8", "utf-8");
            return view;
        }
    }

    public void onButtonClicked(View view) {//로딩창화면보이기
        EditText editText = (EditText) findViewById(R.id.input01);
        String urlString = editText.getText().toString();
        if (urlString.indexOf("http") != -1) {//http라는 문자열이 포함되어 있는지 확인
            new LoadXTML().execute(urlString); //입력한 url에 접속

        }


    }

    //안드로이드에서 쓰레드를 구성하는 공식 외워라
    class LoadXTML extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {//백그라운드 작업 전에 호출 //그냥 외우기
            super.onPreExecute();
            dialog.setMessage("뉴스 RSS 요청 중...");
            dialog.show();//프로그레스 다이얼로그 보여주기
            //XML파싱 ---------------------------
            //ListView 출력------------------------
            listAdapter.notifyDataSetChanged();

        }


        @Override
        protected void onPostExecute(String s) {//백그라운드 작업 후에 호출
            super.onPostExecute(s);
            dialog.dismiss();//프로그래스 다이얼로그 닫기

        }

        @Override
        protected String doInBackground(String... params) {//실제 통신이 처리되는 부분
            StringBuilder output = new StringBuilder();
            try {//통신 부분은 반드시 try-catch로 예외처리 해주어야 한다.
                URL url = new URL(params[0]);//전달받은 urlString으로 URL 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    ////////////////////////////////////////////////////////////////
                    DocumentBuilderFactory builderFactory =
                            DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    InputStream inputStream = conn.getInputStream();//웹서버 결과값수신
                    Document document = builder.parse(inputStream);//도큐먼트객체 생성
                    int count = processDocument(document);//XML파싱(DOM파서)
                    ///////////////////////////////////////////////////////////////
                    Log.i("count", count + "");
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    private int processDocument(Document document) {
        int count = 0;
        newsItemList.clear();//동적배열 초기화
        Element documentElement = document.getDocumentElement();
        NodeList nodelist = documentElement.getElementsByTagName("item");
        if ((nodelist != null) && (nodelist.getLength() > 0)) {//아이템이 존재하면
            for (int i = 0; i < nodelist.getLength(); i++) {//아이템개수만큼 반복
                RSSNewsItem newsitem = dissectNode(nodelist, i);
                if (newsitem != null) {//정상적인 아이템객체이면 동적배열에 추가
                    newsItemList.add(newsitem);
                    count++;
                    return count;
                }
            }
        }
        return count;
    }

    private RSSNewsItem dissectNode(NodeList nodelist, int index) {//아이템정보추출
        RSSNewsItem newsItem = null;
        try {
            Element entry = (Element) nodelist.item(index);//아이템 객체 추출
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element link = (Element) entry.getElementsByTagName("link").item(0);
            Element description =
                    (Element) entry.getElementsByTagName("description").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);
            Element author = (Element) entry.getElementsByTagName("author").item(0);
            Element category = (Element) entry.getElementsByTagName("category").item(0);
            String titleValue = getElementString(title);
            String linkValue = getElementString(link);
            String descriptionValue = getElementString(description);
            String pubDataValue = getElementString(pubDate);
            String authorValue = getElementString(author);
            String categoryValue = getElementString(category);

            newsItem = new RSSNewsItem(titleValue, categoryValue, descriptionValue, pubDataValue, authorValue, categoryValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsItem;
    }

    private String getElementString(Element element) {//Element 객체에서 문자열 추출
        String value = null;
        if (element != null) {
            Node firstChild = element.getFirstChild();
            if (firstChild != null) {
                value = firstChild.getNodeValue();
            }
        }
        return value;
    }
}

