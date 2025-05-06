package com.eventostec.api.adapter.inbound.dto;

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
