package com.tce.mytceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        progressBar1=findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){


                    startActivity(new Intent(Splash.this,MainActivity.class));

                    finish();



                }
                else {
                    startActivity(new Intent(Splash.this,Home.class));

                    finish();



                }
            }
        },3000);

    }
}
