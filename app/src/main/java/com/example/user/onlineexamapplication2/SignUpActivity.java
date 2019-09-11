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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signUpbtn;
    private EditText signUpemail,signUppassword,signUpname,signUpPhn;
    private ProgressBar progressBarsignUp;
    private FirebaseAuth mAuth;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up Activity");

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4ebeaf")));



        signUpbtn=findViewById(R.id.signUpPage_btn);
        signUpemail=findViewById(R.id.signUpEmailEditText);
        signUppassword=findViewById(R.id.signUpPasswordEditText);
        signUpname=findViewById(R.id.nameEditText);
        signUpPhn=findViewById(R.id.signUpContactEditText);


        mAuth = FirebaseAuth.getInstance();

        progressBarsignUp=findViewById(R.id.progressbarIdSignUp);

        signUpbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

       if(v.getId()==R.id.signUpPage_btn){

           userRegister();
           Intent intent=new Intent(getApplicationContext(),LoginPage.class);
           startActivity(intent);

       }
    }


    private void userRegister() {

        String email=signUpemail.getText().toString().trim();
        final String password=signUppassword.getText().toString().trim();

        if(email.isEmpty()){
            signUpemail.setError("Enter an email address");
            signUpemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpemail.setError("Enter a valid email address");
            signUpemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUppassword.setError("Enter a password");
            signUppassword.requestFocus();
            return;
        }
        if(password.length()<6){
            signUppassword.setError("Minimum length should be 6");
            signUppassword.requestFocus();
            return;
        }
        progressBarsignUp.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarsignUp.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    finish();
                    Intent intent =new Intent(getApplicationContext(),LoginPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User is already registered",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
