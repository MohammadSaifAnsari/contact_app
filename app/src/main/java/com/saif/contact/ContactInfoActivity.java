package com.saif.contact;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.saif.contact.Database.ContactModel;
import com.saif.contact.Database.DatabaseHelper;
import com.saif.contact.Fragment.ContactFragment;
import com.saif.contact.databinding.ActivityContactInfoBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ContactInfoActivity extends AppCompatActivity {

    ActivityContactInfoBinding activityContactInfoBinding;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContactInfoBinding = ActivityContactInfoBinding.inflate(getLayoutInflater());
        setContentView(activityContactInfoBinding.getRoot());


        int contactId1 = getIntent().getIntExtra("contactId1",99);
        String firstName1 = getIntent().getStringExtra("firstName1");
        String surName1 = getIntent().getStringExtra("surName1");
        String phoneNo1 = getIntent().getStringExtra("phoneNo1");
        String emailid1 = getIntent().getStringExtra("emailid1");
        byte[] proImg = getIntent().getByteArrayExtra("proImg1");

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        //Creating contact arraylist from table
        ArrayList<ContactModel> contactList = (ArrayList<ContactModel>) databaseHelper.contactDao().getContactList();


        activityContactInfoBinding.userProfName.setText(firstName1);
        activityContactInfoBinding.userSurName.setText(surName1);
        activityContactInfoBinding.userProfPhone.setText(phoneNo1);
        activityContactInfoBinding.userProfEmail.setText(emailid1);
        activityContactInfoBinding.userProfImg.setImageBitmap(getImage(proImg));

        activityContactInfoBinding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = activityContactInfoBinding.userProfName.getText().toString();
                String surname = activityContactInfoBinding.userSurName.getText().toString();
                String phoneNo = activityContactInfoBinding.userProfPhone.getText().toString();
                String email = activityContactInfoBinding.userProfEmail.getText().toString();

                // Get the data from an ImageView as bytes
                activityContactInfoBinding.userProfImg.setDrawingCacheEnabled(true);
                activityContactInfoBinding.userProfImg.buildDrawingCache();
                bitmap = ((BitmapDrawable) activityContactInfoBinding.userProfImg.getDrawable()).getBitmap();
                byte[] data = getBytes(bitmap);

                ContactModel contactModel = new ContactModel(contactId1,firstname,surname,phoneNo,email,data);
                databaseHelper.contactDao().updateContact(contactModel);

                Toast.makeText(ContactInfoActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

            }
        });

        activityContactInfoBinding.userProfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(galleryIntent);
            }
        });


        activityContactInfoBinding.backspaceMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactInfoActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {

                        //Getting user image uri
                        Uri uri = result.getData().getData();
//                        activityCreateContactBinding.userProfImg.setImageURI(uri);

                        try {
                            bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            activityContactInfoBinding.userProfImg.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        Toast.makeText(ContactInfoActivity.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    // convert from byte array to bitmap
    private static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //Convert from bitmap to bytearray
    public  static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactInfoActivity.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}