package com.example.bettingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView credit, email;
    database database;
    public String s ;
    Button addcredit ;
    Button addbet;
    Button profile ;
    String getcredit ;
    EditText stringcredit;
    EditText betteam1 , betteam2,betcredit;
    TextView team1,team2,team1odds, team2odds;
    public int number1 , number2, number3 , number4;
    public String sbetteam1 , sbetteam2, sbetcredit;
    public int t1first = 0 ;
    public int t2first = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String emailfromlogin = getIntent().getStringExtra("email");

        database = new database(this);
        profile=(Button)findViewById(R.id.button_profile);
        credit = (TextView) findViewById(R.id.creditfromdatabase);
        email = (TextView) findViewById(R.id.emailfromdatabase);
        addcredit=(Button)findViewById(R.id.button_add_credit);
        stringcredit=(EditText)findViewById(R.id.addcredit) ;
        email.setText(emailfromlogin);
        team1=(TextView)findViewById(R.id.team1mainpage);
        team2=(TextView)findViewById(R.id.team2mainpage);
        team1odds=(TextView)findViewById(R.id.team1odds);
        team2odds=(TextView)findViewById(R.id.team2odds);
        betteam1=(EditText) findViewById(R.id.bet_team1);
        betteam2=(EditText) findViewById(R.id.bet_team2);
        betcredit=(EditText) findViewById(R.id.bet_credit);
        addbet=(Button)findViewById(R.id.button_place_bet);


        Cursor cursor = database.GETCREDIT(emailfromlogin);

        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Your credit is empty please add some credit ", Toast.LENGTH_SHORT).show();
            s = "No Credit";


        } else if (cursor.getCount() > 0) {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                 s = buffer.append(cursor.getString(3) ).toString();

            }
            for(int i=0 ; i<1 ; i++) {
                credit.setText(s);

            }
            String str = s ;
             number1 = Integer.parseInt(str);





        }
        fetch_team1();
        fetch_team2();
        fetch_team1_odds();
        fetch_team2_odds();



        addcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getcredit = (stringcredit).getText().toString();

                if(getcredit.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Credit field empty , Please Input value   ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                     number2 = Integer.parseInt(getcredit);
                     number1 = number1 + number2;
                    String total_credit = String.valueOf(number1);
                    Toast.makeText(MainActivity.this, "Adding Credit ,  Your updated credit is : "+ total_credit, Toast.LENGTH_SHORT).show();
                    boolean check2 = database.INSERT_CREDIT(emailfromlogin,total_credit);

                    if (check2 == true) {
                        Toast.makeText(MainActivity.this, "Adding Credit ,  Your updated credit is : "+ total_credit, Toast.LENGTH_SHORT).show();
                    } else if (check2 == false) {
                        Toast.makeText(MainActivity.this, "Couldn't add credit  ", Toast.LENGTH_SHORT).show();

                    }
                    recreate();




                }



            }
        });
        addbet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbetteam1=(betteam1).getText().toString();
                sbetteam2=(betteam2).getText().toString();
                sbetcredit=(betcredit).getText().toString();
                number4=0;


                 if(sbetcredit.equals(""))
                 {
                     Toast.makeText(MainActivity.this, "Please Add something in the credit box ", Toast.LENGTH_SHORT).show();

                 }
                 else
                 {
                     number4 = Integer.parseInt(sbetcredit);
                     if (number4>number1)
                     {
                         Toast.makeText(MainActivity.this, "Please Enter value lower than your total credit ", Toast.LENGTH_SHORT).show();

                     }
                     else
                     {
                         boolean check3= database.CHECKTEAMS1(sbetteam1,sbetteam2);
                                 if(check3 == true )
                                 {
                                     Toast.makeText(MainActivity.this, "Teams Present in database  ", Toast.LENGTH_SHORT).show();
                                    t1first= 1 ;
                                 }
                                 else
                                 {
                                     boolean check4 = database.CHECKTEAMS2(sbetteam2,sbetteam1);
                                     if (check4 == true)
                                     {
                                         Toast.makeText(MainActivity.this, "Teams Present in database  ", Toast.LENGTH_SHORT).show();
                                        t2first = 1;

                                     }
                                     else
                                     {
                                         Toast.makeText(MainActivity.this, "Teams not Present in Database , Kindly recheck on going matches ", Toast.LENGTH_SHORT).show();


                                     }
                                 }
                                 if(t1first == 1)
                                 {
                                     t1first=0;

                                     number1=number1-number4;
                                     String total_credit = String.valueOf(number1);
                                     String betting_credit = String.valueOf(number4);
                                     boolean check4 = database.INSERT_BETTING_INFO_TEAMS1(emailfromlogin,total_credit,betting_credit,sbetteam1,sbetteam2);
                                     if(check4 == true)
                                     {

                                         Toast.makeText(MainActivity.this, "Added betting info to your ID " + "  BETTING CREDIT : " + betting_credit + "  TOTAL CREDIT : " + total_credit, Toast.LENGTH_SHORT).show();

                                         recreate();
                                     }

                                 }
                                 else if (t2first == 1)
                                 {
                                     t2first = 0 ;
                                     number1=number1-number4;
                                     String total_credit = String.valueOf(number1);
                                     String betting_credit = String.valueOf(number4);
                                     boolean check4 = database.INSERT_BETTING_INFO_TEAMS2(emailfromlogin,total_credit,betting_credit,sbetteam1,sbetteam2);
                                     if(check4 == true)
                                     {

                                         Toast.makeText(MainActivity.this, "Added betting info to your ID " + "  BETTING CREDIT : " + betting_credit + "  TOTAL CREDIT : " + total_credit, Toast.LENGTH_SHORT).show();
                                         recreate();
                                     }


                                 }






                     }






                 }











            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.SHOWDATA_USER(emailfromlogin);
                StringBuffer buffer=new StringBuffer();
                while(cursor.moveToNext())
                {

                    buffer.append("UserName : " +cursor.getString(0)+"\n");
                    buffer.append("Email : " +cursor.getString(1)+"\n");
                    buffer.append("Total Credit : " +cursor.getString(3)+"\n");
                    buffer.append("Betting Credit : " +cursor.getString(4)+"\n");
                    buffer.append("Team1 : " +cursor.getString(5)+"\n");
                    buffer.append("Team2 : " +cursor.getString(6)+"\n\n");
                    display("User Data and Betting Information " , buffer.toString());

                }
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

    public void fetch_team1()
    {
        Cursor cursor1 =database.GETTEAM1();
        if (cursor1.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Team1 found in database  ", Toast.LENGTH_SHORT).show();

        } else if (cursor1.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor1.moveToNext()) {
                stringBuilder.append(cursor1.getString(0) + "\n\n\n");



            }
            team1.setText(stringBuilder.toString());
        }

    }
    public void fetch_team2()
    {
        Cursor cursor2 =database.GETTEAM2();
        if (cursor2.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Team2 found in database  ", Toast.LENGTH_SHORT).show();

        } else if (cursor2.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor2.moveToNext()) {
                stringBuilder.append(cursor2.getString(0) + "\n\n\n");



            }
            team2.setText(stringBuilder.toString());
        }

    }
    public void fetch_team1_odds()
    {
        Cursor cursor3 =database.GETTEAM1_ODDS();
        if (cursor3.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Team1_Odds found in database  ", Toast.LENGTH_SHORT).show();

        } else if (cursor3.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor3.moveToNext()) {
                stringBuilder.append(cursor3.getString(0) + "\n\n\n");



            }
            team1odds.setText(stringBuilder.toString());
        }

    }
    public void fetch_team2_odds()
    {
        Cursor cursor4 =database.GETTEAM2_ODDS();
        if (cursor4.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Team2_Odds found in database  ", Toast.LENGTH_SHORT).show();

        } else if (cursor4.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor4.moveToNext()) {
                stringBuilder.append(cursor4.getString(0) + "\n\n\n");
            }
            team2odds.setText(stringBuilder.toString());
        }

    }

}
