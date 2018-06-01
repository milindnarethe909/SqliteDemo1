package com.example.sqlitedemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	Button bt_savaData,bt_searchData,bt_updateData,bt_delectData,bt_save;
	
	EditText ed_name,ed_age,ed_address;
	
	DataPojo dataPojo;
	
	SqliteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dataPojo = new DataPojo();
		database = new SqliteDatabase(this);
		
		bt_savaData = (Button)findViewById(R.id.bt_saveData);
		bt_searchData = (Button)findViewById(R.id.bt_searchData);
		bt_updateData = (Button)findViewById(R.id.bt_updateData);
		bt_delectData = (Button)findViewById(R.id.bt_deleteData);
		
		bt_savaData.setOnClickListener(this);
		bt_updateData.setOnClickListener(this);
		bt_searchData.setOnClickListener(this);
		bt_delectData.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.bt_saveData:
			savaData();
			break;
		case R.id.bt_save:
			save();

		default:
			break;
		}
	}

	private void save() {
		// TODO Auto-generated method stub
		dataPojo.setName(ed_name.getText().toString().trim());
		dataPojo.setAge(ed_age.getText().toString().trim());
		dataPojo.setAddress(ed_address.getText().toString().trim());
		
		if(TextUtils.isEmpty(dataPojo.getName())){
			
		}else if(TextUtils.isEmpty(dataPojo.getAge())){
			
		}else if(TextUtils.isEmpty(dataPojo.getAddress())){
			
		}else{
			boolean result = database.saveData(dataPojo.getName(), dataPojo.getAge(), dataPojo.getAddress());
			
			if(result){
				showToast("Save Data");
			}else{
				showToast("Not Save Data");
			}
		}
		
		
		
	}

	private void savaData() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater=LayoutInflater.from(this);
		View view=inflater.inflate(R.layout.save_data, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		
		ed_name = (EditText)view.findViewById(R.id.ed_name);
		ed_age = (EditText)view.findViewById(R.id.ed_age);
		ed_address = (EditText)view.findViewById(R.id.ed_address);
		
		bt_save = (Button)view.findViewById(R.id.bt_save);
		
		bt_save.setOnClickListener(this);
		
		
		AlertDialog dialog=builder.create();
		dialog.show();
		
		
		
	}

	private void showToast(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
