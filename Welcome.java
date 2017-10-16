package com.example.shabab.foodbook;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Intent login = getIntent();
        String name = login.getStringExtra("name");
        String email = login.getStringExtra("email");
        TextView txt = (TextView) findViewById(R.id.text);
        txt.setText("Welcome\n"+name);
        int SPLASH_TIME_OUT= 4000;
        final Intent home = new Intent(this,Home.class);
        home.putExtra("name",name);
        home.putExtra("email",email);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(home);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
