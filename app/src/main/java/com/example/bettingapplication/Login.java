package com.example.bettingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button b1 , b2 ;
    EditText email , password ;
    database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1= (Button)findViewById(R.id.logintomain);
        email=(EditText)findViewById(R.id.lemail);
        password=(EditText)findViewById(R.id.lpass);
        b2= (Button)findViewById(R.id.logintosignup);
        database=new database(this);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = (email).getText().toString();
                String p = (password).getText().toString();
                if(e.equals("admin@admin.com") && p.equals("admin"))
                {
                    Toast.makeText(Login.this, "Admin Entry Detected , moving to Admin activity ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),Adminpanel_main.class);
                    startActivity(intent);


                }
                else
                {

                if(e.equals("") || p.equals("") )
                {
                    Toast.makeText(Login.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean check = database.LOGIN(e,p);
                    if (check == true) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("email" , e);
                        startActivity(intent);

                    }
                    else if(check ==false)
                    {
                        Toast.makeText(Login.this, "Email or Password are incorrect", Toast.LENGTH_SHORT).show();

                    }


                }





            }
            }


        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.bettingapplication.Login.this , Signup.class);
                startActivity(intent);
            }


        });

    }
}