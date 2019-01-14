package com.example.candid_20.kindlycheckapp.fragment.searchthroughname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.candid_20.kindlycheckapp.adapter.CityListAdapter;
import com.example.candid_20.kindlycheckapp.bean.for_service_interested.For_Service_Interested_Bean;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.fragment.Search_Through_Name;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serach_Through_Name_ImageSlide extends Fragment implements View.OnClickListener {

View v;
Bundle b1;
ProgressBar loader;
String str_encoderegid,str_first_name;
ViewPager mPager;
ArrayList<String> arr_img_by_id;
    ImageView img_slider,img_back;
    TextView title;


    public static Serach_Through_Name_ImageSlide newInstance() {
        Serach_Through_Name_ImageSlide fragment = new Serach_Through_Name_ImageSlide();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.imagesilder_search_through_name, container, false);
        getBundleData();
        initUi();
        return v;
    }

    private void getBundleData() {

        b1 = this.getArguments();
        if (b1 != null) {
            str_encoderegid = b1.getString("str_encoderegid");
            str_first_name = b1.getString("str_first_name");

        }
    }


    private void initUi() {
        // ---------------------------- For Slider and Back ImageView for Show and  Hide-------------------------------------------------------------------------------//
// Slider Icon
        img_slider=(ImageView)getActivity().findViewById(R.id.img_slider);
        //Back Icon
        img_back=(ImageView)getActivity().findViewById(R.id.img_back);

        //Casting TextView for Title
        title=(TextView)getActivity().findViewById(R.id.txt_title);

        //Set Title
        if(str_first_name!=null)
        {
            String upperString_str_first_name = str_first_name.substring(0,1).toUpperCase() + str_first_name.substring(1);

            title.setText(upperString_str_first_name);
        }



    loader=(ProgressBar)v.findViewById(R.id.progress1);
        mPager = (ViewPager)v.findViewById(R.id.pager);

        //ImageView  Call Clicklister for Back

        img_slider.setVisibility(View.GONE);

        img_back.setVisibility(View.VISIBLE);

        img_back.setOnClickListener(this);
        callfor_GetImage();


    }
    private void callfor_GetImage() {
        if(getActivity()!=null) {
            if (Utils.isConnected(getActivity())) {
                get_Image();
            } else {
                Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void get_Image() {


            loader.setVisibility(View.VISIBLE);

            // Tag used to cancel the request
            String cancel_req_tag = "area";
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    URLs.URL_IMAGEBYID, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("ImageList", "Image List response: " + response.toString());


                    try {
                        JSONObject jObj = new JSONObject(response);
                        String error = jObj.getString("status");

                        if (error.equals("success")) {
                            loader.setVisibility(View.GONE);

                          String result=jObj.getString("result");

                            JSONArray jsonArray3=new JSONArray(result);
arr_img_by_id=new ArrayList<>();
                            for(int k=0;k<jsonArray3.length();k++)
                            {
                                arr_img_by_id.add(String.valueOf(jsonArray3.get(k)));
                            }


                            CustomPagerAdapter aa2 = new CustomPagerAdapter(getActivity(),arr_img_by_id);
                            mPager.setAdapter(aa2);

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
                    Log.e("CityList", "City List Error: " + error.getMessage());
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
                    params.put("regid", str_encoderegid);

                    return params;
                }
            };
            // Adding request to request queue
            AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

            }

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
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<String> arr_img_by_id3;

        public CustomPagerAdapter(Context context, ArrayList<String> arr_img_by_id2) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arr_img_by_id3=arr_img_by_id2;

        }

        @Override
        public int getCount() {
            return arr_img_by_id3.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            return view == ((LinearLayout) object);
            return view.equals(object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


            String str_image_name2=arr_img_by_id3.get(position);
            //   System.out.println("Image is@@@"+str_image_name2);
            if(!str_image_name2.equalsIgnoreCase("")) {
                String converd_url=str_image_name2.replace("candid-15-pc","192.168.1.5");
                System.out.println("Image is@@@"+converd_url);

                // Set ImageView for  Cover Photo (Come from WebService)
                Glide.with(getActivity()).load(converd_url).into(imageView);
            }
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
          //  container.removeView((LinearLayout) object);
            container.removeView((View) object);

        }
    }

}
