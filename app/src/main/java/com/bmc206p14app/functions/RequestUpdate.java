package com.bmc206p14app.functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestUpdate extends AsyncTask<String, Void,String> {
    private Context context;
    private ProgressDialog dialog;
    private String[] params;
    public RequestUpdate(Context context, String[] params){
        this .context = context;
        this.params = params;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            // creating URL
            URL url = new URL(strings[0]);
            // HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configure the connection
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            // BUILDING PARAMETERS TO URL
            Uri.Builder builder = new Uri.Builder();

            // enhanced for loop
            int i = 1;
            for(String p : params){
                builder.appendQueryParameter(p, strings[i]);
                i = i + 1; // i++
            }
            String query = builder.build().getEncodedQuery();

            // Open Connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os , StandardCharsets.UTF_8));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            StringBuffer result = new StringBuffer();
            // Check connection and read data
            // Check connection
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                // Read data sent from server
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result.append(line + "\n");
                }
            }
            return result.toString();
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
//        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        try {
            JSONObject object = new JSONObject(result);
            if(object.getInt("success")==1){
                Toast.makeText(context,object.getString("msg_success"),
                        Toast.LENGTH_LONG).show();
                // Update user info in session
                Sessions sessions = new Sessions(context);
                sessions.setUserName(object.getString("UserNameUpdate"));
                sessions.setUserFullName(object.getString("FullNameUpdate"));
                sessions.setUserImage(object.getString("UserImageUpdate"));
                sessions.setUserEmail(object.getString("UserEmailUpdate"));
            }else{
                Toast.makeText(context,object.getString("msg_errors"),
                        Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
              e.printStackTrace();
        }


    }
}
