package com.example.plasma_bank_cmh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class acc_info extends AppCompatActivity {
    Button edit,save;
    EditText name,address,contact,mail,dob,role;

    public static String name_s;
    public static String address_s;
    public static String contact_s;
    public static String mail_s;
    public static String password_s;
    public static String description_s;
    public static String dob_s;
    public static String role_s;
    public static String key;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_info);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        contact=findViewById(R.id.contact);
        mail=findViewById(R.id.mail);
        dob=findViewById(R.id.dob);
        role=findViewById(R.id.role);
        edit=findViewById(R.id.edit);
        save=findViewById(R.id.save);

        name.setText(name_s);
        address.setText(address_s);
        contact.setText(contact_s);
        mail.setText(mail_s);
        dob.setText(dob_s);
        role.setText(role_s);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               name.setEnabled(true);
               address.setEnabled(true);
               contact.setEnabled(true);
               mail.setEnabled(true);
              // dob.setEnabled(true);
              // role.setEnabled(true);
               save.setEnabled(true);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_s=name.getText().toString();
                address_s=address.getText().toString();
                contact_s=contact.getText().toString();
                mail_s=mail.getText().toString();
                dob_s=dob.getText().toString();
                role_s=role.getText().toString();
                String dum="approved";
                //public static String key
                user s2=new user(name_s,address_s,contact_s,mail_s,password_s,dob_s,role_s,description_s,dum);

                FirebaseDatabase database;
                DatabaseReference myRef;


                database = FirebaseDatabase.getInstance();
                myRef = FirebaseDatabase.getInstance().getReference("user");



                myRef.child(key).setValue(s2);
                Toast.makeText(getApplicationContext(),"Successfully updated", Toast.LENGTH_LONG).show();

                name.setEnabled(false);
                address.setEnabled(false);
                contact.setEnabled(false);
                mail.setEnabled(false);
                //dob.setEnabled(false);
               // role.setEnabled(false);
                save.setEnabled(false);


            }
        });


       // Toast.makeText(getApplicationContext(),acc_info.email+"   "+acc_info.dob, Toast.LENGTH_LONG).show();

    }
}