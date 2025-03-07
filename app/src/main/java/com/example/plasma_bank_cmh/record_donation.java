package com.example.plasma_bank_cmh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class record_donation extends AppCompatActivity {
    String key;
    ListView listView;
    TextView keyTextView,nameTextView,addressTextView,bloodGroupTextView,dobTextView,emailTextView;
    TextView medicalHistoryTextView,negativeDateTextView,positiveDateTextView,contactTextView;
    TextView pastDonationCountTextView;
    TextView donationDateTextView;
    Button recordDonationButton;
    Button home;

    ArrayList<String> dateArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_donation);

        home=findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(record_donation.this,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        //********************I COLLECT THE INFO SENT TO ME*****************************//
        String[] stringArray=getIntent().getStringArrayExtra("rowContentsString");

        //I collect key
        key=stringArray[0];
        //******************NOW I LINK THE VIEWS AND PLACE THE STRINGS INSIDE********************************//
        linkTheViewsAndMakeThemWorkable(stringArray);
        //**********************MAKE THE RECORD DONATION BUTTON WORKABLE************************************//
        recordDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I am getting the reference to the particular user
                final String donationDateTextViewText= String.valueOf(donationDateTextView.getText().toString());
                if(donationDateTextViewText.equals("PRESS HERE TO SET DATE")){
                    Toast.makeText(record_donation.this,"Please insert Date",Toast.LENGTH_SHORT).show();
                    return;
                }


                final DatabaseReference specificDonorReference= FirebaseDatabase.getInstance().getReference("donor").child(key);
                //At first I assume that there is a count of donation and hence the donation dates
                DatabaseReference donationCountReference=specificDonorReference.child("donationDates");
                //Now I am going to try to read the snapshot from donationCount
                donationCountReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()==null){
                            Log.d("TAGY","This user never donated plasma");
                            //This is the users fast donation of plasma so the task now is simple
                            specificDonorReference.child("donationDates/1").setValue(donationDateTextViewText);
                            //specificDonorReference.child("donationCount").setValue(1);

                        }else{
                            Log.d("TAGY","This user DID donate plasma");
                            //I have to get the count value at first and also I have to add this date. For this I am going to use a listener
                            specificDonorReference.child("donationDates").addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.d("TAGY","DEBUGTEST0:"+dataSnapshot.getValue());
                                    int count=0;

                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                        count++;

                                    }

                                    Log.d("TAG1","Total no of donations is:"+count);
                                    count++;
                                    specificDonorReference.child("donationDates/"+count).setValue(donationDateTextViewText);




                                    specificDonorReference.child("last_donation_date").setValue(donationDateTextViewText);

                                    Toast.makeText(getApplicationContext(),"Donation has been successfully recorded", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(record_donation.this,donorlist.class);
                                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    //




                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        //***************************************THE RECORD BUTTON HAS BEEN MADE WORKABLE**************************//

        //*************************************INSERT DATE DATA INTO THE ARRAYLIST********************************//


    }

    private void linkTheViewsAndMakeThemWorkable(String[] stringArray) {
        pastDonationCountTextView=findViewById(R.id.donationCountTextView);

        listView=findViewById(R.id.dateListView);
        recordDonationButton=findViewById(R.id.recordDonationButton);

        keyTextView=findViewById(R.id.recordKeyTextView);
        keyTextView.setText("Key:"+stringArray[0]);//ok

        nameTextView=findViewById(R.id.recordNameTextView);
        nameTextView.setText("Name:"+stringArray[1]);//ok

        addressTextView=findViewById(R.id.recordAddressTextView);
        addressTextView.setText("Address:"+stringArray[2]);//ok

        bloodGroupTextView=findViewById(R.id.recordBloodGroupTextView);
        bloodGroupTextView.setText("Blood Group:"+stringArray[3]);//ok

        dobTextView=findViewById(R.id.recordDOBTextView);
        dobTextView.setText("Date of Birth:"+stringArray[5]);

        emailTextView=findViewById(R.id.recordMailTextView);
        emailTextView.setText("Email:"+stringArray[6]);

        medicalHistoryTextView=findViewById(R.id.recordMedicalHistoryTextTextView);
        medicalHistoryTextView.setText(stringArray[7]);

        negativeDateTextView=findViewById(R.id.recordNegativeDateTextTextView);
        negativeDateTextView.setText("Negative Date"+stringArray[8]+"  Positive date"+stringArray[9]);

        positiveDateTextView=findViewById(R.id.recordPositiveDateTextTextView);
        positiveDateTextView.setText("Last Donation Date:"+stringArray[10]);

        contactTextView=findViewById(R.id.recordContactTextView);
        contactTextView.setText("Contact:"+stringArray[4]);

        donationDateTextView=findViewById(R.id.recordDonationDate);
        donationDateTextView.setText("PRESS HERE TO SET DATE");


        //******************ADDING THE DATE PICKER TO DONATION DATE*********************//
        final DatePickerDialog.OnDateSetListener donationDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // Log.d(String.valueOf(enroll), "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String month_s=String.valueOf(month);
                String day_s=String.valueOf(day);
                if (month_s.length()==1)
                {
                    month_s="0"+month_s;
                }
                if (day_s.length()==1)
                {
                    day_s="0"+day_s;
                }

                String date = year + "/" + month_s + "/" + day_s;

                //before setting the date I am going to check if 3 month have passed or not
                //I AM GETTING THE DATE 90 days before
                /*Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.DATE,-90);
                Date dateBefore3Months=calendar.getTime();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                String dateBefore3MonthsString=simpleDateFormat.format(dateBefore3Months);
                //I am getting the date 28 days before
                Calendar calendar1=Calendar.getInstance();
                calendar1.add(Calendar.DATE,-90);
                Date dateBefore28Days=calendar1.getTime();
                String dateBefore28DaysString=simpleDateFormat.format(dateBefore28Days);
                Log.d("TAGZ","The two dates are :"+dateBefore3MonthsString+" and "+dateBefore28Days);*/

                donationDateTextView.setText(date);
            }
        };
        donationDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        record_donation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        donationDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }
}