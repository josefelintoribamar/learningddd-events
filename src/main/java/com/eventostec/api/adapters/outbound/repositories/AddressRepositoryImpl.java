package com.eventostec.api.adapters.outbound.repositories;

import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.address.AddressRepository;
import com.eventostec.api.mappers.AddressMapper;
import com.eventostec.api.adapters.outbound.entities.JpaAddress;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private final JpaAddressRepository jpaAddressRepository;
    private final AddressMapper addressMapper;

    public AddressRepositoryImpl(JpaAddressRepository jpaAddressRepository, AddressMapper addressMapper) {
        this.jpaAddressRepository = jpaAddressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Address save(Address address) {
        final JpaAddress jpaAddress = new JpaAddress(address);
        this.jpaAddressRepository.save(jpaAddress);
        return new Address(jpaAddress.getId(),
            jpaAddress.getCity(),
            jpaAddress.getState());
    }

    @Override
    public Optional<Address> findByEvent(Event event) {
        final Optional<JpaAddress> optional = this.jpaAddressRepository.findByEventId(event.getId());
        return optional.map(addressMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        this.jpaAddressRepository.deleteById(id);
    }
}