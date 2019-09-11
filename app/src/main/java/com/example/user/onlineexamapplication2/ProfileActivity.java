package com.example.user.onlineexamapplication2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private EditText profileName,profileEmail,profilePhone;

    DatabaseReference databaseReference;

    FirebaseAuth mAuth;
    private Button next,save;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference= FirebaseDatabase.getInstance().getReference("students");

        profileName=findViewById(R.id.profileNameId);
        profileEmail=findViewById(R.id.profileEmailId);
        profilePhone=findViewById(R.id.profilePhoneId);
        save=findViewById(R.id.save_btn);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4ebeaf")));
        this.setTitle("Profile Activity");




        mAuth=FirebaseAuth.getInstance();
        next=findViewById(R.id.button_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StartExamActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void saveData(){
      String pro_name=profileName.getText().toString().trim();
      String pro_email=profileEmail.getText().toString().trim();
      String pro_phn=profilePhone.getText().toString().trim();

      String key=databaseReference.push().getKey();

      Student student=new Student(pro_name,pro_email,pro_phn);

      databaseReference.child(key).setValue(student);
    }

}
