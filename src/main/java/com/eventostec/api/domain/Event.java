package com.eventostec.api.domain;

import java.util.Date;

public class Event {
    private Long id;

    private String title;
    private String description;
    private String eventUrl;
    private Boolean remote;
    private Date date;

    public Event() {
    }

    public Event(Long id, String title, String description, String eventUrl, Boolean remote, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventUrl = eventUrl;
        this.remote = remote;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventUrl() {
        return this.eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Boolean isRemote() {
        return this.remote;
    }

    public Boolean getRemote() {
        return this.remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", eventUrl='" + getEventUrl() + "'" +
            ", remote='" + isRemote() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }

}
