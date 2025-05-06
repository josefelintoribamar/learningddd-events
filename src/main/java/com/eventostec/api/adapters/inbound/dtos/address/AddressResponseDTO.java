package com.eventostec.api.adapters.inbound.dtos.address;

public record AddressResponseDTO(
    Long id, 
    String city, 
    String state) {
}