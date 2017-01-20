package com.nagifts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class Print extends BaseActivity {
    TextView text_name;
    TextView text_company;
    TextView text_relatrion;
    TextView text_location;
    EditText text_contactper;
    EditText text_contactno;
    TextView text_result;
    //ProgressBar progressBar1;
   public String pkgn;
    String displayText;
    String Contact="";
   public String GiftDeliveryID;
    String ContactPerson;
    SharedPreferences sharedpreferences;
    Spinner workfor_spinner;
    String Status;
    String ExecutiveId;
    private ProgressDialog progressDialog = null;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        Spinner spinner = (Spinner) findViewById(R.id.workfor_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workfor_status, R.layout.spiner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        GiftDeliveryID = sharedpreferences.getString("user_MasterIdKey", null);

        //progressBar1=(ProgressBar) findViewById(R.id.progressBar1);
        text_name = (TextView) findViewById(R.id.text_name);
        text_company = (TextView) findViewById(R.id.text_company);
        text_relatrion = (TextView) findViewById(R.id.text_relatrion);
        text_location = (TextView) findViewById(R.id.text_location);
        text_contactper = (EditText) findViewById(R.id.text_contactper);
        text_contactno = (EditText) findViewById(R.id.text_contactno);
        text_result = (TextView) findViewById(R.id.text_result);

        Bundle bundle = getIntent().getExtras();
        pkgn = bundle.getString("stuff");
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Print.this.finish();
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
    public void invokeLogin(View view)
    {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        GiftDeliveryID= sharedpreferences.getString("user_MasterIdKey", null);
        text_contactper = (EditText) findViewById(R.id.text_contactper);
        text_contactno = (EditText) findViewById(R.id.text_contactno);
        workfor_spinner=(Spinner) findViewById(R.id.workfor_spinner);
        //progressBar1=(ProgressBar) findViewById(R.id.progressBar1);

        Contact=text_contactno.getText().toString();
        ContactPerson=text_contactper.getText().toString();
        Status=workfor_spinner.getSelectedItem().toString();

        if(text_contactper.getText().toString().isEmpty()){
            Toast.makeText(Print.this, "Please Enter Contact Person Name", Toast.LENGTH_SHORT).show();
        }
        else if(Status== null){
            Toast.makeText(Print.this, "Please Select Status.", Toast.LENGTH_SHORT).show();
        }else{
            //progressBar1.setVisibility(View.VISIBLE);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait.");
            progressDialog.show();
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if(Contact=="") {
                displayText = WebService.ShowGift(pkgn,GiftDeliveryID,"ShowGift");
            }else {
                displayText = WebService.InsertDelevery(GiftDeliveryID,Status,ContactPerson,Contact,pkgn,"CreateDilivery");
                progressDialog.dismiss();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if (Contact == "") {
                if (displayText.equals("No Record Found") || displayText.equals("No Network Found")) {
                    text_name.setText(displayText);

                    //progressBar1.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Print.this);
                    builder.setTitle("Result");
                    builder.setMessage("You are Not Allocated Delevewry Boy.");
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    Intent mainactivity = new Intent(Print.this, MainActivity.class);
                    startActivity(mainactivity);


                } else {
                    try {
                        JSONArray jArr = new JSONArray(displayText);
                        StringBuilder strBuilder = new StringBuilder();
                        for (int count = 0; count < jArr.length(); count++) {
                            JSONObject obj = jArr.getJSONObject(count);
                            String fullname = obj.getString("fullname");
                            String Client_Company = obj.getString("Client_Company");
                            String location = obj.getString("location");
                            String Client_Type = obj.getString("Client_Type");
                            String Client_Contactno=obj.getString("Client_Contactno");

                            String ContactPersonName = obj.getString("fullname");
                            strBuilder.append(obj.getString("Gift_Name") + ", ");
                            text_name.setText("Name: " + fullname);
                            text_company.setText("Company: " + Client_Company);
                            text_location.setText("Location: " + location);
                            text_relatrion.setText("Relation: " + Client_Type);
                            text_contactper.setText("Contact Person: "+fullname);
                            text_contactno.setText("Contact Number" +Client_Contactno);
                        }
                        text_result.setText("Gifts: " + strBuilder.toString());

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            } else
            {

                if (displayText.equals("No Network Found")) {
                    //progressBar1.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Print.this);
                     builder.setTitle("Result");
                     builder.setMessage(displayText);
                     AlertDialog alert1 = builder.create();
                     alert1.show();
                }else if (displayText.equals("Gift Allready Delivered")) {
                    //progressBar1.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Print.this);
                    builder.setTitle("Result");
                    builder.setMessage(displayText);
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                }else {
                    Intent ii = new Intent(Print.this, Signiture.class);
                    Bundle b = new Bundle();
                    b.putString("stuff", pkgn);
                    ii.putExtras(b);
                    startActivity(ii);
                }
            }
        }

    }
}
