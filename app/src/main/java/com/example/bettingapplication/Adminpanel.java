package com.example.bettingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Adminpanel extends AppCompatActivity {

    EditText team1 , team2 , team1odds , team2odds;
    String steam1 , steam2 , steam1odds, steam2odds ;
    Button addbet , showuserdata , back;
    database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        team1=(EditText)findViewById(R.id.team1);
        back=(Button)findViewById(R.id.adminback);
        team2=(EditText)findViewById(R.id.team2);
        team1odds=(EditText)findViewById(R.id.team1odds);
        team2odds=(EditText)findViewById(R.id.team2odds);
        addbet=(Button)findViewById(R.id.addbettinginfo);
        showuserdata=(Button)findViewById(R.id.displayuserdata);
        database=new database(this);



        addbet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steam1= (team1).getText().toString();
                steam2= (team2).getText().toString();
                steam1odds= (team1odds).getText().toString();
                steam2odds= (team2odds).getText().toString();



                if(steam1.equals("") || steam2.equals("") || steam1odds.equals("") || steam2odds.equals(""))
                {
                    Toast.makeText(Adminpanel.this, "Please Input values in every field  ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean check2 = database.INSERT_ADMIN(steam1,steam2,steam1odds,steam2odds);
                    if(check2==false)
                    {
                        Toast.makeText(Adminpanel.this, "Cannot Insert values to database ", Toast.LENGTH_SHORT).show();

                    }
                    else if (check2==true)
                    {
                        Toast.makeText(Adminpanel.this, " BET ADDED  ", Toast.LENGTH_SHORT).show();


                    }

                }
            }
        });

        showuserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.SHOWDATA_ADMIN();
                if(cursor.getCount() ==0)
                {
                    display("Error  " , "No user info found in database ");


                }
                StringBuffer buffer=new StringBuffer();
                int i = 1 ;
                while(cursor.moveToNext())
                {
                    buffer.append("USER" + i +"\n\n" );
                    buffer.append("UserName : " +cursor.getString(0)+"\n");
                    buffer.append("Email : " +cursor.getString(1)+"\n");
                    buffer.append("Total Credit : " +cursor.getString(3)+"\n");
                    buffer.append("Betting Credit : " +cursor.getString(4)+"\n");
                    buffer.append("Team1 : " +cursor.getString(5)+"\n");
                    buffer.append("Team2 : " +cursor.getString(6)+"\n\n");

                    display("All User Data" , buffer.toString());
                    i++ ;

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Adminpanel.this, "Moving back ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Adminpanel_main.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void display (String title , String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}