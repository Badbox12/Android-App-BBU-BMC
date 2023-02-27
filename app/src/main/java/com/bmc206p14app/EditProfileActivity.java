package com.bmc206p14app;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bmc206p14app.functions.ConvertImage;
import com.bmc206p14app.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularimageview.CircularImageView;

public class EditProfileActivity extends AppCompatActivity {
    CircularImageView imgPhotoProfile;
    ImageButton imgBtnBrowsePhoto;
    Button btnSave;
    TextInputEditText txtFullName, txtUserName, txtUserEmail;
    Sessions sessions;
    Bitmap bitmap;
    static final int GALLERY = 1;
    static final int CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create sessions
        sessions = new Sessions(this);
        setContentView(R.layout.activity_edit_profile);

        txtFullName = findViewById(R.id.txtFullNameEdit);
        txtFullName.setText(sessions.getUserFullName());

        txtUserName = findViewById(R.id.txtUserNameEdit);
        txtUserName.setText(sessions.getUserName());

        txtUserEmail = findViewById(R.id.txtEmailEdit);
        txtUserEmail.setText(sessions.getUserEmail());

        bitmap = ConvertImage.StringToImage(sessions.getUserImage());
        imgPhotoProfile = findViewById(R.id.imgPhotoEditProfile);
        imgPhotoProfile.setImageBitmap(bitmap);

        imgBtnBrowsePhoto = findViewById(R.id.imgButtonBrowsePhoto);
        imgBtnBrowsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {"From Gallery", "Take a Photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(EditProfileActivity.this);
                ad.setTitle("Please choose:");
                ad.setIcon(android.R.drawable.ic_menu_camera);
                ad.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(options[which].equals("From Gallery")){
                            // call the method
                            ShowGallery();
                        }else {
                            ShowCamera();
                        }
                    }
                });
                ad.show();
            }
        });

    }

    private void ShowCamera() {

    }

    private void ShowGallery() {
        Intent gal = new Intent();
        gal.setType("image/*");
        gal.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gal, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}