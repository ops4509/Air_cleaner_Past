package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeActivity extends AppCompatActivity {
    public EditText user_id;
    public EditText user_password;
    public EditText user_name;
    public EditText region;
    public EditText user_phon_number;
    public EditText aircleaner_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        final ContentValues values = new ContentValues();
        user_id = (EditText) findViewById(R.id.editText3);
        user_password = (EditText) findViewById(R.id.editText4);
        user_name = (EditText) findViewById(R.id.editText5);
        region = (EditText) findViewById(R.id.editText7);
        user_phon_number = (EditText) findViewById(R.id.editText8);
        aircleaner_id = (EditText) findViewById(R.id.editText6);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //values.put("user_id", String.valueOf(user_id));
                //values.put("user_password", String.valueOf(user_password));
                //values.put("user_name", String.valueOf(user_name));
                //values.put("region", String.valueOf(region));
                //values.put("user_phon_number", String.valueOf(user_phon_number));
                //values.put("aircleaner_id", String.valueOf(aircleaner_id));

                String id = null, pw, name, region2, phon, air;

                id = user_id.getText().toString();
                pw = user_password.getText().toString();
                name = user_name.getText().toString();
                region2 = region.getText().toString();
                phon = user_phon_number.getText().toString();
                air = aircleaner_id.getText().toString();

                values.put("user_id", id);
                values.put("user_password", pw);
                values.put("user_name", name);
                values.put("user_phon_number", phon);
                values.put("region", region2);
                values.put("aircleaner_id", air);

                NetworkTask networkTask = new NetworkTask("http://222.108.65.104:9900/app/sign_upServlet", values);
                networkTask.execute();

                //System.out.println(values);
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
            Toast.makeText(getApplicationContext(), "데이터를 전송중입니다...", Toast.LENGTH_LONG).show();
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
            String confirm;
            String a,b,c,d,e,f,g;
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            confirm = String.valueOf(result);
            System.out.println(result);
            String[] array = result.split("/");
            a = array[0];
            b = array[1];
            c = array[2];
            d = array[3];
            e = array[4];
            f = array[5];
            g = array[6];
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if(a.equals("Result:OK")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "회원 정보를 다시 입력해 주세요", Toast.LENGTH_LONG).show();
        }
    }
}
