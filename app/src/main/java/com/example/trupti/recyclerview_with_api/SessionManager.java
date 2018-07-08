package com.example.trupti.recyclerview_with_api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // Sharedpref file name

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_MEMBER_PAGE = "IsMemberPage";
    private static final String IS_MATRIMONIAL_MEMBER_PAGE = "Ismatrimonialmeberpage";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String PASSWORD_SHARED_PREF = "password";
    public static final String Name_PREF = "Name";
    public static  final String FamilyID_PREF ="FamilyID";

    public static  final String LoginID_PREF ="LoginID";

    public static  final String SESSIONID ="sessionID";
    public static  final String ID_VALUE ="ID";
    public static  final String memberID_pre ="memberID";
    public static  final String sessionID_pre ="sessionID";
    public static  final String memberName_pre ="memberName";
    public static  final String LTMemberNo_pre ="LTMemberNo";
    public static  final String VacancyID_pre ="VacancyID";
    public static  final String JSONURL ="URL";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    /**
     * Create login session
     * */
    public void createLoginSession(String email, String password, String LOGINID, String memberID, String sessionID, String familyID, String memberName, String LTMemberNo)
        {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMAIL_SHARED_PREF,email);
        editor.putString(PASSWORD_SHARED_PREF,password);
        editor.putString(LoginID_PREF, LOGINID);
            editor.putString(memberID_pre, memberID);
            editor.putString(memberID_pre, memberID);
            editor.putString(sessionID_pre, sessionID);
            editor.putString(FamilyID_PREF, familyID);
            editor.putString(memberName_pre, memberName);
            editor.putString(LTMemberNo_pre, LTMemberNo);
        // commit changes
        editor.commit();
    }

 /*   public void putidsession()
    {

        editor.putBoolean(IS_MEMBER_PAGE, true);

        // commit changes
        editor.commit();
    }
    public void putIsMatrimonialPage()
    {

        editor.putBoolean(IS_MATRIMONIAL_MEMBER_PAGE, true);

        // commit changes
        editor.commit();
    }
    public void putVacancyID(String VacancyID)
    {

        editor.putString(VacancyID_pre,VacancyID);

        // commit changes
        editor.commit();
    }
    public void putdisableMemberPage()
    {

        editor.putBoolean(IS_MEMBER_PAGE, false);

        // commit changes
        editor.commit();
    }
    public void putdisableMatrimonialMemberPage()
    {

        editor.putBoolean(IS_MATRIMONIAL_MEMBER_PAGE, false);

        // commit changes
        editor.commit();
    }
    public void putJSONURLsession(String jsonurl)
    {

        editor.putString(JSONURL,jsonurl);

        // commit changes
        editor.commit();
    }*/
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity


           Intent i = new Intent(_context,MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(EMAIL_SHARED_PREF, pref.getString(EMAIL_SHARED_PREF, null));

        // user email id
        user.put(PASSWORD_SHARED_PREF, pref.getString(PASSWORD_SHARED_PREF, null));
        // user email id

        user.put(LoginID_PREF, pref.getString(LoginID_PREF, null));
        user.put(FamilyID_PREF, pref.getString(FamilyID_PREF, null));
        user.put(Name_PREF, pref.getString(Name_PREF, null));
        user.put(SESSIONID, pref.getString(SESSIONID, null));
        user.put(ID_VALUE, pref.getString(ID_VALUE, null));
        user.put(JSONURL, pref.getString(JSONURL, null));
        user.put(VacancyID_pre, pref.getString(VacancyID_pre, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    // Get Login State
    public boolean isMemberPage()
    {
        return pref.getBoolean(IS_MEMBER_PAGE, false);
    }
    public boolean IsMatrimonialPage()
    {
        return pref.getBoolean(IS_MATRIMONIAL_MEMBER_PAGE, false);
    }
}