package com.example.plasma_bank_cmh;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class see_all_users_and_edit extends AppCompatActivity {

    String name_s;
    String address_s;
    String contact_s;
    String dob_s;
    String role_s;
    String mail_s;
    String password_s;
    String confirm_passwword_s;
    String description_s;
    public static String key;

    Button approve,reject;
    Button home;

    EditText name,address,phone,mail,dob,description,role;
    //DatePickerDialog.OnDateSetListener mDateSetListener_dob_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_approval);

        String[] stringArray=getIntent().getStringArrayExtra("rowContentsString");

        //I collect key
        key=stringArray[2];

        String str = key;
        final String strNew = str.replace(".", "");
        key=strNew;

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.contact);
        mail=findViewById(R.id.mail);
        description=findViewById(R.id.description);
        dob=findViewById(R.id.dob);
        approve=findViewById(R.id.approve);
        role=findViewById(R.id.role);
        reject=findViewById(R.id.reject);

        ((android.widget.Button)findViewById(R.id.approve)).setText("Remove User");
        ((android.widget.TextView)findViewById(R.id.header_text)).setText("User information");
        View b=findViewById(R.id.reject);
        b.setVisibility(View.GONE);

        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(see_all_users_and_edit.this,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(strNew)) {

                    user u1=snapshot.child(strNew).getValue(user.class);

                    name_s=u1.name;
                    mail_s=u1.mail;
                    address_s=u1.address;
                    dob_s=u1.dob;
                    contact_s=u1.contact;
                    role_s=u1.role;
                    description_s=u1.description;
                    password_s=u1.password;


                    name.setText(name_s);
                    address.setText(address_s);
                    phone.setText(contact_s);
                    mail.setText(mail_s);
                    description.setText(description_s);
                    dob.setText(dob_s);
                    role.setText(role_s);

                    name.setEnabled(false);
                    address.setEnabled(false);
                    phone.setEnabled(false);
                    mail.setEnabled(false);
                    description.setEnabled(false);
                    dob.setEnabled(false);
                    role.setEnabled(false);
                    //    Toast.makeText(getApplicationContext(),key, Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder myAlertDialogBuilder = new AlertDialog.Builder(see_all_users_and_edit.this);
                myAlertDialogBuilder.setMessage("This user will be completely removed from the system. Are you sure to remove this user?");
                myAlertDialogBuilder.setCancelable(true);

                myAlertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                DatabaseReference myRefetence=firebaseDatabase.getReference("user").child(key);
                                myRefetence.removeValue();
//                                user s1=new user(name_s,address_s,contact_s,mail_s,password_s,dob_s,role_s,description_s,"removed");
//
//                                myRefetence.setValue(s1);
                                //  Log.d("TAG1","DONE UPDATING");


                                String toEmails = mail_s;
                                List<String> toEmailList = Arrays.asList(toEmails
                                        .split("\\s*,\\s*"));
                                new SendMailTask(see_all_users_and_edit.this).execute("cmhplasmatherapy@gmail.com",
                                        "CSE-0101", toEmailList, "Removal of Account", "Your account is removed from system. Now you no longer have access to CMH Plasma Bank. For any inconvenience, you may email at this Email-ID:cmhplasmatherapy@gmail.com and request for a new account");
//                                Toast.makeText(getApplicationContext(),"Recovery code is sent to your e-mail",Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(forgot_pass.this,recovery_code.class);
//                                startActivity(intent);
//



                                Toast.makeText(getApplicationContext(),"User removed from the system.", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(see_all_users_and_edit.this,see_all_users.class);
                                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

                myAlertDialogBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent=new Intent(see_all_users_and_edit.this,donorlist.class);
//                                startActivity(intent);
//                                Toast.makeText(see_all_users_and_edit.this,"Going to Donot List option",Toast.LENGTH_SHORT).show();
                            }
                        });

//                myAlertDialogBuilder.setNeutralButton(
//                        "Donor Stat",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent=new Intent(see_all_users_and_edit.this,statistics.class);
//                                startActivity(intent);
//                                Toast.makeText(see_all_users_and_edit.this,"Going to Statisitces List option",Toast.LENGTH_SHORT).show();
//                            }
//                        });

                AlertDialog alertDialog = myAlertDialogBuilder.create();
                alertDialog.show();


            }
        });

//        reject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//                DatabaseReference myRefetence=firebaseDatabase.getReference("user").child(key);
//                myRefetence.removeValue();
//
//                Toast.makeText(getApplicationContext(),"Request rejected ", Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(see_all_users_and_edit.this,pending_req.class);
//                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//            }
//        });



        //  Toast.makeText(getApplicationContext(),name_s+""+address_s, Toast.LENGTH_LONG).show();







    }
}