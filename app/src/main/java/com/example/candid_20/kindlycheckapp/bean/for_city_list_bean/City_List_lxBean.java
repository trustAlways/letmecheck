package com.example.candid_20.kindlycheckapp.bean.for_city_list_bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City_List_lxBean {

    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("std_code")
    @Expose
    private String stdCode;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

}
