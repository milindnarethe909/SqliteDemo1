package com.example.android.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Android on 26-03-2018.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "People";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON_NAME = "name";
    public static final String COLUMN_PERSON_AGE = "age";
    public static final String COLUMN_PERSON_OCCUPATION = "occupation";
    public static final String COLUMN_PERSON_IMAGE = "image";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PERSON_NAME + " TEXT NOT NULL, " +
                COLUMN_PERSON_AGE + " NUMBER NOT NULL, " +
                COLUMN_PERSON_OCCUPATION + " TEXT NOT NULL, " +
                COLUMN_PERSON_IMAGE + " BLOB NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean saveNewPerson(Person person){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_NAME, person.getName());
        values.put(COLUMN_PERSON_AGE, person.getAge());
        values.put(COLUMN_PERSON_OCCUPATION, person.getOccupation());
        values.put(COLUMN_PERSON_IMAGE, person.getImage());

        // insert
        long result=db.insert(TABLE_NAME,null, values);
        db.close();



        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public void updateDataPerson(long P_id,Context context,Person person){
        SQLiteDatabase db=this.getWritableDatabase();

        db.execSQL("UPDATE "+TABLE_NAME+" SET name ='"+ person.getName()+"', age ='"+person.getAge()+"', occupation ='"+person.getOccupation()+"', image ='"+person.getImage()+"' WHERE _id='"+P_id+"'");

        Toast.makeText(context,"Update Succefully",Toast.LENGTH_SHORT).show();
    }

    //**Query only 1 record**//*

    public Person getPerson(long id){
        SQLiteDatabase db=this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE _id="+id;

        Cursor cursor=db.rawQuery(query,null);

        Person receivedPerson = new Person();

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            receivedPerson.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
            receivedPerson.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
            receivedPerson.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_OCCUPATION)));
            receivedPerson.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
        }

        return receivedPerson;
    }

    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public List<Person> peopleList(String filter) {

        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME+" ORDER BY "+COLUMN_ID+" DESC";
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Person> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Person person;

        if (cursor.moveToFirst()) {
            do {
                person = new Person();

                person.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                person.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
                person.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
                person.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_OCCUPATION)));
                person.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
                personLinkedList.add(person);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
    }
}


