package com.eventostec.api.domain.data;

import java.util.Date;

public class Coupon {
    private Long id;

    private String code;
    private Integer discount;
    private Date valid;

    private Event event;

    public Coupon() {
    }

    public Coupon(Long id, String code, Integer discount, Date valid) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.valid = valid;
    }

    public Long getId() {
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

    public void setId(Long id) {
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
