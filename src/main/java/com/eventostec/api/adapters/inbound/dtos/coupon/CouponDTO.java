package com.eventostec.api.adapters.inbound.dtos.coupon;

import java.util.Date;

public record CouponDTO(
    Long id,
    String code,
    Integer discount,
    Date valid) {
}