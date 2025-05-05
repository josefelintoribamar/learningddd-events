package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

public interface EventAddressProjection {
    UUID getId();
    String getTitle();
    String getDescription();
    Date getDate();
    String getEventUrl();
    Boolean getRemote();
    String getCity();
    String getState();
}
