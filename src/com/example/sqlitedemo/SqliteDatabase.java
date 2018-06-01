package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDatabase extends SQLiteOpenHelper {

	public SqliteDatabase(Context context) {
		super(context, ContactsPojo.DATABASE_NAME, null, ContactsPojo.VIRSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+ContactsPojo.TABLE_NAME+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT ,"+
		ContactsPojo.NAME+" TEXT,"+
				ContactsPojo.AGE+" TEXT,"+
		ContactsPojo.ADDRESS+"TEXT)");
		
	}
	
	public List<DataPojo> searchData(){
		
		List<DataPojo> list = new ArrayList<DataPojo>();
		
		String selectQuery = "SELECT * FROM "+ContactsPojo.TABLE_NAME;
		
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		DataPojo data;
		
		if(cursor.moveToFirst()){
			do{
				data = new DataPojo();
				data.setName(cursor.getString(cursor.getColumnIndex(ContactsPojo.NAME)));
				data.setAge(cursor.getString(cursor.getColumnIndex(ContactsPojo.AGE)));
				data.setAddress(cursor.getString(cursor.getColumnIndex(ContactsPojo.ADDRESS)));
				
				list.add(data);
				
			}while(cursor.moveToNext());
		}
		return list;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+ContactsPojo.TABLE_NAME);
		onCreate(db);
		
	}
	
	public boolean saveData(String name,String age,String address){
		
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(ContactsPojo.NAME, name);
		values.put(ContactsPojo.AGE, age);
		values.put(ContactsPojo.ADDRESS, address);
		
		long result = db.insert(ContactsPojo.TABLE_NAME,null, values);
		
		if(result == -1){
			return false;
		}else{
			return true;
		}
	}

}
