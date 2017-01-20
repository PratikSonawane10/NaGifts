package com.nagifts.Connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.nagifts.OldNotification;
import com.nagifts.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.format;

public class Fetch_Old_Notification {
    private static Context context;
    private static final String url="";
    private static String displayText;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<NotificationItems> notificationItemsArrayForAsyncTask;
    private static int currentPage;
    private static String userSelectedDate;
    private static String clientName;
    private static Date newDate;

    private static ProgressDialog progressDialogBox = null;


    public Fetch_Old_Notification(OldNotification notification) {
        context = notification;
    }

    public static void fetchNotifications(final List<NotificationItems> notificationItemsArray, RecyclerView recyclerView, final RecyclerView.Adapter reviewAdapter, int current_page, String selectedDate, String name, ProgressDialog progressDialog) throws ParseException {

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        notificationItemsArrayForAsyncTask = notificationItemsArray;
        currentPage = current_page;
        userSelectedDate = selectedDate;
        clientName = name;
        progressDialogBox = progressDialog;

    }
    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.OldNotiication(currentPage,userSelectedDate,clientName,"NotificationWithFilter");
            progressDialogBox.dismiss();
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
                notificationItemsArrayForAsyncTask.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Delivery Not Available");
                AlertDialog alert1 = builder.create();
                alert1.show();

//                Intent gotoMainActivity = new Intent(context, MainActivity.class);
//                context.startActivity(gotoMainActivity);
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            NotificationItems notificationItems = new NotificationItems();
                            notificationItems.setlocation(obj.getString("location"));
                            notificationItems.setfullname(obj.getString("fullname"));
                            notificationItems.setDelBoy_Fname(obj.getString("DelBoy_Fname"));
                            notificationItems.setListid(obj.getString("GiftDeliveryID"));
                            notificationItems.setrelation(obj.getString("Client_Type"));
                            notificationItems.setDeliveryDate(obj.getString("DeliveryDate"));
                            notificationItems.setdesignation(obj.getString("designation"));

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
