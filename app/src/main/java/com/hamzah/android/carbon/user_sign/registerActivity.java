package com.hamzah.android.carbon.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hamzah.android.carbon.MainActivity;
import com.hamzah.android.carbon.R;

public class registerActivity extends AppCompatActivity {

    private Button btnReg;
     EditText inName, inEmail, inPass;

    private FirebaseAuth fAuth;
    private DatabaseReference fUsersDatabase;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnReg =  findViewById(R.id.btn_reg);
        inName =  findViewById(R.id.input_name);
        inEmail=  findViewById(R.id.input_email);
        inPass =  findViewById(R.id.input_pass);

        fAuth = FirebaseAuth.getInstance();
        fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = inName.getText().toString().trim();
                String uemail = inEmail.getText().toString().trim();
                String upass = inPass.getText().toString().trim();

                registerUser(uname, uemail, upass);

            }
        });

    }
    private void registerUser(final String name, String email, String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            fUsersDatabase.child(fAuth.getCurrentUser().getUid())
                                    .child("basic").child("name").setValue(name)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                progressDialog.dismiss();

                                                Intent mainIntent = new Intent(registerActivity.this, MainActivity.class);
                                                startActivity(mainIntent);
                                                finish();
                                                Toast.makeText(registerActivity.this, "User created!", Toast.LENGTH_SHORT).show();

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(registerActivity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {

                            progressDialog.dismiss();

                            Toast.makeText(registerActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

}
