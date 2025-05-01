package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID id;

    private String title;
    private String description;
    private String imgUrl;
    private String eventUrl;
    private Boolean remote;
    private Date date;

    public Event() {
    }

    public Event(UUID id, String title, String description, String imgUrl, String eventUrl, Boolean remote, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.eventUrl = eventUrl;
        this.remote = remote;
        this.date = date;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
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

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
