package com.example.candid_20.kindlycheckapp.bean.for_city_list_bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City_List_Bean {

    @SerializedName("result")
    @Expose
    private List<City_List_lxBean> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<City_List_lxBean> getResult() {
        return result;
    }

    public void setResult(List<City_List_lxBean> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
