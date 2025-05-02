package com.eventostec.api.domain.coupon;

import com.eventostec.api.domain.event.Event;
import java.util.Date;
import java.util.UUID;

public class Coupon {
    private UUID id;

    private String code;
    private Integer discount;
    private Date valid;

    private Event event;

    public Coupon() {
    }

    public Coupon(UUID id, String code, Integer discount, Date valid) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.valid = valid;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Date getValid() {
        return valid;
    }

    public Event getEvent() {
        return event;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setValid(Date valid) {
        this.valid = valid;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
