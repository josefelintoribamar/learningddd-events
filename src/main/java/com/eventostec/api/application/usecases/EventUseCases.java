package com.eventostec.api.application.usecases;

import java.util.Date;
import java.util.List;

import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;

public interface EventUseCases {
  Event create(EventRequestDTO dto);

  List<EventResponseDTO> getUpcommingEvents(int page, int size);

  EventDTO getEventDetails(Long id);

  void deleteEvent(Long id, String adminKey);

  List<EventResponseDTO> searchEvents(String title);

  List<EventResponseDTO> filterEvents(int page, int size, String city, String state, Date startDate, Date endDate);
}
