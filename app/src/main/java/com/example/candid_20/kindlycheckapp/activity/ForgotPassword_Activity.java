package com.example.candid_20.kindlycheckapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_registration_reg,txt_reset_mobile_phonenumber_error;
    ProgressBar loader;
    EditText edt_reset_mobile_phonenumber;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUI();
    }

    private void initUI() {

        // Casting TextView for login or Signup Screen and Set Text
        txt_registration_reg=(TextView) findViewById(R.id.txt_registration_reg);

        // Casting EditText for Reset Emailid
        edt_reset_mobile_phonenumber=(EditText)findViewById(R.id.edt_reset_mobile_phonenumber);

        // Casting EditText for Reset Emailid error
        txt_reset_mobile_phonenumber_error=(TextView) findViewById(R.id.txt_reset_mobile_phonenumber_error);

        // Casting Button for Reset Password
        btn_reset=(Button)findViewById(R.id.btn_reset);
        // Casting ProgressBar for reset Password
        loader=(ProgressBar) findViewById(R.id.progress_forgotpassword);

        //------------------------------------ Set Text Color for Login and Signup-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        String styledText = "New User/Already Registered? "+" <font color='red'>" + "Click here to Register or Login" + "</font> ";
        txt_registration_reg.setText(Html.fromHtml(styledText));

       //Text Login and Signup OnClickListener
        txt_registration_reg.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        if(v==txt_registration_reg)
        {
            Intent ii=new Intent(ForgotPassword_Activity.this,LoginSignupActivity.class);
            startActivity(ii);
        }


        if(v==btn_reset)

        {
            Validate1();
        }
    }


    public void onBackPressed() {


        Intent ii=new Intent(ForgotPassword_Activity.this,LoginSignupActivity.class);
        startActivity(ii);
    }
    private void Validate1() {
        String email1=edt_reset_mobile_phonenumber.getText().toString().trim();
        boolean iserror=false;

        if(email1.equalsIgnoreCase(""))
        {
            iserror=true;
            txt_reset_mobile_phonenumber_error.setVisibility(View.VISIBLE);
            txt_reset_mobile_phonenumber_error.setText(Html.fromHtml("This Field is Required"));

        }
        else if(!isValidEmail(email1))
        {
            iserror=true;
            txt_reset_mobile_phonenumber_error.setVisibility(View.VISIBLE);
            txt_reset_mobile_phonenumber_error.setText(Html.fromHtml("Please Enter Valid Email Address"));
        }
        else
        {

            txt_reset_mobile_phonenumber_error.setVisibility(View.GONE);
        }

        if(!iserror)
        {
            txt_reset_mobile_phonenumber_error.setVisibility(View.GONE);

            //------------------------------------ Call WebService for User ResetPassword-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
            callWebservice_for_User_Resetpassword();
        }
    }


    private void callWebservice_for_User_Resetpassword() {
        String XAPIKEY="TEST@123";
        String Authorization="Basic YWRtaW46MTIzNA==";

        String email=edt_reset_mobile_phonenumber.getText().toString().trim();

        //------------------------------------ Check Internet Connectivity-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        if(Utils.isConnected(getApplicationContext())) {

            reset_PasswordCall(XAPIKEY, Authorization,email);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }


    }
    private void reset_PasswordCall(String XAPIKEY1, String Authorization1, final String email1)

    {
        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                //"http://192.168.1.4/webservices/api/stockiest_login?", new Response.Listener<String>() {
                URLs.URL_USER_FORGET_PASSWORD, new Response.Listener<String>() {

            //  "http://candid13/webservices/api/salesmen", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("ResetPassword", " Reset Password response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);
                        String result=jObj.getString("result");
                        System.out.println("Result_ResetPassword***"+result);

                        String errorMsg = jObj.getString("message");
                        //       Toast.makeText(Stokist_Forgot_Password.this, errorMsg,Toast.LENGTH_SHORT).show();

                        Toast.makeText(ForgotPassword_Activity.this,"Please check email for new Password",Toast.LENGTH_SHORT).show();
                        Intent ii=new Intent(ForgotPassword_Activity.this,LoginSignupActivity.class);
                        //   ii.putExtra("reset_password",result);
                        startActivity(ii);


                    } else {
                        loader.setVisibility(View.GONE);
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(ForgotPassword_Activity.this, errorMsg,Toast.LENGTH_SHORT).show();

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
                Log.e("ResetPassword Response", "Reset Password  Error: " + error.getMessage());
                Toast.makeText(ForgotPassword_Activity.this,
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY","TEST@123");
                //  params.put("Authorization","Basic YWRtaW46MTIzNA==");

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email1);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(ForgotPassword_Activity.this).addToRequestQueue(strReq, cancel_req_tag);


    }



    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }
}
