package com.eventostec.api.adapters.inbound.dtos.address;

import jakarta.validation.constraints.NotNull;

public record AddressRequestDTO(
    @NotNull(message = "O nome da cidade deve ser informado") String city,
    @NotNull(message = "O estado da cidade deve ser informado") String state) {
}
