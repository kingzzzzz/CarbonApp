package com.hamzah.android.carbon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hamzah.android.carbon.user_sign.loginActivity;
import com.hamzah.android.carbon.user_sign.registerActivity;

public class StartActivity extends AppCompatActivity {

    public Button Reg,btnLog;
    public FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Reg = (Button) findViewById(R.id.start_reg_btn);
        btnLog = (Button) findViewById(R.id.start_log_btn);

        fAuth = FirebaseAuth.getInstance();

        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });



    }

    public void register(){
        Intent regIntent = new Intent(StartActivity.this, registerActivity.class);
        startActivity(regIntent);
    }

    private void login(){
        Intent logIntent = new Intent(StartActivity.this, loginActivity.class);
        startActivity(logIntent);
    }



    public void updateUI(){
        if (fAuth.getCurrentUser() != null){
            Log.i("StartActivity", "fAuth != null");
            Intent startIntent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        } else {

            Log.i("StartActivity", "fAuth == null");
        }
    }
}
