/*
 * Author:Jason
 */
package com.comresource.lunchapp.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.HEAD;

/**
 *
 * @author LabUser1
 */
@Entity
@Table(name = "IS_OPEN")
public class Is_Open implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "OPEN_ID")
    private String openId;
    @Column(name = "RESTAURANT_ID")
    private String restaurantId;
    @Column(name = "MONDAY")
    private Integer monday;
    @Column(name = "TUESDAY")
    private Integer tuesday;
    @Column(name = "WEDNESDAY")
    private Integer wednesday;
    @Column(name = "THURSDAY")
    private Integer thursday;
    @Column(name = "FRIDAY")
    private Integer friday;
    @Column(name = "SATURDAY")
    private Integer saturday;
    @Column(name = "SUNDAY")
    private Integer sunday;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }


    
       public void update( Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Integer saturday, Integer sunday) {
           
           this.setMonday(monday);
           this.setTuesday(tuesday);
           this.setWednesday(wednesday);
           this.setThursday(thursday);
           this.setFriday(friday);
           this.setSaturday(saturday);
           this.setSunday(sunday);
       }

    public void update(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
