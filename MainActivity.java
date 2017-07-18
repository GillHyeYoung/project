package com.example.gathering;

        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());

        Button startButton = (Button)findViewById(R.id.login);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,Main.class);
                startActivity(intent1);
            }
        });

        Button joinButton = (Button)findViewById(R.id.join);
        joinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this,Join.class);
                startActivity(intent2);
            }
        });

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색하기</font>"));
        getSupportActionBar().setTitle("로그인");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFE4E1));


        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
