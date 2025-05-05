package com.eventostec.api.domain.event;

import java.util.Date;

public record EventResponseDTO(
    Long id, 
    String title, 
    String description, 
    Date date, 
    String city, 
    String state, 
    Boolean remote, 
    String eventUrl) {
}
