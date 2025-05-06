package com.eventostec.api.adapters.inbound.dtos.event;

public record EventResponseDTO(
    Long id, 
    String title, 
    String description, 
    Long date, 
    String city, 
    String state, 
    Boolean remote, 
    String eventUrl) {
}
