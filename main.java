package com.example.gathering;

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

import java.util.ArrayList;

/**
 * Created by 혜영 on 2017-06-23.
 */

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();

        //ArrayList에 데이터 넣기
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
        test.add("5");
        test.add("6");
        test.add("7");
        test.add("8");
        test.add("9");
        test.add("10");
        test.add("11");
        test.add("12");
        test.add("13");
        test.add("14");
        test.add("15");
        test.add("16");
        test.add("16");
        test.add("16");
        test.add("16");
        test.add("16");

        //ArrayAdapater을 이용해서 어댑터 생성
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test);

        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(Adapter);

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
            //Toast.makeText(this, "모임순위확인", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(Main.this, Main.class);
            startActivity(intent1);
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
}
