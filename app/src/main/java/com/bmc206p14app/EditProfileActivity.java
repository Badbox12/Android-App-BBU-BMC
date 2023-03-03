package com.bmc206p14app;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bmc206p14app.functions.ConvertImage;
import com.bmc206p14app.functions.RequestUpdate;
import com.bmc206p14app.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularimageview.CircularImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    CircularImageView imgPhotoProfile;
    ImageButton imgBtnBrowsePhoto;
    Button btnSave;
    TextInputEditText txtFullName, txtUserName, txtUserEmail;
    Sessions sessions;
    Bitmap bitmap;
    static final int GALLERY = 1;
    static final int CAMERA = 2;
    boolean isChanged = false;
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
                            // call the method
                            ShowCamera();
                        }
                    }
                });
                ad.show();
            }
        });
        //
        btnSave = findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(this);



    }

    private void ShowCamera() {
        Intent camera = new Intent();
        camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA);
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
        if(requestCode == GALLERY && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgPhotoProfile.setImageBitmap(bitmap);
                isChanged = true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        if(requestCode == CAMERA && resultCode == RESULT_OK && data != null){
            try {

                bitmap = (Bitmap) data.getExtras().get("data");
                imgPhotoProfile.setImageBitmap(bitmap);
                isChanged = true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        String strfullname = txtFullName.getText().toString();
        String strusername = txtUserName.getText().toString();
        String stremail = txtUserEmail.getText().toString();
        String strImage = "NoChange";
        if(isChanged == true) strImage = ConvertImage.ImageToString(bitmap);
        if(strfullname.isEmpty()){
            txtFullName.setError("Required Full Name!");
            txtFullName.requestFocus();
            return;
        }
        if(strusername.isEmpty()){
            txtUserName.setError("Required UserName!");
            txtUserName.requestFocus();
            return;
        }
        if(stremail.isEmpty()){
            txtUserEmail.setError("Required Email!");
            txtUserEmail.requestFocus();
            return;
        }
        String uri = getText(R.string.AppURL).toString() + "edit_user_profile.php";
        String[] params = {"UserNameUpdate","FullNameUpdate","EmailUpdate","ImageUpdate","UserIdUpdate"};
        String strId = String.valueOf(sessions.getUserID());
        // create object of class RequestUpdate.java
        RequestUpdate update = new RequestUpdate(EditProfileActivity.this, params);
        update.execute(uri,strusername,strfullname,stremail,strImage,strId);
    }
}