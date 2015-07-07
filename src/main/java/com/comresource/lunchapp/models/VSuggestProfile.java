package com.comresource.lunchapp.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "V_SUGGEST_PROFILE")
@NamedNativeQuery(name = "suggestionForUser",
        query = "SELECT S.*, P.COST_FACTOR, P.DELICIOUSNESS_FACTOR, P.POST_LUNCH_DISCOMFORT_FACTOR, "
        + "P.POST_LUNCH_FULLNESS_FACTOR, P.TIME_FACTOR, P.OPT_IN, ? as USER_NAME, P.USER_ID "
        + "FROM V_SUGGEST_PROFILE S LEFT JOIN RESTAURANT_PROFILE P ON S.RESTAURANT_ID = P.RESTAURANT_ID "
        + "AND P.USER_ID IN (SELECT USER_ID FROM USERS WHERE USER_NAME = ?)", resultClass = VSuggestProfile.class)
//   Query query = session.getNamedQuery("SampleNameQuery");
//   query.setParameter(0,"fsdfsdf");
//   query.setParameter(1,"");
//   List objList = query.list();

public class VSuggestProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "SUGGEST_ID")
    private String suggestId;
    @Column(name = "RESTAURANT_ID")
    private String restaurantId;
    @Column(name = "RESTAURANT_NAME")
    private String restaurantName;
    @Column(name = "SUGGEST_DATE")
    @Type(type = "date")
    private Date suggestDate;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "WEBSITE")
    private String website;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "TIME_FACTOR")
    private Float timeFactor;
    @Column(name = "COST_FACTOR")
    private Float costFactor;
    @Column(name = "POST_LUNCH_FULLNESS_FACTOR")
    private Float postLunchFullnessFactor;
    @Column(name = "DELICIOUSNESS_FACTOR")
    private Float deliciousnessFactor;
    @Column(name = "POST_LUNCH_DISCOMFORT_FACTOR")
    private Float postLunchDiscomfortFactor;
    @Column(name = "OPT_IN")
    private Integer optIn;
    @Column(name = "AVG_TIME_FACTOR")
    private Float avgTimeFactor;
    @Column(name = "AVG_COST_FACTOR")
    private Float avgCostFactor;
    @Column(name = "AVG_POST_LUNCH_FULLNESS_FACTOR")
    private Float avgPostLunchFullnessFactor;
    @Column(name = "AVG_DELICIOUSNESS_FACTOR")
    private Float avgDeliciousnessFactor;
    @Column(name = "AVG_POST_LUNCH_DISCOMFORT_FACTOR")
    private Float avgPostLunchDiscomfortFactor;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuggestId() {
        return this.suggestId;
    }

    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Date getSuggestDate() {
        return this.suggestDate;
    }

    public void setSuggestDate(Date suggestDate) {
        this.suggestDate = suggestDate;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getTimeFactor() {
        return this.timeFactor;
    }

    public void setTimeFactor(Float timeFactor) {
        this.timeFactor = timeFactor;
    }

    public Float getCostFactor() {
        return this.costFactor;
    }

    public void setCostFactor(Float costFactor) {
        this.costFactor = costFactor;
    }

    public Float getPostLunchFullnessFactor() {
        return this.postLunchFullnessFactor;
    }

    public void setPostLunchFullnessFactor(Float postLunchFullnessFactor) {
        this.postLunchFullnessFactor = postLunchFullnessFactor;
    }

    public Float getDeliciousnessFactor() {
        return this.deliciousnessFactor;
    }

    public void setDeliciousnessFactor(Float deliciousnessFactor) {
        this.deliciousnessFactor = deliciousnessFactor;
    }

    public Float getPostLunchDiscomfortFactor() {
        return this.postLunchDiscomfortFactor;
    }

    public void setPostLunchDiscomfortFactor(Float postLunchDiscomfortFactor) {
        this.postLunchDiscomfortFactor = postLunchDiscomfortFactor;
    }

    public Integer getOptIn() {
        return this.optIn;
    }

    public void setOptIn(Integer optIn) {
        this.optIn = optIn;
    }

    public Float getAvgTimeFactor() {
        return this.avgTimeFactor;
    }

    public void setAvgTimeFactor(Float avgTimeFactor) {
        this.avgTimeFactor = avgTimeFactor;
    }

    public Float getAvgCostFactor() {
        return this.avgCostFactor;
    }

    public void setAvgCostFactor(Float avgCostFactor) {
        this.avgCostFactor = avgCostFactor;
    }

    public Float getAvgPostLunchFullnessFactor() {
        return this.avgPostLunchFullnessFactor;
    }

    public void setAvgPostLunchFullnessFactor(Float avgPostLunchFullnessFactor) {
        this.avgPostLunchFullnessFactor = avgPostLunchFullnessFactor;
    }

    public Float getAvgDeliciousnessFactor() {
        return this.avgDeliciousnessFactor;
    }

    public void setAvgDeliciousnessFactor(Float avgDeliciousnessFactor) {
        this.avgDeliciousnessFactor = this.deliciousnessFactor;
    }

    public Float getAvgPostLunchDiscomfortFactor() {
        return this.avgPostLunchDiscomfortFactor;
    }

    public void setAvgPostLunchDiscomfortFactor(Float avgPostLunchDiscomfortFactor) {
        this.avgPostLunchDiscomfortFactor = avgPostLunchDiscomfortFactor;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
