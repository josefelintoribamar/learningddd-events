package com.eventostec.api.adapters.inbound.controllers;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.application.service.CouponServiceImpl;
import com.eventostec.api.application.service.EventServiceImpl;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventServiceImpl eventService;
    private final CouponServiceImpl couponServiceImpl;

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        Event newEvent = this.eventService.create(eventRequestDTO);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventDetails(@PathVariable Long eventId) {
        EventDTO eventDetails = eventService.getEventDetails(eventId);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        List<EventResponseDTO> allEvents = this.eventService.getUpcommingEvents(page, size);
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam String city,
        @RequestParam String state,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<EventResponseDTO> events = eventService.getFilteredEvents(page, size, city, state, startDate, endDate);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> getSearchEvents(@RequestParam String title) {
        List<EventResponseDTO> events = eventService.searchEvents(title);
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId, @RequestBody String adminKey) {
        eventService.deleteEvent(eventId, adminKey);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/coupon")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable Long eventId, @RequestBody CouponRequestDTO data) {
        Coupon coupons = this.couponServiceImpl.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupons);
    }
}
