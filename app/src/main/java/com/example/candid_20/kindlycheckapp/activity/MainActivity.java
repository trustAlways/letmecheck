package com.example.candid_20.kindlycheckapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.fragment.SearchFragment;
import com.example.candid_20.kindlycheckapp.fragment.Search_Result_Through_CategorySearchterm;
import com.example.candid_20.kindlycheckapp.fragment.Search_Through_Name;
import com.example.candid_20.kindlycheckapp.other.GPSTracker;
import com.example.candid_20.kindlycheckapp.storage.MySharedPref;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean ischeckedpermission;
    //------------------------------------ Initialise boolean-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    boolean isGPSEnabled = false;
    //------------------------------------ Initialise double-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    double currentlat,currentlong;
    RelativeLayout rr_left;
    ImageView img_back,img_slider,img_cross;
    TextView usernameheader,txt_title;
    DrawerLayout drawer;
     NavigationView navigationview;
    String str_table_name, str_city_name, str_first_name, str_encoderegid,write_ratingreviews_data,rate_experience,write_genuine,
            why_writing_reviews,wnt_recommendation;
    Bundle b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBundleData();
        initUI();
        checkForPermission();
       // requestStoragePermission();

        getLocation();

    }
       private void getBundleData() {

        b1 = getIntent().getExtras();
        System.out.println("b1 b1 1b main Activity %%%" + b1);

        if (getIntent().getExtras() != null  && getIntent().getExtras().containsKey("write_ratingreviews_data")) {

            str_table_name = b1.getString("str_table_name");
            str_city_name = b1.getString("str_city_name");
            str_first_name = b1.getString("str_first_name");
            str_encoderegid = b1.getString("str_encoderegid");
            write_ratingreviews_data= b1.getString("write_ratingreviews_data");
            rate_experience = b1.getString("rate_experience");
            write_genuine = b1.getString("write_genuine");
            why_writing_reviews = b1.getString("why_writing_reviews");
            wnt_recommendation = b1.getString("wnt_recommendation");

            System.out.println("Click TableName After is%%%" + str_table_name);
            System.out.println("Click City After is%%%" + str_city_name);
            System.out.println("Click Name After is%%%" + str_first_name);
            System.out.println("Click RegId After is%%%" + str_encoderegid);
            System.out.println("Click Write Rating Reviews After is%%%" + write_ratingreviews_data);
            System.out.println("Click RateExperience After is%%%" + rate_experience);
            System.out.println("Click Write Genuine After is%%%" + write_genuine);
            System.out.println("Click Why Writing Reviews After is%%%" + why_writing_reviews);
            System.out.println("Click Want Recommadation After is%%%" + wnt_recommendation);




        }
    }

    private void getLocation() {


        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled == false) {
            showGPSDisabledAlertToUser();
        }



        GPSTracker gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) {
            currentlat = gps.getLatitude();
            currentlong = gps.getLongitude();
            System.out.println("Current Latitude@@@" + currentlat);
            System.out.println("Current Longitude@@@" + currentlong);
            if(currentlat!=0.0 && currentlong!=0.0) {

                String city_name = getAddressFromLatlong(currentlat, currentlong);
                System.out.println("City Name&&&" + city_name);
            }
            else
            {
                String city_name = getAddressFromLatlong(22.719569, 75.857726);

            }



        } else {
            gps.showSettingsAlert();
        }

    }

    public String getAddressFromLatlong(double currentlat2, double currentlong2)
    {
        Geocoder geocoder;
        String city = null;
        List<Address> addresses;
        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentlat2, currentlong2, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            System.out.println("City Name***"+city);
           }
        catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);


                                dialog.dismiss();

                                //finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        showGPSDisabledAlertToUser();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

   @Override
    protected void onRestart() {
        super.onRestart();
        checkForPermission();
    }

    private void initUI() {

        img_slider=(ImageView)findViewById(R.id.img_slider);
        img_back=(ImageView)findViewById(R.id.img_back);
        txt_title=(TextView)findViewById(R.id.txt_title);
        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationview=(NavigationView)findViewById(R.id.navigationview);
        rr_left=(RelativeLayout)findViewById(R.id.rr_left);

        View header = View.inflate(MainActivity.this, R.layout.nav_header_main, null);
        usernameheader=(TextView) header.findViewById(R.id.usernameheader);
        img_cross=(ImageView) header.findViewById(R.id.img_cross);
        // Image cross OnClickListner
        img_cross.setOnClickListener(this);

               //  callBroadcastformsg();

        MySharedPref sp=new MySharedPref();
        String ldata=sp.getData(getApplicationContext(),"ldata","null");
        System.out.println("Ldata Main###"+ldata);

        if(!ldata.equalsIgnoreCase("null")) {

            navigationview.getMenu().getItem(1).setChecked(true);
            navigationview.getMenu().getItem(1).setVisible(true);

            navigationview.getMenu().getItem(2).setChecked(true);
            navigationview.getMenu().getItem(2).setVisible(true);
        }
        if(ldata.equalsIgnoreCase("null"))
        {

            navigationview.getMenu().getItem(1).setVisible(false);
            navigationview.getMenu().getItem(1).setChecked(false);

            navigationview.getMenu().getItem(2).setVisible(false);
            navigationview.getMenu().getItem(2).setChecked(false);

        }


        navigationview.addHeaderView(header);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case  R.id.home_icon1:

                        img_back.setVisibility(View.GONE);
                        img_slider.setVisibility(View.VISIBLE);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentFrame, new SearchFragment())
                                .commit();
                        break;

                    case  R.id.logout_icon1:
                        img_back.setVisibility(View.GONE);
                        img_slider.setVisibility(View.VISIBLE);
                        // signOut();
                        MySharedPref sp=new MySharedPref();
                        sp.saveData(getApplicationContext(),"ldata","null");
                        sp.saveData(getApplicationContext(),"token","null");

                        Toast.makeText(getApplicationContext(),"You have logged-out successfully",Toast.LENGTH_SHORT).show();

                        Intent i1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                }

                drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        rr_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });



        try{
            if (getIntent().getExtras() != null  && getIntent().getExtras().containsKey("write_ratingreviews_data")) {
                System.out.println("b1 b1 1b main Activity 222%%%" + b1);
                System.out.println("Writing Data is%%%"+write_ratingreviews_data);

               if (write_ratingreviews_data.equalsIgnoreCase("write_ratingreviews_data")
                    && !write_ratingreviews_data.equalsIgnoreCase("null"))
               {

                SearchFragment selectedFragment = SearchFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                Bundle b = new Bundle();
                b.putString("str_table_name", str_table_name);
                b.putString("str_city_name", str_city_name);
                // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                b.putString("str_first_name", str_first_name);
                b.putString("str_encoderegid", str_encoderegid);
                b.putString("write_ratingreviews_data", "write_ratingreviews_data");
                b.putString("rate_experience", rate_experience);
                b.putString("write_genuine", write_genuine);
                b.putString("why_writing_reviews", why_writing_reviews);
                b.putString("wnt_recommendation", wnt_recommendation);

                selectedFragment.setArguments(b);
                transaction.replace(R.id.contentFrame, selectedFragment);
               // transaction.addToBackStack(null);
                transaction.commit();

                b1.clear();
            }
            else
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new SearchFragment())
                        .commit();
            }

        }
        else
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, new SearchFragment())
                    .commit();
        }

     }
      catch(Exception e)
        {
            //e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        System.out.println("Count%%%" + count);

        if (count == 0) {


            //  super.onBackPressed();
            AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("Are you sure you want to exit?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Log.e("hello >>>", "....");
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    //get gps
                }
            });
            dialog.setNegativeButton(MainActivity.this.getString(R.string.no), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }



  private void checkForPermission() {
        int permissionCheckForCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheckForGallery = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        if (permissionCheckForCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForGallery != PackageManager.PERMISSION_GRANTED) {

            ischeckedpermission=false;

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION

                    },
                    1001);


        }
        else
        {
            ischeckedpermission=true;

        }



    }




  @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("", "Permission callback called-------");
        switch (requestCode) {
            case 1001: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission. ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION )== PackageManager.PERMISSION_GRANTED)
                    {
                        Log.d("", "sms & location services permission granted");
                        ischeckedpermission=true;


// process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("", "Some permissions are not granted ask again ");

                        ischeckedpermission=false;

                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                        {
                            showDialogOK("Location permission must required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkForPermission();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {


                            showDialogOK("Location permission must required for this app",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ischeckedpermission=false;
                                            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts("package", getPackageName(), null)));

                                        }

                                    });


                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        if(v==img_cross)
        {
            drawer.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_in_left));
            drawer.closeDrawer(Gravity.LEFT);
        }
    }
}
