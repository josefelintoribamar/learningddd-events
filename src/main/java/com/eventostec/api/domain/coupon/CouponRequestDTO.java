package com.eventostec.api.domain.coupon;

import jakarta.validation.constraints.NotNull;

public record CouponRequestDTO(
    @NotNull(message = "O código do cupom deve ser informado") String code,
    @NotNull(message = "O desconto deve ser informado") Integer discount,
    @NotNull(message = "A validade deve ser informada") Long valid) {
}
