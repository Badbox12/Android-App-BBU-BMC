package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bmc206p14app.functions.EncryptPassword;
import com.bmc206p14app.functions.RequestUpdate;
import com.bmc206p14app.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputEditText txtnewpassword, txtconfirmpassword;
    Button btnSavePassword;
    Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create session
        sessions = new Sessions(this);
        setContentView(R.layout.activity_change_password);
        txtnewpassword = findViewById(R.id.txtNewPassword);
        txtconfirmpassword = findViewById(R.id.txtConfirmPassword);
        btnSavePassword = findViewById(R.id.btnSaveChange);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strnewpassword = txtnewpassword.getText().toString();
                String strconfirmpassword = txtconfirmpassword.getText().toString();
                String strMd5 = EncryptPassword.MD5(strnewpassword);
                if(TextUtils.isEmpty(strnewpassword)){
                    txtnewpassword.setError("Required New Password!");
                    txtnewpassword.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(strconfirmpassword)){
                    txtconfirmpassword.setError("Required Confirm Password!");
                    txtconfirmpassword.requestFocus();
                    return;
                }
                // |Check new password and confirm password
                if(strnewpassword.equals(strconfirmpassword)){
                    // check current password with new password
                    if(strMd5.equals(sessions.getUserPassword())){
                        new AlertDialog.Builder(ChangePasswordActivity.this)
                                .setMessage("Your new password is the same your current password. Please enter another new password")
                                .setPositiveButton("OK", null)
                                .show();

                    }else {
                        // save change the password
                        String uri = getText(R.string.AppURL).toString() + "change_user_password.php";
                        String[] params = {"UserId","NewPassword"};
                        String strId = String.valueOf(sessions.getUserID());
                        // create object of class RequestUpdate()
                        RequestUpdate update = new RequestUpdate(ChangePasswordActivity.this,params);
                        update.execute(uri,strId,strMd5);

                    }
                }else {
                    new AlertDialog.Builder(ChangePasswordActivity.this)
                            .setMessage("Your new password does not match your confirm password")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
}