package com.example.android.sqlitedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Android on 27-03-2018.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolderDemo> {

    private List<Person> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public PersonAdapter(List<Person> mPeopleList, Context mContext, RecyclerView mRecyclerV) {
        this.mPeopleList = mPeopleList;
        this.mContext = mContext;
        this.mRecyclerV = mRecyclerV;
    }

    @Override
    public ViewHolderDemo onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.single_row,null,false);
        return new ViewHolderDemo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDemo holder, final int position) {
        final Person person = mPeopleList.get(position);
        holder.personNameTxtV.setText("Name: " + person.getName());
        holder.personAgeTxtV.setText("Age: " + person.getAge());
        holder.personOccupationTxtV.setText("Occupation: " + person.getOccupation());
        Picasso.with(mContext).load(person.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.personImageImgV);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(person.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqliteHelper dbHelper = new SqliteHelper(mContext);
                        dbHelper.deletePersonRecord(person.getId(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public class ViewHolderDemo extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView personNameTxtV;
        public TextView personAgeTxtV;
        public TextView personOccupationTxtV;
        public ImageView personImageImgV;

        public View layout;

        public ViewHolderDemo(View itemView) {
            super(itemView);

            layout = itemView;
            personNameTxtV = (TextView) itemView.findViewById(R.id.name);
            personAgeTxtV = (TextView) itemView.findViewById(R.id.age);
            personOccupationTxtV = (TextView) itemView.findViewById(R.id.occupation);
            personImageImgV = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, Update_Record.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }


    public void add(int position, Person person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }


    // Return the size of your dataset (invoked by the layout manager)

}
