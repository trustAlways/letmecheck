package com.example.candid_20.kindlycheckapp.storage;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by technorizen on 19/4/17.
 */

public class MySharedPref {
     SharedPreferences sp;

    public void saveData(Context context, String key, String value)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String getData(Context context, String key, String value)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        return sp.getString(key,value);
    }
    public void saveData1(Context context, String key, String[] value)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key +" _size ", value.length);
        for(int i=0 ; i<=value.length ; i++){
        editor.putString(key + "_ " + i, value[i]);
    }
        //editor.putString(key,value);
      //  editor.putString(key,value);
        editor.commit();
    }



    public String getData1(Context context, String key, String value)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        return sp.getString(key,value);
    }
    public void DeleteData(Context context)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


    public void NullData(Context context , String key)
    {
        sp = context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,null);
        editor.commit();
    }
    public  void removeFromSharedPreferences(Context context, String key) {

        sp=context.getSharedPreferences("KindlyCheckApp",context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
     /*  if (mContext != null) {
            SharedPreferences mSharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);
            if (mSharedPreferences != null)
                mSharedPreferences.edit().remove(key).commit();
        }  */
    }






}
