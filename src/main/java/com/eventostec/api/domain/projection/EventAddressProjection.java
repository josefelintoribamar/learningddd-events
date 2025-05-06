package com.eventostec.api.domain.projection;

import java.util.Date;

public interface EventAddressProjection {
    Long getId();
    String getTitle();
    String getDescription();
    Date getDate();
    String getEventUrl();
    Boolean getRemote();
    String getCity();
    String getState();
}
