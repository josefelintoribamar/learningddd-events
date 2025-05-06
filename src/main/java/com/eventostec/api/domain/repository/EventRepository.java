package com.eventostec.api.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.eventostec.api.domain.data.Event;
import com.eventostec.api.domain.projection.EventAddressProjection;
import com.eventostec.api.domain.util.Pagination;

public interface EventRepository {
    Event save(Event event);

    Optional<Event> findById(Long id);

    List<Event> findAll();

    void deleteById(Long id);

    Pagination<EventAddressProjection> findUpcommingEvents(Date currentDate, int page, int size);

    Pagination<EventAddressProjection> findFilteredEvents(String city, String state, Date startDate, Date endDate, int page, int size);

    List<EventAddressProjection> findEventsByTitle(String title);
}
