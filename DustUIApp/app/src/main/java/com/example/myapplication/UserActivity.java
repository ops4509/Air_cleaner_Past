package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//package com.example.test;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserActivity extends AppCompatActivity {
    private TextView tv_outPut;
    String login_id = null;
    String login_pw = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final Intent Data_intent = new Intent(this, User_Info_Activity.class);
        final Intent Data_intent2 = new Intent(this, Logged_MenuActivity.class);
        final ContentValues values = new ContentValues();

        Button button = (Button) findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener(){

            File file = new File("file.txt") ;
            FileWriter fw = null ;
            BufferedWriter bufwr = null ;

            @Override
            public void onClick(View v) {


                    EditText id = (EditText) findViewById(R.id.editText);
                    EditText pw = (EditText) findViewById(R.id.editText2);
                    login_id = id.getText().toString();
                    login_pw = pw.getText().toString();

                    values.put("user_id", login_id);
                    values.put("user_password", login_pw);

                    //쓰기
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", false)); //true 면 이어쓰기 false면 덮어쓰기
                        bw.write(login_id + "/" + login_pw);
                        bw.close();

                        Toast.makeText(UserActivity.this, "로그인 중...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(UserActivity.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                    //읽기
                    UserActivity.NetworkTask networkTask = new UserActivity.NetworkTask("http://222.108.65.104:9900/app/login_userServlet", values);
                    networkTask.execute();

            }

        });

        Button button2 = (Button) findViewById(R.id.button14);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MakeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void patchEOFException() {

        System.setProperty("http.keepAlive", "false");

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

            String a,b="",c,d;
            String sa,sb="";
            String a2 = "",b2 = "";
            String[] array = result.split(",");
            a = array[0];
            b = array[1];
            String[] smallarray = b.split(":");
            sb = smallarray[1];
            c = array[2];
            d = array[3];
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            //데이터 스플릿, 파일 입출력 데이터와 비교
            //같으면 액티비티 실행


            try{
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
                Toast.makeText(UserActivity.this, "File not Found", Toast.LENGTH_SHORT).show();
            }catch (IOException e) {
                e.printStackTrace();
            }

            if(a2.equals(sb)) {
                Intent intent = new Intent(UserActivity.this, Logged_MenuActivity.class);
                intent.putExtra("user_id", a2);

                startActivity(intent);

                MenuActivity aActivity = (MenuActivity) MenuActivity.MenuActivity;
                aActivity.finish();
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), "잘못된 로그인 정보입니다.", Toast.LENGTH_LONG).show();

            //Toast.makeText(getApplicationContext(), a2+sb, Toast.LENGTH_LONG).show();
        }
    }
 }


