package com.bmc206p14app.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessions {
    // data members
  private  SharedPreferences sp;
  // constructor
  public Sessions(Context context){
    sp = PreferenceManager.getDefaultSharedPreferences(context);
  }
  // setter methods
  public void setUserID(int userID){
    sp.edit().putInt("USERID",userID).commit();
  }
  public void setUserName(String userName){
    sp.edit().putString("USERNAME", userName).commit();
  }
  public void setUserPassword(String userPassword){
    sp.edit().putString("PASSWORD", userPassword).commit();
  }
  public void setUserType(String userType){
    sp.edit().putString("USERTYPE", userType).commit();
  }
  public void setUserFullName(String userFullName){
    sp.edit().putString("USERFULLNAME", userFullName).commit();
  }
  public void setUserEmail(String userEmail){
    sp.edit().putString("USEREMAIL", userEmail).commit();
  }
  public void setUserImage(String userImage){
    sp.edit().putString("IMAGE", userImage).commit();
  }

  // getter methods
  public int getUserID(){
    return sp.getInt("USERID", 0);
  }
  public String getUserName(){
    return sp.getString("USERNAME","");
  }
  public String getUserPassword(){
    return sp.getString("PASSWORD","");
  }
  public String getUserType(){
    return sp.getString("USERTYPE","");
  }
  public String getUserFullName(){
    return sp.getString("USERFULLNAME","");
  }
  public String getUserEmail(){
    return sp.getString("USEREMAIL","");
  }
  public String getUserImage(){
    return sp.getString("IMAGE","");
  }

  public void ClearSessions() {
    sp.edit().clear().commit();
  }
}
