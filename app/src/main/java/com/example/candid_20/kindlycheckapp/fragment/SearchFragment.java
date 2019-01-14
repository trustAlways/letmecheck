package com.example.candid_20.kindlycheckapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.bean.For_City_Bean;
import com.example.candid_20.kindlycheckapp.bean.for_all_products_name.Products_Name_Bean;
import com.example.candid_20.kindlycheckapp.bean.for_city_list_bean.City_List_Bean;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.other.GPSTracker;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class SearchFragment extends Fragment implements View.OnClickListener {

    View v;
    ProgressBar loader,loader2;
    RelativeLayout rr_InboxDetailRV,rr_InboxDetailRVv;
    RecyclerView InboxDetailRV,InboxDetailRVv;
    AutoCompleteTextView edt_search;
    EditText edt_currentcity_name;
    //------------------------------------ Initialise boolean-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    boolean isGPSEnabled = false;
    Stokiest_Add_Products_Adp stokiest_add_products_adp;
    ArrayList<Products_Name_Bean> products_name_beans;

    Bundle b1;
    String str_table_name, str_city_name, str_first_name, str_encoderegid,write_ratingreviews_data,rate_experience,
            write_genuine,why_writing_reviews,wnt_recommendation;

    //------------------------------------ Initialise double for  Current Latitude and Longitude-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    double currentlat,currentlong;
    City_List_Bean city_list_bean;
    City_List_Adp city_list_adp;

    CharSequence charSequence,charSequence1;
    ImageView img_slider,img_back;
    DrawerLayout drawer;

    RelativeLayout rr_left;

    ArrayList<For_City_Bean> for_city_beans;
    TextView title;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    //------------------------------------ Initialise PlacepickerCode-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    public static final int CAPTURE_Place_ACTIVITY_REQUEST_CODE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.searchactivity, container, false);
        getBundleData();
        initUi();
        getLocation();

        return v;
    }

    private void getBundleData()
    {
        b1 = this.getArguments();

        if (b1 != null && !b1.equals("null")) {

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


        LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled == false) {
            // showGPSDisabledAlertToUser();
        }



        GPSTracker gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            currentlat = gps.getLatitude();
            currentlong = gps.getLongitude();
            System.out.println("Current Latitude@@@" + currentlat);
            System.out.println("Current Longitude@@@" + currentlong);
            if(currentlat!=0.0 && currentlong!=0.0) {

                String city_name = getAddressFromLatlong(currentlat, currentlong);
                System.out.println("City Name&&&" + city_name);
                edt_currentcity_name.setText(city_name);

            }
            else
            {
                String city_name = getAddressFromLatlong(22.719569, 75.857726);
                System.out.println("City Name###"+city_name);
                edt_currentcity_name.setText(city_name);

            }



        } else {
            //gps.showSettingsAlert();
        }

    }

    public String getAddressFromLatlong(double currentlat2, double currentlong2)
    {
        Geocoder geocoder;
        String city = null;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentlat2, currentlong2, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            System.out.println("City Name$$$"+city);



        } catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }



    private void initUi() {
        //Casting TextView for Title
        title=(TextView)getActivity().findViewById(R.id.txt_title);

        //Set TextView for Title

        title.setText("Home");

        drawer=(DrawerLayout)getActivity().findViewById(R.id.drawer_layout);

        //------------------------------------ Casting  ProgressBar-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        loader = (ProgressBar) v.findViewById(R.id.progress);
        loader.setClickable(false);

        //------------------------------------ Casting  ProgressBar-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        loader2 = (ProgressBar) v.findViewById(R.id.progresss);
        loader2.setClickable(false);

        //------------------------------------ Casting  RelativeLayout-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        rr_InboxDetailRV=(RelativeLayout)v.findViewById(R.id.rr_InboxDetailRV);
        rr_InboxDetailRVv=(RelativeLayout)v.findViewById(R.id.rr_InboxDetailRVv);


        //------------------------------------ Casting  EditText for City-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        edt_currentcity_name=(EditText) v.findViewById(R.id.autoCompleteTextView1);

        //------------------------------------ Casting  RecyclerView-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        InboxDetailRV=(RecyclerView)v.findViewById(R.id.InboxDetailRV);
        InboxDetailRVv=(RecyclerView)v.findViewById(R.id.InboxDetailRVv);

        //------------------------------------ Casting  ImageView-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        img_slider=(ImageView)getActivity().findViewById(R.id.img_slider);
        img_back=(ImageView)getActivity().findViewById(R.id.img_back);

        //------------------------------------ Set  ImageView Visibility-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        img_slider.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.GONE);
        rr_left=(RelativeLayout)getActivity().findViewById(R.id.rr_left);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        InboxDetailRV.setLayoutManager(mLayoutManager);
        InboxDetailRV.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        InboxDetailRV.setItemAnimator(new DefaultItemAnimator());



        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        InboxDetailRVv.setLayoutManager(mLayoutManager2);
        InboxDetailRVv.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        InboxDetailRVv.setItemAnimator(new DefaultItemAnimator());

     //   callWebservice_For_get_City();

        //OnClickListener For City Search
        edt_currentcity_name.setOnClickListener(this);

        //------------------------------------ Casting  AutoCompleteTextView for Search -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        edt_search=(AutoCompleteTextView)v.findViewById(R.id.edt_search);



        // For Business,Lawyer,Doctor and Corporate Search
        edt_search.setThreshold(1);//will start working from first character
        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                edt_search.setSelection(edt_search.getText().length());

                System.out.println("Edittext Selection###"+edt_search.getText().length());
                charSequence=s;

                if (charSequence.length() > 0) {

                    callWebservice_For_get_Products();

                    if(products_name_beans!=null) {
                        InboxDetailRV.setVisibility(View.VISIBLE);
                        rr_InboxDetailRV.setVisibility(View.VISIBLE);
                        InboxDetailRV.setAdapter(stokiest_add_products_adp);


//                      stokiest_add_products_adp.notifyDataSetChanged();
                    }
                }
                else {

                    rr_InboxDetailRV.setVisibility(View.GONE);
                    InboxDetailRV.setVisibility(View.GONE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                //stokiest_add_products_adp.notifyDataSetChanged();
                int   j=edt_search.getSelectionStart();
                System.out.println("Edittext AfterSelection###"+j);

            }
        });



        try
        {
            if(b1!=null && !b1.equals("null")) {
            System.out.println("Writing Data is%%%"+write_ratingreviews_data);
            if (write_ratingreviews_data.equalsIgnoreCase("write_ratingreviews_data") &&
                    !write_ratingreviews_data.equalsIgnoreCase("null")) {

                Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

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
                transaction.addToBackStack("8");
                transaction.commit();

                b1.clear();
            }

          }
        }
        catch (NullPointerException e)
        {
           e.printStackTrace();
        }

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


    }

    private void callWebservice_For_get_City() {

        try {

            if (Utils.isConnected(getActivity())) {

                GetCityNameJsonTask getCityNameJsonTask = new GetCityNameJsonTask();
                getCityNameJsonTask.execute();
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception^^^" + e);
        }
    }

    private void callWebservice_For_get_Products() {

        try {

            if (Utils.isConnected(getActivity())) {

                GetProductNameJsonTask getProductNameJsonTask = new GetProductNameJsonTask();
                getProductNameJsonTask.execute();
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception^^^" + e);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==edt_currentcity_name)
        {
          findPlace();
        }

    }
    public void findPlace() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                  .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .setCountry("IND")
                    .build();
          Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter).build(getActivity());
          startActivityForResult(intent, CAPTURE_Place_ACTIVITY_REQUEST_CODE);


        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            Toast.makeText(getActivity(), "not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_Place_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                if (place != null) {

                    Log.i("PlaceActivity", "Place: " + place.getName());
                    Log.i("PlaceActivity", "MyAddress: " + place.getAddress());
                    Log.i("PlaceActivity", "PlaceId: " + place.getId());
                    Log.i("PlaceActivity","Place Locality:" + place.getPlaceTypes());


/*Toast.makeText(getApplicationContext(),"Latitude is@@@"+place.getLatLng().latitude,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Longitude is@@@"+place.getLatLng().longitude,Toast.LENGTH_SHORT).show();*/

                    Geocoder geocoder = new Geocoder(getActivity(),Locale.getDefault());
                    List<Address> addresses = null;

                    try
                    {
                        double lat1=place.getLatLng().latitude;
                        double lon1=place.getLatLng().longitude;
                     /*   Toast.makeText(getApplicationContext(),"Latitude is###"+lat1,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Longitude is###"+lon1,Toast.LENGTH_SHORT).show();*/

                        addresses = geocoder.getFromLocation(lat1,lon1, 1);
                        // Toast.makeText(getApplicationContext(),"City is###"+addresses,Toast.LENGTH_SHORT).show();

                        String  address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String country=addresses.get(0).getCountryName();
                        String state=addresses.get(0).getAdminArea();
                        String  city = addresses.get(0).getLocality();
                        //   Toast.makeText(getApplicationContext(),"City is###"+city,Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(),"Country is###"+country,Toast.LENGTH_SHORT).show();


                        edt_currentcity_name.setText(city);






                    } catch (Exception e)
                    {

                        e.printStackTrace();
                    }




                }
            }
        }



    }

    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    private class GetCityNameJsonTask extends AsyncTask<String, String, String> {


        String result = "", web_response = "", driver_list, check = "",msg;
        String name, image;
        boolean iserror = false;
        String endlat_str1,endlon_str1;



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


            try {

                loader2.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                for_city_beans = new ArrayList<>();


                HttpClient client =new DefaultHttpClient();
                HttpGet httpPost = new HttpGet(URLs.URL_AUTOSUGGESTCITY_ALL);
                httpPost.addHeader("X-API-KEY","TEST@123");
               /* MultipartEntity m = new MultipartEntity();
                m.addPart("queryString",new StringBody(edt_currentcity_name.getText().toString()));
                httpPost.setEntity(m);
*/
                HttpResponse response1 = client.execute(httpPost);
                web_response = EntityUtils.toString(response1.getEntity());
                System.out.println("#####object GetCityNameList=" + web_response);

                JSONObject jsonObject=new JSONObject(web_response);
                msg=jsonObject.getString("status");

                System.out.println("#####object city_list1=" + msg);

                if(msg.equalsIgnoreCase("success")) {

                    try {
                        result = jsonObject.getString("result");

                        System.out.println("Result" + result);

                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            For_City_Bean for_city_bean1 = new For_City_Bean();
                            System.out.println("Size Of JSONArray Consult" + jsonArray.length());
                            String product_id = null;

                            String city_name = jsonArray.getJSONObject(i).getString("city_name");


                            System.out.println("City Name%%%" + city_name);
                            System.out.println("product id List%%%" + product_id);

                            String upperString_pro_name = city_name.substring(0, 1).toUpperCase() + city_name.substring(1);


                            for_city_bean1.setCity_name(city_name);

                            for_city_beans.add(for_city_bean1);


               /*     Gson gson = new Gson();

                    city_list_bean=gson.fromJson(web_response,City_List_Bean.class);*/
                            System.out.println("List Size:" + for_city_beans.size());
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }



            } catch (Exception e) {
                System.out.println("#####Exception Type=" + e);
                iserror = true;
            }

            return null;
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);

            loader2.setVisibility(View.GONE);
            //     swipeRefreshLayout.setVisibility(View.GONE);


            if (iserror == false) {
                try {
                    if (for_city_beans.size() > 0) {

                        System.out.println("List Size"+for_city_beans .size());

                        city_list_adp = new City_List_Adp(getActivity(),for_city_beans);
                        InboxDetailRVv.setAdapter(city_list_adp);

                        //  city_list_adp.notifyDataSetChanged();
                    } else {

                        try {
                            rr_InboxDetailRVv.setVisibility(View.GONE);
                            InboxDetailRVv.setVisibility(View.GONE);
                            edt_currentcity_name.setFocusable(true);
                            edt_currentcity_name.requestFocus();
                            edt_currentcity_name.setFocusableInTouchMode(true);

                        }

                        catch (Exception e)
                        {
                            System.out.println("Exception^^^"+e);
                        }
                        //   Toast.makeText(getApplicationContext(), "Data not Available", Toast.LENGTH_LONG).show();
                    }


                } catch (IndexOutOfBoundsException e) {
                    iserror = true;
                    System.out.println("Error is type" + e);
                }
            }

            else
            {


            }

        }

    }
    private class GetProductNameJsonTask extends AsyncTask<String, String, String> {


        String result = "", web_response = "", driver_list, check = "",msg;
        String name, image;
        boolean iserror = false;
        String endlat_str1,endlon_str1;



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


            try {

                loader.setVisibility(View.VISIBLE);
                //    swipeRefreshLayout.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                products_name_beans=new ArrayList<Products_Name_Bean>();
                HttpClient client =new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URLs.URL_AUTO_SUGGEST);
                httpPost.addHeader("X-API-KEY","TEST@123");
                MultipartEntity m = new MultipartEntity();
                m.addPart("query_string",new StringBody(edt_search.getText().toString()));
                m.addPart("city",new StringBody(edt_currentcity_name.getText().toString()));
                httpPost.setEntity(m);

                HttpResponse response1 = client.execute(httpPost);
                web_response = EntityUtils.toString(response1.getEntity());
                System.out.println("#####object GetProductNameList=" + web_response);

                JSONObject jsonObject=new JSONObject(web_response);
                msg=jsonObject.getString("status");

                System.out.println("#####object consult_list1=" + msg);

                if(msg.equalsIgnoreCase("success"))
                {
                    JSONObject jsonObject1=new JSONObject(web_response);
                    result=jsonObject1.getString("result");

                    System.out.println("Result"+result);

                    JSONArray jsonArray=new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        Products_Name_Bean products_name_bean=new Products_Name_Bean();
                        System.out.println("Size Of JSONArray Consult" + jsonArray.length());
                        String product_id=jsonArray.getJSONObject(i).getString("reg_id");
                        String  pro_name=jsonArray.getJSONObject(i).getString("name");
                        String  icon=jsonArray.getJSONObject(i).getString("icon");
                        String  table=jsonArray.getJSONObject(i).getString("table");
                        String city_name=jsonArray.getJSONObject(i).getString("city_name");
                        String encode_reg_id=jsonArray.getJSONObject(i).getString("encode_reg_id");
                        String first_name=jsonArray.getJSONObject(i).getString("first_name");

                        System.out.println("product name List%%%"+pro_name);
                        System.out.println("product id List%%%"+product_id);

                        String upperString_pro_name = pro_name.substring(0,1).toUpperCase() + pro_name.substring(1);

                        products_name_bean.setId(product_id);
                        products_name_bean.setName(upperString_pro_name);
                        products_name_bean.setIcon(icon);
                        products_name_bean.setTable(table);
                        products_name_bean.setCity_name(city_name);
                        products_name_bean.setEncode_reg_id(encode_reg_id);
                        products_name_bean.setFirst_name(first_name);
                        products_name_beans.add(products_name_bean);

                    }

                }


            } catch (Exception e) {
                System.out.println("#####Exception Type=" + e);
                iserror = true;
            }

            return null;
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);

            loader.setVisibility(View.GONE);
            //     swipeRefreshLayout.setVisibility(View.GONE);


            if (iserror == false) {
                try {
                    if (products_name_beans.size() > 0) {

                        System.out.println("List Size"+ products_name_beans.size());
                        stokiest_add_products_adp = new Stokiest_Add_Products_Adp(getActivity(),products_name_beans);

                    } else {
                        try {

                            rr_InboxDetailRV.setVisibility(View.GONE);
                            InboxDetailRV.setVisibility(View.GONE);

                        }

                        catch (Exception e)
                        {
                            System.out.println("Exception^^^"+e);
                        }
                        //   Toast.makeText(getApplicationContext(), "Data not Available", Toast.LENGTH_LONG).show();

                    }


                } catch (IndexOutOfBoundsException e) {
                    iserror = true;
                    System.out.println("Error is type" + e);
                }
            }

        }

    }
    private class City_List_Adp extends RecyclerView.Adapter<City_List_Adp.MyViewHolder> {

        Activity activity;
        //     Button btn_confirm_order,btn_processed_order,btn_delivered_order,btn_complete_order,btn_order_completed;

        List<For_City_Bean> arrayList = new ArrayList<>();
        List<For_City_Bean> arSearchlist;
        ArrayList<String> arr_pending=new ArrayList<>();
        CardView rr_first_descp;



        public City_List_Adp(Activity activity, ArrayList<For_City_Bean> city_list_lxBeans) {


            this.activity = activity;
            this.arrayList = city_list_lxBeans;
            this.arSearchlist = new ArrayList<>();

            if (arrayList!=null) {
                arSearchlist.addAll(arrayList);
            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searc_result, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            System.out.println("City Name$$$"+arrayList.get(position).getCity_name());

            try {

                holder.cuisineslistitem.setText(arrayList.get(position).getCity_name());
                final String pro_name=arrayList.get(position).getCity_name();




                rr_first_descp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String upperString_proname = pro_name.substring(0,1).toUpperCase() + pro_name.substring(1);
                        edt_currentcity_name.setText(upperString_proname);
                        rr_InboxDetailRVv.setVisibility(View.GONE);
                        InboxDetailRVv.setVisibility(View.GONE);
                    }
                });



            }
            catch (Exception e)
            {
                System.out.println("Exception Get Driver@@@@"+e);
            }

        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView cuisineslistitem,txt_products_prize,txt_distributor_name,txt_batchnumber,txt_productquantity,orderStatus,paymentmode;
            ImageView img_icon;

            public MyViewHolder(View itemView) {
                super(itemView);
                cuisineslistitem=(TextView)itemView.findViewById(R.id.cuisineslistitem);
                rr_first_descp=(CardView)itemView.findViewById(R.id.rr_first_descp);
                img_icon=(ImageView) itemView.findViewById(R.id.img_icon);
            }


        }



        public void filter(String charText) {



            charText = charText.toString().toLowerCase();
            arrayList.clear();
            if (charText.length() == 0) {
                arrayList.addAll(arSearchlist);
            } else {
                for (For_City_Bean wp : arSearchlist) {
                    if (wp.getCity_name().toLowerCase().startsWith(charText))
                    {
                        arrayList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

    private class Stokiest_Add_Products_Adp extends RecyclerView.Adapter<Stokiest_Add_Products_Adp.MyViewHolder> {

        Activity activity;
        //     Button btn_confirm_order,btn_processed_order,btn_delivered_order,btn_complete_order,btn_order_completed;

        ArrayList<Products_Name_Bean> arrayList = new ArrayList<>();
        ArrayList<Products_Name_Bean> arSearchlist;
        ArrayList<String> arr_pending=new ArrayList<>();
        CardView rr_first_descp;
    //    RelativeLayout rr_first_descp;



        public Stokiest_Add_Products_Adp(Activity activity, ArrayList<Products_Name_Bean> for_allOrder_lxDetails_beans) {


            this.activity = activity;
            this.arrayList = for_allOrder_lxDetails_beans;
            this.arSearchlist = new ArrayList<Products_Name_Bean>();

            if (arrayList!=null) {
                arSearchlist.addAll(arrayList);
            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searc_result, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            System.out.println("Name$$$"+arrayList.get(position).getName());

            try {

                holder.cuisineslistitem.setText(arrayList.get(position).getName());
                final String pro_name=arrayList.get(position).getName();

                String url1=arrayList.get(position).getIcon();

                if(!url1.equalsIgnoreCase("")) {
                    String converd_url=url1.replace("candid-15-pc","192.168.1.5");
                    Glide.with(getActivity()).load(converd_url).into(holder.img_icon);
                }


                rr_first_descp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String upperString_proname = pro_name.substring(0,1).toUpperCase() + pro_name.substring(1);
                        edt_search.setText(upperString_proname);
                        rr_InboxDetailRV.setVisibility(View.GONE);
                        InboxDetailRV.setVisibility(View.GONE);
                        charSequence.subSequence(0,0);

                        String table_name=arrayList.get(position).getTable();
                        if(table_name.equalsIgnoreCase("categories") || table_name.equalsIgnoreCase("search_terms"))
                        {
                            String new_nmestr=upperString_proname.replace(" ","-");
                            System.out.println("Name is%%%"+new_nmestr);
                            String clk_cityname=arrayList.get(position).getCity_name();

                            System.out.println("Click City is%%%"+clk_cityname);

                            Search_Result_Through_CategorySearchterm selectedFragment = Search_Result_Through_CategorySearchterm.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b=new Bundle();
                            b.putString("str_table_name",arrayList.get(position).getTable());
                            b.putString("str_city_name",arrayList.get(position).getCity_name());
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_name",arrayList.get(position).getName());
                            b.putString("str_encoderegid",arrayList.get(position).getId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("8");
                            transaction.commit();



                        }

                        else if(table_name.equalsIgnoreCase("business"))
                        {
                            System.out.println("Click TableName is%%%"+table_name);
                            String clk_cityname=arrayList.get(position).getCity_name();
                            System.out.println("Click City is%%%"+clk_cityname);
                            String new_nmestr=upperString_proname.replace(" ","-");
                            System.out.println("Name is%%%"+new_nmestr);
                            String clk_encode_reg=arrayList.get(position).getId();
                            System.out.println("Encode RegId%%%"+clk_encode_reg);

                            Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b=new Bundle();
                            b.putString("str_table_name",arrayList.get(position).getTable());
                            b.putString("str_city_name",arrayList.get(position).getCity_name());
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name",arrayList.get(position).getFirst_name());
                            b.putString("str_encoderegid",arrayList.get(position).getId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("8");
                            transaction.commit();


                        }

                        else if(table_name.equalsIgnoreCase("corporate"))
                        {
                            System.out.println("Click TableName is%%%"+table_name);
                            String clk_cityname=arrayList.get(position).getCity_name();
                            System.out.println("Click City is%%%"+clk_cityname);
                            String new_nmestr=upperString_proname.replace(" ","-");
                            System.out.println("Name is%%%"+new_nmestr);
                            String clk_encode_reg=arrayList.get(position).getId();
                            System.out.println("Encode RegId%%%"+clk_encode_reg);

                            Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b=new Bundle();
                            b.putString("str_table_name",arrayList.get(position).getTable());
                            b.putString("str_city_name",arrayList.get(position).getCity_name());
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name",arrayList.get(position).getFirst_name());
                            b.putString("str_encoderegid",arrayList.get(position).getId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("8");
                            transaction.commit();
                        }

                        else if(table_name.equalsIgnoreCase("doctors") || table_name.equalsIgnoreCase("lawyers"))
                        {
                            System.out.println("Click TableName is%%%"+table_name);
                            String clk_cityname=arrayList.get(position).getCity_name();
                            System.out.println("Click City is%%%"+clk_cityname);
                            String new_fnmestr=arrayList.get(position).getFirst_name();
                            System.out.println("Name is%%%"+new_fnmestr);
                            String clk_encode_reg=arrayList.get(position).getId();
                            System.out.println("Encode RegId%%%"+clk_encode_reg);

                            Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b=new Bundle();
                            b.putString("str_table_name",arrayList.get(position).getTable());
                            b.putString("str_city_name",arrayList.get(position).getCity_name());
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name",arrayList.get(position).getFirst_name());
                            b.putString("str_encoderegid",arrayList.get(position).getId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("8");
                            transaction.commit();

                        }









                    }
                });



            }
            catch (Exception e)
            {
                System.out.println("Exception Get Driver@@@@"+e);
            }

        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView cuisineslistitem,txt_products_prize,txt_distributor_name,txt_batchnumber,txt_productquantity,orderStatus,paymentmode;
            ImageView img_icon;

            public MyViewHolder(View itemView) {
                super(itemView);
                cuisineslistitem=(TextView)itemView.findViewById(R.id.cuisineslistitem);
                rr_first_descp=(CardView) itemView.findViewById(R.id.rr_first_descp);
                img_icon=(ImageView) itemView.findViewById(R.id.img_icon);
            }


        }



        public void filter(String charText) {



            charText = charText.toString().toLowerCase();
            arrayList.clear();
            if (charText.length() == 0) {
                arrayList.addAll(arSearchlist);
            } else {
                for (Products_Name_Bean wp : arSearchlist) {
                    if (wp.getName().toLowerCase().startsWith(charText))
                    {
                        arrayList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }


}
