package com.nagifts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import android.Manifest;


import com.nagifts.CropImage.CropImage;
import com.nagifts.InternetConnectivity.NetworkChangeReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int PIC_CAMERA_CROP = 3;
    private static final int PIC_GALLERY_CROP = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    private static final int READ_STORAGE_PERMISSION_REQUEST = 6;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 7;

    private ProgressDialog progressDialog = null;

    public AlertDialog alertDialog;
    public ArrayAdapter<String> dialogAdapter;

    EditText DelBoy_Fname1;
    EditText DBoyMName1;
    EditText DelBoy_Lname1;
    EditText DelBoy_Contact1;
    EditText Password1;
    EditText DelBoy_Add1;
    EditText DelBoy_Email1;
    TextView res;

    public String DelBoy_Fname="";
    public String DBoyMName;
    public String DelBoy_Lname;
    public String DelBoy_Contact;
    public String Password="";
    public String DelBoy_Add;
    public String DelBoy_Email;
    String displayText;

    Button selectImageButton1;
    Button selectImageButton2;
    Button selectImageButton3;
    ImageView firstImage;
    ImageView secondImage;
    String currentImagePath;
    String currentImagePath2;
    Spinner role;
    String userRole="";

    Bitmap imageToShow;
    String timeStamp;
    File storageDir;
    File originalFile;
    File originalFile2;
    File cropFile;
    String imageBase64String;
    String imageBase64String2;
    String firstImagePath = "";
    String secondImagePath = "";

    String firstImageName = "";
    String secondImageName = "";

    String asycTaskCounter;
    LinearLayout imageViewLinearLayout;
    boolean doubleBackToExitPressedOnce = false;

    private long TIME = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        role = (Spinner) findViewById(R.id.role);
        DelBoy_Fname1 = (EditText) findViewById(R.id.fname);
        DBoyMName1 = (EditText) findViewById(R.id.mname);
        DelBoy_Lname1 = (EditText) findViewById(R.id.lname);
        DelBoy_Contact1 = (EditText) findViewById(R.id.contact);
        Password1 = (EditText) findViewById(R.id.password);
        DelBoy_Add1 = (EditText) findViewById(R.id.address);
        DelBoy_Email1 = (EditText) findViewById(R.id.email);
        res = (TextView) findViewById(R.id.txtRes);
        selectImageButton3 = (Button) this.findViewById(R.id.btnRegister);
        selectImageButton1 = (Button) this.findViewById(R.id.btnImage1);
        selectImageButton2 = (Button) this.findViewById(R.id.btnImage2);
        firstImage = (ImageView) this.findViewById(R.id.firstImage);
        secondImage = (ImageView) this.findViewById(R.id.secondImage);
        imageViewLinearLayout = (LinearLayout)findViewById(R.id.imageViewLinearLayout);

        imageViewLinearLayout.setVisibility(View.GONE);

        selectImageButton1.setOnClickListener(this);
        selectImageButton2.setOnClickListener(this);
        selectImageButton3.setOnClickListener(this);
        firstImage.setOnClickListener(this);
        secondImage.setOnClickListener(this);

        asycTaskCounter="1";
        AsyncCallWS taskForRole = new AsyncCallWS();
        taskForRole.execute();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onBackPressed() {
        Register.this.finish();
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


    private class AsyncCallWS extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(asycTaskCounter=="1"){
                try {
                    JSONArray jArr = new JSONArray(displayText);
                    String[] userRoleList = new String[]{
                            "Select Role"
                    };
                    ArrayList<String> stringRoleArrayList = new ArrayList<String>();
                    stringRoleArrayList = new ArrayList<>(Arrays.asList(userRoleList));

                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        String role = obj.getString("RoleName");
                        stringRoleArrayList.add(obj.getString("RoleName"));
                    }

                    Spinner role = (Spinner) findViewById(R.id.role);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this, R.layout.spiner_item, stringRoleArrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    role.setAdapter(adapter);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }else{
                // res.setText(displayText);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                builder.setTitle("Result");
                builder.setMessage(displayText);
                android.app.AlertDialog alert1 = builder.create();
                alert1.show();
                if(displayText.equals("New User Successfully Created")){
                    Intent first = new Intent(Register.this,Login.class);
                    startActivity(first);
                }
            }
            super.onPostExecute(aVoid);
        }
        @Override
        protected Void doInBackground(String... params) {
            if(asycTaskCounter.equals("1")){
                displayText = WebService.FetchRole("ShowRole");

            }else{
                displayText = WebService.CreteUser(DelBoy_Fname, DBoyMName, DelBoy_Lname, DelBoy_Contact, Password, DelBoy_Add, DelBoy_Email, firstImagePath, secondImagePath,userRole, "CreateUser");
                progressDialog.dismiss();
            }
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.btnImage1 || v.getId() == R.id.btnImage2 ) {
            if(ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestWriteStoragePermission();
            }
            else {
                if(ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestReadStoragePermission();
                }
                else {
                    createImageFormSelectImageDialogChooser();
                }
            }
        }
        else if(v.getId()==R.id.btnRegister){
            if(DelBoy_Fname1.getText().toString().isEmpty() || DelBoy_Fname1.equals("")){
                Toast.makeText(Register.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
            }else if(DBoyMName1.getText().toString().isEmpty() || DBoyMName1.equals("")){
                Toast.makeText(Register.this, "Please Enter Midle Name", Toast.LENGTH_SHORT).show();
            }
            else if(DelBoy_Lname1.getText().toString().isEmpty() || DelBoy_Lname1.equals("")){
                Toast.makeText(Register.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            }
            else if( (DelBoy_Contact1.length() < 10 || DelBoy_Contact1.length() > 12) ) {
                Toast.makeText(Register.this, "Mobile No is not valid", Toast.LENGTH_LONG).show();
            }
            else if(Password1.getText().toString().isEmpty() || Password1.equals("")){
                Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }
            else if(DelBoy_Add1.getText().toString().isEmpty() || DelBoy_Add1.equals("")){
                Toast.makeText(Register.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
            }
            else if(DelBoy_Email1.getText().toString().isEmpty() || DelBoy_Email1.equals("")){
                Toast.makeText(Register.this, "Please Enter Email Id", Toast.LENGTH_LONG).show();
            }else if(role.getSelectedItem().toString().isEmpty() || role.getSelectedItem().toString().equals("")){
                Toast.makeText(Register.this, "Please Select Role", Toast.LENGTH_LONG).show();
            }
            else if(firstImage.getDrawable() == null){
                Toast.makeText(Register.this, "Please Add Photo", Toast.LENGTH_LONG).show();
            }
            else if(secondImage.getDrawable() == null){
                Toast.makeText(Register.this, "Please Add Photo", Toast.LENGTH_LONG).show();
            }
            else{
                userRole= role.getSelectedItem().toString();
                DelBoy_Fname = DelBoy_Fname1.getText().toString();
                DBoyMName = DBoyMName1.getText().toString();
                DelBoy_Lname = DelBoy_Lname1.getText().toString();
                DelBoy_Contact = DelBoy_Contact1.getText().toString();
                Password=Password1.getText().toString();
                DelBoy_Add=DelBoy_Add1.getText().toString();
                DelBoy_Email=DelBoy_Email1.getText().toString();

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();
                asycTaskCounter="2";
                AsyncCallWS task = new AsyncCallWS();
                task.execute();

            }
        }
    }


    private void createImageFormSelectImageDialogChooser() {
        dialogAdapter = new ArrayAdapter<String>(Register.this, android.R.layout.select_dialog_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.alertDialogListNames));
                return view;
            }
        };
        dialogAdapter.add("Take from Camera");
        dialogAdapter.add("Select from Gallery");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Register.this, R.style.AlertDialogCustom));
        builder.setTitle("Select Image");
        builder.setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    alertDialog.dismiss();
                    if(ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestCameraPermission();
                    }
                    else {
                        new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                    }
                } else if (which == 1) {
                    alertDialog.dismiss();
                    new SelectGalleryImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }
            }
        });
        alertDialog = builder.create();

        alertDialog.show();
    }


    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == CAMERA_REQUEST) {
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), bmOptions);

                    doCropping(originalFile, PIC_CAMERA_CROP);
                }
                else if(requestCode == GALLERY_REQUEST) {
                    Uri uri = intent.getData();
                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    currentImagePath = cursor.getString(columnIndex);
                    cursor.close();
                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile2.getAbsolutePath(), bmOptions);

                    doCropping(originalFile2, PIC_GALLERY_CROP);
                }
                else if(requestCode == PIC_CAMERA_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    String filename=currentImagePath.substring(currentImagePath.lastIndexOf("/")+1);
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String);
                }
                else if(requestCode == PIC_GALLERY_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
                    //String filename=currentImagePath.substring(currentImagePath.lastIndexOf("/")+1);
                    imageBase64String2 = createBase64StringFromImageFile(originalFile2);
                    //this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    this.imageToShow = saveGalleryCropBitmap(imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String2);

                    //setBitmapToImage(this.imageToShow);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Register.this, "Did not taken any image!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(e.getMessage(), "Error");
            Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String createBase64StringFromImageFile(File originalFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setBitmapToImage(Bitmap imageToShow, String imageBase64String) {
        imageViewLinearLayout.setVisibility(View.VISIBLE);
        if(Objects.equals(firstImagePath, "")) {
            firstImage.setImageBitmap(imageToShow);
            //firstImagePath = cropFile.getAbsolutePath();
            //firstImagePath = originalFile.getAbsolutePath();
            //firstImagePath = "data:image/png;base64,"+imageBase64String;
            firstImagePath = imageBase64String;
            firstImageName = splitImageName(currentImagePath);
        }
        else if(!Objects.equals(firstImagePath, "")) {
            secondImage.setImageBitmap(imageToShow);
            //secondImagePath = cropFile.getAbsolutePath();
            //secondImagePath = originalFile.getAbsolutePath();
            //secondImagePath = "data:image/png;base64," + imageBase64String;
            secondImagePath = imageBase64String;
            secondImageName = splitImageName(currentImagePath);
        }
    }

    private String splitImageName(String currentImagePath) {
        String imageName = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
        return imageName;
    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void doCropping(File image, int request_code) {

        Intent cropIntent = new Intent(this, CropImage.class);

        cropIntent.putExtra("image-path", currentImagePath);
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);

        try {
            startActivityForResult(cropIntent, request_code);
        } catch (Exception e) {
            Toast.makeText(Register.this, "Crop Error", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap saveCameraCropBitmap(String filename, Bitmap imageToShow) {
        FileOutputStream outStream = null;

        cropFile = new File(currentImagePath);
        if (cropFile.exists()) {
            cropFile.delete();
            cropFile = new File(storageDir + ".png");
        }
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private Bitmap saveGalleryCropBitmap(Bitmap imageToShow) {
        FileOutputStream outStream = null;

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        cropFile = new File(storageDir + ".png");
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = new File(storageDir + ".png");
        try {
            currentImagePath = image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestWriteStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }

    public class SelectCameraImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
            return null;
        }
    }

    public class SelectGalleryImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            } else {
                Toast.makeText(Register.this, "CAMERA permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == WRITE_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                Toast.makeText(Register.this, "Write storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == READ_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createImageFormSelectImageDialogChooser();
            } else {
                Toast.makeText(Register.this, "Read storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
