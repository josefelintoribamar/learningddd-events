package com.eventostec.api.adapter.outbound.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.eventostec.api.adapter.outbound.entity.JpaEvent;
import com.eventostec.api.domain.data.Event;
import com.eventostec.api.domain.projection.EventAddressProjection;
import com.eventostec.api.domain.repository.EventRepository;
import com.eventostec.api.domain.util.Pagination;
import com.eventostec.api.mapper.EventMapper;

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
    public Pagination<EventAddressProjection> findUpcommingEvents(Date currentDate, int page, int size) {
        Page<EventAddressProjection> springPage = jpaEventRepository.findUpcomingEvents(
            currentDate, PageRequest.of(page, size));
        
        return convertToCustomPagination(springPage);
    }

    @Override
    public Pagination<EventAddressProjection> findFilteredEvents(String city, String state, Date startDate, Date endDate, int page, int size) {
        Page<EventAddressProjection> springPage = jpaEventRepository.findFilteredEvents(
            city, state, startDate, endDate, PageRequest.of(page, size));
        
        return convertToCustomPagination(springPage);
    }

    @Override
    public List<EventAddressProjection> findEventsByTitle(String title) {
        return this.jpaEventRepository.findEventsByTitle(title);
    }

    private <T> Pagination<T> convertToCustomPagination(Page<T> springPage) {
        return new Pagination<>(
            springPage.getContent(),
            springPage.getNumber(),
            springPage.getSize(),
            springPage.getTotalElements()
        );
    }
}
