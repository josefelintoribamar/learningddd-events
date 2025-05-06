package com.eventostec.api.domain.repositories;

import java.util.Optional;

import com.eventostec.api.domain.Address;
import com.eventostec.api.domain.Event;

public interface AddressRepository {
    Address save(Address address);

    Optional<Address> findByEvent(Event event);

    void deleteById(Long id);
}