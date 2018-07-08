package com.example.trupti.recyclerview_with_api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Application_Apis api = new Application_Apis();
    EditText txtUsername, txtPassword, login_id,edit_name,edit_FamilyID,edit_memberID,edit_sessionID,edit_LTM_no;

    // login button
    LinearLayout btnLogin;
    String id;
    TextView btn_forgot_password;
    private ProgressDialog pDialog;
    SessionManager session;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard(this);
        setContentView(R.layout.activity_main);

        session = new SessionManager(MainActivity.this);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        login_id = (EditText) findViewById(R.id.edit_LoginID);
        edit_FamilyID = (EditText) findViewById(R.id.edit_FamilyID);
        edit_name = (EditText) findViewById(R.id.edit_Name);
        //edit_sessionID =(EditText)findViewById(R.id.dp_sessionID);
        edit_memberID =(EditText)findViewById(R.id.edit_memberID);
        edit_LTM_no =(EditText)findViewById(R.id.edit_LTM_no);
        //txtPassword.setTypeface(Typer.set(ActivityLogin.this).getFont(Font.ROBOTO_REGULAR));
        btn_forgot_password=(TextView)findViewById(R.id.forgot_password);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");

        // Login button
        btnLogin = (LinearLayout) findViewById(R.id.btnLogin);

        if (session.isLoggedIn())
        {
            Intent i=new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);
            finish();

        }

        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Login_api();
            }
        });


    }

    private void Login_api() {
        showpDialog();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "" + api.LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        VolleyLog.d("Response: " + response);
                        hidepDialog();
                        if (txtUsername.getText().toString().trim().length() > 0) {
                            //  Toast.makeText(get,""+response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject obj1 = null;

                                obj1 = new JSONObject(response);
                                if (obj1.getString("Status").equalsIgnoreCase("2")) {
                                    Toast.makeText(MainActivity.this,"Username Does not Exits", Toast.LENGTH_SHORT).show();



                                }
                                else if (obj1.getString("Status").equalsIgnoreCase("5")){


                                    String LoginID=obj1.getString("LoginID");
                                    String MemberID=obj1.getString("MemberID");
                                    String SessionID=obj1.getString("SessionID");
                                    String FamilyID=obj1.getString("FamilyID");
                                    String MemberName=obj1.getString("MemberName");
                                    String LTMemberNo=obj1.getString("LTMemberNo");

                                    session.createLoginSession(txtUsername.getText().toString(), txtPassword.getText().toString(), LoginID,MemberID,SessionID,FamilyID,MemberName,LTMemberNo);
                                    HashMap<String, String> user = session.getUserDetails();
                                    Intent i =new Intent(MainActivity.this,SecondActivity.class);
                                    startActivity(i);
                                    finish();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(MainActivity.this,"Connection is slow please try again..",Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            //TODO
                        } else if (error instanceof NetworkError) {
                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }
                        hidepDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Mobile", txtUsername.getText().toString());
                params.put("Password", txtPassword.getText().toString());
                params.put("Action", "MemberLogin");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


    }


    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
       // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
