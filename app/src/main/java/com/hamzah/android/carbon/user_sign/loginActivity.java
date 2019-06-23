package com.hamzah.android.carbon.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hamzah.android.carbon.MainActivity;
import com.hamzah.android.carbon.R;

public class loginActivity extends AppCompatActivity {

    EditText inputEmail, inputPass;
    private Button btnLogIn;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.input_log_email);
        inputPass = findViewById(R.id.input_log_pass);
        btnLogIn = findViewById(R.id.btn_log);

        fAuth = FirebaseAuth.getInstance();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lemail = inputEmail.getText().toString().trim();
                String lpass  = inputPass.getText().toString().trim();

                if (!TextUtils.isEmpty(lemail)&& !TextUtils.isEmpty(lpass)){
                    logIn(lemail,lpass);
                }
            }
        });

    }

    private void logIn(String email, String password) {
        final ProgressDialog progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait.....");
        progressDialog.show();

        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {

                            Intent mainIntent = new Intent(loginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                            Toast.makeText(loginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(loginActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
