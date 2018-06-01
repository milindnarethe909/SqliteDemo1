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

public class Update_Record extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mOccupationEditText;
    private EditText mImageEditText;
    private Button mUpdateBtn;

    private SqliteHelper dbHelper;
    private long receivedPersonId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        //init
        mNameEditText = (EditText)findViewById(R.id.userNameUpdate);
        mAgeEditText = (EditText)findViewById(R.id.userAgeUpdate);
        mOccupationEditText = (EditText)findViewById(R.id.userOccupationUpdate);
        mImageEditText = (EditText)findViewById(R.id.userProfileImageLinkUpdate);
        mUpdateBtn = (Button)findViewById(R.id.updateUserButton);

        dbHelper = new SqliteHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Person queriedPerson = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        mNameEditText.setText(queriedPerson.getName());
        mAgeEditText.setText(queriedPerson.getAge());
        mOccupationEditText.setText(queriedPerson.getOccupation());
        mImageEditText.setText(queriedPerson.getImage());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson();
            }
        });


    }

    private void updatePerson() {

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

        //create updated person
        Person updatedPerson = new Person(name, age, occupation, image);

        //call dbhelper update
        dbHelper.updateDataPerson(receivedPersonId, this, updatedPerson);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
