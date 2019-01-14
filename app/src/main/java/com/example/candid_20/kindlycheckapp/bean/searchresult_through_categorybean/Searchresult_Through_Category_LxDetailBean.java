package com.example.candid_20.kindlycheckapp.bean.searchresult_through_categorybean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Searchresult_Through_Category_LxDetailBean {
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("reg_id")
    @Expose
    private String regId;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("qualification_names")
    @Expose
    private List<String> qualificationNames = null;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("no_of_reviews")
    @Expose
    private String noOfReviews;
    @SerializedName("table_name")
    @Expose
    private String tableName;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<String> getQualificationNames() {
        return qualificationNames;
    }

    public void setQualificationNames(List<String> qualificationNames) {
        this.qualificationNames = qualificationNames;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getNoOfReviews() {
        return noOfReviews;
    }

    public void setNoOfReviews(String noOfReviews) {
        this.noOfReviews = noOfReviews;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
