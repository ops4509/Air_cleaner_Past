package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Logged_MenuActivity extends AppCompatActivity {
    public static Activity Logged_MenuActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_menu);
        final Intent Data_intent = new Intent(this, User_Info_Activity.class);
        final Intent Data_intent2 = new Intent(this, OptionActivity.class);
        final ContentValues values = new ContentValues();
        Button button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Logged_DisplayActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button9);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = getIntent();
                String data = getIntent().getStringExtra("user_id");
                Data_intent.putExtra("user_id",data);
                startActivity(Data_intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button10);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = getIntent();
                String data = getIntent().getStringExtra("user_id");
                Data_intent2.putExtra("user_id", data);
                startActivity(Data_intent2);
            }
        });

        Button exitButton = (Button) findViewById(R.id.button11);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Logged_MenuActivity.this);
                builder.setMessage("정말로 종료하시겠습니까?");
                builder.setTitle("종료 알림창")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("종료 알림창");
                alert.show();
            }
        });
        Logged_MenuActivity = Logged_MenuActivity.this;
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

            Intent intent = new Intent(Logged_MenuActivity.this, Logged_DisplayActivity.class);
            intent.putExtra("regionData",result);
            startActivity(intent);
            //데이터 스플릿, 파일 입출력 데이터와 비교
            //같으면 액티비티 실행

            Toast.makeText(getApplicationContext(), result , Toast.LENGTH_LONG).show();
        }
    }
}
