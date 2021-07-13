package com.example.bettingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Signup extends AppCompatActivity {

    Button b1 ;
    String u , e , p , rp ;
    String c = "100";
    database database;


    EditText username , email , pass , repass ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        b1=(Button)findViewById(R.id.signuptologin);
        username=(EditText) findViewById(R.id.susername);
        email=(EditText) findViewById(R.id.semail);
        pass=(EditText) findViewById(R.id.spassword);
        repass=(EditText) findViewById(R.id.srepassword);
        database=new database(this);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = username.getText().toString();
                e = email.getText().toString();
                p = pass.getText().toString();
                rp = repass.getText().toString();
                if (u.equals("") || e.equals("") || p.equals("") || rp.equals("") )
                {
                    Toast.makeText(com.example.bettingapplication.Signup.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                }

                else if (!p.equals(rp))
                {
                    Toast.makeText(com.example.bettingapplication.Signup.this, "The Entered passwords do not Match", Toast.LENGTH_SHORT).show();

                }
                else if (p.length()<5)
                {
                    Toast.makeText(Signup.this, "Passwords Strength too weak ", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Boolean check1 =database.EMAIL(e);
                    if(check1 ==true)
                    {
                        Toast.makeText(Signup.this, "Email already exists inside the  database ", Toast.LENGTH_SHORT).show();
                    }
                    else if(check1 == false){
                        Boolean check2 = database.INSERT(u,e,p,c);
                        if(check2==false)
                        {
                            Toast.makeText(Signup.this, "Cannot Insert values to database ", Toast.LENGTH_SHORT).show();

                        }
                        else if (check2==true)
                        {
                            Toast.makeText(Signup.this, " Sign up  successful !! ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);



                        }


                    }



                }















            }
        });
    }
}