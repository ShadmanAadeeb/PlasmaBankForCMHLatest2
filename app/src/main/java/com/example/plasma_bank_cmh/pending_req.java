package com.example.plasma_bank_cmh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class pending_req extends AppCompatActivity {
    MyTable2 myTable;
    TableLayout tableLayout;
    Button approved_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_req);

        approved_users=findViewById(R.id.approved_users);


        //**************************CREATING SPACE IN MEMORY FOR THE TABLE*************************//
        setUpTheTableLayoutObject();

        myTable.addTableHeaderContents(new String[]{"Name", "Contact","E-mail","Role"},4);
        //*************************INSERTING TABLE DATA******************************************//
        // Write a message to the database

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user");

        // Read from the database
        ValueEventListener valueEventListener=getAValueEventListener();
        myRef.addValueEventListener(valueEventListener);

        approved_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(pending_req.this,see_all_users.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });





    }
    void setUpTheTableLayoutObject(){
        tableLayout=findViewById(R.id.table_id);
        myTable=new MyTable2(tableLayout,pending_req.this);
        Log.d("TAG1","I have made myTable");
    }

    ValueEventListener getAValueEventListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("TAG1","In side on data change");
                for(DataSnapshot ds:dataSnapshot.getChildren()){


                    if (ds.child("request_status").getValue().toString().equals("pending"))
                    {
                        String name=ds.child("name").getValue().toString();
                        String contact=ds.child("contact").getValue().toString();
                        String mail=ds.child("mail").getValue().toString();
                        String role=ds.child("role").getValue().toString();
                        myTable.addStringArrayContents(new String[]{name, contact, mail,role},4);
                    }




                }
                Log.d("TAG1","Loop passed");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        };

    }

}
