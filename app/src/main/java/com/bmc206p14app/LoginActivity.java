package com.bmc206p14app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bmc206p14app.functions.Sessions;

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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Sessions sessions;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create Sessions
        sessions = new Sessions(this);
        // show the layout
        setContentView(R.layout.login_layout);

        // set ActionBar title
        // getSupportActionBar().setTitle("Login User");
    }
    public void SignUpUser(View view){
        Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(in);
    }
    public void LoginUser(View v){
        // EditText txtname = (EditText) findViewById(R.id.txtUserName);
        EditText txtname = findViewById(R.id.txtUserNameReg);
        EditText txtpwd = findViewById(R.id.txtPassword);

        String strname = txtname.getText().toString();
        String strpwd = txtpwd.getText().toString();

        if(strname.isEmpty()){
            txtname.setError("UserName is required!");
            txtname.requestFocus();
            return;
        }
        if(strpwd.isEmpty()){
            txtpwd.setError("Password is required!");
            txtpwd.requestFocus();
            return;
        }
        String url = getString(R.string.AppURL).toString() + "login_user.php";
        RequestLogin login = new RequestLogin(this);
        login.execute(url,strname,strpwd);
    }

    private class RequestLogin extends AsyncTask<String , Void, String>{
        private Context context;
        private ProgressDialog dialog;
        private InputStream is;
        // constructor
        public RequestLogin(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Logging in...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
//            try {
//                // creating HttpClient
//                HttpClient client = new DefaultHttpClient();
//                // creating HttPost
//                HttpPost post = new HttpPost(strings[0]);
//                // building post parameters
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("UserNameLogin", strings[1]));
//                params.add(new BasicNameValuePair("UserPasswordLogin", strings[2]));
//                // set URL Encoding data
//                post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//                // finally making HttpRequests
//                HttpResponse res = client.execute(post);
//                // write to log
//                Log.d("HttpResponse", res.toString());
//                // read data from server
//                is = res.getEntity().getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                StringBuffer result = new StringBuffer();
//                String line = "";
//                while ((line = reader.readLine()) != null){
//                    result.append(line + "\n");
//                }
//                return result.toString();  // pass value to onPostExecute()
//
//            }catch (Exception ex){
//                    ex.printStackTrace();
//                return null;
//            }

//                try{
//                    url = new URL(Server.url);
//                }catch (MalformedURLException e){
//                    e.printStackTrace();
//                    return "exception";
//                }
                try {
                    /* Setup HttpURLConnection class to send recieve data
                    * from php and mysql
                    * */
                    // creating URL
                    URL url = new URL(strings[0]);
                    // HttpURLConnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    /*
                    * setDoInput and setDoOutput method depict handling of both send and receive
                    * */
                    conn.setDoInput(true);
                    conn.setDoInput(true);
                    // Append parameter to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("UserNameLogin",strings[1])
                            .appendQueryParameter("UserPasswordLogin",strings[2]);
                    String query = builder.build().getEncodedQuery();
                    System.out.println("query = "+ query);
                    // Open connection for sending data
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8")
                    );
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();

//                    StringBuffer result = new StringBuffer();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                            result.append(line + "\n");
                        }
                        return result.toString();
                    }else {
                        return "unsuccessfully";
                    }
                }catch (IOException e1){
                    e1.printStackTrace();
                    return "exception";
                }finally {

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
                Log.d("My App", object.toString());
                if(object.getInt("success") == 1) {
                    // TODO: save user login info
                    sessions.setUserID(object.getInt("UserIDLogin"));
                    sessions.setUserName(object.getString("UserNameLogin"));
                    sessions.setUserPassword(object.getString("UserPwdLogin"));
                    sessions.setUserType(object.getString("UserTypeLogin"));
                    sessions.setUserFullName(object.getString("UserFullName"));
                    sessions.setUserEmail(object.getString("UserEmailLogin"));
                    sessions.setUserImage(object.getString("UserImageLogin"));
                    // goto main dashboard
                    startActivity(new Intent(LoginActivity.this, MainAppActivity.class));
                    finish();
                }else {
                    Toast.makeText(context, object.getString("msg_errors"),
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}