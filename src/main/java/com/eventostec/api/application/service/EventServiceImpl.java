package com.eventostec.api.application.service;

import com.eventostec.api.adapters.outbound.storage.ImageUploadPort;
import com.eventostec.api.application.usecases.EventUseCases;
import com.eventostec.api.domain.address.Address;
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
    private final AddressService addressService;
    private final CouponService couponService;
    private final ImageUploadPort imageUploadPort;

    public EventServiceImpl(EventRepository eventRepository, AddressService addressService, CouponService couponService,
            ImageUploadPort imageUploadPort) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
        this.couponService = couponService;
        this.imageUploadPort = imageUploadPort;
    }

    @Autowired
    private EventMapper mapper;

    public Event create(EventRequestDTO eventRequestDTO) {
        String imgUrl = "";

        if (eventRequestDTO.image() != null) {
            imgUrl = this.imageUploadPort.uploadImage(eventRequestDTO.image());
        }
        Event newEvent = mapper.toEntity(eventRequestDTO, imgUrl);
        eventRepository.save(newEvent);

        if (Boolean.FALSE.equals(eventRequestDTO.remote())) {
            this.addressService.createAddress(eventRequestDTO, newEvent);
        }

        return newEvent;
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
                event.getUf() != null ? event.getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()))
                .stream().toList();
    }

    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Optional<Address> address = addressService.findByEventId(eventId);

        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

        return this.mapper.toDetailsDTO(event, address, coupons);
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
                event.getUf() != null ? event.getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()))
                .toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date startDate,
            Date endDate) {
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Page<EventAddressProjection> eventsPage = this.eventRepository.findFilteredEvents(city, uf, startDate,
                endDate, page, size);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getCity() != null ? event.getCity() : "",
                event.getUf() != null ? event.getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()))
                .stream().toList();
    }

    @Override
    public List<EventResponseDTO> filterEvents(int page, int size, String city, String uf, Date startDate,
            Date endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterEvents'");
    }

}
