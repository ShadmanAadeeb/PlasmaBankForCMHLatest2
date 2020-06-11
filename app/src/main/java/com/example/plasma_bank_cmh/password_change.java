package com.example.plasma_bank_cmh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class password_change extends AppCompatActivity {

    EditText pass,new_pass,re_new_pass;
    Button submit;

    String pass_s;
    String new_pass_s;
    String re_new_pass_s;

    public static String name_s=acc_info.name_s;
    public static String address_s=acc_info.address_s;
    public static String contact_s=acc_info.contact_s;
    public static String mail_s=acc_info.mail_s;
    public static String password_s=acc_info.password_s;
    public static String description_s=acc_info.description_s ;
    public static String dob_s=acc_info.dob_s;
    public static String role_s=acc_info.role_s;
    public static String key=acc_info.key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        pass=findViewById(R.id.pass);
        new_pass=findViewById(R.id.new_pass);
        re_new_pass=findViewById(R.id.retype_new_pass);

        submit=findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_s=pass.getText().toString();
                new_pass_s=new_pass.getText().toString();
                re_new_pass_s=re_new_pass.getText().toString();
                Toast.makeText(getApplicationContext(),pass_s+" "+new_pass_s+re_new_pass_s,Toast.LENGTH_LONG).show();
                if(pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter current password",Toast.LENGTH_LONG).show();
                }
                else if(new_pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_LONG).show();
                }
                else if(re_new_pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_LONG).show();
                }
                else if (!new_pass_s.equals(re_new_pass_s))
                {
                    Toast.makeText(getApplicationContext(),"New password and retype new password did not match",Toast.LENGTH_LONG).show();
                }
                else if (!pass_s.equals(acc_info.password_s))
                {
                   // Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Current password incorrect",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),key, Toast.LENGTH_LONG).show();

                    FirebaseDatabase database;
                    DatabaseReference myRef;


                    database = FirebaseDatabase.getInstance();
                    myRef = FirebaseDatabase.getInstance().getReference("user");

                    String dum="approved";
                    user s10=new user(name_s,address_s,contact_s,mail_s,new_pass_s,dob_s,role_s,description_s,dum);



                    myRef.child(key).setValue(s10);
                    Toast.makeText(getApplicationContext(),"Password successfully updated", Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(password_change.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }

            }
        });

    }
}