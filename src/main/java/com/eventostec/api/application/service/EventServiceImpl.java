package com.eventostec.api.application.service;

import com.eventostec.api.application.usecases.EventUseCases;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.address.AddressRequestDTO;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.*;
import com.eventostec.api.mappers.EventMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventUseCases {

    @Value("${admin.key}")
    private String adminKey;

    private final EventRepository eventRepository;
    private final AddressServiceImpl addressService;
    private final CouponServiceImpl couponService;

    public EventServiceImpl(EventRepository eventRepository, AddressServiceImpl addressService, CouponServiceImpl couponService) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
        this.couponService = couponService;
    }

    @Autowired
    private EventMapper mapper;

    public Event create(EventRequestDTO eventRequestDTO) {
        Event event = mapper.toDomain(eventRequestDTO);
        this.eventRepository.save(event);

        if (Boolean.FALSE.equals(eventRequestDTO.remote())) {
            AddressRequestDTO addressRequestDTO = new AddressRequestDTO(eventRequestDTO.city(), eventRequestDTO.state());
            this.addressService.addAddressToEvent(event.getId(), addressRequestDTO);
        }

        return event;
    }

    @Override
    public List<EventResponseDTO> getUpcommingEvents(int page, int size) {
        Page<EventAddressProjection> eventsPage = this.eventRepository.findUpcommingEvents(new Date(), page, size);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getCity() != null ? event.getCity() : "",
                event.getState() != null ? event.getState() : "",
                event.getRemote(),
                event.getEventUrl()))
                .stream().toList();
    }

    public EventDTO getEventDetails(UUID eventId) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Optional<Address> address = addressService.findByEvent(event);

        List<Coupon> coupons = couponService.consultCoupons(event, new Date());

        return this.mapper.toDTO(event, address, coupons);
    }

    public void deleteEvent(UUID eventId, String adminKey) {
        if (adminKey == null || !adminKey.equals(this.adminKey)) {
            throw new IllegalArgumentException("Invalid admin key");
        }

        this.eventRepository.deleteById(eventId);
    }

    public List<EventResponseDTO> searchEvents(String title) {
        title = (title != null) ? title : "";

        List<EventAddressProjection> eventsList = this.eventRepository.findEventsByTitle(title);
        return eventsList.stream().map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
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

        Page<EventAddressProjection> eventsPage = this.eventRepository.findFilteredEvents(city, state, startDate, endDate, page, size);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getCity() != null ? event.getCity() : "",
                event.getState() != null ? event.getState() : "",
                event.getRemote(),
                event.getEventUrl()))
                .stream().toList();
    }

    @Override
    public List<EventResponseDTO> filterEvents(int page, int size, String city, String state, Date startDate,
            Date endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterEvents'");
    }

}
