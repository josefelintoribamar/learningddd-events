package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.List;

public record EventDTO(
    Long id,
    String title,
    String description,
    Date date,
    String city,
    String state,
    String eventUrl,
    List<CouponDTO> coupons) {

    public record CouponDTO(
        String code,
        Integer discount,
        Date valid) {
    }
}

