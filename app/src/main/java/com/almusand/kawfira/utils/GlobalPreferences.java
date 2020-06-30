package com.almusand.kawfira.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.almusand.kawfira.Models.Login.User;

import java.util.ArrayList;

public class GlobalPreferences {

    Context context;
    final static String PREFS_NAME = "settings";
    final static String USER_ID = "id";
    final static String USER_AuthToken = "auth_token";
    final static String USER_Img = "user_img";
    final static String USER_Email = "user_email";
    final static String USER_Name = "name";
    final static String USER_Logged = "logged";
    final static String KWAFIRA_ID = "kwafiraId";
    final static String KWAFIRA_CERT = "KWAFIRA_CERT";
    final static String KWAFIRA_Available = "KWAFIRA_Available";
    final static String KWAFIRA_Verfied = "KWAFIRA_Verfied";
    final static String USER_ROLE = "role";
    final static String CART_Counter = "counter";
    final static String Service_ID = "service_id";
    final static String CART_Price = "counter_price";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    TinyDB tiny;

    public GlobalPreferences(Context context) {
        this.context = context;
        prefs = null;
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        prefsEditor = prefs.edit();
        tiny= new TinyDB(context);
    }


    public void storeUserInfo(User user, String auth){
        prefsEditor.putString(USER_ID,user.getId());
        prefsEditor.putString(USER_AuthToken,auth);
        prefsEditor.putString(USER_Img,user.getImage());
        prefsEditor.putString(USER_Email,user.getEmail());
        prefsEditor.putString(USER_Name,user.getName());
        prefsEditor.putString(USER_ROLE,user.getRole());
        prefsEditor.commit();
    }

    public void storeUserImgAndName(String img, String name){
        prefsEditor.putString(USER_Img,img);
        prefsEditor.putString(USER_Name,name);
        prefsEditor.commit();
    }
    public void storeLogged(boolean logged){
        prefsEditor.putBoolean(USER_Logged,logged);
        prefsEditor.commit();
    }

    public void increaseCartCounter(int price){
        prefsEditor.putInt(CART_Counter,prefs.getInt(CART_Counter,0)+1);
        prefsEditor.putInt(CART_Price, prefs.getInt(CART_Price, 0) + price);
        prefsEditor.commit();
    }
    public void decreaseCartCounter(int price){
        if(prefs.getInt(CART_Counter,0)>0){
            prefsEditor.putInt(CART_Counter, prefs.getInt(CART_Counter, 0) - 1);
            prefsEditor.putInt(CART_Price, prefs.getInt(CART_Price, 0) - price);
            prefsEditor.commit();
        }
    }


    public int getCartCounter(){
        return prefs.getInt(CART_Counter, 0);
    }
    public String getUserRole(){
        return prefs.getString(USER_ROLE,"client");
    }

    public int getCost(){
        return prefs.getInt(CART_Price, 0);
    }

    public boolean isInCart(int id){
        return prefs.getBoolean(Service_ID+id, false);
    }

    public void saveInCart(int id){
        ArrayList<String> ids = tiny.getListString("ids");
        if(ids==null){
            ids = new ArrayList<>();
        }

        ids.add(id+"");
        tiny.putListString("ids",ids);
        prefsEditor.putBoolean(Service_ID+id,true);
        prefsEditor.commit();
    }
    public void removeFromCart(int id){
        ArrayList<String> ids = tiny.getListString("ids");
        prefsEditor.remove(Service_ID+id);
        ids.remove(id+"");
        prefsEditor.commit();
    }

    public String getUserId(){
        return prefs.getString(USER_ID,"");
    }
    public String getUserName(){
        return prefs.getString(USER_Name,"");
    }
    public String getUSER_Imge(){
        return prefs.getString(USER_Img,"");
    }
    public String getUSER_Email(){
        return prefs.getString(USER_Email,"");
    }
    public boolean getUSER_Logged(){
        return prefs.getBoolean(USER_Logged,false);
    }
    public String getUserAuth(){
        return prefs.getString(USER_AuthToken,"");
    }
    public ArrayList<String> getAllIds(){
        return tiny.getListString("ids");
    }

    public void storeEmail(String email) {

        prefsEditor.putString(USER_Email,email);
        prefsEditor.commit();
    }

    public void storeUserIdSent() {
        prefsEditor.putBoolean(KWAFIRA_ID,true);
        prefsEditor.commit();
    }
    public boolean isIdSent() {
        return  prefs.getBoolean(KWAFIRA_ID,false);
    }

    public void storeUserCertSent() {
        prefsEditor.putBoolean(KWAFIRA_CERT,true);
        prefsEditor.commit();
    }
    public void storeUserAvailable(boolean available) {
        prefsEditor.putBoolean(KWAFIRA_Available,available);
        prefsEditor.commit();
    }
    public void storeKawfiraVerfied() {
        prefsEditor.putBoolean(KWAFIRA_Verfied,true);
        prefsEditor.commit();
    }
    public boolean isKawfiraVerfied() {
        return  prefs.getBoolean(KWAFIRA_Verfied,false);
    }
    public boolean isCertSent() {
        return  prefs.getBoolean(KWAFIRA_CERT,false);
    }
    public boolean isAvailable() {
        return  prefs.getBoolean(KWAFIRA_Available,false);
    }
}
