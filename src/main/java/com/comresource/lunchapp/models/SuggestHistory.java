package com.comresource.lunchapp.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SUGGEST_HISTORY")
public class SuggestHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SUGGEST_ID")
    private String suggestId;

    @Column(name = "RESTAURANT_ID")
    private String restaurantId;

    @Column(name = "SUGGEST_DATE")
    @Type(type = "date")
    private Date suggestDate;

    public String getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getSuggestDate() {
        return suggestDate;
    }

    public void setSuggestDate(Date suggestDate) {
        this.suggestDate = suggestDate;
    }

}
