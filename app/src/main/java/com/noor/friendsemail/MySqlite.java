package com.noor.friendsemail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Noor Mohammed on 11/25/2016.
 */

public class MySqlite extends SQLiteOpenHelper{

    private static final int DB_VERSION=1;
    private static final String DB_NAME="mydb.db";
    private static final String TABLE_NAME="mytable";

    private static final String COLUMN1="ID";
    private static final String COLUMN2="NAME";
    private static final String COLUMN3="EMAIL";


    public MySqlite(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query;
        query="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY,NAME TEXT,EMAIL TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);  //for upgrade db, if previous db found,delete it and crete new one
        onCreate(db);

    }




    //method for inserting data into db
    public boolean addToTable(String ID, String NAME, String EMAIL)
    {

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN1,ID);
        contentValues.put(COLUMN2,NAME);
        contentValues.put(COLUMN3,EMAIL);

        long chk=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if (chk==-1)   // when data not inserted, insert function return -1
            return false;
        else
            return true;


    }


    //method for displaying data
    //Cursor object used for display sqlite raw data
    public Cursor display()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor result;
        result=db.rawQuery("SELECT * FROM "+TABLE_NAME,null); //jehetoh sob data show korabo tai null,jodi sob data na dekia kicu data dekate hoto tokon null er jaigai where clause hbe;
        return result;
    }




    public boolean UpdateData(String id, String name, String email)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN1,id);
        contentValues.put(COLUMN2,name);
        contentValues.put(COLUMN3,email);


        sqLiteDatabase.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }




    //method for deleting data
    public int DeleteData(String id)
    {
        //if delete successfully its return >0
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[] {id}); //delete data using primary key ID

    }




}
