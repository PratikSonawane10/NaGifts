package com.nagifts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.nagifts.Adapter.NotificationAdapter;
import com.nagifts.Adapter.OldNotificationAdapter;
import com.nagifts.Connectivity.Fetch_Notification;
import com.nagifts.Connectivity.Fetch_Old_Notification;
import com.nagifts.InternetConnectivity.NetworkChangeReceiver;
import com.nagifts.Listeners.NotificationListScrollListener;
import com.nagifts.Model.NotificationItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class OldNotification extends BaseActivity implements View.OnClickListener{

    public List<NotificationItems> notificationItems = new ArrayList<NotificationItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    boolean doubleBackToExitPressedOnce = false;
    private int current_page = 1;
    private StringBuilder date;
    String selectedDate="";

    private DatePicker datePicker;
    private Calendar calendar;
    private EditText clientName;
    private int year, month, day;
    String nameofclient="";
    Button submit;
    Button selectDate;
    String name;
    TextView dateView;

    private ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_notification);

        clientName = (EditText) findViewById(R.id.clientName);
        submit= (Button) findViewById(R.id.Submit);
        selectDate = (Button) findViewById(R.id.Date);
        dateView= (TextView) findViewById(R.id.txtselectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(this);
        submit.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.notificationListRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new NotificationListScrollListener(linearLayoutManager, current_page) {

            @Override
            public void onLoadMore(int current_page) {
                //callAsynchronousTask();
                callAsynchronousTask(current_page);
            }
        });

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new OldNotificationAdapter(notificationItems);
        recyclerView.setAdapter(reviewAdapter);
        //callAsynchronousTask(current_page);

    }

    @Override
    public void onClick(View v) {
        name = clientName.getText().toString();
        if(v.getId() == R.id.Submit){
            if (selectedDate == "" && name.equals("")){
                Toast.makeText(this, "Please Select Date or Client Name ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Your Selected Date is : "+selectedDate, Toast.LENGTH_SHORT).show();
                nameofclient = clientName.getText().toString();
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();
                callAsynchronousTask(current_page);
                notificationItems.clear();
            }

            date=null;
            clientName.setText("");
        }
        if(v.getId() == R.id.Date){
            setDate(v);
        }
    }
    public void callAsynchronousTask(final int current_page) {
        try {
            Fetch_Old_Notification fetch_Notification = new Fetch_Old_Notification(OldNotification.this);
            fetch_Notification.fetchNotifications(notificationItems,recyclerView, reviewAdapter,current_page,selectedDate,nameofclient, progressDialog);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
      date = new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year);

        selectedDate = date.toString();
        if(!selectedDate.equals("")){
            selectDate.setText("Change Date");
        }
    }


    @Override
    public void onBackPressed() {
        OldNotification.this.finish();
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


    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = OldNotification.this.getPackageManager();
        ComponentName component = new ComponentName(OldNotification.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = OldNotification.this.getPackageManager();
        ComponentName component = new ComponentName(OldNotification.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
