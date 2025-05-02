package com.eventostec.api.domain.address;

import com.eventostec.api.domain.event.Event;
import java.util.UUID;

public class Address {
    private UUID id;

    private String city;
    private String state;

    private Event event;

    public Address(UUID id, String city, String state) {
        this.id = id;
        this.city = city;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
}
