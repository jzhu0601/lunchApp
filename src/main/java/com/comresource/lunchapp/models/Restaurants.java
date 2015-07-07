package com.comresource.lunchapp.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANTS")
public class Restaurants implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "RESTAURANT_ID")
    private String restaurantId;
    @Column(name = "NAME")
    private String name;
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

    //restaurantId
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    //name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //city
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    //state
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    //address
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    //zip
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    //website
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public void update(String name, String city, String state, String address, String zip, String website) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.address = address;
        this.zip = zip;
        this.website = website;
    }

    public void update(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
