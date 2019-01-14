package com.example.candid_20.kindlycheckapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.activity.LoginSignupActivity;
import com.example.candid_20.kindlycheckapp.activity.MainActivity;
import com.example.candid_20.kindlycheckapp.adapter.CityListAdapter;
import com.example.candid_20.kindlycheckapp.adapter.QuestionListAdapter;
import com.example.candid_20.kindlycheckapp.bean.For_SetData_ProfessionalDetails;
import com.example.candid_20.kindlycheckapp.bean.for_awards_recognization.For_Awards_Recognization_Year;
import com.example.candid_20.kindlycheckapp.bean.for_service_interested.For_Service_Interested_Bean;
import com.example.candid_20.kindlycheckapp.bean.searchresult_through_categorybean.Searchresult_Through_Category_LxDetailBean;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.customgrid.MyGridView;

import com.example.candid_20.kindlycheckapp.fragment.searchthroughname.Serach_Through_Name_ImageSlide;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.storage.MySharedPref;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search_Through_Name extends Fragment implements View.OnClickListener {

    View v, v_professionaldetails, v_userrewies;
    Bundle b1;
    LinearLayoutManager mLayoutManager1,mLayoutManager2;

    String str_table_name, str_city_name, str_first_name, str_encoderegid,write_ratingreviews_data,rate_experience,write_genuine,
            why_writing_reviews,wnt_recommendation;

    ArrayList<For_Awards_Recognization_Year> for_awards_recognization_years;
    LinearLayout linear_listview2;

    RelativeLayout rr_img_arrow_down, rr_img_arrow_up;
    ImageView img_arrow_down, img_arrow_up, img_coverphoto, img_profilepicture, img_arrow_downn, img_arrow_upp;

    RelativeLayout rr_loadmore_show, rr_txt_fulldescp, rr_loadmore, rr_loadmore_up, rr_center_sendenquriy,rr_write_read_reviews,rr_write_A_reviews;
    TextView txt_fulldescp, txt_name, txt_descp, txt_educ, txt_num_ratng, txt_profilecompletionpercent, txt_userreviews, grid_text_pracexperience, grid_text_confees, grid_texttiming, grid_text_web, grid_text_phonenum, grid_text_mobnum, grid_text_knownlanguage, grid_text_clinikaddress, grid_text_address, txt_like,
            txt_professionaldetails, txt_userrewies, txt_membershipandassocisation_descp1, txt_verification_descp1,
            grid_text_companyclass, grid_text_companycatogory, grid_text_companysubcatogory, grid_text_numofemployees, grid_text_annualturnover, grid_text_cindustries,
            grid_text_cname,txt_userrecommended,txt_allrating,txt_want_recommended,txt_gallary_no;
    ProgressBar loader, loader1;
    RatingBar rb_adpshop_ratingg,rb_allrating;
    Button btn_loadmore, btn_loadmore_up;
    ArrayList<String> arr_strt_time, arr_end_time, arr_image_name, arr_qualification, arr_qualification1,
            arr_languages, arr_consultating_type, arr_consultating_minutes,
            arr_counsultating_fees, arr_recognization_title, arr_awards_year,
            arr_branches_city, arr_branches_state, arr_branches_pincode,
            arr_branches_address, arr_branch_phone_number,
            arr_mangement_designstion, arr_mangement_title, arr_management_name,
    arr_all_reviews_list,arr_all_rating_list,arr_all_email_list,arr_all_name_list,arr_all_question_list;
    MyGridView grid, grid_companybranches;

    TextView edt_yourname_err, edt_yourmobile_err, edt_youremail_err, edt_yourservice_err;

    For_Service_Interested_Bean for_service_interested_bean;


    LinearLayout ll_professionaldetails, ll_userrewies, rr_confees, rr_knownlanguage,
            ll_rr_awrd_recognization, ll_rr_educationprofessionalqualification,
            ll_mangement_details, rr_clinikaddress, rr_cindustries,rr_address,rr_cname;
    RelativeLayout rr_professionaldetails, rr_user_reviews, rr_educ, layoutdialog3, rr_companybranches,rr_loadmore_reviewrating;
    String styledText_address, str_service_interested,str_why_writing_reviews,str_want_recommended;
    ScrollView svRecord1;
    TextView txt_nodata;
    Dialog dialog;

    RecyclerView InboxDetailRV,InboxDetailRVv;
    EditText edt_yourname, edt_mobilenumber, edt_email,edt_write_genuine_reviews;
    Spinner edt_service_interested,spn_why_writing_reviews,spn_want_recommended;

    String  nameee,mobile_number,str_rating_experience,address_line2,profile_picture,cover_photo,

    reg_id,main_id;

        RelativeLayout rr_main_corporate,rr_for_doctorelawyers,rr_left_call;
    Animation animSlideUp,animSlideDown;

    ImageView img_slider,img_back;

    LinearLayout ll_reviewandrating_blue,ll_reviewandrating_white,ll_writereviews_blue,ll_writereviews_white;
    View v_numreviewsrating,v_writereviews;
    Button btn_submit_reviews,btn_loadmore_reviewrating;

    String user_id,user_name,token;

    QuestionListAdapter questionListAdapter;
    RatingBar rb_rate_your_experience;
    TextView txt_rate_your_experience_error,txt_write_genuine_reviews_error,title;
    int l=0;
    int c;
    int b=1;
    int f;
    CustomRe_ReviewandRating_Loadmore customRe_reviewandRating_loadmore;
    RelativeLayout rr_left;

    public static Search_Through_Name newInstance() {
        Search_Through_Name fragment = new Search_Through_Name();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.search_through_name, container, false);
        getBundleData();
        initUi();

        return v;
    }

    private void Submit_review()
    {
        try
        {
            if(b1!=null && !b1.equals("null")) {
                System.out.println("Writing Data is%%%"+write_ratingreviews_data);
                if (write_ratingreviews_data.equalsIgnoreCase("write_ratingreviews_data") &&
                        !write_ratingreviews_data.equalsIgnoreCase("null")) {

                    callWrite_Reviews_Ws(user_id, str_encoderegid, user_name, str_table_name, str_city_name, rate_experience,
                            write_genuine, why_writing_reviews, wnt_recommendation,"1");
                }

            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }



    private void initUi() {
        // ---------------------------- For Slider and Back ImageView for Show and  Hide-------------------------------------------------------------------------------//
 // Slider Icon
        img_slider=(ImageView)getActivity().findViewById(R.id.img_slider);
        //Back Icon
        img_back=(ImageView)getActivity().findViewById(R.id.img_back);
        //Back Icon RelativeLayout
        rr_left=(RelativeLayout) getActivity().findViewById(R.id.rr_left);
        // Casting TextView for No Data
        txt_nodata = (TextView) v.findViewById(R.id.txt_nodata);

        // Casting ScrollView for No Data
        svRecord1 = (ScrollView) v.findViewById(R.id.svRecord1);
        // Casting RatingBar for set Rating
        rb_adpshop_ratingg = (RatingBar) v.findViewById(R.id.rb_adpshop_ratingg);

        // Casting MyGridview for Gallery
        grid = (MyGridView) v.findViewById(R.id.grid);
        // Casting TextView for Gallery No Images
        txt_gallary_no=(TextView)v.findViewById(R.id.txt_gallary_no);


        // Casting ImageView About Show and Hide
        img_arrow_down = (ImageView) v.findViewById(R.id.img_arrow_down);
        img_arrow_up = (ImageView) v.findViewById(R.id.img_arrow_up);

        // Casting RelativeLayout for Call
        rr_left_call = (RelativeLayout) v.findViewById(R.id.rr_left_call);

        // Casting RelativeLayout About Show and Hide
        rr_img_arrow_up = (RelativeLayout) v.findViewById(R.id.rr_img_arrow_up);
        rr_img_arrow_down = (RelativeLayout) v.findViewById(R.id.rr_img_arrow_down);

        // Casting ImageView for Cover Photo
        img_coverphoto = (ImageView) v.findViewById(R.id.img_coverphoto);

        // Casting ImageView for Profile Picture
        img_profilepicture = (ImageView) v.findViewById(R.id.img_profilepicture);

        // Casting RelativeLayout  Show  About in full way
        rr_txt_fulldescp = (RelativeLayout) v.findViewById(R.id.rr_txt_fulldescp);

        // Casting RelativeLayout  for Load More
        rr_loadmore = (RelativeLayout) v.findViewById(R.id.rr_loadmore);

        // Casting RelativeLayout  for Hide
        rr_loadmore_up = (RelativeLayout) v.findViewById(R.id.rr_loadmore_up);
        // Casting RelativeLayout  for Send Enquiry
        rr_center_sendenquriy = (RelativeLayout) v.findViewById(R.id.rr_center_sendenquriy);

        // Casting RelativeLayout  for Show loaded Data
        rr_loadmore_show = (RelativeLayout) v.findViewById(R.id.rr_loadmore_show);

        // Casting TextView  Show  About in full way
        txt_fulldescp = (TextView) v.findViewById(R.id.txt_fulldescp);

        // Casting TextView  Practicing Experience
        grid_text_pracexperience = (TextView) v.findViewById(R.id.grid_text_pracexperience);
        // Casting TextView Consultancy Fees
        grid_text_confees = (TextView) v.findViewById(R.id.grid_text_confees);
        // Casting TextView  Timing
        grid_texttiming = (TextView) v.findViewById(R.id.grid_texttiming);
        // Casting TextView  Web
        grid_text_web = (TextView) v.findViewById(R.id.grid_text_web);
        // Casting TextView  Phone Number
        grid_text_phonenum = (TextView) v.findViewById(R.id.grid_text_phonenum);
        // Casting TextView  Mobile Number
        grid_text_mobnum = (TextView) v.findViewById(R.id.grid_text_mobnum);
        // Casting TextView  Known Language
        grid_text_knownlanguage = (TextView) v.findViewById(R.id.grid_text_knownlanguage);
        // Casting TextView  Clinik Address
        grid_text_clinikaddress = (TextView) v.findViewById(R.id.grid_text_clinikaddress);
        // Casting TextView  Address
        grid_text_address = (TextView) v.findViewById(R.id.grid_text_address);


        // Casting TextView  For Name
        txt_name = (TextView) v.findViewById(R.id.txt_name);

        // Casting TextView  For Education
        txt_educ = (TextView) v.findViewById(R.id.txt_educ);

        // Casting TextView  For User Reviews
        txt_userreviews = (TextView) v.findViewById(R.id.txt_userreviews);

        // Casting TextView  For User Recommandation
        txt_like = (TextView) v.findViewById(R.id.txt_like);
        // Casting TextView  For Text User Recommandation

        txt_userrecommended=(TextView) v.findViewById(R.id.txt_userrecommended);

        // Casting TextView  For Number of Rating
        txt_num_ratng = (TextView) v.findViewById(R.id.txt_num_ratng);

        // Casting TextView  For Profile Complete
        txt_profilecompletionpercent = (TextView) v.findViewById(R.id.txt_profilecompletionpercent);
        // Casting Button  For Load More
        btn_loadmore = (Button) v.findViewById(R.id.btn_loadmore);
        // Casting Button  For Hide
        btn_loadmore_up = (Button) v.findViewById(R.id.btn_loadmore_up);
        // Casting Button  For Review and Rating
        btn_loadmore_reviewrating=(Button)v.findViewById(R.id.btn_loadmore_reviewrating);
        // Casting LinearLayout  For Clinik Address
        rr_clinikaddress = (LinearLayout) v.findViewById(R.id.rr_clinikaddress);
        rr_address= (LinearLayout) v.findViewById(R.id.rr_address);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Serach_Through_Name_ImageSlide selectedFragment = Serach_Through_Name_ImageSlide.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
Bundle b=new Bundle();
                b.putString("str_encoderegid",str_encoderegid);
if(!nameee.equalsIgnoreCase(""))
{
    b.putString("str_first_name",nameee);
}


                selectedFragment.setArguments(b);
                transaction.replace(R.id.contentFrame, selectedFragment);
                transaction.addToBackStack("9");
                transaction.commit();
            }
        });

        // Casting TextView  For Conatct Name
        grid_text_cname = (TextView) v.findViewById(R.id.grid_text_cname);
        // ---------------------------- For Corporate -------------------------------------------------------------------------------//
        // Casting RelativeLayout  For Doctor and Lawyer
        rr_for_doctorelawyers=(RelativeLayout)v.findViewById(R.id.rr_for_doctorelawyers);
        // ---------------------------- For Corporate -------------------------------------------------------------------------------//
        // Casting RelativeLayout  For Corporate Show

        rr_main_corporate=(RelativeLayout)v.findViewById(R.id.rr_main_corporate);
        // Casting RelativeLayout  For Company Branches
        rr_companybranches = (RelativeLayout) v.findViewById(R.id.rr_main_companybranches);
        // Casting MyGridView  For Company Branches
        grid_companybranches = (MyGridView) v.findViewById(R.id.grid_companybranches);
        // Casting TextView  For Company Classess
        grid_text_companyclass = (TextView) v.findViewById(R.id.grid_text_companyclass);
        // Casting TextView  For Company Category
        grid_text_companycatogory = (TextView) v.findViewById(R.id.grid_text_companycatogory);

        // Casting TextView  For Company SubCategory
        grid_text_companysubcatogory = (TextView) v.findViewById(R.id.grid_text_companysubcatogory);

        // Casting TextView  For Company Number Of Employees
        grid_text_numofemployees = (TextView) v.findViewById(R.id.grid_text_numofemployees);

        // Casting TextView  For Company Annual Turn over
        grid_text_annualturnover = (TextView) v.findViewById(R.id.grid_text_annualturnover);
        // Casting TextView  For Company Industry
        grid_text_cindustries = (TextView) v.findViewById(R.id.grid_text_cindustries);
        // Casting LinearLayout  For Management Details
        ll_mangement_details = (LinearLayout) v.findViewById(R.id.ll_mangement_details);

        //Casting LinearLayout  For Contact Name
        rr_cname=(LinearLayout)v.findViewById(R.id.rr_cname);

        // Casting LinearLayout  For  Company Industry
        rr_cindustries = (LinearLayout) v.findViewById(R.id.rr_cindustries);

        //Casting  RecyclerView ----//
        InboxDetailRV = (RecyclerView) v.findViewById(R.id.InboxDetailRV);


        InboxDetailRV.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(getActivity());
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        InboxDetailRV.setLayoutManager(mLayoutManager1);


// ---------------------------- For Read and Write Reviews Reviews -------------------------------------------------------------------------------//
        rr_write_read_reviews=(RelativeLayout)v.findViewById(R.id.rr_write_read_reviews);
        rr_write_A_reviews=(RelativeLayout)v.findViewById(R.id.rr_write_A_reviews);
//Casting LinearLayout For Read Reviews
        ll_reviewandrating_blue=(LinearLayout)v.findViewById(R.id.ll_reviewandrating_blue);
        ll_reviewandrating_white=(LinearLayout)v.findViewById(R.id.ll_reviewandrating_white);

//Casting LinearLayout For Write Reviews
        ll_writereviews_blue=(LinearLayout)v.findViewById(R.id.ll_writereviews_blue);
        ll_writereviews_white=(LinearLayout)v.findViewById(R.id.ll_writereviews_white);

       /* // Casting View for Read and Write Reviews
        v_numreviewsrating=(View)v.findViewById(R.id.v_numreviewsrating);
        v_writereviews=(View)v.findViewById(R.id.v_writereviews);*/
        btn_submit_reviews=(Button)v.findViewById(R.id.btn_submit_reviews);

//Casting TextView for Set All Rating
        txt_allrating=(TextView)v.findViewById(R.id.txt_allratingg);

       //Rating bar for All Ratings
        rb_allrating=(RatingBar)v.findViewById(R.id.rb_allratingg);

        //RelativeLayout Load More for more view Review and ratings
        rr_loadmore_reviewrating=(RelativeLayout)v.findViewById(R.id.rr_loadmore_reviewrating);

        //Casting  RecyclerView ----//
        InboxDetailRVv = (RecyclerView) v.findViewById(R.id.InboxDetailRVv);



        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        InboxDetailRVv.setLayoutManager(mLayoutManager2);
        InboxDetailRVv.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        InboxDetailRVv.setNestedScrollingEnabled(false);

        InboxDetailRVv.setItemAnimator(new DefaultItemAnimator());
        InboxDetailRVv.smoothScrollToPosition(0);

        InboxDetailRVv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // disallow scrollview to intercept touch events
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        // allow scrollview to intercept touch events
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });

      // TextView For Write Genuine Reviews Error
        txt_write_genuine_reviews_error=(TextView)v.findViewById(R.id.txt_write_genuine_reviews_error);
// For Rating Error
        txt_rate_your_experience_error=(TextView)v.findViewById(R.id.txt_rate_your_experience_error);


        // EditText for Genuine Reviews
        edt_write_genuine_reviews=(EditText)v.findViewById(R.id.edt_write_genuine_reviews);
        // Spinner for Want Recommended
        spn_want_recommended=(Spinner)v.findViewById(R.id.spn_want_recommended);

        // Set Spinner
        final List<String> list = new ArrayList<String>();
        list.add("Yes");
        list.add("No");
        spn_want_recommended.setPrompt("Select");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_want_recommended.setAdapter(dataAdapter);

        // Spinner ClickListerner

        //------------------------------------ Spinner Want Recommended ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        spn_want_recommended.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_want_recommended = list.get(position);
                /* s_city_id=for_all_city_list_bean.getResult().get(position).getCityId();*/



                /*
                sp.saveData(getApplicationContext(),"city_name",city_name);
                System.out.println("city**************"+city_name);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
          // RatingBar for Rate your Experience
         rb_rate_your_experience=(RatingBar)v.findViewById(R.id.rb_rate_your_experience);
          //  str_rating_experience="1";
          //if rating value is changed,
         //display the current rating value in the result (textview) automatically
        rb_rate_your_experience.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
        str_rating_experience=String.valueOf(rating);

            }
        });
       // Spinner for Why writing Reviews
        spn_why_writing_reviews=(Spinner)v.findViewById(R.id.spn_why_writing_reviews);



        txt_want_recommended=(TextView)v.findViewById(R.id.txt_want_recommended);

        callget_AllQuestions_Ws();

        //------------------------------------ Spinner Questions ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        spn_why_writing_reviews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_why_writing_reviews = arr_all_question_list.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

// ---------------------------- For Awards and Recognization -------------------------------------------------------------------------------//
        // Casting LinearLayout  For Awards and Recognization
        ll_rr_awrd_recognization = (LinearLayout) v.findViewById(R.id.ll_rr_awrd_recognization);
        // ---------------------------- For MemberShip -------------------------------------------------------------------------------//
        // Casting TextView  For MemberShip
        txt_membershipandassocisation_descp1 = (TextView) v.findViewById(R.id.txt_membershipandassocisation_descp1);
// ---------------------------- For Verification -------------------------------------------------------------------------------//
        // Casting TextView  For Verfication
        txt_verification_descp1 = (TextView) v.findViewById(R.id.txt_verification_descp1);

// ---------------------------- For Education and  -------------------------------------------------------------------------------//
        // Casting TextView  For Verfication
        ll_rr_educationprofessionalqualification = (LinearLayout) v.findViewById(R.id.ll_rr_educationprofessionalqualification);

// ---------------------------- For Business,Hide this button -------------------------------------------------------------------------------//
        // Casting RelativeLayout  for Hide
        layoutdialog3 = (RelativeLayout) v.findViewById(R.id.layoutdialog3);
// ---------------------------- For Tab Show -------------------------------------------------------------------------------//
        // Casting LinearLayout  For Professional Details
        ll_professionaldetails = (LinearLayout) v.findViewById(R.id.ll_professionaldetails);
        // Casting LinearLayout  For UserRewies
        ll_userrewies = (LinearLayout) v.findViewById(R.id.ll_userrewies);
        // Casting RelativeLayout  For Professional Details
        rr_professionaldetails = (RelativeLayout) v.findViewById(R.id.rr_professionaldetails);
        // Casting RelativeLayout  For UserRewies
        rr_user_reviews = (RelativeLayout) v.findViewById(R.id.rr_reviews_rating);

        // Casting LinearLayout  for hide Languages fields in Business category
        rr_knownlanguage = (LinearLayout) v.findViewById(R.id.rr_knownlanguage);
        // Casting LinearLayout  for hide Fees fields in Business category
        rr_confees = (LinearLayout) v.findViewById(R.id.rr_confees);
        // Casting RelativeLayout  for hide Education fields in Business category
        rr_educ = (RelativeLayout) v.findViewById(R.id.rr_educ);

        // Casting TextView  For Professional Details
        txt_professionaldetails = (TextView) v.findViewById(R.id.txt_professionaldetails);
        // Casting TextView  For UserRewies
        txt_userrewies = (TextView) v.findViewById(R.id.txt_userrewies);

        // Casting View  For Professional Details
        v_professionaldetails = (View) v.findViewById(R.id.v_professionaldetails);
        // Casting View  For UserRewies
        v_userrewies = (View) v.findViewById(R.id.v_userrewies);

        animSlideUp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        animSlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

// ---------------------------- For Loadmore show and hide -------------------------------------------------------------------------------//
        // Casting ImageView Loadmore Show and Hide
        img_arrow_downn = (ImageView) v.findViewById(R.id.img_arrow_downn);
        img_arrow_upp = (ImageView) v.findViewById(R.id.img_arrow_upp);

        // Casting RelativeLayout  Show  About in 2 Lines
        txt_descp = (TextView) v.findViewById(R.id.txt_descp);


        // RelativeLayout Clicklister
        rr_img_arrow_down.setOnClickListener(this);
        rr_img_arrow_up.setOnClickListener(this);
        // ImageView Clicklister
        img_arrow_down.setOnClickListener(this);
        img_arrow_up.setOnClickListener(this);

        //Button Clicklister
        btn_loadmore.setOnClickListener(this);
        btn_loadmore_up.setOnClickListener(this);

        //RelativeLayout Clicklister
        img_arrow_downn.setOnClickListener(this);
        img_arrow_upp.setOnClickListener(this);

        rr_center_sendenquriy.setOnClickListener(this);

        //LinearLayout Clicklister
        ll_professionaldetails.setOnClickListener(this);
        ll_userrewies.setOnClickListener(this);

        //RelativeLayout Call Clicklister
        rr_left_call.setOnClickListener(this);
        //ImageView  Call Clicklister for Back

        img_slider.setVisibility(View.GONE);

        img_back.setVisibility(View.VISIBLE);


        img_back.setOnClickListener(this);
        rr_left.setOnClickListener(this);

        layoutdialog3.setOnClickListener(this);

        ll_reviewandrating_blue.setOnClickListener(this);
        ll_reviewandrating_white.setOnClickListener(this);
        ll_writereviews_blue.setOnClickListener(this);
        ll_writereviews_white.setOnClickListener(this);
        btn_submit_reviews.setOnClickListener(this);
        btn_loadmore_reviewrating.setOnClickListener(this);
        //Casting TextView for Title
        title=(TextView)getActivity().findViewById(R.id.txt_title);

        // Call WebService for Get SingleData
        callWebService();
    }


    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{

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

    private void callWebService() {

        if (Utils.isConnected(getActivity())) {
            getSearchThroughName();

        } else {
            Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSearchThroughName() {
        loader.setVisibility(View.VISIBLE);
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_LISTING,
                new com.android.volley.Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("SearchThroughName", "Search ThroughName: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        svRecord1.setVisibility(View.VISIBLE);
                        txt_nodata.setVisibility(View.GONE);

                        String errorMsg = jObj.getString("message");
                        if (getActivity() != null) {

                        //    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();

                        }
                        String result = jObj.getString("result");
                        JSONObject jsonObject2 = new JSONObject(result);
                        // Fetch Name from Web Service

                        reg_id = jsonObject2.getString("reg_id");
                        main_id = jsonObject2.getString("main_id");


                        nameee = jsonObject2.getString("name");
                         System.out.println("Name is%%%" + nameee);


                        String upperString_nameee = nameee.substring(0,1).toUpperCase() + nameee.substring(1);

                        // Set TextView for Name (Come from WebService)
                        txt_name.setText(upperString_nameee);


                        //Set Title
                        title.setText(upperString_nameee);

                        // Set TextView for Want Recommended (Come from WebService)
                        txt_want_recommended.setText("Do you want recommended "+nameee+"?");


                        // Fetch Average Rating from Web Service
                        String average_rating = jsonObject2.getString("average_rating");
                        System.out.println("Avarage Rating  is%%%" + average_rating);



                        // Set TextView for Average Rating (Come from WebService)
                        txt_num_ratng.setText(average_rating);

                       //All Rating
                        txt_allrating.setText("All Ratings "+average_rating);

                        rb_allrating.setRating(Float.parseFloat(average_rating));


                        // Set RatingBar  (Come from WebService)
                        rb_adpshop_ratingg.setRating(Float.parseFloat(average_rating));


                        // Fetch Number of Reviews  from Web Service
                        String no_of_reviews = jsonObject2.getString("no_of_reviews");
                        System.out.println("Number of Reviews  is%%%" + no_of_reviews);


if(!no_of_reviews.equalsIgnoreCase("")) {

    if (no_of_reviews.equalsIgnoreCase("1") || no_of_reviews.equalsIgnoreCase("0")) {

        // Set TextView  For Number of Rewies
        txt_userreviews.setText(no_of_reviews + " " + "Review");
    }

    else
    {
        // Set TextView  For Number of Rewies
        txt_userreviews.setText(no_of_reviews + " " + "Reviews");
    }
}

else
{
    // Set TextView  For Number of Rewies
    txt_userreviews.setText("No Review");
}



                        // Fetch Number of Reviews  from Web Service
                        String profile_completeness = jsonObject2.getString("profile_completeness");
                        System.out.println("Profile Complete  is%%%" + profile_completeness);

                        // Set TextView for Profile Completeness(Come from WebService)
                        txt_profilecompletionpercent.setText(profile_completeness);



                        txt_profilecompletionpercent.setOnTouchListener(new View.OnTouchListener() {
                            float lastX = 0, lastY = 0;

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case (MotionEvent.ACTION_DOWN):
                                        lastX = event.getX();
                                        lastY = event.getY();

                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        float dx = event.getX() - lastX;
                                        float dy = event.getY() - lastY;
                                        float finalX = v.getX() + dx;
                                        float finalY = v.getY() + dy + v.getHeight();
                                        v.setX(finalX);
                                        v.setY(finalY);
                                        break;
                                }
                                return true;
                            }
                        });

                        // Fetch All Reviews from Web Service
                        String review = jsonObject2.getString("review");
                        System.out.println("Reviews are%%%" + review);
                        JSONArray jsonArray9 = new JSONArray(review);

                        arr_all_reviews_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray9.length(); i++) {

                            String str_all_reviews=String.valueOf(jsonArray9.get(i));
                            if(!str_all_reviews.equalsIgnoreCase("")) {
                                arr_all_reviews_list.add(String.valueOf(jsonArray9.get(i)));
                            }
                        }

                        // Fetch All Rating from Web Service
                        String rating = jsonObject2.getString("rating");
                        System.out.println("Ratings are%%%" + rating);
                        JSONArray jsonArray10 = new JSONArray(rating);

                        arr_all_rating_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray10.length(); i++) {
                            String str_all_rating=String.valueOf(jsonArray10.get(i));
                            if(!str_all_rating.equalsIgnoreCase("")) {
                                arr_all_rating_list.add(String.valueOf(jsonArray10.get(i)));
                            }
                        }


                        // Fetch All Email from Web Service
                        String email = jsonObject2.getString("email");
                        System.out.println("Email are%%%" + email);
                        JSONArray jsonArray11 = new JSONArray(email);

                        arr_all_email_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray11.length(); i++) {
                            String str_all_emaillist=String.valueOf(jsonArray11.get(i));
                            if(!str_all_emaillist.equalsIgnoreCase("null")) {
                                arr_all_email_list.add(String.valueOf(jsonArray11.get(i)));
                            }
                        }


                        // Fetch UserName from Web Service
                        String user_name = jsonObject2.getString("user_name");
                        System.out.println("Username are%%%" + user_name);
                        JSONArray jsonArray12 = new JSONArray(user_name);

                        arr_all_name_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray12.length(); i++) {

                            String str_all_namelist=String.valueOf(jsonArray12.get(i));
                            if(!str_all_namelist.equalsIgnoreCase("null")) {
                                arr_all_name_list.add(String.valueOf(jsonArray12.get(i)));
                            }
                        }



                        if(arr_all_reviews_list!=null)
                        {
                            if (arr_all_reviews_list.size() > 0) {

                                if(arr_all_reviews_list.size()<=5) {
                                    CustomRe_ReviewandRating customRe_reviewandRating = new CustomRe_ReviewandRating(getActivity(),
                                            arr_all_reviews_list, arr_all_rating_list,
                                            arr_all_email_list, arr_all_name_list);
                                    InboxDetailRVv.setAdapter(customRe_reviewandRating);

                                }

                                if(arr_all_reviews_list.size() > 5)
                                {

                                    System.out.println("List Size is$$$"+arr_all_reviews_list.size());
                                    l=0;
                                    l = l + 5;


                                    customRe_reviewandRating_loadmore=new CustomRe_ReviewandRating_Loadmore(getActivity(),
                                            arr_all_reviews_list, arr_all_rating_list,
                                            arr_all_email_list,
                                            arr_all_name_list,l);
                                    InboxDetailRVv.setAdapter(customRe_reviewandRating_loadmore);
                                    customRe_reviewandRating_loadmore.notifyDataSetChanged();
                                    c = arr_all_reviews_list.size() / 5;
                                    f = arr_all_reviews_list.size() % 5;
                                    System.out.println("C Value is$$$" + c);


                                    rr_loadmore_reviewrating.setVisibility(View.VISIBLE);
                                    btn_loadmore_reviewrating.setVisibility(View.VISIBLE);

                                }


                            }

                        }


                        // Fetch Profile Picture from Web Service
                         profile_picture = jsonObject2.getString("profile_picture");
                         System.out.println("Profile Picture is%%%" + profile_picture);

                        if (!profile_picture.equalsIgnoreCase("")) {
                            String converd_url = profile_picture.replace("candid-15-pc", "192.168.1.5");

                            // Set ImageView for Profile Picture (Come from WebService)
                            Glide.with(getActivity()).load(converd_url).into(img_profilepicture);
                        }

                        // Fetch Cover photo from Web Service
                         cover_photo = jsonObject2.getString("cover_photo");
                         System.out.println("Cover Photo  is%%%" + cover_photo);

                        if (!cover_photo.equalsIgnoreCase("")) {
                            String converd_url = cover_photo.replace("candid-15-pc", "192.168.1.5");
                            // Set ImageView for  Cover Photo (Come from WebService)
                            try {
                                Glide.with(getActivity()).load(converd_url).override(300, 200).into(img_coverphoto);
                            }
                            catch (Exception e)
                            {
                                //e.printStackTrace();
                            }
                        }

                        // Fetch About from Web Service
                        String about = jsonObject2.getString("about");
                        System.out.println("About  is%%%" + about);

                        // Set About for Name (Come from WebService)
                        txt_descp.setText(about);
                        txt_fulldescp.setText(about);


                        // Fetch Website from Web Service
                        String website = jsonObject2.getString("website");
                        System.out.println("Website  is%%%" + website);

                        if (!website.equalsIgnoreCase("")) {
                            //Set TextView for Website (Come from WebService)
                            grid_text_web.setText(website);
                        } else {
                            grid_text_web.setText("Website Not Available");


                        }



                        // Fetch Website from Web Service
                        String user_recommendation = jsonObject2.getString("user_recommendation");
                        System.out.println("User Recommandation  is%%%" + user_recommendation);
                       // Animation  animZoomIn = AnimationUtils.loadAnimation(getActivity(),
                              //  R.anim.zoom_in);

                        // Set TextView for User Recommandation (Come from WebService)
                        txt_like.setText(user_recommendation);

//Fetch Pincode from WebService
                        String pincode = jsonObject2.getString("pincode");
                        System.out.println("Pincode  is%%%" + pincode);


                     //   txt_like.startAnimation(animZoomIn);
                      //  txt_userrecommended.startAnimation(animZoomIn);

                        // Fetch Phone Number1 from Web Service
                        String landline_number1 = jsonObject2.getString("landline_number1");
                        System.out.println("Phone Number1 is%%%" + landline_number1);
                        // Fetch Std Code1 from Web Service
                        String std_code1 = jsonObject2.getString("std_code1");
                        System.out.println("Phone Number1 is%%%" + std_code1);

                        // Fetch Phone Number2 from Web Service
                        String landline_number2 = jsonObject2.getString("landline_number2");
                        System.out.println("Phone Number2 is%%%" + landline_number2);
                        // Fetch Std Code2 from Web Service
                        String std_code2 = jsonObject2.getString("std_code2");
                        System.out.println("Std Code2  is%%%" + std_code2);

                        // Set TextView  For  Phone Number
                        if (!std_code1.equalsIgnoreCase("") || (!std_code2.equalsIgnoreCase(""))) {

                            if(!std_code2.equalsIgnoreCase("")) {
                                //Set Style for  Phone Number
                                String styledText_phonenumber = "<font color=#000000>" + std_code1 + "-" + "</font>" + landline_number1 + "," + "  " + "<font color=#000000>" + std_code2 + "-" + "</font>" + landline_number2;
                                // Set Text for Phone Number
                                grid_text_phonenum.setText(Html.fromHtml(styledText_phonenumber));
                            }

                            else
                            {
                                //Set Style for  Phone Number
                                String styledText_phonenumber = "<font color=#000000>" + std_code1 + "-" + "</font>" + landline_number1;
                                // Set Text for Phone Number
                                grid_text_phonenum.setText(Html.fromHtml(styledText_phonenumber));
                            }




                        } else {
                            grid_text_phonenum.setText("Landline Number Not Available.");
                        }
                        // Fetch Mobile Number1 from Web Service
                         mobile_number = jsonObject2.getString("mobile_number");
                        System.out.println("Mobile Number1  is%%%" + mobile_number);

                        // Fetch Mobile Number2 from Web Service
                        String mobile_number2 = jsonObject2.getString("mobile_number2");
                        System.out.println("Mobile Number2  is%%%" + mobile_number2);

                        if (!mobile_number.equalsIgnoreCase("") || (!mobile_number2.equalsIgnoreCase(""))) {
                            if(!mobile_number2.equalsIgnoreCase("")) {
                                //Set Style for  Mobile Number
                                String styledText_mobile = "<font color=#000000>" + "+91 " + "</font>" + mobile_number + ",  " + mobile_number2;
                                // Set TextView  For Mobile Number
                                grid_text_mobnum.setText(Html.fromHtml(styledText_mobile));
                            }

                            else
                            {
                                //Set Style for  Mobile Number
                                String styledText_mobile = "<font color=#000000>" + "+91 " + "</font>" + mobile_number;
                                // Set TextView  For Mobile Number
                                grid_text_mobnum.setText(Html.fromHtml(styledText_mobile));
                            }


                        } else {
                            grid_text_mobnum.setText("Mobile Number Not Available.");
                        }
                        // Fetch address1 from Web Service
                        String address_line1 = jsonObject2.getString("address_line1");
                        System.out.println("Address1  is%%%" + address_line1);
                        // Fetch address2 from Web Service
                        address_line2 = jsonObject2.getString("address_line2");
                        System.out.println("Address2  is%%%" + address_line2);

                        String first = "";
                        String second = "";

                        int length = address_line1.length();
                        String convert = String.valueOf(length);
                        System.out.println("Length! is###" + convert);

                        int length2 = address_line2.length();
                        String convert2 = String.valueOf(length2);
                        System.out.println("Length@ is###" + convert2);

                        // Fetch Landmark from Web Service

                      String  str_landmark=jsonObject2.getString("landmark");
if(!str_landmark.equalsIgnoreCase(""))
{

    String upperString_landmark = str_landmark.substring(0,1).toUpperCase() + str_landmark.substring(1);

    grid_text_address.setText(upperString_landmark);
}

else
{
    grid_text_address.setText("Landmark Not available");

}
/*


                        try {
                            StringTokenizer tokens = new StringTokenizer(address_line1, " ");

                            first = tokens.nextToken();// this will contain "Fruit"
                            second = tokens.nextToken();

                            //Set Style for  Address
                            styledText_address = "<font color=#000000>" + first + "</font>" + " " + second + ",  " + address_line2;
                            String total_date = first + "," + " " + second;
                            // Set TextView  For Address is
                            grid_text_address.setText(Html.fromHtml(styledText_address));
                        } catch (NoSuchElementException e) {

                            //Set Style for  Address
                            styledText_address = "<font color=#000000>" + address_line1 + "</font>" + ",  " + address_line2;
                            String total_date = first + "," + " " + second;
                            // Set TextView  For Address is
                            grid_text_address.setText(Html.fromHtml(styledText_address));
                        }

*/

                        // Fetch Image Array from Web Service
                        String image_name = jsonObject2.getString("image_name");
                        System.out.println("Image Name is%%%" + image_name);



                        if (!image_name.equalsIgnoreCase("")) {

                            JSONArray jsonArray4 = new JSONArray(image_name);
                            arr_image_name = new ArrayList<>();
                            for (int k = 0; k < jsonArray4.length(); k++) {
                                arr_image_name.add(String.valueOf(jsonArray4.get(k)));

                                if(arr_image_name.get(k).equalsIgnoreCase(""))
                                {
                                    txt_gallary_no.setVisibility(View.VISIBLE);
                                }

                                if (!arr_image_name.get(k).equalsIgnoreCase("")) {
                                    txt_gallary_no.setVisibility(View.GONE);

                                    grid.setExpanded(true);
                                    CustomGrid adapter = new CustomGrid(getActivity(), arr_image_name);
                                    grid.setAdapter(adapter);
                                }
                            }
                        }

                        //-------------------------- For Corporate----------------------------------------------------------------------------------//
                        if (str_table_name.equalsIgnoreCase("corporate")) {
                            rr_educ.setVisibility(View.GONE);
                            rr_confees.setVisibility(View.GONE);
                            rr_knownlanguage.setVisibility(View.GONE);
                            layoutdialog3.setVisibility(View.GONE);
                            rr_cname.setVisibility(View.VISIBLE);
                            rr_companybranches.setVisibility(View.VISIBLE);
                            rr_clinikaddress.setVisibility(View.VISIBLE);
                            rr_address.setVisibility(View.GONE);
                            rr_cindustries.setVisibility(View.VISIBLE);
                            rr_loadmore.setVisibility(View.VISIBLE);
                            btn_loadmore.setVisibility(View.VISIBLE);


                            try {
                                StringTokenizer tokens = new StringTokenizer(address_line1, " ");

                                first = tokens.nextToken();// this will contain "Fruit"
                                second = tokens.nextToken();
                                String upperString_first = first.substring(0,1).toUpperCase() + first.substring(1);
                                String upperString_second = second.substring(0,1).toUpperCase() + second.substring(1);

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + upperString_first + "</font>" + " " + upperString_second + ",  " + address_line2+", "+pincode;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            } catch (NoSuchElementException e) {
                                String upperString_first = address_line1.substring(0,1).toUpperCase() + address_line1.substring(1);
                                String upperString_second = address_line2.substring(0,1).toUpperCase() + address_line2.substring(1);
                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + upperString_first + "</font>" + ",  " + upperString_second+", "+pincode;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            }

                            // Fetch  Company Branches from Web Service
                            String company_branches_in_india = jsonObject2.getString("company_branches_in_india");
                            System.out.println("Company Branches in India%%%" + company_branches_in_india);
                            arr_branches_state = new ArrayList<>();
                            arr_branches_pincode = new ArrayList<>();
                            arr_branches_address = new ArrayList<>();
                            arr_branch_phone_number = new ArrayList<>();

                            // Fetch Title  from Web Service
                            String title = jsonObject2.getString("title");
                            System.out.println("Title  is%%%" + title);
                            // Fetch First Name  from Web Service
                            String first_name = jsonObject2.getString("first_name");
                            System.out.println("First Name  is%%%" + first_name);
                            // Fetch Last Name  from Web Service
                            String last_name = jsonObject2.getString("last_name");
                            System.out.println("Last Name  is%%%" + last_name);


                            //Set Style for Contact  Name
                            String styledText_cname = "<font color=#000000>" + "Contact Name: " + "</font>" + " " + title + " " + first_name + " " + last_name;


                            // Set TextView for Contact name(Come from WebService)
                            grid_text_cname.setText(Html.fromHtml(styledText_cname));

                            if (company_branches_in_india.equalsIgnoreCase("yes")) {
// Fetch   Branches City from Web Service
                                String branch_city = jsonObject2.getString("branch_city");
                                System.out.println("Branch City in India%%%" + branch_city);
                                JSONArray jsonArray3 = new JSONArray(branch_city);

                                arr_branches_city = new ArrayList<>();
                                for (int i = 0; i < jsonArray3.length(); i++) {
                                    arr_branches_city.add(String.valueOf(jsonArray3.get(i)));
                                }
// Fetch   Branches State from Web Service
                                String branch_state = jsonObject2.getString("branch_state");
                                System.out.println("Branch State in India%%%" + branch_state);
                                JSONArray jsonArray4 = new JSONArray(branch_state);

                                arr_branches_state = new ArrayList<>();
                                for (int i = 0; i < jsonArray4.length(); i++) {
                                    arr_branches_state.add(String.valueOf(jsonArray4.get(i)));
                                }
// Fetch   Branches Pincode from Web Service
                                String branch_pincode = jsonObject2.getString("branch_pincode");
                                System.out.println("Branch Pincode in India%%%" + branch_pincode);
                                JSONArray jsonArray5 = new JSONArray(branch_pincode);

                                arr_branches_pincode = new ArrayList<>();
                                for (int i = 0; i < jsonArray5.length(); i++) {
                                    arr_branches_pincode.add(String.valueOf(jsonArray5.get(i)));
                                }
// Fetch   Branches Address from Web Service
                                String branch_address = jsonObject2.getString("branch_address");
                                System.out.println("Branch Address in India%%%" + branch_address);
                                JSONArray jsonArray6 = new JSONArray(branch_address);

                                arr_branches_address = new ArrayList<>();
                                for (int i = 0; i < jsonArray6.length(); i++) {
                                    arr_branches_address.add(String.valueOf(jsonArray6.get(i)));
                                }
// Fetch   Branches Phone Number from Web Service
                                String branch_phone_number = jsonObject2.getString("branch_phone_number");
                                System.out.println("Branch Phone Number in India%%%" + branch_phone_number);
                                JSONArray jsonArray7 = new JSONArray(branch_phone_number);

                                arr_branch_phone_number = new ArrayList<>();
                                for (int i = 0; i < jsonArray7.length(); i++) {
                                    arr_branch_phone_number.add(String.valueOf(jsonArray7.get(i)));
                                }


                               /* // Fetch   Branches City from Web Service
                                String branch_city=jsonObject2.getString("branch_city");
                                System.out.println("Branch City in India%%%"+branch_city);
                                // Fetch   Branches City from Web Service
                                String branch_city=jsonObject2.getString("branch_address");
                                System.out.println("Branch  in India%%%"+branch_city);


                                try {

                                StringTokenizer tokens = new StringTokenizer(",", " ");

                                first = tokens.nextToken();// this will contain "Fruit"
                                second = tokens.nextToken();

                                arr_branches_city.add(first);
                                arr_branches_city.add(second);

                            }
catch (NoSuchElementException e)
                            {
                                // Add Data in ArrayList
                                arr_branches_city.add(branch_city);
                                System.out.println("Array BrachSize%%%"+arr_branches_city.size());

                            }*/

                                if (arr_branches_city != null) {
                                    if (arr_branches_city.size() > 0) {
                                        System.out.println("Array BrachSize%%%" + arr_branches_city.size());
                                      /*  CustomGrid_Corporate_Company_Branches customGrid_corporate_company_branches=new CustomGrid_Corporate_Company_Branches(getActivity(),arr_branches_city);
                                        grid_companybranches.setAdapter(customGrid_corporate_company_branches);*/
                                        CustomGridRe_Corporate_Company_Branches customGrid_corporate_company_branches = new CustomGridRe_Corporate_Company_Branches(getActivity(), arr_branches_city, arr_branches_state, arr_branches_pincode, arr_branch_phone_number, arr_branches_address);
                                        InboxDetailRV.setAdapter(customGrid_corporate_company_branches);


                                    }
                                }

                            }


// Fetch Founded Year from Web Service
                            String year = jsonObject2.getString("year");
                            System.out.println("Founded in%%%" + year);

                            //Set Style for  Founded Year
                            String styledText_consultating_fees = "<font color=#000000>" + "Founded In: " + "</font>" + year;

                            // Set TextView  For  Founded Year
                            grid_text_pracexperience.setText(Html.fromHtml(styledText_consultating_fees));

// Fetch  Class Of Company from Web Service
                            String class_of_company = jsonObject2.getString("class_of_company");
                            System.out.println("Class Of Company%%%" + class_of_company);

                            if (!class_of_company.equalsIgnoreCase("null")) {

                                if (class_of_company.equalsIgnoreCase("public_limited")) {

                                    //Set Style for  Class Of Company
                                    String styledText_class_of_company = "<font color=#000000>" + "Company Class: " + "</font>" + "Public Limited";
                                    // Set TextView  For  Company Classess
                                    grid_text_companyclass.setText(Html.fromHtml(styledText_class_of_company));
                                } else {
                                    String styledText_class_of_company = "<font color=#000000>" + "Company Class: " + "</font>" + "Private Limited";
                                    // Set TextView  For  Company Classess
                                    grid_text_companyclass.setText(Html.fromHtml(styledText_class_of_company));
                                }
                            } else {
                                String styledText_class_of_company = "<font color=#000000>" + "Company Class: " + "</font>" + "No information provided";
                                // Set TextView  For  Company Classess
                                grid_text_companyclass.setText(Html.fromHtml(styledText_class_of_company));

                            }


// Fetch  Category Of Company from Web Service
                            String company_category = jsonObject2.getString("company_category");
                            System.out.println("Category Of Company%%%" + company_category);

                            if (!company_category.equalsIgnoreCase("null")) {


                                String upperString_company_category = company_category.substring(0, 1).toUpperCase() + company_category.substring(1);

                                //Set Style for  Category Of Company
                                String styledText_category_of_company = "<font color=#000000>" + "Company Category: " + "</font>" + upperString_company_category;
                                // Set TextView  For  Category Of Company
                                grid_text_companycatogory.setText(Html.fromHtml(styledText_category_of_company));


                            } else {
                                String styledText_category_of_company = "<font color=#000000>" + "Company Class: " + "</font>" + "No information provided";
                                // Set TextView  For  Category Of Company
                                grid_text_companycatogory.setText(Html.fromHtml(styledText_category_of_company));

                            }
// Fetch  SubCategory Of Company from Web Service
                            String company_sub_category = jsonObject2.getString("company_sub_category");
                            System.out.println("SubCategory Of Company%%%" + company_sub_category);

                            if (!company_sub_category.equalsIgnoreCase("null")) {


                                String upperString_company_sub_category = company_sub_category.substring(0, 1).toUpperCase() + company_sub_category.substring(1);

                                //Set Style for  SubCategory Of Company
                                String styledText_sub_category_of_company = "<font color=#000000>" + "Company Sub Category: " + "</font>" + upperString_company_sub_category;
                                // Set TextView  For  SubCategory Of Company
                                grid_text_companysubcatogory.setText(Html.fromHtml(styledText_sub_category_of_company));


                            } else {
                                String styledText_category_of_company = "<font color=#000000>" + "Company Sub Category: " + "</font>" + "No information provided";
                                // Set TextView  For SubCategory  Company
                                grid_text_companysubcatogory.setText(Html.fromHtml(styledText_category_of_company));

                            }


                            // Fetch  Number of Employess Of Company from Web Service
                            String number_of_employees = jsonObject2.getString("number_of_employees");
                            System.out.println("Number of Employess Of Company%%%" + number_of_employees);

                            if (!number_of_employees.equalsIgnoreCase("null")) {


                                String upperString_company_number_of_employees = number_of_employees.substring(0, 1).toUpperCase() + number_of_employees.substring(1);

                                //Set Style for  Number of Employess Of Company
                                String styledText_number_of_employees_of_company = "<font color=#000000>" + "Number of Employees: " + "</font>" + upperString_company_number_of_employees;
                                // Set TextView  For  Number of Employess Of Company
                                grid_text_numofemployees.setText(Html.fromHtml(styledText_number_of_employees_of_company));


                            } else {
                                String styledText_styledText_number_of_employees_of_company_of_company = "<font color=#000000>" + "Number of Employees: " + "</font>" + "No information provided";
                                // Set TextView  For Number of Employess of  Company
                                grid_text_numofemployees.setText(Html.fromHtml(styledText_styledText_number_of_employees_of_company_of_company));

                            }


                            // Fetch Annual TurnOver Of Company from Web Service
                            String annual_turnover = jsonObject2.getString("annual_turnover");
                            System.out.println("Annual TurnOver Of Company%%%" + annual_turnover);

                            if(annual_turnover.equalsIgnoreCase(""))                            {
                                String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + "No information provided";
                                // Set TextView  For Number of Employess of  Company
                                grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));
                            }

                            if (annual_turnover!= null) {

                                if (!annual_turnover.equalsIgnoreCase("")) {


                                    String upperString_company_annual_turnover = annual_turnover.substring(0, 1).toUpperCase() + annual_turnover.substring(1);

                                    //Set Style for  Number of Employess Of Company
                                    String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + upperString_company_annual_turnover;
                                    // Set TextView  For  Number of Employess Of Company
                                    grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));


                                } else {
                                    String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + "No information provided";
                                    // Set TextView  For Number of Employess of  Company
                                    grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));

                                }
                                if (!annual_turnover.equalsIgnoreCase("null")) {

                                    try {

                                        String upperString_company_annual_turnover = annual_turnover.substring(0, 1).toUpperCase() + annual_turnover.substring(1);

                                        //Set Style for  Number of Employess Of Company
                                        String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + upperString_company_annual_turnover;
                                        // Set TextView  For  Number of Employess Of Company
                                        grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));
                                    }
                                    catch (StringIndexOutOfBoundsException e)
                                    {
                                        String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + "No information provided";
                                        // Set TextView  For Number of Employess of  Company
                                        grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));
                                    }



                                }


                                else {
                                    String styledText_annual_turnover_of_company = "<font color=#000000>" + "Annual Turnover: " + "</font>" + "No information provided";
                                    // Set TextView  For Number of Employess of  Company
                                    grid_text_annualturnover.setText(Html.fromHtml(styledText_annual_turnover_of_company));

                                }


                            }
                            // Fetch Industries Name Of Company from Web Service
                            String industries = jsonObject2.getString("industries");
                            System.out.println("Industries Of Company%%%" + annual_turnover);

                            if (industries != null) {
                                if (!industries.equalsIgnoreCase("null")) {


                                    String upperString_company_industries = industries.substring(0, 1).toUpperCase() + industries.substring(1);

                                    //Set Style for  Industries Name Of Company
                                    String styledText_industries_of_company = "<font color=#000000>" + "Industry Name: " + "</font>" + upperString_company_industries;
                                    // Set TextView  For  Industries Name Of Company
                                    grid_text_cindustries.setText(Html.fromHtml(styledText_industries_of_company));


                                } else {
                                    String styledText_industries_of_company = "<font color=#000000>" + "Industry Name: " + "</font>" + "No information provided";
                                    // Set TextView  For Industries Nameof  Company
                                    grid_text_cindustries.setText(Html.fromHtml(styledText_industries_of_company));

                                }

                                if (!industries.equalsIgnoreCase("")) {


                                    String upperString_company_industries = industries.substring(0, 1).toUpperCase() + industries.substring(1);

                                    //Set Style for  Industries Name Of Company
                                    String styledText_industries_of_company = "<font color=#000000>" + "Industry Name: " + "</font>" + upperString_company_industries;
                                    // Set TextView  For  Industries Name Of Company
                                    grid_text_cindustries.setText(Html.fromHtml(styledText_industries_of_company));


                                } else {
                                    String styledText_industries_of_company = "<font color=#000000>" + "Industry Name: " + "</font>" + "No information provided";
                                    // Set TextView  For Industries Nameof  Company
                                    grid_text_cindustries.setText(Html.fromHtml(styledText_industries_of_company));
                                }
                            }

                            // Fetch number of Directors from Web Service
                            String no_of_directors = jsonObject2.getString("no_of_directors");

                            if (no_of_directors.equalsIgnoreCase("yes")) {

                                // Fetch Designation from Web Service
                                String designation = jsonObject2.getString("designation");
                                System.out.println("Designation are%%%" + designation);
                                if (!designation.equalsIgnoreCase("")) {

                                    JSONArray jsonArray4 = new JSONArray(designation);
                                    arr_mangement_designstion = new ArrayList<>();
                                    for (int k = 0; k < jsonArray4.length(); k++) {


                                        arr_mangement_designstion.add(String.valueOf(jsonArray4.get(k)));


                                    }
                                }

                                // Fetch Management Title from Web Service
                                String mtitle = jsonObject2.getString("mtitle");
                                System.out.println("Management Title are%%%" + mtitle);
                                if (!mtitle.equalsIgnoreCase("")) {

                                    JSONArray jsonArray5 = new JSONArray(mtitle);
                                    arr_mangement_title = new ArrayList<>();
                                    for (int k = 0; k < jsonArray5.length(); k++) {


                                        arr_mangement_title.add(String.valueOf(jsonArray5.get(k)));


                                    }
                                }

                                // Fetch MANAGEMENT  Name from Web Service
                                String mname = jsonObject2.getString("mname");
                                System.out.println("MANAGEMENT  Name are%%%" + mname);
                                if (!mname.equalsIgnoreCase("")) {

                                    JSONArray jsonArray6 = new JSONArray(mname);
                                    arr_management_name = new ArrayList<>();
                                    for (int k = 0; k < jsonArray6.length(); k++) {


                                        arr_management_name.add(String.valueOf(jsonArray6.get(k)));


                                    }
                                }


                                // Set Data for Awards and Recognization

                                if (arr_mangement_designstion != null) {
                                    ll_mangement_details.setVisibility(View.VISIBLE);

                                    for (int m = 0; m < arr_mangement_designstion.size(); m++) {
                                        LayoutInflater inflater = null;
                                        inflater = (LayoutInflater) getActivity()
                                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View mLinearView = inflater.inflate(R.layout.search_through_name_loadmore_adp, null);
                                        /**
                                         * getting id of row.xml
                                         */
                                        TextView mFirstName = (TextView) mLinearView
                                                .findViewById(R.id.txt_awrd_recognization_descp1);


                                        /**
                                         * set item into row
                                         */

                                        String str_recognization_designation = arr_mangement_designstion.get(m);
                                        String upperString_str_recognization_designation = str_recognization_designation.substring(0, 1).toUpperCase() + str_recognization_designation.substring(1);

                                        String str_recognization_title = arr_mangement_title.get(m);
                                        String upperString_str_recognization_title = str_recognization_title.substring(0, 1).toUpperCase() + str_recognization_title.substring(1);

                                        String str_recognization_name = arr_management_name.get(m);
                                        String upperString_str_recognization_name = str_recognization_name.substring(0, 1).toUpperCase() + str_recognization_name.substring(1);


                                        System.out.println("List Size^^^" + arr_mangement_designstion.size());

                                        if (!str_recognization_title.equalsIgnoreCase("")) {
                                            //    Set Awards and Recognization Data ,when come from webservice
                                            mFirstName.setText(upperString_str_recognization_designation + ", " + upperString_str_recognization_title + " " + upperString_str_recognization_name);
                                        } else {
                                            mFirstName.setText("No information provided");
                                        }

                                        /**
                                         * add view in top linear
                                         */

                                        ll_mangement_details.addView(mLinearView);
                                    }
                                }

                            }

                            // Fetch Start Timing from Web Service
                            String starttime = jsonObject2.getString("starttimes1");
                            System.out.println(" Start Timing is%%%" + starttime);


                            // Fetch End Timing from Web Service
                            String endtime = jsonObject2.getString("endtimes1");
                            System.out.println(" Start Timing is%%%" + starttime);

                            //Set Style for Timing for BusinessMen
                            String styledText_timing = "<font color=#000000>" + "Timings: " + "</font>" + starttime + "-" + endtime;

                            // Set TextView  For Timing for BusinessMen
                            grid_texttiming.setText(Html.fromHtml(styledText_timing));


                        }


                        //-------------------------- For Business----------------------------------------------------------------------------------//
                        if (str_table_name.equalsIgnoreCase("business")) {
                            rr_confees.setVisibility(View.GONE);
                            rr_knownlanguage.setVisibility(View.GONE);
                            rr_educ.setVisibility(View.GONE);
                            rr_loadmore.setVisibility(View.VISIBLE);
                            rr_loadmore_show.setVisibility(View.GONE);
                            rr_cname.setVisibility(View.VISIBLE);


                            layoutdialog3.setVisibility(View.GONE);
                            rr_companybranches.setVisibility(View.GONE);
                            rr_clinikaddress.setVisibility(View.VISIBLE);
                            rr_address.setVisibility(View.VISIBLE);
                            rr_cindustries.setVisibility(View.GONE);
                            btn_loadmore.setVisibility(View.VISIBLE);

// Fetch Title  from Web Service
                            String title = jsonObject2.getString("title");
                            System.out.println("Title  is%%%" + title);
                            // Fetch First Name  from Web Service
                            String first_name = jsonObject2.getString("first_name");
                            System.out.println("First Name  is%%%" + first_name);
                            // Fetch Last Name  from Web Service
                            String last_name = jsonObject2.getString("last_name");
                            System.out.println("Last Name  is%%%" + last_name);


                            //Set Style for Contact  Name
                            String styledText_cname = "<font color=#000000>" + "Contact Name: " + "</font>" + " " + title + " " + first_name + " " + last_name;

                            // Set TextView for Contact name(Come from WebService)
                            grid_text_cname.setText(Html.fromHtml(styledText_cname));
// Fetch Founded Year from Web Service
                            String year = jsonObject2.getString("year");
                            System.out.println("Founded in%%%" + year);

                            //Set Style for  Founded Year
                            String styledText_consultating_fees = "<font color=#000000>" + "Founded In: " + "</font>" + year;

                            // Set TextView  For  Founded Year
                            grid_text_pracexperience.setText(Html.fromHtml(styledText_consultating_fees));


                            // Fetch Start Timing from Web Service
                            String starttime = jsonObject2.getString("starttimes1");
                            System.out.println(" Start Timing is%%%" + starttime);


                            // Fetch End Timing from Web Service
                            String endtime = jsonObject2.getString("endtimes1");
                            System.out.println(" Start Timing is%%%" + starttime);



                            //Set Style for Timing for BusinessMen
                            String styledText_timing = "<font color=#000000>" + "Timings: " + "</font>" + starttime + "-" + endtime;

                            // Set TextView  For Timing for BusinessMen
                            grid_texttiming.setText(Html.fromHtml(styledText_timing));

                            try {
                                StringTokenizer tokens = new StringTokenizer(address_line1, " ");

                                first = tokens.nextToken();// this will contain "Fruit"
                                second = tokens.nextToken();
                                String upperString_first = first.substring(0,1).toUpperCase() + first.substring(1);
                                String upperString_second = second.substring(0,1).toUpperCase() + second.substring(1);

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + upperString_first + "</font>" + " " + upperString_second + ",  " + address_line2+", "+pincode;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            } catch (NoSuchElementException e) {
                                String upperString_first = address_line1.substring(0,1).toUpperCase() + address_line1.substring(1);
                                String upperString_second = address_line2.substring(0,1).toUpperCase() + address_line2.substring(1);
                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + upperString_first + "</font>" + ",  " + upperString_second+", "+pincode;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            }


                            //Set Business Address from Web Service for BusinessMen
                       //     grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));

                        }

                        //-------------------------------- For Doctors and Lawyers-----------------------------------------------------------------------------//

                        if (str_table_name.equalsIgnoreCase("doctors") || str_table_name.equalsIgnoreCase("lawyers")) {
                            rr_confees.setVisibility(View.VISIBLE);
                            rr_knownlanguage.setVisibility(View.VISIBLE);
                            rr_educ.setVisibility(View.VISIBLE);
                            rr_loadmore.setVisibility(View.VISIBLE);
                            btn_loadmore.setVisibility(View.VISIBLE);
                            rr_cname.setVisibility(View.GONE);

                            //   rr_loadmore_show.setVisibility(View.VISIBLE);
                            layoutdialog3.setVisibility(View.VISIBLE);

                            rr_companybranches.setVisibility(View.GONE);
                            rr_clinikaddress.setVisibility(View.VISIBLE);
                            rr_address.setVisibility(View.VISIBLE);

                            rr_cindustries.setVisibility(View.GONE);


                            //Set Style for Contact  Name
                            String styledText_cname = "<font color=#000000>" + "Contact Name: " + "</font>" + " " + nameee;

                            // Set TextView for Contact name(Come from WebService)
                            grid_text_cname.setText(Html.fromHtml(styledText_cname));

// Fetch  Verfication By from Web Service
                            String identity_proof = jsonObject2.getString("identity_proof");
                            System.out.println("Verfified  by%%%" + identity_proof);
                            if (identity_proof.equalsIgnoreCase("")) {
                                txt_verification_descp1.setText("Not Verfied");
                            } else {
                                txt_verification_descp1.setText("Identity: " + identity_proof);
                            }


// Fetch  MemberShip from Web Service
                            String memberships = jsonObject2.getString("memberships");
                            System.out.println("MemberShips  are%%%" + memberships);
                            if (memberships.equalsIgnoreCase("")) {
                                //Set Text For Membership
                                txt_membershipandassocisation_descp1.setText("No information provided");
                            } else {
                                //Set Text For Membership
                                txt_membershipandassocisation_descp1.setText("MemberShip from: " + memberships);
                            }

// Fetch Recognization title from Web Service
                            String recognization_title = jsonObject2.getString("recognization_title");
                            System.out.println("Recognization Tiltes  are%%%" + recognization_title);

                            if (!recognization_title.equalsIgnoreCase("")) {
                                JSONArray jsonArray3 = new JSONArray(recognization_title);
                                arr_recognization_title = new ArrayList<>();
                                for (int k = 0; k < jsonArray3.length(); k++) {


                                    arr_recognization_title.add(String.valueOf(jsonArray3.get(k)));


                                }
                            }


                            // Fetch Awards Winning Year from Web Service
                            String award_winning_year = jsonObject2.getString("award_winning_year");
                            System.out.println("Awards Winning Tear  are%%%" + award_winning_year);
                            if (!recognization_title.equalsIgnoreCase("")) {

                                JSONArray jsonArray4 = new JSONArray(award_winning_year);
                                arr_awards_year = new ArrayList<>();
                                for (int k = 0; k < jsonArray4.length(); k++) {


                                    arr_awards_year.add(String.valueOf(jsonArray4.get(k)));


                                }
                            }
                            // Set Data for Awards and Recognization

                            if (arr_recognization_title != null) {
                                ll_rr_awrd_recognization.setVisibility(View.VISIBLE);

                                for (int m = 0; m < arr_recognization_title.size(); m++) {
                                    LayoutInflater inflater = null;
                                    inflater = (LayoutInflater) getActivity()
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View mLinearView = inflater.inflate(R.layout.search_through_name_loadmore_adp, null);
                                    /**
                                     * getting id of row.xml
                                     */
                                    TextView mFirstName = (TextView) mLinearView
                                            .findViewById(R.id.txt_awrd_recognization_descp1);


                                    /**
                                     * set item into row
                                     */

                                    String str_recognization_title = arr_recognization_title.get(m);
                                    String str_awards_year = arr_awards_year.get(m);

                                    System.out.println("List Size^^^" + arr_recognization_title.size());

                                    if (!str_recognization_title.equalsIgnoreCase("")) {
                                        //    Set Awards and Recognization Data ,when come from webservice
                                        mFirstName.setText("Got awarded the " + str_recognization_title + " in the " + "Year " + str_awards_year);
                                    } else {
                                        mFirstName.setText("No information provided");
                                    }

                                    /**
                                     * add view in top linear
                                     */

                                    ll_rr_awrd_recognization.addView(mLinearView);
                                }
                            }


                            // Fetch Languges from Web Service
                            String languages = jsonObject2.getString("languages");
                            System.out.println("Languages  are%%%" + languages);


                            JSONArray jsonArray2 = new JSONArray(languages);
                            arr_languages = new ArrayList<>();
                            for (int k = 0; k < jsonArray2.length(); k++) {
                                String str_lang = jsonArray2.get(k) + ",";

                                arr_languages.add(str_lang);
                            }


                            // Set TextView  For Languages
                            for (int z = 0; z < arr_languages.size(); z++) {
                               if(z==0)
                                {
                                    String str_languages2 = arr_languages.get(z);
                                    //Set Style for Languages
                                    String styledText_languages = "<font color=#000000>" + str_languages2 + "</font>";

                                    // Set TextView  For Languages
                                    grid_text_knownlanguage.append("Can Speak ");
                                }
                                if (z == arr_languages.size() - 1) {
                                    String str_languages2 = arr_languages.get(arr_languages.size() - 1);
                                    String str_lan = str_languages2.replace(",", "");

                                    //Set Style for Languages
                                    String styledText_languages = "<font color=#000000>" + str_lan + "</font>";

                                    // Set TextView  For Languages
                                    grid_text_knownlanguage.append(Html.fromHtml(styledText_languages)+" "+"languages");

                                } else {
                                    String str_languages2 = arr_languages.get(z);
                                    System.out.println("Languages in List###" + str_languages2);

                                    //Set Style for Languages
                                    String styledText_languages = "<font color=#000000>" + str_languages2 + "</font>";
                                    // Set TextView  For Languages
                                    grid_text_knownlanguage.append(Html.fromHtml(styledText_languages));

                                    }

                            }
                        }

                        if (str_table_name.equalsIgnoreCase("doctors") || str_table_name.equalsIgnoreCase("lawyers")) {



                      /*      // Fetch Title  from Web Service
                            String title = jsonObject2.getString("title");
                            System.out.println("Title  is%%%" + title);
                            // Fetch First Name  from Web Service
                            String first_name = jsonObject2.getString("first_name");
                            System.out.println("First Name  is%%%" + first_name);
                            // Fetch Last Name  from Web Service
                            String last_name = jsonObject2.getString("last_name");
                            System.out.println("Last Name  is%%%" + last_name);*/

                            // Fetch App Book Appointment from Web Service
                            String is_app_book = jsonObject2.getString("is_app_book");
                            System.out.println("Book Appointment  is%%%" + is_app_book);
if(is_app_book.equalsIgnoreCase("1"))
{
    layoutdialog3.setVisibility(View.VISIBLE);
}
else
{
    layoutdialog3.setVisibility(View.GONE);
}

                            // Fetch Qualification from Web Service
                            String qualification = jsonObject2.getString("qualification_name");
                            System.out.println("Qualification  is%%%" + qualification);

                            JSONArray jsonArray2 = new JSONArray(qualification);
                            arr_qualification = new ArrayList<>();
                            arr_qualification1 = new ArrayList<>();

                            for (int k = 0; k < jsonArray2.length(); k++) {

                                arr_qualification1.add(String.valueOf(jsonArray2.get(k)));
                                String str_qual = jsonArray2.get(k) + ", ";

                                arr_qualification.add(str_qual);
                                System.out.println("Qualification Size@@@" + arr_qualification1.size());

                            }


                            // Set TextView  For Qualification
                            for (int z = 0; z < arr_qualification.size(); z++) {


                                if (z == arr_qualification.size() - 1) {
                                    String str_qualificatin2 = arr_qualification.get(arr_qualification.size() - 1);

                                    if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                        rr_educ.setVisibility(View.GONE);

                                    } else {
                                        rr_educ.setVisibility(View.VISIBLE);

                                        String str_qua = str_qualificatin2.replace(", ", "");
                                        txt_educ.append(str_qua);
                                    }

                                } else {
                                    String str_qualificatin2 = arr_qualification.get(z);
                                    System.out.println("Qualification in List###" + str_qualificatin2);
                                    if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                        // txt_educ.append("");

                                        rr_educ.setVisibility(View.GONE);


                                    } else {
                                        rr_educ.setVisibility(View.VISIBLE);

                                        txt_educ.append(arr_qualification.get(z));
                                    }

                                    //txt_educ.append(arr_qualification.get(z));

                                }
                            }

                        }

                        // Set Data for Qualification

                        if (arr_qualification1 != null) {
                            ll_rr_educationprofessionalqualification.setVisibility(View.VISIBLE);

                            for (int m = 0; m < arr_qualification1.size(); m++) {
                                LayoutInflater inflater = null;
                                inflater = (LayoutInflater) getActivity()
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View mLinearView = inflater.inflate(R.layout.search_through_name_loadmore_adp, null);
                                /**
                                 * getting id of row.xml
                                 */
                                TextView mFirstName = (TextView) mLinearView
                                        .findViewById(R.id.txt_awrd_recognization_descp1);


                                /**
                                 * set item into row
                                 */

                                String str_recognization_title = arr_qualification1.get(m);

                                System.out.println("List Size$$$" + arr_qualification1.size());

                                if (!str_recognization_title.equalsIgnoreCase("null, ")) {

                                    if (!str_recognization_title.equalsIgnoreCase("")) {
                                        //    Set Awards and Recognization Data ,when come from webservice
                                        mFirstName.setText("Education in: " + str_recognization_title);
                                    } else {
                                        mFirstName.setText("No information provided");
                                    }

                                } else {
                                    mFirstName.setText("No information provided");

                                }


                                /**
                                 * add view in top linear
                                 */

                                ll_rr_educationprofessionalqualification.addView(mLinearView);
                            }
                        }


                        // Fetch Practicing Experiece from Web Service
                        String year = jsonObject2.getString("year");
                        System.out.println("Practicing Experiece  is%%%" + year);

                        //Fetch Data for Lawyers
                        if (str_table_name.equalsIgnoreCase("lawyers")) {
                            // Fetch Clinik Address from Web Service
                            String procourt = jsonObject2.getString("procourt");
                            System.out.println(" Court Address  is%%%" + procourt);

                            try {
                                StringTokenizer tokens = new StringTokenizer(address_line1, " ");

                                first = tokens.nextToken();// this will contain "Fruit"
                                second = tokens.nextToken();

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" +"Court Address:" + "</font>"+first + " " + second + ",  " + address_line2;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            } catch (NoSuchElementException e) {

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + "Court Address: " + "</font>"+address_line1 + ",  " + address_line2;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            }

                            //Set Style for Court Address
                            String styledText_courtaddress = "<font color=#000000>" + "Court Address: " + "</font>" + procourt;

                            // Set TextView  For Court Address
                           // grid_text_clinikaddress.setText(Html.fromHtml(styledText_courtaddress));


                            // Fetch Consultating Type for Lawyers from Web Service
                            String consultating_type = jsonObject2.getString("consultation_types");
                            System.out.println("Consultating Type is%%%" + consultating_type);

                            JSONArray jsonArray2 = new JSONArray(consultating_type);
                            arr_consultating_type = new ArrayList<>();
                            for (int k = 0; k < jsonArray2.length(); k++) {
                                arr_consultating_type.add(String.valueOf(jsonArray2.get(k)));
                            }


                            // Fetch Consultating Time for Lawyers from Web Service
                            String consultating_time = jsonObject2.getString("consultation_minutess");
                            System.out.println("Consultating Time is%%%" + consultating_time);

                            JSONArray jsonArray3 = new JSONArray(consultating_time);
                            arr_consultating_minutes = new ArrayList<>();
                            for (int k = 0; k < jsonArray3.length(); k++) {
                                arr_consultating_minutes.add(String.valueOf(jsonArray3.get(k)));
                            }

                            // Fetch Consultating Fees for Lawyers from Web Service
                            String consultation_feess = jsonObject2.getString("consultation_feess");
                            System.out.println("Consultating Fees is%%%" + consultating_time);

                            JSONArray jsonArray4 = new JSONArray(consultation_feess);
                            arr_counsultating_fees = new ArrayList<>();
                            for (int k = 0; k < jsonArray4.length(); k++) {


                                arr_counsultating_fees.add(String.valueOf(jsonArray4.get(k)));
                            }
                            // Set TextView  For Cosultating Fees
                            for (int z = 0; z < arr_consultating_type.size(); z++) {

                                String str_consultating_type = arr_consultating_type.get(0);
                                String str_consultating_mintues = arr_consultating_minutes.get(0);
                                String str_consultating_fees;

                                if (arr_consultating_type.size() > 1) {
                                    str_consultating_fees = arr_counsultating_fees.get(1);
                                } else {
                                    str_consultating_fees = arr_counsultating_fees.get(0);

                                }

                                String upperStringstr_consultating_type = str_consultating_type.substring(0, 1).toUpperCase() + str_consultating_type.substring(1);
                                //Set Style for  Consultating Fees
                                String styledText_consultating_fees = "<font color=#000000>" + "Consultation Fees: " + "</font>" + "starting from Rs. " + str_consultating_fees + " " + "(View all)";
                                // Set Text for Consultating Fees of Lawyers
                                grid_text_confees.setText(Html.fromHtml(styledText_consultating_fees));

                            }

                        }

                        //Fetch Data for Doctors
                        if (str_table_name.equalsIgnoreCase("doctors")) {
                            // Fetch Consulting Fees  from Web Service
                            String new_patient = jsonObject2.getString("new_patient");
                            System.out.println("Consulting Fees  is%%%" + new_patient);
                            //Set Style for  Consultating Fees
                            String styledText_consultating_fees = "<font color=#000000>" + "Consultation Fees: " + "</font>" + "starting from Rs " + new_patient + " " + "(View all)";
                            // Set Text for Consultating Fees of Lawyers
                            grid_text_confees.setText(Html.fromHtml(styledText_consultating_fees));




                            try {
                                StringTokenizer tokens = new StringTokenizer(address_line1, " ");

                                first = tokens.nextToken();// this will contain "Fruit"
                                second = tokens.nextToken();

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" +"Clinik Address:" + "</font>"+first + " " + second + ",  " + address_line2;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            } catch (NoSuchElementException e) {

                                //Set Style for  Address
                                styledText_address = "<font color=#000000>" + "Clinik Address: " + "</font>"+address_line1 + ",  " + address_line2;
                                String total_date = first + "," + " " + second;
                                // Set TextView  For Address is
                                grid_text_clinikaddress.setText(Html.fromHtml(styledText_address));
                            }

                            // Fetch Clinik Address from Web Service
                            String proclinic = jsonObject2.getString("proclinic");
                            System.out.println(" Clinik Address  is%%%" + proclinic);



                            //Set Style for Clinik Address
                            String styledText_clinikaddress = "<font color=#000000>" + "Clinik Address: " + "</font>" + proclinic;

                            // Set TextView  For Clinik Address
                            //grid_text_clinikaddress.setText(Html.fromHtml(styledText_clinikaddress));

                        }

                        // Fetch Start Timing from Web Service
                        String starttime = jsonObject2.getString("starttime");
                        System.out.println(" Start Timing is%%%" + starttime);

                        JSONArray jsonArray2 = new JSONArray(starttime);
                        arr_strt_time = new ArrayList<>();
                        for (int k = 0; k < jsonArray2.length(); k++) {
                            arr_strt_time.add(String.valueOf(jsonArray2.get(k)));
                        }


                        // Fetch End Timing from Web Service
                        String endtime = jsonObject2.getString("endtime");
                        System.out.println("End Timing is%%%" + endtime);

                        JSONArray jsonArray3 = new JSONArray(endtime);
                        arr_end_time = new ArrayList<>();
                        for (int k = 0; k < jsonArray3.length(); k++) {
                            arr_end_time.add(String.valueOf(jsonArray3.get(k)));
                        }


                        // Set TextView  For Timing
                        for (int q = 0; q < arr_strt_time.size(); q++) {
                            String str_time_first1 = arr_strt_time.get(0);
                            String end_time_first1 = arr_end_time.get(0);

                            if (str_table_name.equalsIgnoreCase("doctors")) {

                                //Set Style for Timing for doctors
                                String styledText_timing = "<font color=#000000>" + "Timings: " + "</font>" + str_time_first1 + "-" + end_time_first1 + "(View all)";

                                // Set TextView  For Timing for Doctors
                                grid_texttiming.setText(Html.fromHtml(styledText_timing));
                            }

                            if (str_table_name.equalsIgnoreCase("lawyers")) {
                                //Set Style for Timing for Lawyers
                                String styledText_timing = "<font color=#000000>" + "Timings: " + "</font>" + str_time_first1 + "-" + end_time_first1;

                                // Set TextView  For Timing for Lawyers
                                grid_texttiming.setText(Html.fromHtml(styledText_timing));
                            }


                        }

                        // Set TextView for Name (Come from WebService)
                        //   txt_educ.setText(qualification);

                        //Set Style for  Practicing Experiece
                        String styledText_consultating_fees = "<font color=#000000>" + "Practicing experience: " + "</font>" + year + " " + "Years";

                        // Set TextView  For Practicing Experiece
                        grid_text_pracexperience.setText(Html.fromHtml(styledText_consultating_fees));

                    } else {
                        loader.setVisibility(View.GONE);


                        txt_nodata.setVisibility(View.VISIBLE);

                        String errorMsg = jObj.getString("message");
                        if (getActivity() != null) {

                        //    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                        }

                        Log.e("errorMsg", errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                txt_nodata.setVisibility(View.VISIBLE);
                Log.e("SearchThroughName", "SearchThroughName Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "TEST@123");

                /*params.put("Authorization","Basic YWRtaW46MTIzNA==");
                params.put("Content-Type","application/x-www-form-urlencoded");*/

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tablename", str_table_name);
                params.put("city", str_city_name);
                params.put("name", str_first_name);
                params.put("regid", str_encoderegid);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }


    private void getBundleData() {

        // Casting ProgressBar for load data from Web Service
        loader = (ProgressBar) v.findViewById(R.id.progress_getsigledata);

        MySharedPref sp = new MySharedPref();
        String ldata = sp.getData(getActivity(), "ldata", "null");
        System.out.println("Ldata is***"+ldata);

        try {
            if (!ldata.equalsIgnoreCase("null"))
            {
                token = sp.getData(getActivity(), "token", "null");
                System.out.println("Token is***" + token);



                JSONObject jsonObject = new JSONObject(ldata);
                user_id = jsonObject.getString("id");
                user_name = jsonObject.getString("name");

                System.out.println("Id is***" + user_id);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        b1 = this.getArguments();
        if (b1 != null) {

            str_table_name = b1.getString("str_table_name");
            str_city_name = b1.getString("str_city_name");
            str_first_name = b1.getString("str_first_name");
            str_encoderegid = b1.getString("str_encoderegid");


            System.out.println("Click TableName After is%%%" + str_table_name);
            System.out.println("Click City After is%%%" + str_city_name);
            System.out.println("Click Name After is%%%" + str_first_name);
            System.out.println("Click RegId After is%%%" + str_encoderegid);

            write_ratingreviews_data= b1.getString("write_ratingreviews_data");
            rate_experience = b1.getString("rate_experience");
            write_genuine = b1.getString("write_genuine");
            why_writing_reviews = b1.getString("why_writing_reviews");
            wnt_recommendation = b1.getString("wnt_recommendation");


            System.out.println("Click RatingReviews After is%%%" + write_ratingreviews_data);
            System.out.println("Click Rate Experience After is%%%" + rate_experience);
            System.out.println("Click Write Genuine After is%%%" + write_genuine);
            System.out.println("Click Write Reviews After is%%%" + why_writing_reviews);
            System.out.println("Click Recommadatin After is%%%" + wnt_recommendation);


            Submit_review();

        }



    }


    @Override
    public void onClick(View v) {

        if (v == rr_center_sendenquriy) {
            showSendenquirydialog();
        }
        if (v == img_arrow_down) {
            // Set Visibility
            img_arrow_down.setVisibility(View.GONE);
            rr_img_arrow_down.setVisibility(View.GONE);
            // rr_descp.setVisibility(View.GONE);
            txt_descp.setVisibility(View.GONE);
            img_arrow_up.setVisibility(View.VISIBLE);
            rr_img_arrow_up.setVisibility(View.VISIBLE);
            rr_txt_fulldescp.setVisibility(View.VISIBLE);
            rr_txt_fulldescp.startAnimation(animSlideDown);

        }
        if (v == img_arrow_up) {
            // Set Visibility

            img_arrow_up.setVisibility(View.GONE);
            rr_img_arrow_up.setVisibility(View.GONE);
            //  rr_descp.setVisibility(View.VISIBLE);
            rr_txt_fulldescp.startAnimation(animSlideUp);
            rr_txt_fulldescp.setVisibility(View.GONE);

            txt_descp.startAnimation(animSlideDown);
            txt_descp.setVisibility(View.VISIBLE);

            img_arrow_down.setVisibility(View.VISIBLE);
            rr_img_arrow_down.setVisibility(View.VISIBLE);


        }

        if (v == rr_img_arrow_down) {
            // Set Visibility
            img_arrow_down.setVisibility(View.GONE);
            rr_img_arrow_down.setVisibility(View.GONE);

            // rr_descp.setVisibility(View.GONE);
            txt_descp.setVisibility(View.GONE);
            img_arrow_up.setVisibility(View.VISIBLE);
            rr_img_arrow_up.setVisibility(View.VISIBLE);
            rr_txt_fulldescp.setVisibility(View.VISIBLE);
        }
        if (v == rr_img_arrow_up) {
            // Set Visibility
            img_arrow_down.setVisibility(View.VISIBLE);
            rr_img_arrow_down.setVisibility(View.VISIBLE);
            txt_descp.setVisibility(View.VISIBLE);
            img_arrow_up.setVisibility(View.GONE);
            rr_img_arrow_up.setVisibility(View.GONE);
            //  rr_descp.setVisibility(View.VISIBLE);
            rr_txt_fulldescp.setVisibility(View.GONE);
        }
        if (v == btn_loadmore) {
            rr_loadmore.setVisibility(View.GONE);
            rr_loadmore_show.setVisibility(View.VISIBLE);
            rr_loadmore_up.setVisibility(View.VISIBLE);

            //-------------------------- For Corporate----------------------------------------------------------------------------------//
            if (str_table_name.equalsIgnoreCase("corporate")) {
               rr_main_corporate.setVisibility(View.VISIBLE);
               rr_for_doctorelawyers.setVisibility(View.GONE);
            }

            if (str_table_name.equalsIgnoreCase("doctors") || str_table_name.equalsIgnoreCase("lawyers")) {
                rr_main_corporate.setVisibility(View.GONE);
                rr_for_doctorelawyers.setVisibility(View.VISIBLE);
            }
        }
        if (v == btn_loadmore_up) {
            rr_loadmore.setVisibility(View.VISIBLE);
            rr_loadmore_show.setVisibility(View.GONE);
            rr_loadmore_up.setVisibility(View.GONE);

        }

        if (v == img_arrow_downn) {
            rr_loadmore.setVisibility(View.GONE);
            rr_loadmore_show.setVisibility(View.VISIBLE);
            rr_loadmore_up.setVisibility(View.VISIBLE);

            //-------------------------- For Corporate----------------------------------------------------------------------------------//
            if (str_table_name.equalsIgnoreCase("corporate")) {
                rr_main_corporate.setVisibility(View.VISIBLE);
                rr_for_doctorelawyers.setVisibility(View.GONE);
            }

            if (str_table_name.equalsIgnoreCase("doctors") || str_table_name.equalsIgnoreCase("lawyers")) {
                rr_main_corporate.setVisibility(View.GONE);
                rr_for_doctorelawyers.setVisibility(View.VISIBLE);
            }

        }
        if (v == img_arrow_upp) {
            rr_loadmore.setVisibility(View.VISIBLE);
            rr_loadmore_show.setVisibility(View.GONE);
            rr_loadmore_up.setVisibility(View.GONE);

        }

        if (v == ll_professionaldetails) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll_professionaldetails.setBackground(getResources().getDrawable(R.drawable.white_bg));
                ll_userrewies.setBackground(getResources().getDrawable(R.drawable.grey_bg));

            }

            rr_professionaldetails.setVisibility(View.VISIBLE);
            rr_user_reviews.setVisibility(View.GONE);


            v_professionaldetails.setVisibility(View.VISIBLE);
            v_userrewies.setVisibility(View.GONE);
            txt_professionaldetails.setTextColor(getResources().getColor(R.color.txt_colr_blue));
            txt_userrewies.setTextColor(getResources().getColor(R.color.gray));
        }

        if (v == ll_userrewies) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll_professionaldetails.setBackground(getResources().getDrawable(R.drawable.grey_bg));
                ll_userrewies.setBackground(getResources().getDrawable(R.drawable.white_bg));

            }
            rr_professionaldetails.setVisibility(View.GONE);
            rr_user_reviews.setVisibility(View.VISIBLE);
            ll_reviewandrating_blue.setVisibility(View.VISIBLE);
            rr_write_read_reviews.setVisibility(View.VISIBLE);
            rr_write_A_reviews.setVisibility(View.GONE);
            v_professionaldetails.setVisibility(View.GONE);
            v_userrewies.setVisibility(View.VISIBLE);
            txt_professionaldetails.setTextColor(getResources().getColor(R.color.gray));
            txt_userrewies.setTextColor(getResources().getColor(R.color.txt_colr_blue));
        }

        if (v==rr_left_call) {

            checkForPermission();
            if (!mobile_number.equalsIgnoreCase("")) {
                String contact =mobile_number;
                //   Toast.makeText(getActivity(), "Conatct is:"+contact, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +contact));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }

            else
            {
                Toast.makeText(getActivity(),"No Information Provided...",Toast.LENGTH_SHORT).show();
            }
        }

        if(v==img_back)
        {
            img_back.setVisibility(View.GONE);
            img_slider.setVisibility(View.VISIBLE);
            if(getActivity()!=null) {

                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
                System.out.println("countsss += "+ count);
                if (count == 2) {
                    Intent ii = new Intent(getActivity(), MainActivity.class);
                    startActivity(ii);
                }
                else {
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
                System.out.println("countsss += "+ count);

                if (count == 0) {
                    Intent ii = new Intent(getActivity(), MainActivity.class);
                    startActivity(ii);
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        }

        if(v==ll_writereviews_white)

        {
         //   ll_reviewandrating_white.setVisibility(View.VISIBLE);
          //  ll_reviewandrating_blue.setVisibility(View.GONE);
            ll_writereviews_white.setVisibility(View.GONE);
            ll_writereviews_blue.setVisibility(View.VISIBLE);
           // v_numreviewsrating.setVisibility(View.VISIBLE);
         //   v_writereviews.setVisibility(View.GONE);
            rr_write_read_reviews.setVisibility(View.VISIBLE);
                    rr_write_A_reviews.setVisibility(View.GONE);


        }
        if(v==ll_writereviews_blue)

        {
          //  ll_reviewandrating_white.setVisibility(View.GONE);
         //   ll_reviewandrating_blue.setVisibility(View.VISIBLE);
           // ll_writereviews_white.setVisibility(View.VISIBLE);
           // ll_writereviews_blue.setVisibility(View.GONE);
         //   v_numreviewsrating.setVisibility(View.GONE);
         //   v_writereviews.setVisibility(View.VISIBLE);
            ll_reviewandrating_blue.setVisibility(View.GONE);

            rr_write_read_reviews.setVisibility(View.GONE);
            rr_write_A_reviews.setVisibility(View.VISIBLE);


        }

        if(v==ll_reviewandrating_blue)

        {
          //  ll_reviewandrating_white.setVisibility(View.VISIBLE);
         //   ll_reviewandrating_blue.setVisibility(View.GONE);
            ll_writereviews_white.setVisibility(View.GONE);
            ll_writereviews_blue.setVisibility(View.VISIBLE);
          //  v_numreviewsrating.setVisibility(View.VISIBLE);
          //  v_writereviews.setVisibility(View.GONE);
            rr_write_read_reviews.setVisibility(View.VISIBLE);
            rr_write_A_reviews.setVisibility(View.GONE);


        }

        if(v==ll_reviewandrating_white)

        {
        //    ll_reviewandrating_white.setVisibility(View.GONE);
        //    ll_reviewandrating_blue.setVisibility(View.VISIBLE);
            ll_writereviews_white.setVisibility(View.VISIBLE);
            ll_writereviews_blue.setVisibility(View.GONE);
       //     v_numreviewsrating.setVisibility(View.GONE);
           // v_writereviews.setVisibility(View.VISIBLE);
            rr_write_read_reviews.setVisibility(View.GONE);
            rr_write_A_reviews.setVisibility(View.VISIBLE);

        }

        if(v==btn_loadmore_reviewrating)

        {
            if (arr_all_reviews_list!=null) {


                if (b < c)
                {
                    System.out.println("Value of B^^^" + b);
                    System.out.println("Value of L^^^" + l);
                    System.out.println("Value of C^^^" + c);
                    b = b + 1;
                    l = l + 5;
                    System.out.println("Valueinc of B^^^" + b);
                    System.out.println("Valueinc of L^^^" + l);
                    System.out.println("Valueinc of C^^^" + c);
                    customRe_reviewandRating_loadmore=new CustomRe_ReviewandRating_Loadmore(getActivity(), arr_all_reviews_list, arr_all_rating_list, arr_all_email_list, arr_all_name_list,l);
                    InboxDetailRVv.setAdapter(customRe_reviewandRating_loadmore);
                    customRe_reviewandRating_loadmore.notifyDataSetChanged();
                }
                else {

                    System.out.println("F value is%%%"+f);

                    if(f==0)
                    {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        rr_loadmore_reviewrating.setVisibility(View.GONE);
                        btn_loadmore_reviewrating.setVisibility(View.GONE);

                    }

                    else
                    {
                        f=l+f;
                        System.out.println("F value is^^^"+f);

                        customRe_reviewandRating_loadmore=new CustomRe_ReviewandRating_Loadmore(getActivity(), arr_all_reviews_list,
                                arr_all_rating_list, arr_all_email_list, arr_all_name_list,f);
                        InboxDetailRVv.setAdapter(customRe_reviewandRating_loadmore);
                        customRe_reviewandRating_loadmore.notifyDataSetChanged();
                        rr_loadmore_reviewrating.setVisibility(View.GONE);
                        btn_loadmore_reviewrating.setVisibility(View.GONE);

                  }
                }

            }
            else
            {
                Toast.makeText(getActivity(),"No Data Found",Toast.LENGTH_SHORT).show();
            }

        }
        if(v==btn_submit_reviews) {

            Validate2();
        }

        if(v==layoutdialog3)
        {

            MySharedPref sp = new MySharedPref();
            String ldata = sp.getData(getActivity(), "ldata", "null");
            System.out.println("Ldata is***"+ldata);
            if(ldata.equalsIgnoreCase("null"))
            {
                Intent ii = new Intent(getActivity(), LoginSignupActivity.class);
                ii.putExtra("str_table_name",str_table_name);
                ii.putExtra("str_city_name",str_city_name);
                ii.putExtra("str_first_name",str_first_name);
                ii.putExtra("str_encoderegid",str_encoderegid);
                startActivity(ii);
            }
            else
            {
                Book_AppointMent book_appointMent = Book_AppointMent.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle b=new Bundle();
                b.putString("str_user_name",txt_name.getText().toString());
                b.putString("str_category","");
                // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                b.putString("str_degrees",txt_educ.getText().toString());
                b.putString("address",address_line2);
                b.putString("pro_pic",profile_picture);
                b.putString("reg_id",reg_id);
                b.putString("main_id",main_id);
                b.putString("type",str_table_name);
                b.putString("city",str_city_name);


                book_appointMent.setArguments(b);
                transaction.replace(R.id.contentFrame, book_appointMent);
                transaction.addToBackStack(null);
                transaction.commit();
            }




        }


    }



    private void checkForPermission() {

        int permissionCheckForAccessCallPhone = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        if (permissionCheckForAccessCallPhone != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    1001);
            // checkForPermission();

   /*   Intent ii=new Intent(Stockist_Main_Activity.this,Stockist_Main_Activity.class);
            startActivity(ii);*/
        }

    }

    private void showSendenquirydialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Include dialog.xml file
        dialog.setContentView(R.layout.send_enquiry_by_email);


        Button bt_reg_register = (Button) dialog.findViewById(R.id.bt_reg_register);
        ImageView  img_cross=(ImageView)dialog.findViewById(R.id.img_cross);
        TextView txt_fill_form=(TextView)dialog.findViewById(R.id.txt_fill_form);
        loader1 = (ProgressBar) dialog.findViewById(R.id.progreess1);
        edt_yourname = (EditText) dialog.findViewById(R.id.edt_yourname);
        edt_mobilenumber = (EditText) dialog.findViewById(R.id.edt_mobilenumber);
        edt_email = (EditText) dialog.findViewById(R.id.edt_email);
        edt_service_interested = (Spinner) dialog.findViewById(R.id.edt_service_interested);

        edt_yourname_err = (TextView) dialog.findViewById(R.id.edt_yourname_err);
        edt_yourmobile_err = (TextView) dialog.findViewById(R.id.edt_yourmobile_err);
        edt_youremail_err = (TextView) dialog.findViewById(R.id.edt_youremail_err);
        edt_yourservice_err = (TextView) dialog.findViewById(R.id.edt_yourservice_err);

        //Set Style for Court Address
        String styledText_courtaddress = "Fill this form and get best detail on "+"<font color=#FF0000>" +nameee+ "</font>" ;
        if (!nameee.equalsIgnoreCase(""))
        {
            String upperString_nameee = nameee.substring(0,1).toUpperCase() + nameee.substring(1);
            txt_fill_form.setText(upperString_nameee);

        }
        callservice_Interesed_Ws();
        //------------------------------------ Spinner Service Interested According ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        edt_service_interested.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_service_interested = for_service_interested_bean.getResult().get(position).getServicesName();
                /* s_city_id=for_all_city_list_bean.getResult().get(position).getCityId();*/



                /*
                sp.saveData(getApplicationContext(),"city_name",city_name);
                System.out.println("city**************"+city_name);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

    //    edt_yourname.setOnFocusChangeListener((View.OnFocusChangeListener) this);
/*
        edt_yourname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edt_yourname.getText().toString().trim().equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
                            edt_yourname_err.setVisibility(View.VISIBLE);
                            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
                            edt_yourname_err.setText(Html.fromHtml("This field is required"));
                        }
                    }
                    else if(!isValid(edt_yourname.getText().toString()))
                    {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
                        }
                        edt_yourname_err.setVisibility(View.VISIBLE);
                        //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
                        edt_yourname_err.setText(Html.fromHtml("Special characters are not allowed"));
                    }
                    else
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
                            edt_yourname_err.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });*/


        bt_reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//callsend_Enquiry_Ws(edt_yourname.getText().toString(),edt_mobilenumber.getText().toString(),edt_email.getText().toString(),edt_service_interested.getText().toString());
                Validate1();
                // dialog.dismiss();

            }
        });
        dialog.show();
    }


    private void get_ServiceInterested(final String country_id4) {
        loader1.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_SERVICES, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("CityList", "City List response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader1.setVisibility(View.GONE);

                        Gson gson = new Gson();


                        for_service_interested_bean = gson.fromJson(response, For_Service_Interested_Bean.class);
                        CityListAdapter aa2 = new CityListAdapter(getActivity(), for_service_interested_bean.getResult());
                        edt_service_interested.setAdapter(aa2);

                    }
                    else {
                        loader1.setVisibility(View.GONE);
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();

                        Log.e("errorMsg", errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader1.setVisibility(View.GONE);
                Log.e("CityList", "City List Error: " + error.getMessage());
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
                params.put("regid", str_encoderegid);
                params.put("table", str_table_name);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);


    }

    private void get_Questions() {
        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URLs.URL_QUESTIONS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("QuestionList", "Question List response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        String result = jObj.getString("result");
                        JSONObject jsonObject3=new JSONObject(result);
                        String questions = jsonObject3.getString("questions");
JSONArray jsonArray4=new JSONArray(questions);
                        arr_all_question_list = new ArrayList<>();

for(int k=0;k<jsonArray4.length();k++) {

    arr_all_question_list.add(String.valueOf(jsonArray4.get(k)));

 /*   questionListAdapter= new QuestionListAdapter(getActivity(),arr_all_question_list);
    spn_why_writing_reviews.setAdapter(questionListAdapter);*/
}

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, arr_all_question_list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spn_why_writing_reviews.setAdapter(dataAdapter);

                    } else {
                        loader.setVisibility(View.GONE);
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();

                        Log.e("errorMsg", errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                Log.e("QuestionList", "Question List  Error: " + error.getMessage());
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
            /*    params.put("regid", str_encoderegid);
                params.put("table", str_table_name);*/

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);


    }


    private void Validate2() {

        String str_write_genuine_reviews = edt_write_genuine_reviews.getText().toString().trim();
        boolean iserror=false;

        if(str_rating_experience==null)
        {
            iserror=true;
            txt_rate_your_experience_error.setVisibility(View.VISIBLE);
            txt_rate_your_experience_error.setText("Please select rating.");
        }

        else
        {
            txt_rate_your_experience_error.setVisibility(View.GONE);

        }

        if(str_write_genuine_reviews.equalsIgnoreCase(""))
        {
            iserror=true;
            txt_write_genuine_reviews_error.setVisibility(View.VISIBLE);
            txt_write_genuine_reviews_error.setText("Please select rating.");
        }

        else
        {
            txt_write_genuine_reviews_error.setVisibility(View.GONE);
        }

        if(!iserror)
        {
            iserror=false;
            txt_rate_your_experience_error.setVisibility(View.GONE);
            txt_write_genuine_reviews_error.setVisibility(View.GONE);


            MySharedPref sp = new MySharedPref();
            String ldata = sp.getData(getActivity(), "ldata", "null");
            System.out.println("Ldata is***"+ldata);
            if(ldata.equalsIgnoreCase("null"))
            {
                String str_rat= String.valueOf(rb_rate_your_experience.getRating());
                Intent ii = new Intent(getActivity(), LoginSignupActivity.class);
                ii.putExtra("write_ratingreviews_data","write_ratingreviews_data");
                ii.putExtra("rate_experience",str_rat);
                ii.putExtra("write_genuine",edt_write_genuine_reviews.getText().toString());
                ii.putExtra("why_writing_reviews",str_why_writing_reviews);
                ii.putExtra("wnt_recommendation",str_want_recommended);
                ii.putExtra("str_table_name",str_table_name);
                ii.putExtra("str_city_name",str_city_name);
                ii.putExtra("str_first_name",str_first_name);
                ii.putExtra("str_encoderegid",str_encoderegid);
                startActivity(ii);
            }
            else {

                try {

                    token = sp.getData(getActivity(), "token", "null");
                    System.out.println("Token is***" + token);


                    JSONObject jsonObject = new JSONObject(ldata);
                    user_id = jsonObject.getString("id");
                    System.out.println("Id is***" + user_id);

                    // Validate2();

                    callWrite_Reviews_Ws(user_id, str_encoderegid, nameee, str_table_name, str_city_name, str_rating_experience,
                            str_write_genuine_reviews, str_why_writing_reviews, str_want_recommended,"0");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }






        }

    }

    private void callWrite_Reviews_Ws(String user_id2,String str_encoderegid2,String nameee2,String str_table_name2,String str_city_name2,String str_rating_experience2,String str_write_genuine_reviews2,
                                      String str_why_writing_reviews2,String str_want_recommended2,String one)
    {
        if(Utils.isConnected(getActivity()))
        {
            write_Reviews_Ws(user_id2,str_encoderegid,nameee2,str_table_name2,str_city_name2,str_rating_experience2,
                    str_write_genuine_reviews2,str_why_writing_reviews2,str_want_recommended2,one);
        }

        else {
            Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }


    }




    private void write_Reviews_Ws(final String user_id2, final String str_encoderegid2, final String nameee2, final String str_table_name2,
                                  final String str_city_name2, final String str_rating_experience2, final String str_write_genuine_reviews2,
                                  final String str_why_writing_reviews2, final String str_want_recommended2, final String one) {

        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_REVIEWS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("Send Reviews", " Send Reviews response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        String result=jObj.getString("result");
                        System.out.println("Result Reviews***"+result);
                        JSONObject jsonObject22=new JSONObject(result);
                        String message=jObj.getString("message");

                        Toast.makeText(getActivity(), message,Toast.LENGTH_LONG).show();

                        if (one.equalsIgnoreCase("1"))
                        {
                            callWebService();
                        }
                        else
                        {
                           Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b=new Bundle();
                            b.putString("str_table_name",str_table_name);
                            b.putString("str_city_name",str_city_name);
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name",str_first_name);
                            b.putString("str_encoderegid",str_encoderegid);

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();



                        }


/*Intent ii=new Intent(getActivity(),MainActivity.class);
startActivity(ii);*/

                        }
                        else {
                        loader.setVisibility(View.GONE);

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(), errorMsg,Toast.LENGTH_SHORT).show();

                        Log.e("errorMsg", errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                Log.e("Send Reviews Response", "Send Reviews  Error: " + error.getMessage());
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY","TEST@123");
                params.put("Authorization","Bearer "+token);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                System.out.println("User Id is"+user_id2);
                System.out.println("User Id is$$$"+ str_encoderegid2);
                System.out.println("User Id is$$$"+nameee2 +" table "+str_table_name2 + " city nam "+str_city_name2);
                System.out.println("user deatil "+ str_rating_experience2+" genuine "+ str_write_genuine_reviews2);
                System.out.println("user why "+ str_why_writing_reviews2 +" want recomedd "+ str_want_recommended2);


                params.put("user_id",user_id2);
                params.put("reg_id",str_encoderegid2);
                params.put("name",nameee2);
                params.put("table",str_table_name2);
                params.put("city",str_city_name2);
                params.put("rating",str_rating_experience2);
                params.put("review",str_write_genuine_reviews2);
                params.put("question",str_why_writing_reviews2);
                params.put("recommendation",str_want_recommended2);
         /*       params.put("user_id","37");
                params.put("reg_id","26");
                params.put("name","Dr. Neelam Doctor");
                params.put("table","doctors");
                params.put("city","Pune");
                params.put("rating","0.5");

                params.put("review","hhhhhhh");

                params.put("question","Question 1");
                params.put("recommendation ","Yes");*/

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

    }


    private void Validate1() {

        String str_yourname = edt_yourname.getText().toString().trim();

        String str_mobilenumber = edt_mobilenumber.getText().toString().trim();


        String email1 = edt_email.getText().toString().trim();

        boolean iserror = false;


        if (str_yourname.equalsIgnoreCase("")) {
            iserror = true;
            //   Toast.makeText(getApplicationContext(),"Please Select Registration Process By",Toast.LENGTH_SHORT).show();
            edt_yourname_err.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            edt_yourname_err.setText(Html.fromHtml("Please enter name."));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
            }

        }


        else if(!isValid(str_yourname))
        {
            iserror=true;
            //   Toast.makeText(getApplicationContext(),"Only Alphabates are allowed",Toast.LENGTH_SHORT).show();
            //    edt_stockist_retailer_name.setError(getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_name));
            //   edt_stockist_retailer_name.setError("");

            edt_yourname_err.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            edt_yourname_err.setText(Html.fromHtml("Special characters are not allowed"));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
            }

        }
        else {


            edt_yourname_err.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
            }


        }


        if (str_mobilenumber.equalsIgnoreCase("")) {
            iserror = true;
            //   Toast.makeText(getApplicationContext(),"Please Select Registration Process By",Toast.LENGTH_SHORT).show();
            edt_yourmobile_err.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            edt_yourmobile_err.setText(Html.fromHtml("Please enter mobile number."));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_mobilenumber.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
            }


        }

        else if(edt_mobilenumber.getText().length()<10)
        {
            iserror = true;
            //s   Toast.makeText(getApplicationContext(),"Please Select Registration Process By",Toast.LENGTH_SHORT).show();
            edt_yourmobile_err.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            edt_yourmobile_err.setText(Html.fromHtml("Mobile number must be 10 digits long."));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_mobilenumber.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
            }


        }

        else {


            edt_yourmobile_err.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_mobilenumber.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
            }

        }

if(!email1.equalsIgnoreCase("")) {
    if (!isValidEmail(email1)) {
        iserror = true;
        //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

        //  edt_email.setError("Please Enter Valid Email Address");
        //  edt_email.setError("");

        edt_youremail_err.setVisibility(View.VISIBLE);
        //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
        edt_youremail_err.setText(Html.fromHtml("Please enter a valid email address."));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            edt_email.setBackground(getResources().getDrawable(R.drawable.x_et_bg_login_red));
        }


    } else {


        edt_youremail_err.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            edt_email.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
        }



    }
}

if(str_service_interested.equalsIgnoreCase(""))
{
    iserror = true;
    //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

    //  edt_email.setError("Please Enter Valid Email Address");
    //  edt_email.setError("");

    edt_yourservice_err.setVisibility(View.VISIBLE);
    //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
    edt_yourservice_err.setText(Html.fromHtml("Please select service."));

}

else if(str_service_interested.equalsIgnoreCase("Select Service"))
{
    iserror = true;
    //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

    //  edt_email.setError("Please Enter Valid Email Address");
    //  edt_email.setError("");

    edt_yourservice_err.setVisibility(View.VISIBLE);
    //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
    edt_yourservice_err.setText(Html.fromHtml("Please select service."));

}


        if (!iserror) {
            edt_yourname_err.setVisibility(View.GONE);
            edt_yourmobile_err.setVisibility(View.GONE);
            edt_youremail_err.setVisibility(View.GONE);
            edt_yourservice_err.setVisibility(View.GONE);
            edt_yourservice_err.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_yourname.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_mobilenumber.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                edt_email.setBackground(getResources().getDrawable(R.drawable.x_et_bg_log_gray));
            }
            callsend_Enquiry_Ws(str_yourname, str_mobilenumber, edt_email.getText().toString(), str_service_interested);
        }


    }


    public static boolean isValid(String str)
    {
        boolean isValid = false;
        String expression = "^[a-z_A-Z ]*$";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
        {
            isValid = true;
        }
        return isValid;
    }






    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }




    public class CustomGrid extends BaseAdapter {
        private Context mContext;
ArrayList<String> arr_image2;

        public CustomGrid(Context c,String[] web,int[] Imageid ) {
            mContext = c;

        }

        public CustomGrid(Context c, ArrayList<String> arr_image_name) {
            mContext = c;
            this.arr_image2=arr_image_name;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr_image2.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.gallery_adp, null);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                String str_image_name2=arr_image2.get(position);
             //   System.out.println("Image is@@@"+str_image_name2);
                if(!str_image_name2.equalsIgnoreCase("")) {
                    String converd_url=str_image_name2.replace("candid-15-pc","192.168.1.5");
                    System.out.println("Image is@@@"+converd_url);

                    // Set ImageView for  Cover Photo (Come from WebService)
                    Glide.with(getActivity()).load(converd_url).override(200,150).into(imageView);
                }



            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }


    public class CustomGrid_Corporate_Company_Branches extends BaseAdapter {
        private Context mContext;
        ArrayList<String> arr_image2;

        public CustomGrid_Corporate_Company_Branches(Context c,String[] web,int[] Imageid ) {
            mContext = c;

        }

        public CustomGrid_Corporate_Company_Branches(Context c, ArrayList<String> arr_image_name) {
            mContext = c;
            this.arr_image2=arr_image_name;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr_image2.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.companybraches_adp, null);
                TextView textView1 = (TextView) grid.findViewById(R.id.txt_companybranch_name1);
                TextView txt_companybranch_mobilenumber2= (TextView) grid.findViewById(R.id.txt_companybranch_mobilenumber2);
                TextView txt_companybranch_address2= (TextView) grid.findViewById(R.id.txt_companybranch_address2);
                String str_image_name2=arr_image2.get(position);
                String str_branches_state2=arr_branches_state.get(position);


                textView1.setText(str_image_name2+", "+str_branches_state2+", "+"India");
                txt_companybranch_mobilenumber2.setText(arr_branch_phone_number.get(position));
                txt_companybranch_address2.setText(arr_branches_address.get(position));

                } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }
    private void callsend_Enquiry_Ws(String str_your_name, String str_mobile_number,String str_emailid,String str_service_interested) {
        if(getActivity()!=null) {
            if (Utils.isConnected(getActivity())) {
                send_Enquiry("TEST@123", str_your_name, str_mobile_number,str_emailid,str_service_interested);
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void callservice_Interesed_Ws() {
        if(getActivity()!=null) {
            if (Utils.isConnected(getActivity())) {
              get_ServiceInterested(str_encoderegid);
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void callget_AllQuestions_Ws() {
        if(getActivity()!=null) {
            if (Utils.isConnected(getActivity())) {
                get_Questions();
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        }


    }





    private void send_Enquiry(String XAPIKEY1,final String str_your_name,final String str_mobile_number,final String str_emailid,final String str_service_interested)

    {
        loader1.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_ENQUIRY, new Response.Listener<String>() {
            //  "http://candid13/webservices/api/salesmen", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("Send Enquiry", " Send Enquiry response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader1.setVisibility(View.GONE);
                        dialog.dismiss();

                        String result=jObj.getString("result");
                        System.out.println("Result_ChangeStatusy***"+result);
                        JSONObject jsonObject22=new JSONObject(result);
                        String message=jObj.getString("message");

                        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
                        Search_Through_Name selectedFragment = Search_Through_Name.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                        Bundle b=new Bundle();
                        b.putString("str_table_name",str_table_name);
                        b.putString("str_city_name",str_city_name);
                        // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                        b.putString("str_first_name",str_first_name);
                        b.putString("str_encoderegid",str_encoderegid);

                        selectedFragment.setArguments(b);
                        transaction.replace(R.id.contentFrame, selectedFragment);
                        transaction.addToBackStack("8");
                        transaction.commit();

                        }
                        else {

                        loader1.setVisibility(View.GONE);
                        dialog.dismiss();

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(), errorMsg,Toast.LENGTH_SHORT).show();

                        Log.e("errorMsg", errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader1.setVisibility(View.GONE);
                dialog.dismiss();
                Log.e("ChangeStatus Response", "ChangeStatus  Error: " + error.getMessage());

            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY","TEST@123");


                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("regid",str_encoderegid);
                params.put("name",str_your_name);
                params.put("mobile_number",str_mobile_number);
                params.put("email",str_emailid);
                params.put("service",str_service_interested);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

        }
private class CustomGridRe_Corporate_Company_Branches extends RecyclerView.Adapter<CustomGridRe_Corporate_Company_Branches.MyViewHolder> {

    Activity activity;

    private Context mContext;
    ArrayList<String> arr_branches_city3, arr_branches_state3, arr_branches_pincode3, arr_branch_phone_number3, arr_branches_address3;


    public CustomGridRe_Corporate_Company_Branches(Activity activity, ArrayList<String> arr_branches_city2, ArrayList<String> arr_branches_state2, ArrayList<String> arr_branches_pincode2, ArrayList<String> arr_branch_phone_number2, ArrayList<String> arr_branches_address2) {

        this.activity = activity;

        this.arr_branches_city3 = arr_branches_city2;
        this.arr_branches_state3 = arr_branches_state2;

        this.arr_branches_pincode3 = arr_branches_pincode2;
        this.arr_branch_phone_number3 = arr_branch_phone_number2;
        this.arr_branches_address3 = arr_branches_address2;

    }


    @Override
    public CustomGridRe_Corporate_Company_Branches.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.companybraches_adp, parent, false);

        return new CustomGridRe_Corporate_Company_Branches.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomGridRe_Corporate_Company_Branches.MyViewHolder holder, final int position) {

        System.out.println("Company Branches" + arr_branches_city3.get(position));

        String str_branches_city2 = arr_branches_city3.get(position);
        String str_branches_state2 = arr_branches_state3.get(position);
        System.out.println("Recycle Branch City^^^" + str_branches_city2);

        System.out.println("Recycle Branch State^^^" + str_branches_state2);
        holder.textView1.setText(str_branches_city2 + ", " + str_branches_state2 + ", " + "India");
        holder.txt_companybranch_mobilenumber2.setText(arr_branch_phone_number3.get(position));
        holder.txt_companybranch_address2.setText(arr_branches_address3.get(position) + ", " + str_branches_city2 + " - " + str_branches_state2 + "(India) " + arr_branches_pincode3.get(position));


    }

    @Override
    public int getItemCount() {
        return arr_branches_city3.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, txt_companybranch_mobilenumber2, txt_companybranch_address2;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.txt_companybranch_name1);
            txt_companybranch_mobilenumber2 = (TextView) itemView.findViewById(R.id.txt_companybranch_mobilenumber2);
            txt_companybranch_address2 = (TextView) itemView.findViewById(R.id.txt_companybranch_address2);

        }


    }
}


    private class CustomRe_ReviewandRating extends RecyclerView.Adapter<CustomRe_ReviewandRating.MyViewHolder> {

        Activity activity;

        private Context mContext;
        ArrayList<String> arr_all_reviews_list3, arr_all_rating_list3, arr_all_email_list3,arr_all_name_list3;


        public CustomRe_ReviewandRating(Activity activity, ArrayList<String> arr_all_reviews_list2, ArrayList<String> arr_all_rating_list2, ArrayList<String> arr_all_email_list2, ArrayList<String> arr_all_name_list2) {

            this.activity = activity;

            this.arr_all_reviews_list3 = arr_all_reviews_list2;
            this.arr_all_rating_list3 = arr_all_rating_list2;

            this.arr_all_email_list3 = arr_all_email_list2;
            this.arr_all_name_list3 = arr_all_name_list2;
            }


        @Override
        public CustomRe_ReviewandRating.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rewiews_and_ratings_adp, parent, false);

            return new CustomRe_ReviewandRating.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CustomRe_ReviewandRating.MyViewHolder holder, final int position) {

            try {

                System.out.println("Review Name" + arr_all_name_list3.get(position));
                String str_arr_all_name_list3 = arr_all_name_list3.get(position);

            if (!str_arr_all_name_list3.equalsIgnoreCase("")) {
                char first = str_arr_all_name_list3.charAt(0);
                System.out.println("First Name is$$$" + first);
                String str_first = String.valueOf(first);

                String upperString_firstname = str_first.substring(0, 1).toUpperCase() + str_first.substring(1);

                holder.txt_num_ratng.setText(upperString_firstname);


                String str_uname = arr_all_name_list3.get(position);
                String upperString_uname = str_uname.substring(0, 1).toUpperCase() + str_uname.substring(1);

                holder.txt_user_name_for_Review_rating.setText(upperString_uname);

                String str_arr_all_email_list3 = arr_all_email_list3.get(position);
                StringTokenizer tokens = new StringTokenizer(str_arr_all_email_list3, "@");
                System.out.println("First Token is###" + tokens);

                String firstt = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                System.out.println("First Token is###" + firstt);
                System.out.println("Second Token is###" + second);

                int firstt_length = firstt.length();

                System.out.println("First Token length is###" + firstt_length);

                try {

                    char third = firstt.charAt(3);
                    char fourth = firstt.charAt(4);
                    char fifth = firstt.charAt(5);
                    char sixth = firstt.charAt(6);

                    String replace_str_arr_all_email_list345 = firstt.replace(third, '*').replace(fourth, '*').replace(fifth, '*').replace(sixth, '*');
                    char second_first = second.charAt(0);
                    String replace_str_arr_all_email_list3 = second.replace(second_first, '*');


                    holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                } catch (StringIndexOutOfBoundsException e) {
                    if (firstt_length == 4) {
                        char third = firstt.charAt(3);

                        //  char fourth = firstt.charAt(4);
                        //   char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');
                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }

                    if (firstt_length == 5) {
                        char third = firstt.charAt(3);

                        char fourth = firstt.charAt(4);
                        //   char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*').replace(fourth, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');
                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }

                    if (firstt_length > 5) {
                        char third = firstt.charAt(3);

                        char fourth = firstt.charAt(4);
                        char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*').replace(fourth, '*').replace(fifth, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');


                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    } else {
                        char third = firstt.charAt(3);
                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');


                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }
                }

       /*   if(firstt_length>=6)
             {

                    char third = firstt.charAt(3);
                    char fourth = firstt.charAt(4);
                    char fifth = firstt.charAt(5);
                    char sixth = firstt.charAt(6);

                    String replace_str_arr_all_email_list345= firstt.replace(third, '*').replace(fourth,'*').replace(fifth,'*').replace(sixth,'*');
                    char second_first = second.charAt(0);
                    String replace_str_arr_all_email_list3= second.replace(second_first, '*');


                    holder.txt_userreviews.setText(replace_str_arr_all_email_list345+replace_str_arr_all_email_list3);

                }

                if(firstt_length<6)
                {

                    char third = firstt.charAt(3);
                    char fourth = firstt.charAt(4);
                    char fifth = firstt.charAt(5);

                    String replace_str_arr_all_email_list345= firstt.replace(third, '*').replace(fourth,'*').replace(fifth,'*');
                    char second_first = second.charAt(0);
                    String replace_str_arr_all_email_list3= second.replace(second_first, '*');


                    holder.txt_userreviews.setText(replace_str_arr_all_email_list345+replace_str_arr_all_email_list3);

                }*/


                String str_rating = arr_all_rating_list3.get(position);
                ;
                float float_rating = Float.parseFloat(str_rating);

                float avarage_float_rating = float_rating / 2;

                System.out.println("Avarage Rating in List is%%%" + avarage_float_rating);
                holder.rb_adpshop_ratingg.setRating(avarage_float_rating);
                holder.txt_user_review.setText(arr_all_reviews_list3.get(position));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        }

        @Override
        public int getItemCount() {
            return arr_all_reviews_list3.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_num_ratng, txt_user_name_for_Review_rating, txt_userreviews,txt_user_review;
            RatingBar rb_adpshop_ratingg;


            public MyViewHolder(View itemView) {
                super(itemView);
                txt_num_ratng = (TextView) itemView.findViewById(R.id.txt_num_ratng);
                txt_user_name_for_Review_rating = (TextView) itemView.findViewById(R.id.txt_user_name_for_Review_rating);
                txt_userreviews = (TextView) itemView.findViewById(R.id.txt_userreviews);
                txt_user_review = (TextView) itemView.findViewById(R.id.txt_user_review);
                rb_adpshop_ratingg=(RatingBar)itemView.findViewById(R.id.rb_adpshop_ratingg);
            }


        }
    }
    private class CustomRe_ReviewandRating_Loadmore extends RecyclerView.Adapter<CustomRe_ReviewandRating_Loadmore.MyViewHolder> {

        Activity activity;

        private Context mContext;
        ArrayList<String> arr_all_reviews_list3, arr_all_rating_list3, arr_all_email_list3,arr_all_name_list3;
int j;
String str_desired2;

        public CustomRe_ReviewandRating_Loadmore(Activity activity, ArrayList<String> arr_all_reviews_list2,
                                                 ArrayList<String> arr_all_rating_list2,
                                                 ArrayList<String> arr_all_email_list2, ArrayList<String> arr_all_name_list2,int k) {

            this.activity = activity;

            this.arr_all_reviews_list3 = arr_all_reviews_list2;
            this.arr_all_rating_list3 = arr_all_rating_list2;

            this.arr_all_email_list3 = arr_all_email_list2;
            this.arr_all_name_list3 = arr_all_name_list2;
            this.j = k;
            System.out.println("List value$$$" + j);

        }


        @Override
        public CustomRe_ReviewandRating_Loadmore.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rewiews_and_ratings_adp, parent, false);

            return new CustomRe_ReviewandRating_Loadmore.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CustomRe_ReviewandRating_Loadmore.MyViewHolder holder, final int position) {

            try {

                System.out.println("Review Name" + arr_all_name_list3.get(position));
                String str_arr_all_name_list3 = arr_all_name_list3.get(position);
                if (!str_arr_all_name_list3.equalsIgnoreCase("")) {

                    char first = str_arr_all_name_list3.charAt(0);
                    System.out.println("First Name is$$$" + first);
                    String str_first = String.valueOf(first);

                    String upperString_firstname = str_first.substring(0, 1).toUpperCase() + str_first.substring(1);

                    holder.txt_num_ratng.setText(upperString_firstname);


                    String str_uname = arr_all_name_list3.get(position);
                    String upperString_uname = str_uname.substring(0, 1).toUpperCase() + str_uname.substring(1);

                    holder.txt_user_name_for_Review_rating.setText(upperString_uname);

                    String str_arr_all_email_list3 = arr_all_email_list3.get(position);


                /*  String desiredString_four = str_arr_all_email_list3.substring(4);
                String desiredString_fifth = str_arr_all_email_list3.substring(5);
                    String desiredString_sixth = str_arr_all_email_list3.substring(6);*/


                    //   System.out.println("Desired String###"+str_final);

         /*       char third = str_arr_all_email_list3.charAt(3);
                System.out.println("Third is$$$"+third);
                char fourth = str_arr_all_email_list3.charAt(4);
                System.out.println(" is$$$"+fourth);

                char fifth = str_arr_all_email_list3.charAt(5);
                System.out.println("Fifth is$$$"+fifth);

                char sixth = str_arr_all_email_list3.charAt(6);
                System.out.println("Sixth is$$$"+sixth);

                String final_result = str_arr_all_email_list3.replace(str_arr_all_email_list3.charAt(3),'*').replace(str_arr_all_email_list3.charAt(4),'*')
                        .replace(str_arr_all_email_list3.charAt(5),'*')
                        .replace(str_arr_all_email_list3.charAt(6),'*');

                String maskedEmail = str_arr_all_email_list3.replaceAll("(?<=.{4}).(?=[^@])", "*");*/
                    if (!str_arr_all_email_list3.equalsIgnoreCase("null")) {

                        StringBuilder myName = new StringBuilder(str_arr_all_email_list3);

                        myName.setCharAt(3, '*');
                        myName.setCharAt(4, '*');
                        myName.setCharAt(5, '*');
                        myName.setCharAt(6, '*');


                        holder.txt_userreviews.setText(myName);
                    }



               /* StringTokenizer tokens = new StringTokenizer(str_arr_all_email_list3, "@");
                String firstt = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                System.out.println("First Token is###"+firstt);
                System.out.println("Second Token is###"+second);

                int firstt_length=firstt.length();

                System.out.println("First Token length is###"+firstt_length);

                try {

                    char third = firstt.charAt(3);
                    char fourth = firstt.charAt(4);
                    char fifth = firstt.charAt(5);
                    char sixth = firstt.charAt(6);

                    String replace_str_arr_all_email_list345= firstt.replace(third, '*').replace(fourth,'*').replace(fifth,'*').replace(sixth,'*');
                    char second_first = second.charAt(0);
                    String replace_str_arr_all_email_list3= second.replace(second_first, '*');


                    holder.txt_userreviews.setText(replace_str_arr_all_email_list345+replace_str_arr_all_email_list3);
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    if(firstt_length==4) {
                        char third = firstt.charAt(3);

                        //  char fourth = firstt.charAt(4);
                        //   char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');
                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }

                    if(firstt_length==5) {
                        char third = firstt.charAt(3);

                        char fourth = firstt.charAt(4);
                        //   char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*').replace(fourth, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');
                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }

                    if(firstt_length>5)
                    {
                        char third = firstt.charAt(3);

                        char fourth = firstt.charAt(4);
                        char fifth = firstt.charAt(5);

                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*').replace(fourth, '*').replace(fifth, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');


                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }

                    else
                    {
                        char third = firstt.charAt(3);
                        String replace_str_arr_all_email_list345 = firstt.replace(third, '*');
                        char second_first = second.charAt(0);
                        String replace_str_arr_all_email_list3 = second.replace(second_first, '*');


                        holder.txt_userreviews.setText(replace_str_arr_all_email_list345 + replace_str_arr_all_email_list3);
                    }
                }

*/


                    String str_rating = arr_all_rating_list3.get(position);
                    ;
                    float float_rating = Float.parseFloat(str_rating);

                    float avarage_float_rating = float_rating / 2;

                    System.out.println("Avarage Rating in List is%%%" + avarage_float_rating);
                    holder.rb_adpshop_ratingg.setRating(avarage_float_rating);


                    holder.txt_user_review_more.setText(arr_all_reviews_list3.get(position));
                    holder.txt_user_review.setText(arr_all_reviews_list3.get(position));

                    holder.txt_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.txt_user_review.setVisibility(View.GONE);
                            holder.txt_more.setVisibility(View.GONE);
                            holder.txt_hide.setVisibility(View.VISIBLE);

                            holder.txt_user_review_more.setVisibility(View.VISIBLE);
                            holder.txt_user_review_more.startAnimation(animSlideDown);


                        }
                    });

                    holder.txt_hide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            holder.txt_user_review_more.setVisibility(View.GONE);
                            holder.txt_hide.setVisibility(View.GONE);
                            holder.txt_more.setVisibility(View.VISIBLE);
                            holder.txt_user_review.startAnimation(animSlideUp);
                            holder.txt_user_review.setVisibility(View.GONE);
                        }
                    });

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return j;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_num_ratng, txt_user_name_for_Review_rating, txt_userreviews,txt_user_review,txt_more,txt_user_review_more,txt_hide;
            RatingBar rb_adpshop_ratingg;


            public MyViewHolder(View itemView) {
                super(itemView);
                txt_num_ratng = (TextView) itemView.findViewById(R.id.txt_num_ratng);
                txt_user_name_for_Review_rating = (TextView) itemView.findViewById(R.id.txt_user_name_for_Review_rating);
                txt_userreviews = (TextView) itemView.findViewById(R.id.txt_userreviews);
                txt_user_review = (TextView) itemView.findViewById(R.id.txt_user_review);
                rb_adpshop_ratingg=(RatingBar)itemView.findViewById(R.id.rb_adpshop_ratingg);
                txt_more=(TextView)itemView.findViewById(R.id.txt_more);
                txt_user_review_more=(TextView)itemView.findViewById(R.id.txt_user_review_more);
                txt_hide=(TextView)itemView.findViewById(R.id.txt_hide);
            }


        }
    }

}
