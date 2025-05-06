package com.eventostec.api.mappers;

import com.eventostec.api.adapters.inbound.dtos.event.EventDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventRequestDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventResponseDTO;
import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.domain.Address;
import com.eventostec.api.domain.Coupon;
import com.eventostec.api.domain.Event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mappings({
        @Mapping(source = "eventRequestDTO.id", target = "id", qualifiedByName = "handleId"),
        @Mapping(source = "eventRequestDTO.title", target = "title"),
        @Mapping(source = "eventRequestDTO.description", target = "description"),
        @Mapping(source = "eventRequestDTO.eventUrl", target = "eventUrl"),
        @Mapping(source = "eventRequestDTO.date", target = "date", qualifiedByName = "epochToDate"),
        @Mapping(source = "eventRequestDTO.remote", target = "remote"),
        @Mapping(target = "address.city", ignore = true),
        @Mapping(target = "address.state", ignore = true)
    })
    @SuppressWarnings("UnmappedTargetProperties")
    Event toDomain(EventRequestDTO eventRequestDTO);

    @Mappings({
        @Mapping(source = "event.title", target = "title"),
        @Mapping(source = "event.description", target = "description"),
        @Mapping(source = "event.eventUrl", target = "eventUrl"),
        @Mapping(source = "event.date", target = "date", qualifiedByName = "dateToEpoch"),
        @Mapping(source = "event.remote", target = "remote"),
    })
    EventRequestDTO toRequestDto(Event event);

    @Mappings({
        @Mapping(source = "event.title", target = "title"),
        @Mapping(source = "event.description", target = "description"),
        @Mapping(source = "event.eventUrl", target = "eventUrl"),
        @Mapping(source = "event.date", target = "date", qualifiedByName = "dateToEpoch")
    })
    EventDTO toDto(Event event);

    @Mappings({
        @Mapping(source = "jpaEvent.title", target = "title"),
        @Mapping(source = "jpaEvent.description", target = "description"),
        @Mapping(source = "jpaEvent.eventUrl", target = "eventUrl"),
        @Mapping(source = "jpaEvent.date", target = "date"),
        @Mapping(source = "jpaEvent.remote", target = "remote"),
        @Mapping(source = "jpaEvent.id", target = "id"),
    })
    Event toDomain(JpaEvent jpaEvent);

    default EventResponseDTO toResponseDto(Event event, Optional<Address> address) {
        String city = address.map(Address::getCity).orElse("");
        String state = address.map(Address::getState).orElse("");
    
        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate().getTime(),
                city,
                state,
                event.getRemote(),
                event.getEventUrl());
    }

    default EventDTO toDto(Event event, Optional<Address> address, List<Coupon> coupons) {
        List<EventDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());
    
        String city = address.map(Address::getCity).orElse("");
        String state = address.map(Address::getState).orElse("");
    
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate().getTime(),
                city,
                state,
                event.getEventUrl(),
                couponDTOs);
    }

    @Named("handleId")
    default Long handleId(Long id) {
        return id != null ? id : null;
    }

    @Named("epochToDate")
    default Date epochToDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @Named("dateToEpoch")
    default Long dateToEpoch(Date date) {
        return date != null ? date.getTime() : null;
    }
}
