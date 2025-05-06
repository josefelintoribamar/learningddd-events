package com.eventostec.api.adapter.outbound.repository;

import com.eventostec.api.adapter.outbound.entity.JpaAddress;
import com.eventostec.api.domain.data.Address;
import com.eventostec.api.domain.data.Event;
import com.eventostec.api.domain.repository.AddressRepository;
import com.eventostec.api.mapper.AddressMapper;

import java.util.Optional;

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
        final Optional<JpaAddress> optional = this.jpaAddressRepository.findByJpaEvent_Id(event.getId());
        return optional.map(addressMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        this.jpaAddressRepository.deleteById(id);
    }
}