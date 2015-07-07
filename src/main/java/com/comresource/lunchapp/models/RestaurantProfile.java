package com.comresource.lunchapp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT_PROFILE")
public class RestaurantProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "RESTAURANT_PROFILE_ID")
    private String restaurantProfileId;
    @Column(name = "RESTAURANT_ID")
    private String restaurantId;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "TIME_FACTOR")
    private String timeFactor;
    @Column(name = "COST_FACTOR")
    private String costFactor;
    @Column(name = "POST_LUNCH_FULLNESS_FACTOR")
    private String postLunchFullnessFactor;
    @Column(name = "DELICIOUSNESS_FACTOR")
    private String deliciousnessFactor;
    @Column(name = "POST_LUNCH_DISCOMFORT_FACTOR")
    private String postLunchDiscomfortFactor;
    @Column(name = "OPT_IN")
    private String optIn;

    //restaurantProfileId
    public String getRestaurantProfileId() {
        return restaurantProfileId;
    }
    public void setRestaurantProfileId(String restaurantProfileId) {
        this.restaurantProfileId = restaurantProfileId;
    }
    //restaurantId
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    //userId
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    //timeFactor
    public String getTimeFactor() {
        return timeFactor;
    }
    public void setTimeFactor(String timeFactor) {
        this.timeFactor = timeFactor;
    }
    //costFactor
    public String getCostFactor() {
        return costFactor;
    }
    public void setCostFactor(String costFactor) {
        this.costFactor = costFactor;
    }
    //postLunchFullnessFactor
    public String getPostLunchFullnessFactor() {
        return postLunchFullnessFactor;
    }
    public void setPostLunchFullnessFactor(String postLunchFullnessFactor) {
        this.postLunchFullnessFactor = postLunchFullnessFactor;
    }
    //deliciousnessFactor
    public String getDeliciousnessFactor() {
        return deliciousnessFactor;
    }
    public void setDeliciousnessFactor(String deliciousnessFactor) {
        this.deliciousnessFactor = deliciousnessFactor;
    }
    //postLunchDiscomfortFactor
    public String getPostLunchDiscomfortFactor() {
        return postLunchDiscomfortFactor;
    }
    public void setPostLunchDiscomfortFactor(String postLunchDiscomfortFactor) {
        this.postLunchDiscomfortFactor = postLunchDiscomfortFactor;
    }
    //optIn
    public String getOptIn() {
        return optIn;
    }
    public void setOptIn(String optIn) {
        this.optIn = optIn;
    }

    public void update(String timeFactor, String costFactor, String postLunchFullnessFactor, String deliciousnessFactor, String postLunchDiscomfortFactor, String optIn) {
        this.timeFactor = timeFactor;
        this.costFactor = costFactor;
        this.postLunchFullnessFactor = postLunchFullnessFactor;
        this.deliciousnessFactor = deliciousnessFactor;
        this.postLunchDiscomfortFactor = postLunchDiscomfortFactor;
        this.optIn = optIn;
    }

   
}
