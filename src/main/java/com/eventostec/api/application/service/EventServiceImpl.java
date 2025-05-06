package com.eventostec.api.application.service;

import com.eventostec.api.adapters.inbound.dtos.address.AddressRequestDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventRequestDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventResponseDTO;
import com.eventostec.api.application.usecases.EventUseCases;
import com.eventostec.api.domain.Address;
import com.eventostec.api.domain.Coupon;
import com.eventostec.api.domain.Event;
import com.eventostec.api.domain.EventAddressProjection;
import com.eventostec.api.domain.Pagination;
import com.eventostec.api.domain.repositories.EventRepository;
import com.eventostec.api.mappers.EventMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EventServiceImpl implements EventUseCases {

    @Value("${admin.key}")
    private String adminKey;

    private final EventRepository eventRepository;
    private final AddressServiceImpl addressService;
    private final CouponServiceImpl couponService;
    private final EventMapper mapper;

    public EventServiceImpl(EventRepository eventRepository, AddressServiceImpl addressService, CouponServiceImpl couponService, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
        this.couponService = couponService;
        this.mapper = mapper;
    }

    @Transactional
    public EventResponseDTO create(EventRequestDTO eventRequestDTO) {
        log.info(" > eventRequestDTO: {}", eventRequestDTO);
        Event event = mapper.toDomain(eventRequestDTO);
        log.info(" > event: {}", event);
        event = this.eventRepository.save(event);
        log.info(" > event created: {}", event);
        
        if (eventRequestDTO.remote()) {
            return this.mapper.toResponseDto(event, Optional.of(new Address()));   
        } else {
            AddressRequestDTO addressRequestDTO = new AddressRequestDTO(eventRequestDTO.city(), eventRequestDTO.state());
            Address address = this.addressService.addAddressToEvent(event.getId(), addressRequestDTO);
            return this.mapper.toResponseDto(event, Optional.of(address));
        }
    }

    @Override
    public List<EventResponseDTO> getUpcommingEvents(int page, int size) {
        Pagination<EventAddressProjection> eventsPagination = 
            this.eventRepository.findUpcommingEvents(new Date(), page, size);
        
        return eventsPagination.getContent().stream()
            .map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate().getTime(),
                event.getCity() != null ? event.getCity() : "",
                event.getState() != null ? event.getState() : "",
                event.getRemote(),
                event.getEventUrl()))
            .toList();
    }

    public EventDTO getEventDetails(Long eventId) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Optional<Address> address = addressService.findByEvent(event);

        List<Coupon> coupons = couponService.consultCoupons(event, new Date());

        return this.mapper.toDto(event, address, coupons);
    }

    public List<EventResponseDTO> searchEvents(String title) {
        title = (title != null) ? title : "";

        List<EventAddressProjection> eventsList = this.eventRepository.findEventsByTitle(title);
        return eventsList.stream().map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate().getTime(),
                event.getCity() != null ? event.getCity() : "",
                event.getState() != null ? event.getState() : "",
                event.getRemote(),
                event.getEventUrl()))
            .toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String state, Date startDate, Date endDate) {
        city = (city != null) ? city : "";
        state = (state != null) ? state : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pagination<EventAddressProjection> eventsPagination = this.eventRepository.findFilteredEvents(city, state, startDate, endDate, page, size);
        return eventsPagination.getContent().stream().map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate().getTime(),
                event.getCity() != null ? event.getCity() : "",
                event.getState() != null ? event.getState() : "",
                event.getRemote(),
                event.getEventUrl()))
            .toList();
    }

    public void deleteEvent(Long eventId, String adminKey) {
        if (adminKey == null || !adminKey.equals(this.adminKey)) {
            throw new IllegalArgumentException("Invalid admin key");
        }

        this.eventRepository.deleteById(eventId);
    }
}
