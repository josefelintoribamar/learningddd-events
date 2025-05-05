package com.eventostec.api.adapters.outbound.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventAddressProjection;
import com.eventostec.api.domain.event.EventRepository;
import com.eventostec.api.mappers.EventMapper;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private final JpaEventRepository jpaEventRepository;
    private final EventMapper eventMapper;

    public EventRepositoryImpl(JpaEventRepository jpaEventRepository, EventMapper eventMapper) {
        this.jpaEventRepository = jpaEventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event save(Event event) {
        final JpaEvent jpaEvent = new JpaEvent(event);
        this.jpaEventRepository.save(jpaEvent);
        return new Event(jpaEvent.getId(),
            jpaEvent.getTitle(),
            jpaEvent.getDescription(),
            jpaEvent.getEventUrl(),
            jpaEvent.getRemote(),
            jpaEvent.getDate());
    }

    @Override
    public Optional<Event> findById(Long id) {
        final Optional<JpaEvent> optional = this.jpaEventRepository.findById(id);
        return optional.map(eventMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return this.jpaEventRepository.findAll().stream()
            .map(jpaEvent -> new Event(jpaEvent.getId(),
                jpaEvent.getTitle(),
                jpaEvent.getDescription(),
                jpaEvent.getEventUrl(),
                jpaEvent.getRemote(),
                jpaEvent.getDate()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        this.jpaEventRepository.deleteById(id);
    }

    @Override
    public Page<EventAddressProjection> findUpcommingEvents(Date currentDate, int page, int size) {
        return this.jpaEventRepository.findUpcomingEvents(currentDate, PageRequest.of(page, size));
    }

    @Override
    public Page<EventAddressProjection> findFilteredEvents(String city, String state, Date startDate, Date endDate, int page, int size) {
        return this.jpaEventRepository.findFilteredEvents(city, state, startDate, endDate, PageRequest.of(page, size));
    }

    @Override
    public List<EventAddressProjection> findEventsByTitle(String title) {
        return this.jpaEventRepository.findEventsByTitle(title);
    }
}
