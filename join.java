package com.example.gathering;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 혜영 on 2017-06-23.
 */

public class Join extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Intent intent = getIntent();

        Button JoinButton = (Button)findViewById(R.id.button02);
        JoinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Join.this,Main.class);
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
}

