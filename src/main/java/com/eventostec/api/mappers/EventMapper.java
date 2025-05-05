package com.eventostec.api.mappers;

import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
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
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "eventRequestDTO.title", target = "title"),
        @Mapping(source = "eventRequestDTO.description", target = "description"),
        @Mapping(source = "eventRequestDTO.eventUrl", target = "eventUrl"),
        @Mapping(source = "eventRequestDTO.date", target = "date", qualifiedByName = "epochToDate"),
        @Mapping(source = "eventRequestDTO.remote", target = "remote"),
        @Mapping(target = "address.city", ignore = true), // Ignorar city
        @Mapping(target = "address.state", ignore = true) // Ignorar state
    })
    Event toDomain(EventRequestDTO eventRequestDTO);

    @Mappings({
        @Mapping(source = "event.title", target = "title"),
        @Mapping(source = "event.description", target = "description"),
        @Mapping(source = "event.eventUrl", target = "eventUrl"),
        @Mapping(source = "event.date", target = "date", qualifiedByName = "dateToEpoch"),
        @Mapping(source = "event.remote", target = "remote"),
    })
    EventRequestDTO toDto(Event event);

    @Mappings({
        @Mapping(source = "jpaEvent.title", target = "title"),
        @Mapping(source = "jpaEvent.description", target = "description"),
        @Mapping(source = "jpaEvent.eventUrl", target = "eventUrl"),
        @Mapping(source = "jpaEvent.date", target = "date"),
        @Mapping(source = "jpaEvent.remote", target = "remote"),
        @Mapping(source = "jpaEvent.id", target = "id"),
    })
    Event toDomain(JpaEvent jpaEvent);

    default EventDTO toDTO(Event event, Optional<Address> address, List<Coupon> coupons) {
        List<EventDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                address.isPresent() ? address.get().getCity() : "",
                address.isPresent() ? address.get().getState() : "",
                event.getEventUrl(),
                couponDTOs);
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
