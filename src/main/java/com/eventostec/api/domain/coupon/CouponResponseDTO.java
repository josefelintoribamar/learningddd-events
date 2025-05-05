package com.eventostec.api.domain.coupon;

import java.util.Date;

public record CouponResponseDTO(
    Long id, 
    String code, 
    Integer discount, 
    Date valid) {
}