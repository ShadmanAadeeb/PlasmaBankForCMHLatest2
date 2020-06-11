package com.example.plasma_bank_cmh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class statistics extends AppCompatActivity {
    TextView o,o_,a,a_,b,b_,ab,ab_,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        o=findViewById(R.id.o);
        o_=findViewById(R.id.o_);
        a=findViewById(R.id.a);
        a_=findViewById(R.id.a_);
        b=findViewById(R.id.b);
        b_=findViewById(R.id.b_);
        ab=findViewById(R.id.ab);
        ab_=findViewById(R.id.ab_);
        total=findViewById(R.id.total);


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("statistics");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //blood_group bd_grp=snapshot.getValue(blood_group.class);


//                o.setText("O+                            "+snapshot.child("O+").getValue());
//                o_.setText("O-                            "+snapshot.child("O-").getValue());
//                a.setText("A+                           "+snapshot.child("A+").getValue());
//                a_.setText("A-                            "+snapshot.child("A-").getValue());
//                b.setText("B+                           "+snapshot.child("B+").getValue());
//                b_.setText("B-                            "+snapshot.child("B-").getValue());
//                ab.setText("AB+                        "+snapshot.child("AB+").getValue());
//                ab_.setText("AB-                         "+snapshot.child("AB-").getValue());
//                total.setText("Total Donor:"+snapshot.child("count").getValue());

                o.setText("'O+'  Donor : "+snapshot.child("O+").getValue());
                o_.setText("'O-'  Donor : "+snapshot.child("O-").getValue());
                a.setText("'A+'  Donor : "+snapshot.child("A+").getValue());
                a_.setText("'A-'  Donor : "+snapshot.child("A-").getValue());
                b.setText("'B+'  Donor : "+snapshot.child("B+").getValue());
                b_.setText("'B-'  Donor : "+snapshot.child("B-").getValue());
                ab.setText("'AB+'  Donor : "+snapshot.child("AB+").getValue());
                ab_.setText("'AB-'  Donor : "+snapshot.child("AB-").getValue());
                total.setText("Total Donor Available: "+snapshot.child("count").getValue());





                //    o.setText(snapshot.getValue("O+").toString());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}