package com.eventostec.api.domain.repository;

import java.util.Optional;

import com.eventostec.api.domain.data.Address;
import com.eventostec.api.domain.data.Event;

public interface AddressRepository {
    Address save(Address address);

    Optional<Address> findByEvent(Event event);

    void deleteById(Long id);
}