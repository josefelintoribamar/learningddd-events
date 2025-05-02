package com.eventostec.api.domain.address;

import java.util.UUID;

public record AddressDTO (
    UUID id,
    String city,
    String state) {
}