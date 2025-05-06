package com.eventostec.api.application.service;

import com.eventostec.api.adapter.inbound.dto.AddressRequestDTO;
import com.eventostec.api.adapter.outbound.entity.JpaEvent;
import com.eventostec.api.adapter.outbound.repository.JpaEventRepository;
import com.eventostec.api.domain.data.Address;
import com.eventostec.api.domain.data.Event;
import com.eventostec.api.domain.repository.AddressRepository;
import com.eventostec.api.mapper.AddressMapper;
import com.eventostec.api.mapper.EventMapper;

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
