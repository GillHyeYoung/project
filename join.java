package com.example.gathering;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

import static org.apache.http.util.EntityUtils.getContentCharSet;

public class Join extends AppCompatActivity {
    EditText et_id, et_pw, et_pw_chk, et_name, et_age;
    RadioButton et_gender;
    String sId, sPw, sPw_chk, sName, sGender, sAge;
    private static String TAG = "phptest_Join";
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        // BaseActivity.setGlobalFont(this, getWindow().getDecorView());

        et_id = (EditText) findViewById(R.id.editText01);
        et_pw = (EditText) findViewById(R.id.editText02);
        et_pw_chk = (EditText) findViewById(R.id.editText05);
        et_name = (EditText) findViewById(R.id.editText03);
        et_age = (EditText) findViewById(R.id.editText04);

        sId = et_id.getText().toString().trim();
        sPw = et_pw.getText().toString().trim();
        sPw_chk = et_pw_chk.getText().toString().trim();
        sName = et_name.getText().toString().trim();
        sAge = et_age.getText().toString().trim();

        final RadioGroup gendergroup = (RadioGroup) findViewById(R.id.gendergroup);
        //RadioButton radio0 = (RadioButton)findViewById(R.id.radio0);
        //RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);

        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = gendergroup.getCheckedRadioButtonId();
                et_gender = (RadioButton) findViewById(id);
                sGender = et_gender.toString();

                if (checkedId == R.id.radio0) {
                    sGender = "남자";
                } else if (checkedId == R.id.radio1) {
                    sGender = "여자";
                }
            }
        });

        Button JoinButton = (Button) findViewById(R.id.button02);
        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Join.this, Main.class);
                startActivity(intent1);
            }
        });

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색하기</font>"));
        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFE4E1));

        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void bt_Join(View view) {
        //버튼을 눌렀을 때 동작하는 소스
        sId = et_id.getText().toString().trim();
        sPw = et_pw.getText().toString().trim();
        sPw_chk = et_pw_chk.getText().toString().trim();

        if(sPw.equals(sPw_chk))
        {//패스워드 확인이 정상적으로 됨
            GetData rdb = new GetData();
         /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("회원가입 완료");
        // AlertDialog 셋팅
            alertDialogBuilder.setMessage("가입을 축하합니다");*/
            rdb.execute();

            Button JoinButton = (Button)findViewById(R.id.button02);
            JoinButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent1 = new Intent(Join.this,MainActivity.class);
                    startActivity(intent1);
                }
            });
        }
        else
        {//패스워드 확인이 불일치 함
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //패스워드 확인이 정상적으로 됨
            alertDialogBuilder.setTitle("회원가입 실패");
            // AlertDialog 셋팅
            alertDialogBuilder.setMessage("패스워드 확인");
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Join.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            // 모두 작업을 마치고 실행할 일 (메소드 등등)
            super.onPostExecute(result);

        }

        @Override
        protected String doInBackground(String... params) {
            String content = executeClient();
            return content;
        }

        // 실제 전송하는 부분
        public String executeClient() {
            String postURL = "http://192.168.10.2/join2.php";
            HttpPost httpPost = new HttpPost(postURL); // Post객체 생성

            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("id", sId));
            post.add(new BasicNameValuePair("password",sPw));
            post.add(new BasicNameValuePair("name",sName));
            post.add(new BasicNameValuePair("age",sAge));
            post.add(new BasicNameValuePair("gender",sGender));

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();


            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
                httpPost.setEntity(entity);
                client.execute(httpPost); //실제로 실행시킨다
                return getContentCharSet(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

