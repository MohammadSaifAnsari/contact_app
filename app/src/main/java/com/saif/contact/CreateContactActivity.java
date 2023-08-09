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
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.saif.contact.Database.DatabaseHelper;
import com.saif.contact.Database.ContactModel;
import com.saif.contact.databinding.ActivityCreateContactBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.sql.Blob;

public class CreateContactActivity extends AppCompatActivity {

    ActivityCreateContactBinding activityCreateContactBinding;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateContactBinding = ActivityCreateContactBinding.inflate(getLayoutInflater());
        setContentView(activityCreateContactBinding.getRoot());

        //Creating database helper object
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        activityCreateContactBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = activityCreateContactBinding.userProfName.getText().toString();
                String surname = activityCreateContactBinding.userSurName.getText().toString();
                String phoneNo = activityCreateContactBinding.userProfPhone.getText().toString();
                String email = activityCreateContactBinding.userProfEmail.getText().toString();

                // Get the data from an ImageView as bytes
                activityCreateContactBinding.userProfImg.setDrawingCacheEnabled(true);
                activityCreateContactBinding.userProfImg.buildDrawingCache();
                bitmap = ((BitmapDrawable) activityCreateContactBinding.userProfImg.getDrawable()).getBitmap();
                byte[] data = getBytes(bitmap);

                ContactModel contactModel = new ContactModel(firstname,surname,phoneNo,email,data);
                databaseHelper.contactDao().addContact(contactModel);


                Toast.makeText(CreateContactActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });


        activityCreateContactBinding.backspaceMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        activityCreateContactBinding.userProfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(galleryintent);

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
                            activityCreateContactBinding.userProfImg.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        Toast.makeText(CreateContactActivity.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                    }
                }
    });


    //Convert from bitmap to bytearray
    public  static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}