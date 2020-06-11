package com.example.plasma_bank_cmh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class forgot_pass extends AppCompatActivity {

    EditText email;
    Button submit;
    String mail_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email=findViewById(R.id.code);
        submit=findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail_s=email.getText().toString();
                String str = mail_s;
                final String strNew = str.replace(".", "");
                recovery_code.key=strNew;
                password_reset.key=strNew;

                try {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(strNew))
                            {
                                if (snapshot.child(strNew).child("request_status").getValue().equals("approved")) {
                                    Random rand = new Random();
                                    int value = rand. nextInt(99999);


                                    DatabaseReference myRef;
                                    myRef = FirebaseDatabase.getInstance().getReference("recovery");
                                    String key=strNew;
                                    myRef.child(key).setValue(value);

                                    String value_s=String.valueOf(value);
                                    recovery_code.sent_code_s=value_s;

                                    String toEmails = mail_s;
                                    List<String> toEmailList = Arrays.asList(toEmails
                                            .split("\\s*,\\s*"));
                                    new SendMailTask(forgot_pass.this).execute("cmhplasmatherapy@gmail.com",
                                            "CSE-0101", toEmailList, "Recover Code", "Your recovery code is :"+value_s);
                                    Toast.makeText(getApplicationContext(),"Recovery code is sent to your e-mail",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(forgot_pass.this,recovery_code.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Your account is not yet verified by admin",Toast.LENGTH_LONG).show();
                                }



                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Email not registered in the system",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                catch (Exception e)
                {
                    Toast.makeText(forgot_pass.this, "Cannot execute", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}