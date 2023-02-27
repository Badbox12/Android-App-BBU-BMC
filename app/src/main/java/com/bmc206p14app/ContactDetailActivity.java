package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailActivity extends AppCompatActivity {
    ImageView img;
    TextView tvname, tvphone, tvemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_layout);

        int imgId = Integer.parseInt(getIntent().getStringExtra("CONTACT_IMAGE"));
        img = (ImageView) findViewById(R.id.imgShowPhoto);
        img.setImageResource(imgId);

        String strName = getIntent().getStringExtra("CONTACT_NAME");
        String strPhone = getIntent().getStringExtra("CONTACT_PHONE");
        String strEmail = getIntent().getStringExtra("CONTACT_EMAIL");

        tvname = (TextView) findViewById(R.id.tvShowContactName);
        tvname.setText(strName);

        tvphone = (TextView) findViewById(R.id.tvShowPhoneNumber);
        tvphone.setText(strPhone);

        tvemail = (TextView) findViewById(R.id.tvShowEmail);
        tvemail.setText(strEmail);
    }
}