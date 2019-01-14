package com.example.candid_20.kindlycheckapp.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.activity.MainActivity;
import com.example.candid_20.kindlycheckapp.bean.appointment.sessions;
import com.example.candid_20.kindlycheckapp.bean.appointment.timeslots;
import com.example.candid_20.kindlycheckapp.bean.services;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.storage.MySharedPref;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Book_AppointMent extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {
    View v;
    ImageView img_slider,img_back;
    TextView txt_nodata,title;
    RelativeLayout rr_left;
    TabLayout allTabs;
    String service_name="";
    ProgressBar progressBar;

    //------------------arraylist data
    ArrayList<sessions> arr_all_session_list;
    ArrayList<timeslots> arrayList_time_slots;
    ArrayList<String> arr_all_booked_session_time;
    ArrayList<services> arr_all_services_list;

    RecyclerView recyclerView_session_1,recyclerView_session_2,recyclerView_session_3, recyclerView_for_appointment;
    String recycle_one_value="",recycle_two_value="",recycle_three_value="";

    MyRecyclerViewAdapter1 adapter;
    Appoinrment_services_adapter appointment_services_adapter;
    //--------------------booking detail view
    TextView txt_lawyer_doc_name,txt_specializtion,txt_degrees,txt_address,txt_date_of_appointment,
            txt_selected_session,txt_time_of_appointment,txt_session_time;
    ImageView img_profile_picture;

    CalendarView calendarView;
    FrameLayout frameLayout_recycle;
    RelativeLayout rr_session_select,rr_session_selection_view,rr_time_of_appointment,rr_appointment_for;
    Button btn_proceed,btn_after_check_appointments;
    //String for user details
    String str_user_name,str_category,str_degrees,address,pic_url,reg_id,main_id,type,city,user_id,date,email;

   /* // data to populate the RecyclerView with
    String[] data = {"2:00 PM", "2:10 PM", "2:20 PM", "book", "2:40 PM", "book", "3:00 PM", "3:10 PM", "3:20 PM", "3:30 PM",
            "3:40 PM", "3:50 PM", "4:00 PM"};*/


    public static Book_AppointMent newInstance() {

        Book_AppointMent fragment = new Book_AppointMent();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.book_appointment, container, false);

        getBundleData();
        initUi();
        setOnclickMethod();

        getAllWidgets();

        //bindWidgetsWithAnEvent();
        //setupTabLayout(jsonArray9.length());



        return v;
    }
    //---------------------------------------------------------------
    private void getBundleData()
    {
        MySharedPref sp = new MySharedPref();
        String ldata = sp.getData(getActivity(), "ldata", "null");

        if(!ldata.equalsIgnoreCase("null")) {
            try {

                JSONObject jsonObject = new JSONObject(ldata);
                user_id = jsonObject.getString("id");
                email = jsonObject.getString("email");
                System.out.println("Id is***" + user_id +" "+email);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       Bundle b1 = this.getArguments();
        if (b1 != null) {

            str_user_name = b1.getString("str_user_name");
            str_category = b1.getString("str_category");
            str_degrees = b1.getString("str_degrees");
            address = b1.getString("address");
            pic_url = b1.getString("pro_pic");
            reg_id = b1.getString("reg_id");
            main_id = b1.getString("main_id");
            type = b1.getString("type");
            city = b1.getString("city");



            System.out.println("Click TableName After is%%%" + str_user_name);
            System.out.println("Click City After is%%%" + str_category);
            System.out.println("Click Name After is%%%" + str_degrees);
            System.out.println("Click RegId After is%%%" + address +"  "+pic_url +" "+reg_id+" "+main_id +" "+city);
        }

    }
//--------------------------------------------------------
    private void initUi()
    {

        progressBar = (ProgressBar)v.findViewById(R.id.progreess_bar);
        //Casting TextView for Title
        title=(TextView)getActivity().findViewById(R.id.txt_title);
        //Set TextView for Title
        title.setText("Book Appointment");
        // Slider Icon
        img_slider=(ImageView)getActivity().findViewById(R.id.img_slider);
        //Back Icon
        img_back=(ImageView)getActivity().findViewById(R.id.img_back);
        //Back Icon RelativeLayout
        rr_left=(RelativeLayout) getActivity().findViewById(R.id.rr_left);
        // Casting TextView for No Data
        txt_nodata = (TextView) v.findViewById(R.id.txt_nodata);

        img_slider.setVisibility(View.GONE);
        img_back.setVisibility(View.VISIBLE);

        //---------------------------user view------------------

        txt_lawyer_doc_name = (TextView)v.findViewById(R.id.doctore_or_lawyer_name);
        txt_specializtion = (TextView)v.findViewById(R.id.doctor_lawyer_specilization);
        txt_degrees = (TextView)v.findViewById(R.id.lawyer_doctore_degrees);

        txt_address = (TextView)v.findViewById(R.id.txt_address);

        img_profile_picture = (ImageView)v.findViewById(R.id.doctor_lawyer_pic);

        txt_lawyer_doc_name.setText(str_user_name);
        txt_specializtion.setText(str_category);
        txt_degrees.setText(str_degrees);


        if (!pic_url.equalsIgnoreCase("")) {
            String converd_url = pic_url.replace("candid-15-pc", "192.168.1.5");

            // Set ImageView for Profile Picture (Come from WebService)
            Glide.with(getActivity()).load(converd_url).into(img_profile_picture);
        }

        txt_address.setText(address);


        //-----------------------------Booking deatail view

         txt_date_of_appointment = (TextView)v.findViewById(R.id.txt_date_of_appointment);

         calendarView = (CalendarView) v.findViewById(R.id.calendar_view);

         Calendar c = Calendar.getInstance();
         calendarView.setMinDate(c.getTimeInMillis());

         txt_selected_session = (TextView)v.findViewById(R.id.txt_session);
         txt_time_of_appointment = (TextView)v.findViewById(R.id.txt_time_of_appointment);

         rr_session_select = (RelativeLayout)v.findViewById(R.id.rr_third_DoA_view);
         rr_session_selection_view = (RelativeLayout)v.findViewById(R.id.rr_session_selection);

         rr_time_of_appointment = (RelativeLayout)v.findViewById(R.id.rr_fourth_DoA_view);
         rr_appointment_for = (RelativeLayout)v.findViewById(R.id.rr_appointment_for);

        //---------------------recycler view
        frameLayout_recycle = (FrameLayout)v.findViewById(R.id.frame_container);
        txt_session_time = (TextView)v.findViewById(R.id.session_time);

        recyclerView_session_1 = (RecyclerView)v.findViewById(R.id.session_one_recyclerview);
        recyclerView_session_1.setLayoutManager(new GridLayoutManager(getActivity(), 3));



        recyclerView_session_2 = (RecyclerView)v.findViewById(R.id.session_two_recyclerview);
        recyclerView_session_2.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        recyclerView_session_3 = (RecyclerView)v.findViewById(R.id.session_three_recyclerview);
        recyclerView_session_3.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        recyclerView_for_appointment = (RecyclerView)v.findViewById(R.id.appointment_for_recycle);
        recyclerView_for_appointment.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_proceed = (Button)v.findViewById(R.id.btn_proceed);
        btn_after_check_appointments = (Button)v.findViewById(R.id.btn_after_get_appointments);
    }
//----------------------------------
    private void setOnclickMethod() {

        img_back.setOnClickListener(this);
        rr_left.setOnClickListener(this);

        txt_date_of_appointment.setOnClickListener(this);
        calendarView.setOnDateChangeListener(this);

        btn_proceed.setOnClickListener(this);

    }
 //---------------calendar click

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

        calendarView.setVisibility(View.GONE);

        setDate(dayOfMonth,month,year);

        //For get Doctor ya Lawyer session
         date = year+"-"+month+1+"-"+dayOfMonth;
         System.out.println("user choosed date "+ date);

          getSessions(date);

       // rr_session_select.setVisibility(View.VISIBLE);
       // rr_session_selection_view.setVisibility(View.VISIBLE);

    }

    private void setDate(int dayOfMonth, int month, int year)
    {
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        int monthnum=month;
        cal.set(Calendar.MONTH,monthnum);
        String month_name = month_date.format(cal.getTime());

        Log.e("",""+month_name);

        txt_date_of_appointment.setText(dayOfMonth+" "+month_name +" "+year);
        txt_date_of_appointment.setBackgroundColor(getResources().getColor(R.color.white));
    }
    //--------------------------web serverices calling-------------------------------------

    private void getSessions(final String date)
    {
        progressBar.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_GET_USER_SESSIONS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("session data", "session dau response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String msg = jObj.getString("message");

                    if (error.equals("success")) {
                     progressBar.setVisibility(View.GONE);
                        JSONArray jsonArray9 = jObj.getJSONArray("result");
                         arr_all_session_list = new ArrayList<>();
                         arr_all_session_list.clear();

                        if (jsonArray9.length()==0)
                        {
                            setupTabLayout(jsonArray9.length());
                            System.out.println("length "+ jsonArray9.length());
                        }
                        else
                        {

                            System.out.println("length "+ jsonArray9.length());

                            for (int i = 0; i < jsonArray9.length(); i++) {

                                JSONObject jsonObject = jsonArray9.getJSONObject(i);
                                String session = jsonObject.getString("session");
                                String strt_time = jsonObject.getString("starttime");
                                String end_time = jsonObject.getString("endtime");


                                arr_all_session_list.add(new sessions(session,strt_time,end_time));

                            }

                            getBooked_TimeSlots(user_id,main_id,type,date,"session1");

                          /*  for (int i=0; i < 1; i++)
                            {
                                getSession_time_slots(arr_all_session_list.get(0).getSession(),
                                        arr_all_session_list.get(0).getStart_time(),arr_all_session_list.get(0).getEnd_time());
                            }*/

                            bindWidgetsWithAnEvent();
                            setupTabLayout(jsonArray9.length());

                        }

                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("sessin List", "session List Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                if (type.equalsIgnoreCase("doctors"))
                {
                    params.put("type", "doctor");
                }
                else
                {
                    params.put("type", "lawyer");
                }
                params.put("id",main_id);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }
//-----------------------------------------------------------------------------------------------------------------------------
//api calling for get booked time slots according to start time nd end time
//################################
    private void getBooked_TimeSlots(final String user_id, final String main_id, final String type, final String date, final String session)
    {
        progressBar.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_GET_BOOKED_TIME_SLOTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("session booked time ", "session booked time : " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String msg = jObj.getString("message");

                    if (error.equals("success")) {
                        progressBar.setVisibility(View.GONE);

                        arr_all_booked_session_time = new ArrayList<>();
                        arr_all_booked_session_time.clear();

                        JSONArray jsonArray9 = jObj.getJSONArray("result");
                        for (int i = 0; i < jsonArray9.length(); i++) {
                         arr_all_booked_session_time.add(jsonArray9.getString(i));
                        }

                        if (session.equalsIgnoreCase("session1"))
                        {
                            getSession_time_slots(arr_all_session_list.get(0).getSession(),
                                    arr_all_session_list.get(0).getStart_time(),arr_all_session_list.get(0).getEnd_time());
                        }
                        else if (session.equalsIgnoreCase("session2"))
                        {
                            getSession_time_slots(arr_all_session_list.get(1).getSession(),
                                    arr_all_session_list.get(1).getStart_time(),arr_all_session_list.get(1).getEnd_time());
                        }
                        else
                        {
                            getSession_time_slots(arr_all_session_list.get(2).getSession(),
                                    arr_all_session_list.get(2).getStart_time(),arr_all_session_list.get(2).getEnd_time());
                        }


                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("sessin time List", "session time slot List Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        })
        {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                System.out.println("book_user-id "+user_id);
                System.out.println("book_user-id "+main_id);
                System.out.println("book_user-id "+type);
                System.out.println("book_user-id "+date);
                System.out.println("book_user-id "+session);


               // params.put("user_id", user_id);
                params.put("id",main_id);
                if (type.equalsIgnoreCase("doctors"))
                {
                    params.put("type", "doctor");
                }
                else
                {
                    params.put("type", "lawyer");
                }
                params.put("appointment_date",date);
                params.put("s_session", session);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }

    //------------------------------------------------------------------------------------------
  //api calling for get time slots according to start time nd end time
  //################################
    private void getSession_time_slots(final String session, final String start_time, final String end_time)
    {
        progressBar.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_GET_USER_SESSIONS_TIME_SLOTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("session time slot ", "session time slots: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String msg = jObj.getString("message");

                    if (error.equals("success")) {
                        progressBar.setVisibility(View.GONE);

                        arrayList_time_slots = new ArrayList<>();
                        arrayList_time_slots.clear();

                        JSONArray jsonArray9 = jObj.getJSONArray("result");
                        for (int i = 0; i < jsonArray9.length(); i++) {
                            String time = String.valueOf(jsonArray9.get(i));
                            arrayList_time_slots.add(new timeslots(time));
                        }

                        adapter = new MyRecyclerViewAdapter1(getActivity(), arrayList_time_slots , session, arr_all_booked_session_time);
                           //adapter.setClickListener(this);
                        if (session.equalsIgnoreCase("1"))
                        {
                            recyclerView_session_1.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            txt_session_time.setText(msg);
                        }
                        else if (session.equalsIgnoreCase("2"))
                        {
                            recyclerView_session_2.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            txt_session_time.setText(msg);
                        }
                        else
                        {
                            recyclerView_session_3.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            txt_session_time.setText(msg);
                        }

                        rr_session_select.setVisibility(View.VISIBLE);
                        rr_session_selection_view.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("sessin time List", "session time slot List Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        })
        {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("s_start_time", start_time);
                params.put("s_end_time",end_time);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }

    //--------------------------web serverices calling for get services-------------------------------------

    private void getAppoinement_For_services()
    {
        progressBar.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_GET_APPOINTMENT_FOR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("session appointment for", "session appointment response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String msg = jObj.getString("message");

                    if (error.equals("success")) {
                        progressBar.setVisibility(View.GONE);
                        arr_all_services_list = new ArrayList<>();
                        arr_all_services_list.clear();

                        JSONArray jsonArray9 = jObj.getJSONArray("result");
                        if (jsonArray9.length()==0)
                        {

                        }
                        else
                        {
                            System.out.println("length "+ jsonArray9.length());

                            for (int i = 0; i < jsonArray9.length(); i++) {

                                JSONObject jsonObject = jsonArray9.getJSONObject(i);
                                String service_id = jsonObject.getString("id");
                                String services_name = jsonObject.getString("services_name");

                                arr_all_services_list.add(new services(service_id,services_name));

                            }

                            appointment_services_adapter = new Appoinrment_services_adapter(getActivity(),arr_all_services_list);
                            recyclerView_for_appointment.setAdapter(appointment_services_adapter);
                            appointment_services_adapter.notifyDataSetChanged();

                        }

                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("appoinment  List", "session List Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                System.out.println("params "+ type +" "+ reg_id);
                params.put("table",type);
                params.put("regid",reg_id);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }

    //----------------------------------calling api for create appointments-----------------------------------

    private void creat_appointments()
    {
        progressBar.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_CREATE_APPOINTMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("create appointment for", "create appointment response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String msg = jObj.getString("message");

                    if (error.equals("success")) {
                        progressBar.setVisibility(View.GONE);
                        getActivity().getSupportFragmentManager().popBackStack();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("appoinment  List", "session List Error: " + error.getMessage());
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                System.out.println("params "+ user_id
                        +" "+ email
                         +" "+type
                        +" "+main_id
                        +" "+reg_id
                        +" "+city
                        +" "+service_name);

                params.put("user_id",user_id);
                params.put("email",email);
                if (type.equalsIgnoreCase("doctors"))
                {
                    params.put("type","doctor");
                }
                else
                {
                    params.put("type", "lawyer");
                }
                params.put("name",txt_lawyer_doc_name.getText().toString());

                if (type.equalsIgnoreCase("doctors"))
                {
                    params.put("doctor_id",main_id);
                }
                else
                {
                    params.put("lawyer_id", main_id);
                }
                params.put("reg_id",reg_id);
                params.put("city",city);
                params.put("appointment_date",date);
                params.put("session_schedule",txt_selected_session.getText().toString().trim());
                params.put("appointment_time",txt_time_of_appointment.getText().toString().trim());
                params.put("appointment_for",service_name);
                params.put("location",txt_address.getText().toString().trim());
                params.put("app_id","0");

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }

    //----------------------method for run click events
    @Override
    public void onClick(View v) {

        if(v==img_back)
        {
            img_back.setVisibility(View.GONE);
            img_slider.setVisibility(View.VISIBLE);
            if(getActivity()!=null) {

                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    Intent ii = new Intent(getActivity(), MainActivity.class);
                    startActivity(ii);
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        }

        if(v==rr_left)
        {
            img_back.setVisibility(View.GONE);
            img_slider.setVisibility(View.VISIBLE);
            if(getActivity()!=null) {

                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    Intent ii = new Intent(getActivity(), MainActivity.class);
                    startActivity(ii);
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        }

        if(v==txt_date_of_appointment)
        {
            calendarView.setVisibility(View.VISIBLE);

            rr_session_select.setVisibility(View.GONE);
            rr_session_selection_view.setVisibility(View.GONE);
            rr_time_of_appointment.setVisibility(View.GONE);

            txt_selected_session.setText("");
        }

        if (v==btn_proceed)
        {
            if (recyclerView_session_1.getVisibility()==View.VISIBLE)
            {
                if (!recycle_one_value.equalsIgnoreCase(""))
                {
                    rr_session_selection_view.setVisibility(View.GONE);

                    txt_selected_session.setText("session1");

                    rr_time_of_appointment.setVisibility(View.VISIBLE);
                    txt_time_of_appointment.setText(recycle_one_value);

                    rr_appointment_for.setVisibility(View.VISIBLE);

                    getAppoinement_For_services();
                }
                else
                {
                    Toast.makeText(getActivity(), "Select Suitable Session", Toast.LENGTH_SHORT).show();
                }
            }

            else if (recyclerView_session_2.getVisibility()==View.VISIBLE)
            {
                if (!recycle_two_value.equalsIgnoreCase(""))
                {
                    rr_session_selection_view.setVisibility(View.GONE);

                    txt_selected_session.setText("Session 2");
                    rr_time_of_appointment.setVisibility(View.VISIBLE);
                    txt_time_of_appointment.setText(recycle_two_value);

                    rr_appointment_for.setVisibility(View.VISIBLE);
                    getAppoinement_For_services();

                }
                else
                {
                    Toast.makeText(getActivity(), "Select Suitable Session", Toast.LENGTH_SHORT).show();
                }
            }
            else if (recyclerView_session_3.getVisibility()==View.VISIBLE)
            {
                if (!recycle_three_value.equalsIgnoreCase(""))
                {
                        rr_session_selection_view.setVisibility(View.GONE);

                        txt_selected_session.setText("session3");
                        rr_time_of_appointment.setVisibility(View.VISIBLE);
                        txt_time_of_appointment.setText(recycle_three_value);

                        rr_appointment_for.setVisibility(View.VISIBLE);

                        getAppoinement_For_services();

                }
                else
                {
                    Toast.makeText(getActivity(), "Select Suitable Session", Toast.LENGTH_SHORT).show();
                }
            }
        }

        btn_after_check_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (arr_all_services_list != null) {
                        service_name="";
                        for (int j = 0; j < arr_all_services_list.size(); j++)
                        {
                            if (arr_all_services_list.get(j).isSelected()) {

                                System.out.println("Exception is###" + arr_all_services_list.get(j).isSelected());

                                if (!arr_all_services_list.get(j).getService_name().equalsIgnoreCase("null"))
                                {
                                    service_name += arr_all_services_list.get(j).getService_name()+",";
                                }

                            }
                        }

                        if (arr_all_services_list.size()==0)
                        {
                            System.out.println("46#########333---- "+service_name);
                            //select_area_local_relative_view.setVisibility(View.GONE);
                        }
                        System.out.println("46#########333---- "+service_name);
                    }

                    creat_appointments();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }



//------------------------------------onclick item--------------------------------------------------------------------

    private void getAllWidgets() {
        allTabs = (TabLayout)v.findViewById(R.id.tabs);
    }

    private void bindWidgetsWithAnEvent()
    {
        allTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupTabLayout(int length) {
        System.out.println("sessions length "+ length);
        allTabs.removeAllTabs();

        if (length==1)
        {
            allTabs.addTab(allTabs.newTab().setText("Session 1"),true);
            allTabs.addTab(allTabs.newTab().setText("Session 2"));
        }
        else if (length==2)
        {
            allTabs.addTab(allTabs.newTab().setText("Session 1"),true);
            allTabs.addTab(allTabs.newTab().setText("Session 2"));
        }
        else
        {
            allTabs.addTab(allTabs.newTab().setText("Session 1"));
            allTabs.addTab(allTabs.newTab().setText("Session 2"));
            allTabs.addTab(allTabs.newTab().setText("Session 3"));
        }
    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :

                for (int i=0; i < 1; i++)
                {
                    txt_session_time.setText( arr_all_session_list.get(0).getStart_time()+" to "+arr_all_session_list.get(0).getEnd_time());
                }
                recyclerView_session_1.setVisibility(View.VISIBLE);
                recyclerView_session_3.setVisibility(View.GONE);
                recyclerView_session_2.setVisibility(View.GONE);
                break;
            case 1 :
                for (int i=0; i < 1; i++)
                {
                    getBooked_TimeSlots(user_id,main_id,type,date,"session2");
                }
                recyclerView_session_1.setVisibility(View.GONE);
                recyclerView_session_3.setVisibility(View.GONE);
                recyclerView_session_2.setVisibility(View.VISIBLE);
                break;
            case 2 :
                for (int i=0; i < 1; i++)
                {
                    getBooked_TimeSlots(user_id,main_id,type,date,"session3");
                }
                recyclerView_session_1.setVisibility(View.GONE);
                recyclerView_session_2.setVisibility(View.GONE);
                recyclerView_session_3.setVisibility(View.VISIBLE);
                break;
        }
    }

//-------------------Adapter for RecyclerView session one---------------------------------
    private class MyRecyclerViewAdapter1 extends RecyclerView.Adapter<MyRecyclerViewAdapter1.ViewHolder>{

     private ArrayList<timeslots> mData;
     private ArrayList<String> mbooked_time;

    private LayoutInflater mInflater;
   //  private ItemClickListener mClickListener;
     int pos = -1;
     String session_value="";
     ArrayList<String> value;

    public MyRecyclerViewAdapter1(Context context, ArrayList<timeslots> data, String session, ArrayList<String> arr_all_booked_session_time) {
           this.mInflater = LayoutInflater.from(context);
           this.mData = data;
           this.mbooked_time = arr_all_booked_session_time;
           session_value = session;
           value = new ArrayList<>();


           if (mbooked_time!=null)
           {
               for (int i=0; i< mbooked_time.size(); i++)
               {
                  value.add(mbooked_time.get(i));
               }
           }
            System.out.println("value of size of booked time "+ value.size());


        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item_session_one, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.myTextView.setText(mData.get(position).getTime());

        if(pos == position)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.itemView.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
            }
            if (session_value=="1")
            {
                recycle_one_value = mData.get(position).getTime();
            }
            else if (session_value=="2")
            {
                recycle_two_value = mData.get(position).getTime();
            }
            else
            {
                recycle_three_value = mData.get(position).getTime();
            }
        }
        else
        {

        try{


            System.out.println("value of time "+ mData.get(position).getTime());
            System.out.println("value of time "+ value.size());

            /* if (mData.get(position).getTime().equalsIgnoreCase(value+" "))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_gray));
                }
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_blue));
                }
            }


*/
            if (value!=null && value.size()!=0)
            {


            for (int i = 0; i< mData.size(); i++)
            {
                //System.out.println("value of i "+ i);

                for (int j =0; j< value.size();j++)
                {
                  //  System.out.println("value of j "+ j);
                    String v = value.get(j);

                    if (mData.get(position).getTime().equalsIgnoreCase(v))
                    {
                        System.out.println("value of positions "+ mData.get(i).getTime()+" gfh "+value.get(j));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            holder.myTextView.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_gray));
                            holder.myTextView.setEnabled(false);
                            holder.myTextView.setFocusable(false);

                        }
                    }
                    else
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_blue));
                        }
                    }
                }
            }
        }
        else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_blue));
                }
            }
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_blue));
            }*/

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (pos != position && NumberOfCheckItem==1) {
                    Toast.makeText(getActivity(), "Only one appointment can select.", Toast.LENGTH_SHORT).show();
                }
                else */
              /* if (position == pos)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_blue));
                    }
                    recycle_one_value="";
                    pos = -1;
                    NumberOfCheckItem = 0;
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
                    }
                    recycle_one_value = data[position];
                    pos = position;
                   NumberOfCheckItem = 1;
                }*/


             /* if (!mData.get(0).getTime().equalsIgnoreCase(value.get(0)))
                {
                    pos = position;
                    System.out.println("position "+ position);
                    System.out.println("previous "+ pos);
                    notifyDataSetChanged();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
                   }
                }
                    else
                    {
                        Toast.makeText(getActivity(), "booked", Toast.LENGTH_SHORT).show();
                    }
*/
               /* for (int i = 0; i< mData.size(); i++)
                {
                    //System.out.println("value of i "+ i);

                    for (int j =0; j< value.size();j++)
                    {
                        //  System.out.println("value of j "+ j);
                        String vi = value.get(j);

                    }
                }
*/
               if (value!=null && value.size()!=0)
               {
                   for (String n: value) {
                       if (mData.get(position).getTime().contains(n)) {

                           Toast.makeText(getActivity(), "booked"+ n, Toast.LENGTH_SHORT).show();
                           notifyDataSetChanged();
                           break;
                       }
                       else
                       {
                           pos = position;
                           System.out.println("position "+ position);
                           System.out.println("previous "+ pos);
                           notifyDataSetChanged();

                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                               holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
                           }
                       }
                   }
               }
               else
               {
                   pos = position;
                   System.out.println("position "+ position);
                   System.out.println("previous "+ pos);
                   notifyDataSetChanged();

                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                       holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
                   }
               }


              /*  if (mData.get(position).getTime().equalsIgnoreCase(vi))
                {
                    Toast.makeText(getActivity(), "booked", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pos = position;
                    System.out.println("position "+ position);
                    System.out.println("previous "+ pos);
                    notifyDataSetChanged();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangular_corner_red));
                    }
                }
*/

            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll);
            myTextView = itemView.findViewById(R.id.info_text);

        }
    }
}
//--------------------------------------Adapter for book appointment---------------------------
    private class Appoinrment_services_adapter extends RecyclerView.Adapter<Appoinrment_services_adapter.ViewHolder>{

        private Context context;
        ArrayList<services> get_all_services;
        View v;
        LayoutInflater inflater;

        public Appoinrment_services_adapter(FragmentActivity activity, ArrayList<services> arr_all_services_list) {

            this.context = activity;
            inflater = LayoutInflater.from(activity);
            get_all_services = arr_all_services_list;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = inflater.inflate(R.layout.adapter_appointment_for,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(arr_all_services_list.get(position).getService_name());
        if(position==0)
        {
            holder.checkBox.setVisibility(View.GONE);
        }


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("service name + "+ arr_all_services_list.get(position).getService_name() + isChecked);
                    arr_all_services_list.get(position).setSelected(true);
                }
                else
                {
                    arr_all_services_list.get(position).setSelected(false);
                    System.out.println("service name - "+ arr_all_services_list.get(position).getService_name() + isChecked);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr_all_services_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.chk_left_checked);
            textView = itemView.findViewById(R.id.txt_area);
        }
    }
}

    //---------------------------------------------------------------------------------------
}
