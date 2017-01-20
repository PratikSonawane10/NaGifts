package com.nagifts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;


import com.nagifts.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText mobile;
    EditText password;
    String displayText;
    String mob, pass;
    String test;
    TextView result;
    Button btnLogin;
    Button btnRegister;
    ProgressBar progressBar1;

    private ProgressDialog progressDialog = null;
    SharedPreferences sharedpreferences;

    SessionManager sessionManager;

    boolean doubleBackToExitPressedOnce = false;
    String mobNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        result = (TextView) findViewById(R.id.result);
        mobile = (EditText) findViewById(R.id.txtUserName);
        password = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegisterForLogin);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        sessionManager = new SessionManager(Login.this);
    }
//    public void btn_Click(View view) {
//
//       // result = (TextView) findViewById(R.id.result);
//        mob = mobile.getText().toString();
//        pass = password.getText().toString();
//
//        if(mobile.getText().toString().isEmpty() || mobile.equals("")){
//            Toast.makeText(Login.this, "Please Enter Mobile No.", Toast.LENGTH_SHORT).show();
//        }else if(password.getText().toString().isEmpty() || password.equals("")){
//            Toast.makeText(Login.this, "Please Enter Passworf", Toast.LENGTH_SHORT).show();
//        }
//
//        else {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Please Wait.");
//            progressDialog.show();
//            AsyncCallWS task = new AsyncCallWS();
//            task.execute();
//        }
//    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegisterForLogin){
            Intent gotoRegister = new Intent(Login.this, Register.class);
            startActivity(gotoRegister);

        }else if(v.getId() == R.id.btnLogin){
            mob = mobile.getText().toString();
            pass = password.getText().toString();

            if(mobile.getText().toString().isEmpty() || mobile.equals("")){
                Toast.makeText(Login.this, "Please Enter Mobile No.", Toast.LENGTH_SHORT).show();
            }else if(password.getText().toString().isEmpty() || password.equals("")){
                Toast.makeText(Login.this, "Please Enter Passworf", Toast.LENGTH_SHORT).show();
            }

            else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        }

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.CreteLogin(mob,pass,"CreateLogin");
            progressDialog.dismiss();
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Invalid UserName & Password") || displayText.equals("No Network Found")) {

                mobile.setText("");
                password.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage(displayText);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                try {
                    JSONArray jArr = new JSONArray(displayText);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        editor.putString("user_MasterIdKey",obj.getString("DelBoyId"));
                        test=obj.getString("DelBoyId");
                        editor.putString("nameKey",obj.getString("DelBoy_Fname"));
                        test=obj.getString("DelBoy_Fname");
                        editor.putString("emailKey",obj.getString("DelBoy_Email"));
                        editor.putString("usernameKey",obj.getString("DelBoy_Email"));
                        mobNo=obj.getString("DelBoy_Contact");

                        sessionManager.createUserLoginSession(mobNo);
                        editor.putString("mobileKey",obj.getString("DelBoy_Contact"));
                        editor.putString("roleName",obj.getString("RoleName"));
                        editor.commit();
                        result.setVisibility(View.INVISIBLE);
                        Intent gotoindexpage = new Intent(Login.this,MainActivity.class);
                        startActivity(gotoindexpage);
                        mobile.setText("");
                        password.setText("");
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
