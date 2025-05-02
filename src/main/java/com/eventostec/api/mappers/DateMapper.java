package com.eventostec.api.mappers;


import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

    public Date epochToDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp);
    }

    public Long dateToEpoch(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }
}