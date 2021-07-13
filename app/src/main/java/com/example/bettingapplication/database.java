package com.example.bettingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    public static final String name = "betting.db";
    Context context;



    public database(Context context) {
        super(context, "betting.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table user (username TEXT , email TEXT  , password TEXT , totalcredit TEXT ,  bettingcredit TEXT , team1 TEXT , team2 TEXT)");
        db.execSQL("create Table admin (team1 TEXT , team2 TEXT  , team1odds TEXT , team2odds TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean INSERT(String username , String email , String password , String totalcredit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username" , username);
        contentValues.put("email" , email);
        contentValues.put("password" , password);
        contentValues.put("totalcredit",totalcredit);
        long result = db.insert("user " , null , contentValues);
        return result != -1;
    }

    public boolean EMAIL(String email){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email" , email);
        Cursor cursor =db.rawQuery("Select * from user where email=?",new String[] {email});
        return cursor.getCount() > 0;
    }

    public boolean LOGIN(String email , String password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select  * from user where email =? and password=?" , new String[] {email , password});
        return cursor.getCount() > 0;


    }

    public boolean INSERT_ADMIN(String team1 , String team2 , String team1odds , String team2odds)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("team1" , team1);
        contentValues.put("team2" , team2);
        contentValues.put("team1odds" , team1odds);
        contentValues.put("team2odds",team2odds);
        long result = db.insert("admin" , null , contentValues);
        return result != -1;
    }

    public Cursor SHOWDATA_ADMIN()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select  * from user " ,null);
        return cursor ;

    }
    public Cursor SHOWDATA_USER(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select  * from user where email = ?" ,new String[]{email});
        return cursor ;

    }

    public Cursor GETCREDIT(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from user where email = ?" , new String[] {email});
        return cursor;

    }

    public boolean INSERT_CREDIT(String email,String total_credit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email" , email);
        contentValues.put("totalcredit",total_credit);
        long result = db.update("user " , contentValues ,"email=?",new String[] {email} );
        return result != -1;



    }
    public Cursor GETTEAM1()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select team1 from admin" , null);
        return cursor;

    }

    public Cursor GETTEAM2()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select team2 from admin" , null);
        return cursor;

    }


    public Cursor GETTEAM1_ODDS()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select team1odds from admin" , null);
        return cursor;

    }


    public Cursor GETTEAM2_ODDS()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select team2odds from admin" , null);
        return cursor;

    }
    public boolean DELETEUSER(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from user where email=?" , new String[]{email});
        if (cursor.getCount()>0)
        {
            long result =db.delete("user","email=?",new String[]{email});
            if (result == -1)
            {
                return false;
            }
            else{
                return true;
            }
        }
        else
        {
            return false;
        }

    }
    public boolean DELETEALL()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Delete from admin " , null);
        if (cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean CHECKTEAMS1(String team1 , String team2)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select  * from admin where team1 =? and team2=?" , new String[] {team1 , team2});
        return cursor.getCount() > 0;

    }

    public boolean CHECKTEAMS2(String team2 , String team1)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select  * from admin where team1 =? and team2=?" , new String[] {team2 , team1});
        return cursor.getCount() > 0;

    }
    public boolean INSERT_BETTING_INFO_TEAMS1(String email,String total_credit,String betting_credit, String team1 , String team2)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("totalcredit" , total_credit);
        contentValues.put("bettingcredit" , betting_credit);
        contentValues.put("team1" , team1);
        contentValues.put("team2" , team2);
        long result = db.update("user " , contentValues ,"email=?",new String[] {email} );
        return result != -1;
    }

    public boolean INSERT_BETTING_INFO_TEAMS2(String email,String total_credit,String betting_credit, String team1 , String team2)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("totalcredit" , total_credit);
        contentValues.put("bettingcredit" , betting_credit);
        contentValues.put("team1" , team2);
        contentValues.put("team2" , team1);
        long result = db.update("user " , contentValues ,"email=?",new String[] {email} );
        return result != -1;
    }


}
