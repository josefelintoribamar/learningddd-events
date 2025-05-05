package com.eventostec.api.application.service;

import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.adapters.outbound.repositories.JpaEventRepository;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.address.AddressRepository;
import com.eventostec.api.domain.address.AddressRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.mappers.AddressMapper;
import com.eventostec.api.mappers.EventMapper;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl {

    private final JpaEventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final EventMapper eventMapper;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(JpaEventRepository eventRepository, AddressRepository addressRepository, EventMapper eventMapper, AddressMapper addressMapper) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.eventMapper = eventMapper;
        this.addressMapper = addressMapper;
    }

    public Address addAddressToEvent(Long eventId, AddressRequestDTO addressData) {
        JpaEvent jpaEvent = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Address address = this.addressMapper.toDomain(addressData);
        address.setEvent(this.eventMapper.toDomain(jpaEvent));
        return addressRepository.save(address);
    }

    public Optional<Address> findByEvent(Event event) {
        return addressRepository.findByEvent(event);
    }
}
