package com.example.candid_20.kindlycheckapp.bean.for_service_interested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class For_Service_Interested_Bean {

    @SerializedName("result")
    @Expose
    private List<For_Service_LXinterested_Bean> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<For_Service_LXinterested_Bean> getResult() {
        return result;
    }

    public void setResult(List<For_Service_LXinterested_Bean> result) {
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
