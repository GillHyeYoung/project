package com.example.sm_pc.gathering;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sm-pc on 2017-06-23.
 */

public class Main extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_GNAME = "gathering_name";
    private static final String TAG_PNAME = "promotor_name";
    private static final String TAG_DATE = "date";
    private static final String TAG_LOCATION ="location";
    private static final String TAG_PHONENUM = "phone_number";
    private static final String TAG_PEOPLENUM = "num_of_people";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());

        Intent intent = getIntent();

        GetData task = new GetData();
        task.execute("http://192.168.0.43/getjson.php");

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();



        //ArrayList에 데이터 넣기
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");        test.add("2");        test.add("3");        test.add("4");        test.add("5");
        test.add("6");        test.add("7");        test.add("8");        test.add("9");        test.add("10");
        test.add("11");        test.add("12");        test.add("13");        test.add("14");        test.add("15");
        test.add("16");        test.add("17");        test.add("18");        test.add("19");        test.add("20");

        //ArrayAdapater을 이용해서 어댑터 생성
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test);

        //ListView listView = (ListView)findViewById(R.id.listView_main_list);
        mlistView.setAdapter(Adapter);

        Button registerButton = (Button)findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Main.this,Register.class);
                startActivity(intent1);
            }
        });

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색하기</font>"));
        getSupportActionBar().setTitle("검색하기");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFE4E1));


        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_rank) {
            Toast.makeText(this, "모임순위확인", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_register) {
            //Toast.makeText(this, "모임등록", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(Main.this,Register.class);
            startActivity(intent2);
            return true;
        }
        if (id == R.id.action_better) {
            //Toast.makeText(this, "개선사항", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(Intent.ACTION_SEND);
            String[] mailaddr = {"weemail@gmail.com"};

            it.setType("plaine/text");
            it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
            it.putExtra(Intent.EXTRA_SUBJECT, "[app improvement requirement]"); // 제목
            //it.putExtra(Intent.EXTRA_TEXT, "\n\n" + "v" + appVersion); // 첨부내용

            startActivity(it);
            return true;
        }
        if (id == R.id.action_question) {
            //Toast.makeText(this, "문의하기", Toast.LENGTH_SHORT).show();
            String[] to = {"weemail@gmail.com"};

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, to);
            email.putExtra(Intent.EXTRA_SUBJECT, "[question]");

            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Main.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String gname = item.getString(TAG_GNAME);
                String pname = item.getString(TAG_PNAME);
                String date = item.getString(TAG_DATE);
                String location = item.getString(TAG_LOCATION);
                String phonenum = item.getString(TAG_PHONENUM);
                String peoplenum = item.getString(TAG_PEOPLENUM);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_GNAME, gname);
                hashMap.put(TAG_PNAME, pname);
                hashMap.put(TAG_DATE, date);
                hashMap.put(TAG_LOCATION, location);
                hashMap.put(TAG_PHONENUM, phonenum);
                hashMap.put(TAG_PEOPLENUM, peoplenum);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Main.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_GNAME,TAG_PNAME,TAG_DATE,TAG_LOCATION,TAG_PHONENUM,TAG_PEOPLENUM},
                    new int[]{R.id.textView_gathering_name, R.id.textView_promotor_name, R.id.textView_date, R.id.textView_location, R.id.textView_phone_number, R.id.textView_num_of_people}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}
