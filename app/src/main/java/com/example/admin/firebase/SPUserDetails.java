package com.example.admin.firebase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 2/17/2017.
 */

public class SPUserDetails {

   private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    Context _context;

    private String KEY_USER_NAME = "USER_NAME";


    public SPUserDetails(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences("USER_DETAILS",0);
        editor = pref.edit();
    }

    public String getKEY_USER_NAME()
    {
        return pref.getString(KEY_USER_NAME,"Guest User");
    }

    public void setKEY_USER_NAME(String user_name)
    {
        editor.putString(KEY_USER_NAME,user_name);
        editor.commit();
    }

}
