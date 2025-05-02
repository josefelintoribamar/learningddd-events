package com.eventostec.api.domain.coupon;

import java.util.Date;
import java.util.UUID;

public record CouponDTO(
    UUID id,
    String code,
    Integer discount,
    Date valid) {
}