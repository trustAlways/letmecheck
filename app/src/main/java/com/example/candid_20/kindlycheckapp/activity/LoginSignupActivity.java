package com.example.candid_20.kindlycheckapp.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.candid_20.kindlycheckapp.PwdStrength.PasswordStrength;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.fragment.Search_Through_Name;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.storage.MySharedPref;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

   //For Visible and Hide Login and Signup
    RelativeLayout rr_login, rr_signup;
    View v_professionaldetails, v_userrewies;
    TextView txt_professionaldetails, txt_userrewies;
    LinearLayout ll_professionaldetails, ll_userrewies;

    // For Signup
    Spinner spn_country_name;
    EditText edt_signup_name, edt_signup_emailid, edt_signup_mobilenumber, edt_signup_password;
    TextView txt_signup_name, txt_signup_emailid, txt_signup_mobilenumber, txt_signup_password, txt_termscondition_error;
    SeekBar sek_percent;
    CheckBox chk_termscondition;
    Button btn_signup;
    ProgressBar loader,progressBar;
    String str_country_name;
    Bundle b1;
    String str_table_name, str_city_name, str_first_name, str_encoderegid,write_ratingreviews_data,
            rate_experience,write_genuine,why_writing_reviews,wnt_recommendation;


    // For Login
    EditText edt_login_mobile_phonenumber,edt_login_password;
    TextView txt_login_mobile_phonenumber,txt_login_password,txt_forgot_password;
    Button btn_login;
    TextView password_strength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        getBundleData();
        initUI();
    }


    private void getBundleData() {

        b1 = getIntent().getExtras();
        if (b1 != null) {

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

    private void initUI() {
// ---------------------------- For Tab Show -------------------------------------------------------------------------------//
        // Casting LinearLayout  For Professional Details
        ll_professionaldetails = (LinearLayout) findViewById(R.id.ll_professionaldetails);
        // Casting LinearLayout  For UserRewies
        ll_userrewies = (LinearLayout) findViewById(R.id.ll_userrewies);
        // Casting RelativeLayout  For Professional Details
        rr_login = (RelativeLayout) findViewById(R.id.rr_login);
        // Casting RelativeLayout  For UserRewies
        rr_signup = (RelativeLayout) findViewById(R.id.rr_signup);
        // Casting TextView  For Professional Details
        txt_professionaldetails = (TextView) findViewById(R.id.txt_professionaldetails);
        // Casting TextView  For UserRewies
        txt_userrewies = (TextView) findViewById(R.id.txt_userrewies);
        // Casting View  For Professional Details
        v_professionaldetails = (View) findViewById(R.id.v_professionaldetails);
        // Casting View  For UserRewies
        v_userrewies = (View) findViewById(R.id.v_userrewies);

        //LinearLayout Clicklister
        ll_professionaldetails.setOnClickListener(this);
        ll_userrewies.setOnClickListener(this);


        // Casting ProgressBar
        loader=(ProgressBar)findViewById(R.id.loader);

        // ---------------------------- For Signup -------------------------------------------------------------------------------//


        // Casting ProgressBar for Password Strength
        progressBar=(ProgressBar)findViewById(R.id.progressBar);



        // Casting TextView for Password Strength
        password_strength=(TextView)findViewById(R.id.password_strength);

        // Casting EditText   For  Signup Name
        edt_signup_name = (EditText) findViewById(R.id.edt_signup_name);
        // Casting TextView   For  Signup Name Error
        txt_signup_name = (TextView) findViewById(R.id.txt_signup_name);


        // Casting EditText   For  Signup EmailId
        edt_signup_emailid = (EditText) findViewById(R.id.edt_signup_emailid);
        // Casting TextView   For  Signup EmailId Error
        txt_signup_emailid = (TextView) findViewById(R.id.txt_signup_emailid);


        // Casting Spinner   For  Signup Country Name
        spn_country_name = (Spinner) findViewById(R.id.spn_country_name);
        // Casting EditText   For  Signup Mobile number
        edt_signup_mobilenumber = (EditText) findViewById(R.id.edt_signup_mobilenumber);
        // Casting TextView   For  Signup Mobile number Error
        txt_signup_mobilenumber = (TextView) findViewById(R.id.txt_signup_mobilenumber);


        // Casting EditText   For  Signup Password
        edt_signup_password = (EditText) findViewById(R.id.edt_signup_password);
        // Casting TextView   For  Signup Password Error
        txt_signup_password = (TextView) findViewById(R.id.txt_signup_password);
        //OnClick Password ChangeListner
        edt_signup_password.addTextChangedListener(this);


        // Casting CheckBox   For  Signup Terms and Condition
        chk_termscondition = (CheckBox) findViewById(R.id.chk_termscondition);
        // Casting TextView   For  Signup Terms and Condition Error
        txt_termscondition_error = (TextView) findViewById(R.id.txt_termscondition_error);

        // Casting TextView   For  Forgot Password
        txt_forgot_password=(TextView)findViewById(R.id.txt_forgot_password);

        // Casting Button   For  Signup
        btn_signup = (Button) findViewById(R.id.btn_signup);


// Set Spinner
        final List<String> list = new ArrayList<String>();
        list.add("+91(IND)");
        spn_country_name.setPrompt("Country Code");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_country_name.setAdapter(dataAdapter);


        // Spinner ClickListerner

        //------------------------------------ Spinner Contry Name ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        spn_country_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_country_name = list.get(position);
                /* s_city_id=for_all_city_list_bean.getResult().get(position).getCityId();*/



                /*
                sp.saveData(getApplicationContext(),"city_name",city_name);
                System.out.println("city**************"+city_name);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Button SignUp OnClickListener
        btn_signup.setOnClickListener(this);

        // ---------------------------- For Login -------------------------------------------------------------------------------//
        // Casting EditText   For  Login Email
        edt_login_mobile_phonenumber=(EditText)findViewById(R.id.edt_login_mobile_phonenumber);

        // Casting TextView   For  Login Email Error
        txt_login_mobile_phonenumber= (TextView) findViewById(R.id.txt_login_mobile_phonenumber);

        // Casting EditText   For  Login Password
        edt_login_password=(EditText)findViewById(R.id.edt_login_password);

        // Casting TextView   For  Login Password
        txt_login_password= (TextView) findViewById(R.id.txt_login_password);
        // Casting Button   For  Login
        btn_login = (Button) findViewById(R.id.btn_login);

        // Button Login OnClickListener
        btn_login.setOnClickListener(this);

        // Text ForgotPassword OnClickListener
        txt_forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == ll_professionaldetails) {
            rr_login.setVisibility(View.VISIBLE);
            rr_signup.setVisibility(View.GONE);

            v_professionaldetails.setVisibility(View.VISIBLE);
            v_userrewies.setVisibility(View.GONE);
            txt_professionaldetails.setTextColor(getResources().getColor(R.color.txt_colr_blue));
            txt_userrewies.setTextColor(getResources().getColor(R.color.gray));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll_professionaldetails.setBackground(getResources().getDrawable(R.drawable.white_bg));
                ll_userrewies.setBackground(getResources().getDrawable(R.drawable.grey_bg));
                }
        }

        if (v == ll_userrewies) {
            rr_login.setVisibility(View.GONE);
            rr_signup.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll_professionaldetails.setBackground(getResources().getDrawable(R.drawable.grey_bg));
                ll_userrewies.setBackground(getResources().getDrawable(R.drawable.white_bg));

            }

            v_professionaldetails.setVisibility(View.GONE);
            v_userrewies.setVisibility(View.VISIBLE);
            txt_professionaldetails.setTextColor(getResources().getColor(R.color.gray));
            txt_userrewies.setTextColor(getResources().getColor(R.color.txt_colr_blue));



        }

        if (v == btn_signup) {
            Validate1();
        }
        if (v == btn_login) {
            Validate2();
        }
        if (v == txt_forgot_password) {
            Intent ii=new Intent(LoginSignupActivity.this,ForgotPassword_Activity.class);
            startActivity(ii);
        }

    }

    private void Validate1() {

        String str_signup_name = edt_signup_name.getText().toString();
        String str_signup_emailid = edt_signup_emailid.getText().toString();
        String str_signup_mobilenumber = edt_signup_mobilenumber.getText().toString();
        String str_signup_password = edt_signup_password.getText().toString();

        boolean iserror = false;

        if (str_signup_name.equalsIgnoreCase("")) {
            iserror = true;

            txt_signup_name.setVisibility(View.VISIBLE);
            txt_signup_name.setText("This field is required.");
        }
        else if(!isValid(str_signup_name))
        {
            txt_signup_name.setVisibility(View.VISIBLE);
            txt_signup_name.setText("Special characters are not allowed.");
        }
        else {
            txt_signup_name.setVisibility(View.GONE);

        }



        if (str_signup_emailid.equalsIgnoreCase("")) {
            iserror = true;

            txt_signup_emailid.setVisibility(View.VISIBLE);
            txt_signup_emailid.setText("This field is required.");
        } else if (!isValidEmail(str_signup_emailid)) {
            iserror = true;
            //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

            //  edt_email.setError("Please Enter Valid Email Address");
            //  edt_email.setError("");

            txt_signup_emailid.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_signup_emailid.setText("Please Enter Valid Email");

        } else {
            txt_signup_emailid.setVisibility(View.GONE);

        }

        if(str_signup_mobilenumber.equalsIgnoreCase(""))
        {
            iserror=true;
            // Toast.makeText(getApplicationContext(),getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password),Toast.LENGTH_SHORT).show();
            //   edt_password.setError(getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password));
            //  edt_password.setError("");

            txt_signup_mobilenumber.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_signup_mobilenumber.setText("This field is required.");
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);

        }

        else  if(edt_signup_mobilenumber.getText().length()<10)
        {
            iserror=true;

            txt_signup_mobilenumber.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_signup_mobilenumber.setText("The Mobile Number must be 10 digits.");
        }
        else
        {
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_ATOP);
            txt_signup_mobilenumber.setVisibility(View.GONE);

        }


        if(str_signup_password.equalsIgnoreCase(""))
        {
            iserror=true;
            // Toast.makeText(getApplicationContext(),getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password),Toast.LENGTH_SHORT).show();
            //   edt_password.setError(getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password));
            //  edt_password.setError("");

            txt_signup_password.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_signup_password.setText("This field is required");
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);

        }

        else  if(edt_signup_password.getText().length()<6)
        {
            iserror=true;

            txt_signup_password.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_signup_password.setText("The Password must be 6 Digit");
        }
        else
        {
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_ATOP);
            txt_signup_password.setVisibility(View.GONE);

        }

        if(!chk_termscondition.isChecked())
        {
            iserror=true;
            txt_termscondition_error.setVisibility(View.VISIBLE);
            txt_termscondition_error.setText("Please Check Terms and Condition.");
        }

        else
        {
            txt_termscondition_error.setVisibility(View.GONE);
        }

        if(!iserror)

        {
            txt_signup_name.setVisibility(View.GONE);
            txt_signup_emailid.setVisibility(View.GONE);
            txt_signup_mobilenumber.setVisibility(View.GONE);
            txt_signup_password.setVisibility(View.GONE);
            txt_termscondition_error.setVisibility(View.GONE);
            // Call WebService for SignUp
            callWebService_ForSignUp(str_signup_name,str_signup_emailid,str_signup_mobilenumber,str_signup_password);

        }



    }

    private void callWebService_ForSignUp(String str_signup_name2, String str_signup_emailid2, String str_signup_mobilenumber2, String str_signup_password2) {
        if (Utils.isConnected(getApplicationContext())) {
            sign_Up("TEST@123", str_signup_name2, str_signup_emailid2,str_signup_mobilenumber2,str_signup_password2);
        } else {
            Toast.makeText(LoginSignupActivity.this, "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }

        }

    private void sign_Up(String s, final String str_signup_name3, final String str_signup_emailid3, final String str_signup_mobilenumber3, final String str_signup_password3) {

        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_REGISTRATION, new Response.Listener<String>() {
            //  "http://candid13/webservices/api/salesmen", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("SignUp", " Sign Up response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        String result=jObj.getString("result");
                        System.out.println("Result_ChangeStatusy***"+result);
                        JSONObject jsonObject22=new JSONObject(result);
                        String message=jObj.getString("message");

                        Toast.makeText(LoginSignupActivity.this, message,Toast.LENGTH_SHORT).show();

                        Intent ii=new Intent(LoginSignupActivity.this,LoginSignupActivity.class);
                     //   ii.putExtra("loginsignup","loginsignup");
                        startActivity(ii);

                        } else {
                        loader.setVisibility(View.GONE);

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(LoginSignupActivity.this, errorMsg,Toast.LENGTH_SHORT).show();

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

                Log.e("SignUp Response", "SignUp  Error: " + error.getMessage());
                Toast.makeText(LoginSignupActivity.this,
                        "Server Problem", Toast.LENGTH_LONG).show();
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

                params.put("name",str_signup_name3);
                params.put("email",str_signup_emailid3);
                params.put("mobile",str_signup_mobilenumber3);
                params.put("password",str_signup_password3);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(LoginSignupActivity.this).addToRequestQueue(strReq, cancel_req_tag);
    }


    private void log_In(String s, final String str_signup_emailid3, final String str_signup_password3) {

        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_LOGIN, new Response.Listener<String>() {
            //  "http://candid13/webservices/api/salesmen", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("LogIn", "Log In response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        String token=jObj.getString("token");
                        System.out.println("Token is###"+token);

                        String result=jObj.getString("result");
                        System.out.println("Log in response ***"+result);
                        JSONObject jsonObject22=new JSONObject(result);
                        String message=jObj.getString("message");

                        Toast.makeText(LoginSignupActivity.this, message,Toast.LENGTH_SHORT).show();

                        MySharedPref sp=new MySharedPref();

                        sp.saveData(getApplicationContext(),"token",token);
                        sp.saveData(getApplicationContext(),"ldata",result+"");

                        Intent ii=new Intent(LoginSignupActivity.this,MainActivity.class);
                        ii.putExtra("write_ratingreviews_data","write_ratingreviews_data");
                        ii.putExtra("rate_experience",rate_experience);
                        ii.putExtra("write_genuine",write_genuine);
                        ii.putExtra("why_writing_reviews",why_writing_reviews);
                        ii.putExtra("wnt_recommendation",wnt_recommendation);
                        ii.putExtra("str_table_name",str_table_name);
                        ii.putExtra("str_city_name",str_city_name);
                        ii.putExtra("str_first_name",str_first_name);
                        ii.putExtra("str_encoderegid",str_encoderegid);
                        startActivity(ii);



                    }
                    else {
                        loader.setVisibility(View.GONE);

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(LoginSignupActivity.this, errorMsg,Toast.LENGTH_SHORT).show();

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

                Log.e("LogIn Response", "LogIn  Error: " + error.getMessage());
                Toast.makeText(LoginSignupActivity.this,
                        "Server Problem", Toast.LENGTH_LONG).show();
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
                params.put("email",str_signup_emailid3);
                params.put("password",str_signup_password3);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(LoginSignupActivity.this).addToRequestQueue(strReq, cancel_req_tag);
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



    private void Validate2() {

        String str_login_mobile_phonenumber = edt_login_mobile_phonenumber.getText().toString();
        String str_login_password = edt_login_password.getText().toString();
        boolean iserror=false;
        if (str_login_mobile_phonenumber.equalsIgnoreCase("")) {
            iserror = true;

            txt_login_mobile_phonenumber.setVisibility(View.VISIBLE);
            txt_login_mobile_phonenumber.setText("This field is required.");
        } else if (!isValidEmail(str_login_mobile_phonenumber)) {
            iserror = true;
            //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();

            //  edt_email.setError("Please Enter Valid Email Address");
            //  edt_email.setError("");

            txt_login_mobile_phonenumber.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_login_mobile_phonenumber.setText("Please Enter Valid Email");

        } else {
            txt_login_mobile_phonenumber.setVisibility(View.GONE);

        }

        if(str_login_password.equalsIgnoreCase(""))
        {
            iserror=true;
            // Toast.makeText(getApplicationContext(),getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password),Toast.LENGTH_SHORT).show();
            //   edt_password.setError(getString(R.string.edit_error_msg)+" "+getString(R.string.stockist_retailer_password));
            //  edt_password.setError("");

            txt_login_password.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_login_password.setText("This field is required");
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);

        }

        else  if(edt_login_password.getText().length()<6)
        {
            iserror=true;

            txt_login_password.setVisibility(View.VISIBLE);
            //  txt_stockist_retailer_namefield.setText(Html.fromHtml(getString(R.string.edit_error_msg)+""+getString(R.string.stockist_retailer_name)<sup>2</sup>));
            txt_login_password.setText("The Password must be 6 Digit");
        }
        else
        {
            //   edt_password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_ATOP);
            txt_login_password.setVisibility(View.GONE);

        }

        if(!iserror)

        {
            txt_login_mobile_phonenumber.setVisibility(View.GONE);
            txt_login_password.setVisibility(View.GONE);
            // Call WebService for SignUp
            callWebService_ForLogin(str_login_mobile_phonenumber,str_login_password);

        }
    }

    private void callWebService_ForLogin(String str_signup_emailid2, String str_signup_password2) {
        if (Utils.isConnected(getApplicationContext())) {
            log_In("TEST@123",str_signup_emailid2,str_signup_password2);
        } else {
            Toast.makeText(LoginSignupActivity.this, "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updatePasswordStrengthView(s.toString());
    }

    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
   //    strengthView.setTextColor(str.getColor());

 // progressBar.getProgressDrawable().setColorFilter(str.getColor(), PorterDuff.Mode.SRC_IN);


        if (str.getText(this).equals("Weak")) {

            progressBar.setProgress(25);

// set the drawable as progress drawable
          //  progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_bar2));
        } else if (str.getText(this).equals("Medium")) {

            progressBar.setProgress(50);

        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);

        } else {
            progressBar.setProgress(100);

        }
    }


}
