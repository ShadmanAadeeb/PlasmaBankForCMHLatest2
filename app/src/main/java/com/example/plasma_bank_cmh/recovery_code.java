package com.example.plasma_bank_cmh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class recovery_code extends AppCompatActivity {

    EditText code;
    Button submit;
    public static String key;
    String code_s;
    public static String sent_code_s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_code);

        code=findViewById(R.id.code);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_s=code.getText().toString();
                if (!code_s.isEmpty())
                {
                    if (sent_code_s.equals(code_s))
                    {
                      //  Toast.makeText(getApplicationContext(),"Sent code matches",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(recovery_code.this,password_reset.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Recovery code did not match",Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter recovery code",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}