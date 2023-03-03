package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class CreateContactActivity extends AppCompatActivity {
    SQLiteDatabase db;
    TextInputEditText txtname, txtphone, txtemail;
    Button btncreate;
//    Bitmap
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        // Create or Open data SQLite
        db = openOrCreateDatabase("ContactDB206",MODE_PRIVATE,null);

        CreateTable();
        txtname = findViewById(R.id.txtContactName);
        txtphone = findViewById(R.id.txtNewContactNumber);
        txtemail = findViewById(R.id.txtContactEmail);
        btncreate = findViewById(R.id.btnCreateNewContact);
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String strName = txtname.getText().toString();
                    String strPhone = txtphone.getText().toString();
                    String strEmail = txtemail.getText().toString();
                    ContentValues cv = new ContentValues();
                    cv.put("contactName",strName);
                    cv.put("contactNumber",strPhone);
                    cv.put("contactImage","");
                    cv.put("contactEmail",strEmail);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }

    private void CreateTable() {
        String sql_create = "CREATE TABLE IF NOT EXISTS tblcontact(" +
                "contactId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "contactName VARCHAR(30) NOT NULL," +
                "contactNumber VARCHAR(20) NOT NULL," +
                "contactImage BLOB," +
                "contactEmail VARCHAR(50));";
        db.execSQL(sql_create);
    }
}