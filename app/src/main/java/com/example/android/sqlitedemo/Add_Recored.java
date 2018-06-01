package com.example.android.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Android on 27-03-2018.
 */

public class Add_Recored extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mOccupationEditText;
    private EditText mImageEditText;
    private Button mAddBtn;

    private SqliteHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedata);

        dbHelper=new SqliteHelper(this);

        //init
        mNameEditText = (EditText)findViewById(R.id.userName);
        mAgeEditText = (EditText)findViewById(R.id.userAge);
        mOccupationEditText = (EditText)findViewById(R.id.userOccupation);
        mImageEditText = (EditText)findViewById(R.id.userProfileImageLink);
        mAddBtn = (Button)findViewById(R.id.addNewUserButton);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                savePerson();
            }
        });
        
    }

    private void savePerson() {
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(age.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(occupation.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an occupation", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Person person=new Person(name,age,occupation,image);
        boolean result = dbHelper.saveNewPerson(person);

        if (result){
            Toast.makeText(this, "Inserted Data", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Not Inserted Data", Toast.LENGTH_SHORT).show();
        }

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        gobackPress();


    }

    private void gobackPress() {
        startActivity(new Intent(Add_Recored.this,MainActivity.class));
    }
}
