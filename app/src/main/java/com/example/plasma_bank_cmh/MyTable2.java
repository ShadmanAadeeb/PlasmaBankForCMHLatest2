package com.example.plasma_bank_cmh;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MyTable2 {
    TableLayout tableLayout;
    Context context;
    public MyTable2(TableLayout tableLayout, Context context){
        this.tableLayout=tableLayout;
        this.context=context;
    }

    void addTableHeaderContents(String [] stringArray,int stringArrayLength){
        //making the table row
        TableRow tableRow=new TableRow(tableLayout.getContext());
        tableRow.setOrientation(LinearLayout.HORIZONTAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setPaddingRelative(10,10,10,10);
        }

        //making the textview array
        TextView[] textView=new TextView[stringArrayLength];

        //setting each of the strings in textviews
        for(int i=0;i<stringArrayLength;i++){
            textView[i]=new TextView(tableLayout.getContext());
            textView[i].setText(stringArray[i]);
            textView[i].setTextColor(Color.WHITE);
            textView[i].setPadding(10,10,10,10);
            textView[i].setAllCaps(true);
            textView[i].setBackgroundColor(Color.BLACK);
        }
        //adding the textviews to the tablerow
        for(int i=0;i<stringArrayLength;i++){
            tableRow.addView(textView[i]);
        }
        //finally adding the table row to the table layout
        tableLayout.addView(tableRow);

    }



    void addStringArrayContents(final String [] stringArray, int stringArrayLength){
        //making the table row
        final TableRow tableRow=new TableRow(tableLayout.getContext());
        tableRow.setOrientation(LinearLayout.HORIZONTAL);
        tableRow.setPadding(10,10,10,10);

        //making the textview array
        TextView[] textView=new TextView[stringArrayLength];

        //setting each of the strings in textviews
        for(int i=0;i<stringArrayLength;i++){
            textView[i]=new TextView(tableLayout.getContext());
            textView[i].setText(stringArray[i]);
            textView[i].setTextColor(Color.BLACK);


            if(i%2==0){//even textview or cell
                textView[i].setBackgroundColor(Color.LTGRAY);
            }else{
                textView[i].setBackgroundColor(Color.WHITE);
            }
        }
        //adding the textviews to the tablerow
        for(int i=0;i<stringArrayLength;i++){
            tableRow.addView(textView[i]);
        }

        //Adding the long press listener to the table row
        tableRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(context,pending_approval.class);

                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("rowContentsString",stringArray);
                context.startActivity(intent);
                Toast.makeText(context,"Updating",Toast.LENGTH_SHORT).show();
                return true;
                //      return false;
            }
        });



        //finally adding the table row to the table layout
        tableLayout.addView(tableRow);
    }

}