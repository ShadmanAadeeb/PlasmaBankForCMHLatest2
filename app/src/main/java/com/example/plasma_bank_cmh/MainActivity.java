package com.example.plasma_bank_cmh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText id,password;
    Button log_in;
    TextView forgot,sign_up;
    CheckBox rememberMeCheckBox;
    Button testButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //***********************************Parmanent Login Code Checking*******************************//
        //There is a file by the name rememberFile which comes with this app
        //at has the following key-valu pair : (remember,true) or record(remember,false)
        //At first I am going to check that file
        SharedPreferences sharedPreferences=getSharedPreferences("rememberFile",MODE_PRIVATE);
        String rememberValue=sharedPreferences.getString("remember","");
        if(rememberValue.equals("trueAdmin")){
            Home.role="Admin";
            Intent intent=new Intent(MainActivity.this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"Yes I was told to remember you as an admin",Toast.LENGTH_SHORT).show();
        }else if(rememberValue.equals("trueStaff")){

            Home.role="Staff";
            Intent intent=new Intent(MainActivity.this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"I was  told to remember you as a staff",Toast.LENGTH_SHORT).show();
        }else if(rememberValue.equals("trueDoctor")){
            Home.role="Doctor";
            Intent intent=new Intent(MainActivity.this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"I was  told to remember you as a doctor",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"I was NOT told to remember you ",Toast.LENGTH_SHORT).show();
        }

        //***********************************Parmanent Login Checking ends here*******************************//
        rememberMeCheckBox=findViewById(R.id.rememberMeCheckBox);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        log_in=findViewById(R.id.log_in);
        forgot=findViewById(R.id.forgot);
        sign_up=findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  Toast.makeText(getApplicationContext(),"Clicked on sign up", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,sign_up.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Clicked on forgot pass", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,forgot_pass.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_s=id.getText().toString();
                final String password_s=password.getText().toString();

                if (id_s.isEmpty() && password_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter ID and Password", Toast.LENGTH_LONG).show();
                }
                else if(id_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter ID",Toast.LENGTH_LONG).show();
                }
                else if(password_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String str = id_s;
                    final String strNew = str.replace(".", "");

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(strNew)) {

                                user u1=snapshot.child(strNew).getValue(user.class);
                                if (u1.password.equals(password_s))
                                {
                                    if (u1.request_status.equals("approved"))
                                    {
                                        acc_info.name_s=u1.name;
                                        acc_info.mail_s=u1.mail;
                                        acc_info.address_s=u1.address;
                                        acc_info.dob_s=u1.dob;
                                        acc_info.contact_s=u1.contact;
                                        acc_info.role_s=u1.role;
                                        Home.role=u1.role;
                                        acc_info.password_s=u1.password;
                                        acc_info.key=snapshot.getKey();
                                        if (u1.role.equals("Admin"))
                                        {
                                            Toast.makeText(getApplicationContext(),"You are logged in as an Admin", Toast.LENGTH_LONG).show();

                                        }
                                        else if(u1.role.equals("Staff"))
                                        {
                                            Toast.makeText(getApplicationContext(),"You are logged in as s Staff", Toast.LENGTH_LONG).show();
                                        }
                                        else if(u1.role.equals("Doctor"))
                                        {
                                            Toast.makeText(getApplicationContext(),"You are logged in as a Doctor", Toast.LENGTH_LONG).show();
                                        }
                                        //This is the part where login is completed and the activity is about to be changed
                                        //here i am telling the app to store the remember me value
                                        if(rememberMeCheckBox.isChecked()){
                                            SharedPreferences sharedPreferences1=getSharedPreferences("rememberFile",MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                                            editor.putString("remember","true"+u1.role);
                                            editor.apply();
                                            Toast.makeText(MainActivity.this,"Checked the checkbox",Toast.LENGTH_SHORT).show();
                                        }else{
                                            SharedPreferences sharedPreferences1=getSharedPreferences("rememberFile",MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                                            editor.putString("remember","false");
                                            editor.apply();
                                            Toast.makeText(MainActivity.this,"DID not Check the checkbox",Toast.LENGTH_SHORT).show();
                                        }


                                        //The remember me checkbox code has been dealt with
                                        Intent intent=new Intent(MainActivity.this,Home.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        //
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Your account is not yet verified by admin!", Toast.LENGTH_LONG).show();
                                    }

                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"invalid login credentials", Toast.LENGTH_LONG).show();
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"invalid login credentials",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
        //This is just the other way around kept for the purpose of fast testing
        testButton=findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                myAlertDialogBuilder.setMessage("Choose your option");
                myAlertDialogBuilder.setCancelable(true);

                myAlertDialogBuilder.setPositiveButton(
                        "Enroll Donor",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(MainActivity.this,enroll.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"Going to enroll option",Toast.LENGTH_SHORT).show();
                            }
                        });

                myAlertDialogBuilder.setNegativeButton(
                        "Donor List Option",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(MainActivity.this,donorlist.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"Going to Donot List option",Toast.LENGTH_SHORT).show();
                            }
                        });

                myAlertDialogBuilder.setNeutralButton(
                        "Donor Stat",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(MainActivity.this,statistics.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"Going to Statisitces List option",Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alertDialog = myAlertDialogBuilder.create();
                alertDialog.show();



            }
        });

        //This WAS just the other way around kept for the purpose of fast testing




    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
