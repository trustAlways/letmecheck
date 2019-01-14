package com.example.candid_20.kindlycheckapp.bean.searchresult_through_categorybean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Searchresult_Through_Category_Bean {
    @SerializedName("result")
    @Expose
    private List<Searchresult_Through_Category_LxDetailBean> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Searchresult_Through_Category_LxDetailBean> getResult() {
        return result;
    }

    public void setResult(List<Searchresult_Through_Category_LxDetailBean> result) {
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
