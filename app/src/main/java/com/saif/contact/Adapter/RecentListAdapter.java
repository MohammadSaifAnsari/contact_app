package com.saif.contact.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.saif.contact.ContactInfoActivity;
import com.saif.contact.Database.ContactModel;
import com.saif.contact.Database.DatabaseHelper;
import com.saif.contact.Database.RecentModel;
import com.saif.contact.R;

import java.util.ArrayList;

public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.ViewHolder> {
    ArrayList<RecentModel> recentList;
    Context context;
    DatabaseHelper databaseHelper;

    public RecentListAdapter(ArrayList<RecentModel> recentList, Context context) {
        this.recentList = recentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_recycler_view,parent,false);
        return new RecentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        RecentModel recentModel = recentList.get(position);

        databaseHelper = DatabaseHelper.getDB(context);
        holder.username.setText(recentModel.getFirstname()+" "+recentModel.getSurname());
        holder.phonenumber.setText(recentModel.getPhoneNo());
        holder.proImg.setImageBitmap(getImage(recentModel.getProfileImg()));

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ holder.phonenumber.getText().toString().trim()));
                context.startActivity(intent);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this contact")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.recentDao().deleteContact(recentModel);
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username,phonenumber;
        ShapeableImageView proImg;
        ImageButton callButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userListName);
            phonenumber = itemView.findViewById(R.id.lastMess);
            proImg = itemView.findViewById(R.id.userProfileImg);
            callButton = itemView.findViewById(R.id.callButton);
        }
    }

    // convert from byte array to bitmap
    private static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void  queryList(ArrayList<RecentModel> filterlist){
        this.recentList = filterlist;
        notifyDataSetChanged();
    }
}

