package com.nagifts.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.nagifts.Model.NotificationItems;
import com.nagifts.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    List<NotificationItems> notificationItems;
    View v;
    ViewHolder viewHolder;
    LinearLayout greenColorLinearLayout;

    public NotificationAdapter(List<NotificationItems> notificationItemsArrayList) {
        this.notificationItems = notificationItemsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_notification_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        NotificationItems notificationItem = notificationItems.get(position);
        viewHolder.bindNotificationDetailsList(notificationItem );
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView city;
        public TextView clientName;
        public TextView executive;
        public TextView date;
        public TextView time;
        public TextView srNo;
        public TextView relation;
        public TextView status;
        public View cardView;
        String designation;

        public View notificationListDeviderLine;
        public TextView EmptyKeyResponse;

        RelativeLayout greenColorLinearLayout;
        NotificationItems notificationItems = new NotificationItems();

        public ViewHolder(View itemView) {
            super(itemView);

            city = (TextView) itemView.findViewById(R.id.locationOfDelivery);
            clientName = (TextView) itemView.findViewById(R.id.clientName);
            executive = (TextView) itemView.findViewById(R.id.executive);
            date = (TextView) itemView.findViewById(R.id.date);
            srNo = (TextView) itemView.findViewById(R.id.serialNo);
            relation = (TextView) itemView.findViewById(R.id.relation);
            status = (TextView) itemView.findViewById(R.id.status);
            cardView = itemView;
            //notificationListDeviderLine = itemView.findViewById(R.id.viewdividerline);
            greenColorLinearLayout = (RelativeLayout) itemView.findViewById(R.id.mainRelativeLayout);
        }

        public void bindNotificationDetailsList(NotificationItems notificationItems) {
            this.notificationItems = notificationItems;

            designation = notificationItems.getdesignation();
            if(designation.equals("")){
                clientName.setText(notificationItems.getfullnames());
            }else{
                String name = notificationItems.getfullnames();
                String nameAndDesignation = name + "\n" +"("+ designation +")";
                clientName.setText(nameAndDesignation);
            }

            status.setText(notificationItems.getstatus());
            city.setText(notificationItems.getlocation());
            executive.setText(notificationItems.getDelBoy_Fname());
            srNo.setText(notificationItems.getListid());
            relation.setText(notificationItems.getrelaton());
            date.setText(notificationItems.getDeliveryDate());
        }
    }

}
