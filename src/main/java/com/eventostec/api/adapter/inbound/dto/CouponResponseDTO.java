package com.eventostec.api.adapter.inbound.dto;

import java.util.Date;

public record CouponResponseDTO(
    Long id, 
    String code, 
    Integer discount, 
    Date valid) {
}