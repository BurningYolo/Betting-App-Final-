package com.example.bettingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Adminpanel_main extends AppCompatActivity {
    Button betgenerationpage;
    EditText adminemail;
    Button deleteuser,deleteall ;
    database database ;
    Button sendemail ;
    public String s ;

    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel_main);

        betgenerationpage=(Button)findViewById(R.id.tobetgenerationpage);
        deleteuser=(Button)findViewById(R.id.buttondeleteuser);
        deleteall=(Button)findViewById(R.id.buttondeleteall);
        adminemail=(EditText)findViewById(R.id.adminemail);
        sendemail=(Button)findViewById(R.id.buttonsendemail);

        database=new database(this);




        betgenerationpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Adminpanel.class);
                startActivity(intent);
                finish();

            }
        });
        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=(adminemail).getText().toString();
                if (email.equals(""))
                {
                    Toast.makeText(Adminpanel_main.this, "Please ADD Email to delete  ", Toast.LENGTH_SHORT).show();

                }
                else
                {
                   Boolean check2= database.DELETEUSER(email);

                    if(check2==false)
                    {
                        Toast.makeText(Adminpanel_main.this, "Can't find value in database  ", Toast.LENGTH_SHORT).show();

                    }
                    else if (check2==true)
                    {
                        Toast.makeText(Adminpanel_main.this, " Value found in database and deleted  ", Toast.LENGTH_SHORT).show();

                    }

                }



            }
        });
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = database.DELETEALL();
                if(check==false)
                {
                    Toast.makeText(Adminpanel_main.this, "Betting table Deleted   ", Toast.LENGTH_SHORT).show();

                }
                else if (check==true)
                {
                    Toast.makeText(Adminpanel_main.this, " Nothing in database  ", Toast.LENGTH_SHORT).show();

                }

            }
        });

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=(adminemail).getText().toString();
                if (email.equals(""))
                {
                    Toast.makeText(Adminpanel_main.this, "Please ADD Email  ", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Cursor cursor = database.SHOWDATA_USER(email);
                    StringBuffer buffer=new StringBuffer();
                    if(cursor.getCount() ==0)
                    {
                        Toast.makeText(Adminpanel_main.this, "EMAIL NOT FOUND IN DATABASE  ", Toast.LENGTH_SHORT).show();



                    }
                    else{
                    while(cursor.moveToNext())
                    {

                        buffer.append("UserName : " +cursor.getString(0)+"\n");
                        buffer.append("Email : " +cursor.getString(1)+"\n");
                        buffer.append("Total Credit : " +cursor.getString(3)+"\n");
                        buffer.append("Betting Credit : " +cursor.getString(4)+"\n");
                        buffer.append("Team1 : " +cursor.getString(5)+"\n");
                        buffer.append("Team2 : " +cursor.getString(6)+"\n\n");
                    }
                     s = buffer.toString();
                    sendmail(s,email);

                }}


            }
        });


    }
    public void sendmail(String s , String email)
    {
        String email_to = email ;
        String to_emails[]={email_to};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto"));
        intent.putExtra(Intent.EXTRA_EMAIL,to_emails);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT," Betting / Account information of Email :  "+ email_to );
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"Please Choose any app to email "));

    }
}