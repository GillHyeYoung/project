package com.example.gathering;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 혜영 on 2017-06-23.
 */

public class Join extends AppCompatActivity {
    EditText et_id, et_pw, et_pw_chk, et_name, et_age;
    RadioButton et_gender;
    String sId, sPw, sPw_chk, sName, sGender, sAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        //BaseActivity.setGlobalFont(this, getWindow().getDecorView());

        et_id = (EditText)findViewById(R.id.editText01);
        et_pw = (EditText)findViewById(R.id.editText02);
        et_pw_chk = (EditText)findViewById(R.id.editText05);
        et_name = (EditText)findViewById(R.id.editText03);
        et_age = (EditText)findViewById(R.id.editText04);

        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();
        sPw_chk = et_pw_chk.getText().toString();
        sName = et_name.getText().toString();
        sAge = et_age.getText().toString();

        final RadioGroup gendergroup = (RadioGroup)findViewById(R.id.gendergroup);
        //RadioButton radio0 = (RadioButton)findViewById(R.id.radio0);
        //RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);

        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = gendergroup.getCheckedRadioButtonId();
                et_gender = (RadioButton) findViewById(id);
                sGender = et_gender.toString();

                if(checkedId == R.id.radio0){
                    sGender = "남자";
                }else if(checkedId == R.id.radio1){
                    sGender = "여자";
                }
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
        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();
        sPw_chk = et_pw_chk.getText().toString();

        if(sPw.equals(sPw_chk))
        {//패스워드 확인이 정상적으로 됨
            JoinDB rdb = new JoinDB();
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

    public class JoinDB extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {

//인풋 파라메터값 생성
            String param = "u_id=" + sId + "&u_pw=" + sPw + "&u_name=" + sName + "&u_age=" + sAge + "&u_gender=" + sGender + "";
            try {
// 서버연결
                URL url = new URL("http://192.168.10.10/join.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

// 안드로이드 -> 서버 파라메터값 전달
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

// 서버 -> 안드로이드 파라메터값 전달
                InputStream is = null;
                BufferedReader in = null;
                String data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
                Log.e("RECV DATA",data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

