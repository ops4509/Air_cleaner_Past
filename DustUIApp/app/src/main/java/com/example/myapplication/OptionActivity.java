package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class OptionActivity extends AppCompatActivity {
    static int i = 0;
    static int j = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ContentValues values = new ContentValues();

        Intent intent = getIntent();
        String data = intent.getStringExtra("user_id");
        values.put("user_id", data);
        String Data = "";
        if(count==0) {
            Button button3 = (Button) findViewById(R.id.button6); //수동 자동
            button3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String data = "";
                    j = 1 - j;
                    if (j == 0) {
                        data = "false";
                        if(i==1){
                            values.put("ON_OFF_checking", "false");
                        }
                    } else if (j == 1) {
                        data = "true";
                    }
                    values.put("manual_acting_checking", data);
                    System.out.println(count);
                    OptionActivity.NetworkTask networkTask = new OptionActivity.NetworkTask("http://222.108.65.104:9900/app/change_actingMotionServlet", values);
                    networkTask.execute();
                }
            });
        }
        count++;
        if (count == 1||j==1) {
            Button button = (Button) findViewById(R.id.button5); //전원
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String data = "";

                    i = 1 - i;
                    if (i == 0) {
                        data = "false";
                    } else if (i == 1) {
                        data = "true";
                    }

                    values.put("ON_OFF_checking", data);
                    System.out.println(count);
                    OptionActivity.NetworkTask networkTask = new OptionActivity.NetworkTask("http://222.108.65.104:9900/app/change_actingMotionServlet", values);
                    networkTask.execute();
                }
            });

            /*if(j==0){
                values.put("ON_OFF_checking", "false");
                OptionActivity.NetworkTask networkTask = new OptionActivity.NetworkTask("http://222.108.65.104:9900/app/change_actingMotionServlet", values);
                networkTask.execute();
            }*/
        }
        count--;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            ImageView Timg1 = (ImageView) findViewById(R.id.imageView);
            ImageView Timg2 = (ImageView) findViewById(R.id.imageView2);// 수동/자동
            ImageView Timg3 = (ImageView) findViewById(R.id.imageView3);
            ImageView Timg4 = (ImageView) findViewById(R.id.imageView4);// 전원

            String sa, sb;
            String a, b, c;
            System.out.println(result);
            String[] array = result.split("/");
            a = array[0];
            String[] smallarray = a.split(":");
            b = array[1];//수동/자동
            sa = smallarray[1];//전원
            c = array[2];
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if (b.equals("true")) {
                Timg1.setVisibility(VISIBLE);
                Timg2.setVisibility(INVISIBLE);

                if (sa.equals("true")) {
                    Timg3.setVisibility(VISIBLE);
                    Timg4.setVisibility(INVISIBLE);
                } else if (sa.equals("false")) {
                    Timg3.setVisibility(INVISIBLE);
                    Timg4.setVisibility(VISIBLE);
                }
            } else if (b.equals("false")) {
                Timg1.setVisibility(INVISIBLE);
                Timg2.setVisibility(VISIBLE);
                Timg3.setVisibility(INVISIBLE);
                Timg4.setVisibility(VISIBLE);
            }

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

}
