package com.nagifts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nagifts.InternetConnectivity.NetworkChangeReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddClient extends BaseActivity {
    public  String displayText;
    public  String title="";
    public  String fullname="";
    public  String options;
    public  String Client_Contactno="";
    public  String Client_Company;
    public  String addres="";
    public  String email;
    public  String landmark;
    public  String location;
    public  String city;
    public  String district;
    public  String state;
    public  String pincode;
    public  String ClientTypeId;

    boolean doubleBackToExitPressedOnce = false;
    public ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        Spinner spinner = (Spinner) findViewById(R.id.Client_Company);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workfor_array, R.layout.spiner_item2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner title = (Spinner) findViewById(R.id.title);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.title_array, R.layout.spiner_item2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setAdapter(adapter1);


        Spinner options = (Spinner) findViewById(R.id.options);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.option_array, R.layout.spiner_item2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter2);

        Spinner ClientTypeId = (Spinner) findViewById(R.id.ClientTypeId);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.clienttype_array, R.layout.spiner_item2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ClientTypeId.setAdapter(adapter3);

        Spinner district = (Spinner) findViewById(R.id.district);

        AsyncCallWS task = new AsyncCallWS();
        task.execute();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = AddClient.this.getPackageManager();
        ComponentName component = new ComponentName(AddClient.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = AddClient.this.getPackageManager();
        ComponentName component = new ComponentName(AddClient.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        AddClient.this.finish();
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
    public void AddClient(View view)
    {
        try {
            Spinner title1 = (Spinner) findViewById(R.id.title);
            Spinner spinner = (Spinner) findViewById(R.id.Client_Company);
            Spinner options1 = (Spinner) findViewById(R.id.options);
            Spinner ClientTypeId1 = (Spinner) findViewById(R.id.ClientTypeId);
            Spinner district1 = (Spinner) findViewById(R.id.district);
            TextView fullname1=(TextView) findViewById(R.id.fullname);
            TextView Client_Contactno1=(TextView) findViewById(R.id.Client_Contactno);
            TextView addres1=(TextView) findViewById(R.id.addres);
            TextView email1=(TextView) findViewById(R.id.email);
            TextView landmark1=(TextView) findViewById(R.id.landmark);
            TextView location1=(TextView) findViewById(R.id.location);
            TextView city1=(TextView) findViewById(R.id.city);
            TextView state1=(TextView) findViewById(R.id.state);
            TextView pincode1=(TextView) findViewById(R.id.pincode);


            //Worksfor = workFor.getSelectedItem().toString();
            title=title1.getSelectedItem().toString();
            fullname = fullname1.getText().toString();
            options = options1.getSelectedItem().toString();
            Client_Contactno = Client_Contactno1.getText().toString();
            Client_Company = spinner.getSelectedItem().toString();
            addres = addres1.getText().toString();
            email = email1.getText().toString();
            landmark = landmark1.getText().toString();
            location = location1.getText().toString();
            city = city1.getText().toString();
            district = district1.getSelectedItem().toString();
            state = state1.getText().toString();
            pincode = pincode1.getText().toString();
            ClientTypeId = ClientTypeId1.getSelectedItem().toString();

            if(fullname1.getText().toString().isEmpty() || fullname1.equals("")){
                Toast.makeText(AddClient.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            }
            else if( (Client_Contactno1.length() < 10 || Client_Contactno1.length() > 12) ) {
                Toast.makeText(AddClient.this, "Mobile No is not valid", Toast.LENGTH_LONG).show();
            }
            else if(addres1.getText().toString().isEmpty() || addres1.equals("")){
                Toast.makeText(AddClient.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
            }else{
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if(fullname =="") {
                displayText = WebService.GetDistrict("SelectDistrict");
            }else {
                if(fullname!="" && Client_Contactno!="" && addres!="") {
                    displayText = WebService.CreateClient(title, fullname, options, Client_Contactno, Client_Company, addres, email, landmark, location, city, district, state, pincode, ClientTypeId, "CreateClient");
                    progressDialog.dismiss();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {

            if (fullname == "") {
                if (displayText.equals("No Record Found") || displayText.equals("No Network Found")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddClient.this);
                    builder.setTitle("Result");
                    builder.setMessage(displayText);
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                } else {

                    try {
                        JSONArray jArr = new JSONArray(displayText);
                        String[] districtList = new String[]{
                                "Select District"
                        };
                        ArrayList<String> stringArrayList = new ArrayList<String>();
                        stringArrayList = new ArrayList<>(Arrays.asList(districtList));

                        for (int count = 0; count < jArr.length(); count++) {
                            JSONObject obj = jArr.getJSONObject(count);
                            String districtName = obj.getString("districtName");
                            stringArrayList.add(obj.getString("districtName"));
                        }


                        Spinner district = (Spinner) findViewById(R.id.district);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddClient.this, R.layout.spiner_item, stringArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        district.setAdapter(adapter);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddClient.this);
                    builder.setTitle("Result");
                    builder.setMessage(displayText);
                    AlertDialog alert1 = builder.create();
                    alert1.show();

            }
        }
    }
}
