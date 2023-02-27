package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText txtfullname, txtusername, txtpassword, txtconfirmpassword;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        txtfullname = findViewById(R.id.txtFullName);
        txtusername = findViewById(R.id.txtUserNameReg);
        txtpassword = findViewById(R.id.txtPasswordReg);
        txtconfirmpassword = findViewById(R.id.txtConfirmPasswordReg);
        btnReg = findViewById(R.id.btnOKReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strfullname = txtfullname.getText().toString();
                String strName = txtusername.getText().toString();
                String strPwd = txtpassword.getText().toString();
                String strConfirmPwd = txtconfirmpassword.getText().toString();
                if (strfullname.isEmpty()){
                    txtfullname.setError("Full name is required");
                    txtfullname.requestFocus();
                    return;
                }
                if (strName.isEmpty()) {
                    txtusername.setError("User name is required");
                    txtusername.requestFocus();
                    return;
                }
                    if( strPwd.isEmpty()){
                        txtpassword.setError("Password is required");
                        txtpassword.requestFocus();
                        return ;
                    }
                    if(strConfirmPwd.isEmpty()){
                        txtconfirmpassword.setError("Confirm password is required");
                        txtconfirmpassword.requestFocus();
                        return ;
                    }
                    if ( strPwd.equals(strConfirmPwd)){
                            String url = getString(R.string.AppURL).toString() + "register_user.php";
                            // Create an object of class RequestRegister
                        RequestRegister reg = new RequestRegister(SignUpActivity.this);
                        reg.execute(url,strName,strPwd,strfullname);
                        }else{
                        Toast.makeText(SignUpActivity.this, "Confirm Password does not exist",
                                Toast.LENGTH_SHORT).show();
                        }

            }
        });
    }


    private class RequestRegister extends AsyncTask<String,Void,String >{
        // data member
        private ProgressDialog dialog;
        private InputStream is;
        private Context context;

        // constructor
        public RequestRegister(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() { // pre do
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Inserting...");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                // Create httpClient
                HttpClient client = new DefaultHttpClient();
                //creating HttpPost
                HttpPost post = new HttpPost(strings[0]);
                // Building Post Parameter
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("UserNameReg", strings[1]));
                pairs.add(new BasicNameValuePair("UserPassReg", strings[2]));
                pairs.add(new BasicNameValuePair("UserFullName", strings[3]));

                // URL ENCODING POST DATA
                post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
                // final making http Request
                HttpResponse res =  client.execute(post);
                Log.d("HttpResponse", res.toString());
                // read data sent from server
                is = res.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                // Pass values to onPostExecute()
                return result.toString();
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
//            new AlertDialog.Builder(context).setMessage(result).show();
            try {
                JSONObject object = new JSONObject(result);
                if(object.getInt("success") == 1){
                    Toast.makeText(context, object.getString("msg_success"),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(context, object.getString("msg_errors"),
                            Toast.LENGTH_LONG).show();
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }
    }
}