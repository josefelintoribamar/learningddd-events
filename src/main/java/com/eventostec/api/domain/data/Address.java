package com.eventostec.api.domain.data;

public class Address {
    private Long id;

    private String city;
    private String state;

    private Event event;

    public Address() {
    }

    public Address(Long id, String city, String state) {
        this.id = id;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
