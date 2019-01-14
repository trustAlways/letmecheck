package com.example.candid_20.kindlycheckapp.bean.for_service_interested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class For_Service_LXinterested_Bean {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("services_name")
    @Expose
    private String servicesName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }
}
