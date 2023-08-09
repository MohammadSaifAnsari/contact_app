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
import com.saif.contact.R;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    ArrayList<ContactModel> contactList;
    Context context;
    DatabaseHelper databaseHelper;
    public ContactListAdapter(ArrayList<ContactModel> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ContactModel contactModel = contactList.get(position);

        databaseHelper = DatabaseHelper.getDB(context);
        holder.username.setText(contactModel.getFirstname()+" "+contactModel.getSurname());
        holder.phonenumber.setText(contactModel.getPhoneNo());
        holder.proImg.setImageBitmap(getImage(contactModel.getProfileImg()));

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ holder.phonenumber.getText().toString().trim()));
                context.startActivity(intent);
                ArrayList<ContactModel> recentList = new ArrayList<ContactModel>();
                recentList.add(contactModel);
                databaseHelper.recentDao().addContact(contactModel);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactInfoActivity.class);
                intent.putExtra("contactId1",contactModel.getId());
                intent.putExtra("firstName1",contactModel.getFirstname());
                intent.putExtra("surName1",contactModel.getSurname());
                intent.putExtra("phoneNo1",contactModel.getPhoneNo());
                intent.putExtra("emailid1",contactModel.getEmail());
                intent.putExtra("proImg1",contactModel.getProfileImg());
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
                                databaseHelper.contactDao().deleteContact(contactModel);
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
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
    public void  filterList(ArrayList<ContactModel>filterlist){
        this.contactList = filterlist;
        notifyDataSetChanged();
    }
}
