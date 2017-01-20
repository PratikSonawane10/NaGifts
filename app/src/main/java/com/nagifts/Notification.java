package com.nagifts;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.nagifts.Adapter.NotificationAdapter;
import com.nagifts.Connectivity.Fetch_Notification;
import com.nagifts.InternetConnectivity.NetworkChangeReceiver;
import com.nagifts.Listeners.NotificationListScrollListener;
import com.nagifts.Model.NotificationItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Notification extends BaseActivity {

    public List<NotificationItems> notificationItems = new ArrayList<NotificationItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    MediaPlayer mp;
    boolean doubleBackToExitPressedOnce = false;
    private int current_page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
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
        reviewAdapter = new NotificationAdapter(notificationItems);
        recyclerView.setAdapter(reviewAdapter);
        callAsynchronousTask(current_page);

    }
    @Override
    public void onBackPressed() {
        Notification.this.finish();
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

    public void callAsynchronousTask(final int current_page) {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //callToFetchRecords();
                        try {

                            Fetch_Notification fetch_Notification = new Fetch_Notification(Notification.this);
                            fetch_Notification.fetchNotifications(notificationItems,recyclerView, reviewAdapter,mp,current_page);
                            //fetch_Notification.fetchNotifications(notificationItems, reviewAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 120000); //execute in every 2 min
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Notification.this.getPackageManager();
        ComponentName component = new ComponentName(Notification.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Notification.this.getPackageManager();
        ComponentName component = new ComponentName(Notification.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



}
