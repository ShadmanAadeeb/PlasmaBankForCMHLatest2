package com.example.plasma_bank_cmh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class password_reset extends AppCompatActivity {

    public static String key;
    EditText new_pass,retype_new_pass;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
       // Toast.makeText(getApplicationContext(),key, Toast.LENGTH_LONG).show();
        new_pass=findViewById(R.id.new_pass);
        retype_new_pass=findViewById(R.id.retype_new_pass);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new_pass.getText().toString().equals(retype_new_pass.getText().toString()))
                {
                    DatabaseReference myRef;
                    myRef = FirebaseDatabase.getInstance().getReference("user");
                    myRef.child(key).child("password").setValue(new_pass.getText().toString());

                    //Toast.makeText(getApplicationContext(),"Clicked on sign up", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(password_reset.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"New password and Retype new password did not match",Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}