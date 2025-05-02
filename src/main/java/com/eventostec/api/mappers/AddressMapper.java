package com.eventostec.api.mappers;

import com.eventostec.api.adapters.outbound.entities.JpaAddress;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.address.AddressRequestDTO;
import com.eventostec.api.domain.address.AddressResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "addressRequestDTO.city", target = "city"),
        @Mapping(source = "addressRequestDTO.state", target = "state")
    })
    Address toDomain(AddressRequestDTO addressRequestDTO);

    @Mappings({
        @Mapping(source = "address.city", target = "city"),
        @Mapping(source = "address.state", target = "state")
    })
    AddressRequestDTO toDto(Address address);

    @Mappings({
        @Mapping(source = "address.city", target = "city"),
        @Mapping(source = "address.state", target = "state")
    })
    AddressResponseDTO toResponseDto(Address address);

    @Mappings({
        @Mapping(source = "jpaAddress.city", target = "city"),
        @Mapping(source = "jpaAddress.state", target = "state"),
        @Mapping(source = "jpaAddress.id", target = "id")
    })
    Address toDomain(JpaAddress jpaAddress);
}