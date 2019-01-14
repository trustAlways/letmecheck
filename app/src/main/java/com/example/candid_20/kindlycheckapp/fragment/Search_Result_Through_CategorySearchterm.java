package com.example.candid_20.kindlycheckapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.activity.MainActivity;
import com.example.candid_20.kindlycheckapp.bean.searchresult_through_categorybean.Searchresult_Through_Category_Bean;
import com.example.candid_20.kindlycheckapp.bean.searchresult_through_categorybean.Searchresult_Through_Category_LxDetailBean;
import com.example.candid_20.kindlycheckapp.constant.Utils;
import com.example.candid_20.kindlycheckapp.other.URLs;
import com.example.candid_20.kindlycheckapp.volleyconnector.AppSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search_Result_Through_CategorySearchterm extends Fragment implements View.OnClickListener {

    View v;
    Bundle b1;
    String str_table_name,str_city_name,str_name,str_encoderegid;

    ProgressBar loader;
    RelativeLayout rr_InboxDetailRV,rr_left;
    RecyclerView InboxDetailRV;
    ImageView img_slider,img_back,img_search,img_search_ascending;
    Searchresult_Through_Category_Bean searchresult_through_category_bean1;
TextView txt_avail_pro,title;
    EditText edt_search_bar;
    Search_CategorySearch_Result_Adp   search_categorySearch_result_adp;
    ArrayList<String> arr_qualification;
    private boolean sortAscending=true;
    private boolean unSorted=true;

    public static Search_Result_Through_CategorySearchterm newInstance() {
        Search_Result_Through_CategorySearchterm fragment = new Search_Result_Through_CategorySearchterm();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.search_through_corporate, container, false);
        getBundleData();
        initUi();



        return v;
    }

    private void getBundleData() {

        b1=this.getArguments();
        if(b1!=null) {
            str_table_name = b1.getString("str_table_name");
            str_city_name=b1.getString("str_city_name");
            str_name=b1.getString("str_name");
            str_encoderegid=b1.getString("str_encoderegid");
        }

    }

    private void initUi() {

        loader=(ProgressBar)v.findViewById(R.id.progress);
        txt_avail_pro=(TextView)v.findViewById(R.id.txt_avail_pro);

        //------------------------------------ Casting  RelativeLayout-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        rr_InboxDetailRV=(RelativeLayout)v.findViewById(R.id.rr_InboxDetailRV);
        //------------------------------------ Casting  RecyclerView-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        InboxDetailRV=(RecyclerView)v.findViewById(R.id.InboxDetailRV);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        InboxDetailRV.setLayoutManager(mLayoutManager);
        InboxDetailRV.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        InboxDetailRV.setItemAnimator(new DefaultItemAnimator());

        img_slider=(ImageView)getActivity().findViewById(R.id.img_slider);
        img_back=(ImageView)getActivity().findViewById(R.id.img_back);
        //Casting ImageView for Desending Order
        img_search=(ImageView)v.findViewById(R.id.img_search);

        //Casting ImageView for Ascending Order
        img_search_ascending=(ImageView)v.findViewById(R.id.img_search_ascending);
        // Casting RelativeLayout for Back
        rr_left=(RelativeLayout)getActivity().findViewById(R.id.rr_left);



        //Casting TextView for Title
        title=(TextView)getActivity().findViewById(R.id.txt_title);

        // For First letter Capital
        String upperString_title_name = str_name.substring(0, 1).toUpperCase() + str_name.substring(1);

        //Set TextView for Title

        title.setText(upperString_title_name);

        //------------------------------------ Casting  EditText-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        edt_search_bar=(EditText) v.findViewById(R.id.edt_search_bar);


        img_slider.setVisibility(View.GONE);

        img_back.setVisibility(View.VISIBLE);
        img_search.setOnClickListener(this);
        img_search_ascending.setOnClickListener(this);
        rr_left.setOnClickListener(this);
        img_back.setOnClickListener(this);


        callWebService();


        edt_search_bar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
                if (searchresult_through_category_bean1 != null) {
                    System.out.println("List Size@@@"+searchresult_through_category_bean1.getResult().size());

                    if (search_categorySearch_result_adp != null) {

                            search_categorySearch_result_adp.filter(s.toString());
                            //  customList1.notifyDataSetChanged();

                    }

                    // TODO Auto-generated method stub
                }
            }
        });

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

        if(v==img_search)
        {
            img_search.setVisibility(View.GONE);
            img_search_ascending.setVisibility(View.VISIBLE);

        if(searchresult_through_category_bean1!=null) {

            Collections.reverse(searchresult_through_category_bean1.getResult());

            search_categorySearch_result_adp = new Search_CategorySearch_Result_Adp(getActivity(), searchresult_through_category_bean1.getResult());
            InboxDetailRV.setAdapter(search_categorySearch_result_adp);
        }
        }

        if(v==img_search_ascending)
        {
            img_search.setVisibility(View.VISIBLE);
            img_search_ascending.setVisibility(View.GONE);

            if(searchresult_through_category_bean1!=null) {
            search_categorySearch_result_adp = new Search_CategorySearch_Result_Adp(getActivity(), searchresult_through_category_bean1.getResult());
                InboxDetailRV.setAdapter(search_categorySearch_result_adp);
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
    private void callWebService() {

        if(Utils.isConnected(getActivity())) {
            getSearchResuly_byCategory();
        }
        else
        {
            Toast.makeText(getActivity(), "Please Check network conection..", Toast.LENGTH_SHORT).show();
        }


    }
    private void getSearchResuly_byCategory() {
        loader.setVisibility(View.VISIBLE);

        // Tag used to cancel the request
        String cancel_req_tag = "area";
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_SEARCHRESULT, new com.android.volley.Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("All Search CategoryList", "All Search CategoryList: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");

                    if (error.equals("success")) {
                        loader.setVisibility(View.GONE);

                        Gson gson = new Gson();

                        searchresult_through_category_bean1=gson.fromJson(response,Searchresult_Through_Category_Bean.class);
                        System.out.println("List Size:"+searchresult_through_category_bean1.getResult().size());
                       /* String result = jObj.getString("result");
                        JSONObject jsonObject2=new JSONObject(result);
                        // Fetch Qualification from Web Service
                        String qualification = jsonObject2.getString("qualification_name");
                        System.out.println("Qualification  is%%%" + qualification);


                        JSONArray jsonArray2 = new JSONArray(qualification);
                        arr_qualification = new ArrayList<>();

                        for (int k = 0; k < jsonArray2.length(); k++) {

                            String str_qual=jsonArray2.get(k)+", ";


                            arr_qualification.add(str_qual);
                            System.out.println("Qualification Size@@@"+arr_qualification.size());

                        }*/

                        if(searchresult_through_category_bean1.getResult().size()>0)
                        {
                         //   txt_avail_pro.setVisibility(View.VISIBLE);
                          //  txt_avail_pro.setText("There are"+" "+searchresult_through_category_bean1.getResult().size()+" "+"Results Available");


                            //   btn_add_products.setVisibility(View.VISIBLE);

                            System.out.println("List Size:"+searchresult_through_category_bean1.getResult().size());



                               search_categorySearch_result_adp = new Search_CategorySearch_Result_Adp(getActivity(),searchresult_through_category_bean1.getResult());
        InboxDetailRV.setAdapter(search_categorySearch_result_adp);



                        }
                        String errorMsg = jObj.getString("message");
                        if(getActivity()!=null) {
                         /*   txt_avail_pro.setVisibility(View.VISIBLE);
                            txt_avail_pro.setText("No products added yet.");*/
                           //   Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                        }




                    } else {
                        loader.setVisibility(View.GONE);
                       // txt_avail_pro.setVisibility(View.VISIBLE);
                       // txt_avail_pro.setText("No Result Found.");
                        String errorMsg = jObj.getString("message");
                        if(getActivity()!=null) {

                            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
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
                Log.e("All Search CategoryList", "All Search CategoryList Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Server Problem", Toast.LENGTH_LONG).show();
            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY","TEST@123");

                /*params.put("Authorization","Basic YWRtaW46MTIzNA==");
                params.put("Content-Type","application/x-www-form-urlencoded");*/

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("query_string",str_name);
                params.put("city",str_city_name);

                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);

        }



    private class Search_CategorySearch_Result_Adp extends RecyclerView.Adapter<Search_CategorySearch_Result_Adp.MyViewHolder> {

        Activity activity;
        //     Button btn_confirm_order,btn_processed_order,btn_delivered_order,btn_complete_order,btn_order_completed;

        List<Searchresult_Through_Category_LxDetailBean> arrayList = new ArrayList<>();
        List<Searchresult_Through_Category_LxDetailBean> arSearchlist;
        ArrayList<String> arr_pending=new ArrayList<>();
        CardView rr_first_descp;





        public Search_CategorySearch_Result_Adp(Activity activity, List<Searchresult_Through_Category_LxDetailBean> city_list_lxBeans) {


            this.activity = activity;
            this.arrayList = city_list_lxBeans;
            this.arSearchlist = new ArrayList<>();

            if (arrayList!=null) {
                arSearchlist.addAll(arrayList);
            }

        }


        @Override
        public Search_CategorySearch_Result_Adp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seach_through_category_adp, parent, false);

            return new Search_CategorySearch_Result_Adp.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Search_CategorySearch_Result_Adp.MyViewHolder holder, final int position) {

            System.out.println("City Name$$$"+arrayList.get(position).getName());

            try {
                String str_table_name=arrayList.get(position).getTableName();

                if(str_table_name.equalsIgnoreCase("doctors") || str_table_name.equalsIgnoreCase("lawyers")) {

                    //     holder.cuisineslistitem.setText(arrayList.get(position).getName());
                    final String pro_title = arrayList.get(position).getTitle();
                    String upperString_title = pro_title.substring(0, 1).toUpperCase() + pro_title.substring(1);
                    final String pro_name = arrayList.get(position).getName();
                    String upperString = pro_name.substring(0, 1).toUpperCase() + pro_name.substring(1);

                    final String pro_lastname = arrayList.get(position).getLname();
                    String upperString_lastname = pro_lastname.substring(0, 1).toUpperCase() + pro_lastname.substring(1);

                    holder.cuisineslistitem.setText(upperString_title + " " + upperString + " " + upperString_lastname);


                    List<String> qual = arrayList.get(position).getQualificationNames();

                    arr_qualification = new ArrayList<>();

                    for (int k = 0; k < qual.size(); k++) {


                        String str_qual = qual.get(k) + ", ";


                        arr_qualification.add(str_qual);
                        System.out.println("Qualification Size@@@" + arr_qualification.size());

                    }

                    // Set TextView  For Qualification
                    for (int z = 0; z < arr_qualification.size(); z++) {


                        if (z == arr_qualification.size() - 1) {
                            String str_qualificatin2 = arr_qualification.get(arr_qualification.size() - 1);
                            System.out.println("Qualification###" + str_qualificatin2);

                            if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                holder.txt_products_prizee.setText("");

                            } else {
                                String str_qua = str_qualificatin2.replace(", ", "");
                                holder.txt_products_prizee.append(str_qua);
                            }

                        } else {
                            String str_qualificatin2 = arr_qualification.get(z);
                            System.out.println("Qualification in List###" + str_qualificatin2);

                            if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                holder.txt_products_prizee.setText("");

                            } else {
                                holder.txt_products_prizee.append(arr_qualification.get(z));

                            }


                        }
                    }
                    String url1 = arrayList.get(position).getLogo();
                    if (!url1.equalsIgnoreCase("")) {
                        String converd_url = url1.replace("candid-15-pc", "192.168.1.4");
                        Glide.with(getActivity()).load(converd_url).into(holder.img_icon);
                    }
                    holder.txt_distributor_namee.setText(arrayList.get(position).getAddressLine2());
                    holder.txt_review.setText(arrayList.get(position).getNoOfReviews() + " " + "Reviews");
                    String str_rating = arrayList.get(position).getAverageRating();
                    holder.rb_adpshop_rating.setRating(Float.parseFloat(str_rating));
                    rr_first_descp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Search_Through_Name selectedFragment =
                                    Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b = new Bundle();
                            b.putString("str_table_name", arrayList.get(position).getTableName());
                            b.putString("str_city_name", str_city_name);
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name", arrayList.get(position).getName());
                            b.putString("str_encoderegid", arrayList.get(position).getRegId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("9");
                            transaction.commit();


                        }
                    });
                }



                if(str_table_name.equalsIgnoreCase("corporate") || str_table_name.equalsIgnoreCase("business")) {

                    //     holder.cuisineslistitem.setText(arrayList.get(position).getName());
                    final String pro_title = arrayList.get(position).getTitle();
                    String upperString_title = pro_title.substring(0, 1).toUpperCase() + pro_title.substring(1);
                    final String pro_name = arrayList.get(position).getName();
                    String upperString = pro_name.substring(0, 1).toUpperCase() + pro_name.substring(1);

                    final String pro_lastname = arrayList.get(position).getCname();
                    String upperString_lastname = pro_lastname.substring(0, 1).toUpperCase() + pro_lastname.substring(1);

                    holder.cuisineslistitem.setText(upperString_lastname);


                    List<String> qual = arrayList.get(position).getQualificationNames();

                    arr_qualification = new ArrayList<>();

                    for (int k = 0; k < qual.size(); k++) {


                        String str_qual = qual.get(k) + ", ";


                        arr_qualification.add(str_qual);
                        System.out.println("Qualification Size@@@" + arr_qualification.size());

                    }

                    // Set TextView  For Qualification
                    for (int z = 0; z < arr_qualification.size(); z++) {


                        if (z == arr_qualification.size() - 1) {
                            String str_qualificatin2 = arr_qualification.get(arr_qualification.size() - 1);
                            System.out.println("Qualification###" + str_qualificatin2);

                            if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                holder.txt_products_prizee.setText("");

                            } else {
                                String str_qua = str_qualificatin2.replace(", ", "");
                                holder.txt_products_prizee.append(str_qua);
                            }

                        } else {
                            String str_qualificatin2 = arr_qualification.get(z);
                            System.out.println("Qualification in List###" + str_qualificatin2);

                            if (str_qualificatin2.equalsIgnoreCase("null, ")) {
                                holder.txt_products_prizee.setText("");

                            } else {
                                holder.txt_products_prizee.append(arr_qualification.get(z));

                            }


                        }
                    }
                    String url1 = arrayList.get(position).getLogo();
                    if (!url1.equalsIgnoreCase("")) {
                        String converd_url = url1.replace("candid-15-pc", "192.168.1.4");
                        Glide.with(getActivity()).load(converd_url).into(holder.img_icon);
                    }
                    holder.txt_distributor_namee.setText(arrayList.get(position).getAddressLine2());
                    holder.txt_review.setText(arrayList.get(position).getNoOfReviews() + " " + "Reviews");
                    String str_rating = arrayList.get(position).getAverageRating();
                    holder.rb_adpshop_rating.setRating(Float.parseFloat(str_rating));
                    rr_first_descp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Search_Through_Name selectedFragment =
                                    Search_Through_Name.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            Bundle b = new Bundle();
                            b.putString("str_table_name", arrayList.get(position).getTableName());
                            b.putString("str_city_name", str_city_name);
                            // b.putString("str_product_img",for_allProducts_lxDetails_beans.get(position).getImageUrl());
                            b.putString("str_first_name", arrayList.get(position).getName());
                            b.putString("str_encoderegid", arrayList.get(position).getRegId());

                            selectedFragment.setArguments(b);
                            transaction.replace(R.id.contentFrame, selectedFragment);
                            transaction.addToBackStack("9");
                            transaction.commit();


                        }
                    });
                }



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
            TextView cuisineslistitem,txt_distributor_namee,txt_review,txt_products_prizee,txt_productquantity,orderStatus,paymentmode;
            ImageView img_icon;
            RatingBar rb_adpshop_rating;

            public MyViewHolder(View itemView) {
                super(itemView);
                cuisineslistitem=(TextView)itemView.findViewById(R.id.txt_product_namee);
                txt_distributor_namee=(TextView)itemView.findViewById(R.id.txt_distributor_namee);
                txt_products_prizee=(TextView)itemView.findViewById(R.id.txt_products_prizee);
                txt_review=(TextView)itemView.findViewById(R.id.txt_review);
                rr_first_descp=(CardView)itemView.findViewById(R.id.cardlist_item);
                img_icon=(ImageView) itemView.findViewById(R.id.img_profile);
                rb_adpshop_rating=(RatingBar) itemView.findViewById(R.id.rb_adpshop_rating);
            }


        }



        public void filter(String charText) {



            charText = charText.toString().toLowerCase();
            arrayList.clear();
            if (charText.length() == 0) {
                arrayList.addAll(arSearchlist);
            } else {
                for (Searchresult_Through_Category_LxDetailBean wp : arSearchlist) {
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
