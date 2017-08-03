package com.example.gathering;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button b;
    EditText et,pass;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BaseActivity.setGlobalFont(this, getWindow().getDecorView());

        b = (Button)findViewById(R.id.login);
        et = (EditText)findViewById(R.id.identity);
        pass= (EditText)findViewById(R.id.password);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(MainActivity.this, "", "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
            }
        });

        /*Button startButton = (Button)findViewById(R.id.login);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,Main.class);
                startActivity(intent1);
            }
        });*/

        Button joinButton = (Button)findViewById(R.id.join);
        joinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this,Join.class);
                startActivity(intent2);
            }
        });

        //액션바 설정하기
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색하기</font>"));
        getSupportActionBar().setTitle("로그인");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFE4E1));

        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void login(){
        try{
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://192.168.0.62/login.php");

            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("username",et.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString().trim()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);

            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("User Found")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(MainActivity.this, Main.class));
            }else{
                showAlert();
            }

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}

