package com.nagifts.Connectivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.nagifts.MainActivity;
import com.nagifts.Model.NotificationItems;
import com.nagifts.Notification;
import com.nagifts.SessionManager.SessionManager;
import com.nagifts.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Fetch_Notification {
    private static Context context;
    private static final String url="";
    private static String displayText;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<NotificationItems> notificationItemsArrayForAsyncTask;
    private static MediaPlayer mp;
    private static int currentPage;

    private static SessionManager sessionManager;
    private static String giftId;
    private static String oldGiftId;

    public Fetch_Notification(Notification notification) {
        context = notification;
    }

    public static void fetchNotifications( List<NotificationItems> notificationItemsArray, RecyclerView recyclerView,  RecyclerView.Adapter reviewAdapter, MediaPlayer mpNotification, int current_page) {
   //public static void fetchNotifications(final List<NotificationItems> notificationItemsArray, final RecyclerView.Adapter reviewAdapter) {

        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        notificationItemsArrayForAsyncTask = notificationItemsArray;
        mp = mpNotification;
        currentPage = current_page;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

    }
    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.Notiication(currentPage,"Notification");
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
           // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("No Recent Delivery Found in last 2min");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                sessionManager = new SessionManager(context);
                HashMap<String, String> user = sessionManager.getGiftId();
                oldGiftId = user.get(SessionManager.KEY_GiftId);
                int oldId = Integer.parseInt(oldGiftId);

                try {
                    JSONArray jsonArray = new JSONArray(displayText);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            if(currentPage == 1){
                                if(i == 0){
                                    // suppose giftId = 6 oldGiftId = 5 then update lst other wise skip
                                    giftId = obj.getString("GiftDeliveryID");
                                    int newId = Integer.parseInt(giftId);

                                    if(newId > oldId){
                                        mp.start();
                                        sessionManager.checkGiftId(giftId);
                                        notificationItemsArrayForAsyncTask.clear();
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Result");
                                        builder.setMessage("No Recent Delivery Found in last 2 min");
                                        AlertDialog alert1 = builder.create();
                                        alert1.show();
                                    }
                                }
                            }

                            NotificationItems notificationItems = new NotificationItems();
                            notificationItems.setlocation(obj.getString("location"));
                            notificationItems.setfullname(obj.getString("fullname"));
                            notificationItems.setDelBoy_Fname(obj.getString("DelBoy_Fname"));
                            notificationItems.setListid(obj.getString("GiftDeliveryID"));
                            notificationItems.setrelation(obj.getString("Client_Type"));
                            notificationItems.setDeliveryDate(obj.getString("DeliveryDate"));
                            notificationItems.setdesignation(obj.getString("designation"));
                            notificationItems.setstatus(obj.getString("Status"));

                            notificationItemsArrayForAsyncTask.add(notificationItems);
                            adapterForAsyncTask.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
