package com.example.candid_20.kindlycheckapp.bean.appointment;

public class sessions {

    String session;
    String start_time;
    String end_time;

    public sessions(String session, String start_time, String end_time) {
        this.session = session;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
