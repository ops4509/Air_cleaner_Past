package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User_Info_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        final ContentValues values = new ContentValues();

        Intent intent = getIntent();
        String data = intent.getStringExtra("user_id");
        values.put("user_id", data);

        User_Info_Activity.NetworkTask networkTask = new User_Info_Activity.NetworkTask("http://222.108.65.104:9900/app/full_userInfoServlet", values);
        networkTask.execute();

        Button button2 = (Button) findViewById(R.id.button7);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //쓰기
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", false)); //true 면 이어쓰기 false면 덮어쓰기
                    bw.write("");
                    bw.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);

                Logged_MenuActivity aActivity = (Logged_MenuActivity) Logged_MenuActivity.Logged_MenuActivity;
                aActivity.finish();
                finish();

                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다." , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void patchEOFException() {

        System.setProperty("http.keepAlive", "false");

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
            //Toast.makeText(getApplicationContext(), "데이터를 전송중입니다...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "데이터를 불러오고 있습니다..." , Toast.LENGTH_LONG).show();
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

            String a, b, c, d, e, f;
            String a2 = "", b2 = "";
            String[] array = result.split("/");
            a = array[0];
            b = array[1];
            c = array[2];
            d = array[3];
            e = array[4];
            f = array[5];
            //데이터 스플릿, 파일 입출력 데이터와 비교
            //같으면 액티비티 실행

            EditText user_id = (EditText) findViewById(R.id.editText10);
            EditText user_name = (EditText) findViewById(R.id.editText11);
            EditText user_region = (EditText) findViewById(R.id.editText12);
            EditText user_Num = (EditText) findViewById(R.id.editText13);
            EditText user_cleaner_id = (EditText) findViewById(R.id.editText14);

            user_id.setText(b);
            user_name.setText(c);
            user_region.setText(e);
            user_Num.setText(d);
            user_cleaner_id.setText(f);

            Intent intent = new Intent(User_Info_Activity.this, Logged_MenuActivity.class);
            intent.putExtra("region",e);

            Intent intent2 = new Intent(User_Info_Activity.this, Logged_DisplayActivity.class);
            intent2.putExtra("aircleaner", f);
        }
    }
}
