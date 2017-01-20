package com.nagifts;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import com.nagifts.Adapter.DrawerAdapter;
import com.nagifts.Adapter.DrawerAdapterForNgo;
import com.nagifts.Model.DrawerItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    TextView txtlogout;
    TextView lblName;
    TextView lblEmail;
    RecyclerView listItems;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawer;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DrawerAdapterForNgo drawerAdapterForNgo;
    DrawerAdapter drawerAdapter;
    String isAdmin;

    public ArrayList<DrawerItems> itemArrayList;
    public ArrayList<DrawerItems> itemSelectedArrayListForNgo;

    public ArrayList<DrawerItems> itemArrayListForNgo;
    public ArrayList<DrawerItems> itemSelectedArrayList; SharedPreferences sharedPreferences;


    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, null);
        frameLayout = (FrameLayout) drawer.findViewById(R.id.contentFrame);
        linearLayout = (LinearLayout) drawer.findViewById(R.id.drawerlinearlayout);
        listItems = (RecyclerView) drawer.findViewById(R.id.drawerListItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isAdmin= sharedPreferences.getString("roleName", null);

            if (isAdmin.equals("Admin")) {
                final String[] tittleForNgo = new String[]{"Home", "Add Client","Scan Gift", "Delivery Status","Old Delivery Status","Logout"};

                final int[] iconsForNgo = new int[]{R.drawable.home, R.drawable.addclient, R.drawable.scanimage, R.drawable.ordertruck,R.drawable.ordertruck, R.drawable.logout};
                itemArrayListForNgo = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittleForNgo.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittleForNgo[i]);
                    drawerItems.setIcons(iconsForNgo[i]);
                    itemArrayListForNgo.add(drawerItems);
                }

                final int[] selectediconsForNgo = new int[]{R.drawable.home_red, R.drawable.addclient_red, R.drawable.scanimage_red, R.drawable.ordertruck_red,R.drawable.ordertruck_red, R.drawable.logout};
                itemSelectedArrayListForNgo = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittleForNgo.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittleForNgo[i]);
                    drawerItems.setIcons(selectediconsForNgo[i]);
                    itemSelectedArrayListForNgo.add(drawerItems);
                }
                drawerAdapterForNgo = new DrawerAdapterForNgo(itemArrayListForNgo, itemSelectedArrayListForNgo, drawer);
                getLayoutInflater().inflate(layoutResID, frameLayout, true);
                getLayoutInflater().inflate(layoutResID, linearLayout, true);
                drawer.setClickable(true);
                drawerAdapterForNgo.notifyDataSetChanged();
                listItems.setAdapter(drawerAdapterForNgo);
            }
            // for normal user
            else {
                final String[] tittle = new String[]{"Home", "Add Client", "Scan Gift","Logout"};
                final int[] icons = new int[]{R.drawable.home, R.drawable.addclient, R.drawable.scanimage, R.drawable.logout};
                itemArrayList = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittle.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittle[i]);
                    drawerItems.setIcons(icons[i]);
                    itemArrayList.add(drawerItems);
                }
                final int[] selectedicons = new int[]{R.drawable.home_red, R.drawable.addclient_red, R.drawable.scanimage_red, R.drawable.logout};
                itemSelectedArrayList = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittle.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittle[i]);
                    drawerItems.setIcons(selectedicons[i]);
                    itemSelectedArrayList.add(drawerItems);
                }
                drawerAdapter = new DrawerAdapter(itemArrayList, itemSelectedArrayList, drawer);
                getLayoutInflater().inflate(layoutResID, frameLayout, true);
                getLayoutInflater().inflate(layoutResID, linearLayout, true);
                drawer.setClickable(true);
                drawerAdapter.notifyDataSetChanged();
                listItems.setAdapter(drawerAdapter);
            }

        toolbar = (Toolbar) drawer.findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        super.setContentView(drawer);

        lblName = (TextView) findViewById(R.id.lblname);
        lblEmail = (TextView) findViewById(R.id.lblemail);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName= sharedPreferences.getString("nameKey", null);
        String userMobileNo= sharedPreferences.getString("mobileKey", null);

        lblName.setText(userName);
        lblEmail.setText(userMobileNo);
    }




}