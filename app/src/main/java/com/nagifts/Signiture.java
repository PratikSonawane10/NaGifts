package com.nagifts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.Result;

public class Signiture extends BaseActivity {
    public  String pkgn;
    public  String img_str;
    ImageView iv;
    GestureOverlayView gv;
    public  String displayText;

    boolean doubleBackToExitPressedOnce = false;
    GestureOverlayView gestureView;
    String path;
    File file;
    Bitmap bitmap;
    public boolean gestureTouch=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signiture);


        Bundle bundle = getIntent().getExtras();
        pkgn = bundle.getString("stuff");


        Button donebutton = (Button) findViewById(R.id.DoneButton);
        donebutton.setText("Done");
        Button clearButton = (Button) findViewById(R.id.ClearButton);
        clearButton.setText("Clear");

        path= Environment.getExternalStorageDirectory()+"/signature.png";
        file = new File(path);
        file.delete();
        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        gestureView.setDrawingCacheEnabled(true);

        gestureView.setAlwaysDrawnWithCacheEnabled(true);
        gestureView.setHapticFeedbackEnabled(false);
        gestureView.cancelLongPress();
        gestureView.cancelClearAnimation();
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

            @Override
            public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureCancelled(GestureOverlayView arg0,
                                           MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureStarted(GestureOverlayView arg0,
                                         MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    gestureTouch = false;
                } else {
                    gestureTouch = true;
                }
            }
        });

        donebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    iv = (ImageView)findViewById(R.id.ImageView);
                    gv = (GestureOverlayView)findViewById(R.id.signaturePad);

                    gv.setDrawingCacheEnabled(true);
                    Bitmap b = Bitmap.createBitmap(gv.getDrawingCache());
                    iv.setImageBitmap(b);
                    gv.setDrawingCacheEnabled(false);

                    BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bb = bos.toByteArray();
                    img_str = Base64.encodeToString(bb, 0);

                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                gestureView.invalidate();
                gestureView.clear(true);
                gestureView.clearAnimation();
                gestureView.cancelClearAnimation();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Signiture.this.finish();
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
            displayText = WebService.InsertSigniture(pkgn, img_str, "UpdateGift");
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            new AlertDialog.Builder(Signiture.this)
                    .setMessage(displayText)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Perform Your Task Here--When Yes Is Pressed.
                            dialog.cancel();
                            Intent i = new Intent(Signiture.this,MainActivity.class);
                            startActivity(i);
                        }
                    }).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
