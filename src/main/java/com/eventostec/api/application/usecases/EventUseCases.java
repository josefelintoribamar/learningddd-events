package com.eventostec.api.application.usecases;

import java.util.Date;
import java.util.List;

import com.eventostec.api.adapters.inbound.dtos.event.EventDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventRequestDTO;
import com.eventostec.api.adapters.inbound.dtos.event.EventResponseDTO;

public interface EventUseCases {
    EventResponseDTO create(EventRequestDTO dto);

    List<EventResponseDTO> getUpcommingEvents(int page, int size);

    EventDTO getEventDetails(Long id);

    List<EventResponseDTO> searchEvents(String title);

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String state, Date startDate, Date endDate);

    void deleteEvent(Long id, String adminKey);
}
