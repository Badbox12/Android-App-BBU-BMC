package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bmc206p14app.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;

public class CheckPasswordActivity extends AppCompatActivity {
    TextInputEditText txtcurrentpassword;
    Button btnCheckpwd;
    Sessions sessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create sessions
        sessions = new Sessions(this);
        setContentView(R.layout.activity_check_password);

        txtcurrentpassword = findViewById(R.id.txtCurrentPassword);
        btnCheckpwd = findViewById(R.id.btnContinues);
        btnCheckpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strcurrentpassword = txtcurrentpassword.getText().toString();
                if(strcurrentpassword.isEmpty()){
                    txtcurrentpassword.setError("Please Enter your current password!");
                    txtcurrentpassword.requestFocus();
                    return ;
                }
                if(strcurrentpassword.equals(sessions.getUserPassword())){
                    // go to change the password

                }else {
                    new AlertDialog.Builder(CheckPasswordActivity.this)
                            .setMessage("Your current password is invalid")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
}