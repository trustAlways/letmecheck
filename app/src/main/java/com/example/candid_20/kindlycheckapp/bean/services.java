package com.example.candid_20.kindlycheckapp.bean;

public class services {

    String service_id;
    String service_name;
    private boolean isSelected;

    public services(String service_id, String service_name) {

        this.service_id = service_id;
        this.service_name = service_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }




}
