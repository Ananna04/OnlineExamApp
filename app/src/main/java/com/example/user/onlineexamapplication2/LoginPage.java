package com.example.user.onlineexamapplication2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private Button signInBtn;
    private EditText signInemailEdit, signInpasswordEdit;
    private TextView signUpTextView;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;
    private ProgressBar progressBarsignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4ebeaf")));


        this.setTitle("Login Activity");
        signInBtn=findViewById(R.id.loginpage_btn);
        signInemailEdit=findViewById(R.id.userId);
        signInpasswordEdit=findViewById(R.id.passwordId);
        signUpTextView=findViewById(R.id.signInTextView);

        progressBarsignIn=findViewById(R.id.progressbarIdSignIn);

       signInBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               userLogin();
           }
       });

       signUpTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
               startActivity(intent);
           }
       });

        mAuth = FirebaseAuth.getInstance();
    }
    private void userLogin() {
        String email = signInemailEdit.getText().toString().trim();
        final String password = signInpasswordEdit.getText().toString().trim();

        if (email.isEmpty()) {
            signInemailEdit.setError("Enter an email address");
            signInemailEdit.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInemailEdit.setError("Enter a valid email address");
            signInemailEdit.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signInpasswordEdit.setError("Enter a password");
            signInpasswordEdit.requestFocus();
            return;
        }
        if (password.length() < 6) {
            signInpasswordEdit.setError("Minimum length should be 6");
            signInpasswordEdit.requestFocus();
            return;
        }

        progressBarsignIn.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarsignIn.setVisibility(View.GONE);
                if ((task.isSuccessful())){
                    finish();
                    Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Unsuccessfull",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
