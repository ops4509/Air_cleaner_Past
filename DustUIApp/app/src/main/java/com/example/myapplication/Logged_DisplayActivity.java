package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Logged_DisplayActivity extends AppCompatActivity {

    final ContentValues values = new ContentValues();
    final ContentValues values2 = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_logged);

        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String Data = getIntent().getStringExtra("aircleaner");

                values2.put("aircleaner_id", 1);

                Logged_DisplayActivity.NetworkTask2 networkTask2 = new Logged_DisplayActivity.NetworkTask2("http://222.108.65.104:9900/app/show_indoor_microDust_on_regionServlet", values2);
                networkTask2.execute();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Data;
                EditText id = (EditText) findViewById(R.id.editText17);
                Data = id.getText().toString();

                values.put("region",Data);
                //쓰기
        /*try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", false)); //true 면 이어쓰기 false면 덮어쓰기
            bw.write(login_id+"/"+login_pw);
            bw.close();

            Toast.makeText(Logged_DisplayActivity.this,"로그인 중...", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(Logged_DisplayActivity.this,"실패", Toast.LENGTH_SHORT).show();
        }
        //읽기*/
                Logged_DisplayActivity.NetworkTask networkTask = new Logged_DisplayActivity.NetworkTask("http://222.108.65.104:9900/app/show_outdoor_dust_region_findServlet", values);
                networkTask.execute();
            }
        });

    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
            //Toast.makeText(getApplicationContext(), "데이터를 전송중입니다...", Toast.LENGTH_LONG).show();
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

            String a,b;
            //String sa,sb,sc,sd,se;
            String[] array = result.split(",");
            a = array[0];

            EditText id = (EditText) findViewById(R.id.editText16);
            id.setText(a);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        NetworkTask2(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
            //Toast.makeText(getApplicationContext(), "데이터를 전송중입니다...", Toast.LENGTH_LONG).show();
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

            String a;
            //String sa,sb,sc,sd,se;
            String[] array = result.split(",");
            a = array[0];
            //String[] smallarray = result.split("/");
            //데이터 스플릿, 파일 입출력 데이터와 비교
            //같으면 액티비티 실행


            EditText id = (EditText) findViewById(R.id.editText15);
            id.setText(a);

            /*try{
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"test.txt"));
                String readStr = "";
                String str = null;
                while(((str = br.readLine()) != null)){
                    readStr += str +"\n";
                }
                br.close();

                //Toast.makeText(UserActivity.this, readStr.substring(0, readStr.length()-1), Toast.LENGTH_SHORT).show();

                String[] array2 = readStr.split("/");
                a2 = array2[0];
                b2 = array2[1];

            }catch (FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(Logged_DisplayActivity.this, "File not Found", Toast.LENGTH_SHORT).show();
            }catch (IOException e) {
                e.printStackTrace();
            }*/

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}