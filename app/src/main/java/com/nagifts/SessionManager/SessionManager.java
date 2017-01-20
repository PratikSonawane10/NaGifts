package com.nagifts.SessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.nagifts.Login;
import com.nagifts.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_MOB = "mob";

    public static final String KEY_GiftId = "giftId";
    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String mobNo) {
            // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();


        editor.putString(KEY_MOB, mobNo);


// commit changes
        editor.commit();
    }

    public HashMap<String, String> getGiftId() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_GiftId, pref.getString(KEY_GiftId, "0"));

        return user;
    }

    public void checkGiftId( String giftId) {
        // Storing login value as TRUE
        editor.commit();


        editor.putString(KEY_GiftId, giftId);


// commit changes
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        // Staring Login Activity
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);

    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
        else{
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }


}
