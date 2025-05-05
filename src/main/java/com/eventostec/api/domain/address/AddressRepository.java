package com.eventostec.api.domain.address;

import java.util.Optional;

import com.eventostec.api.domain.event.Event;

public interface AddressRepository {
    Address save(Address address);

    Optional<Address> findByEvent(Event event);

    void deleteById(Long id);
}