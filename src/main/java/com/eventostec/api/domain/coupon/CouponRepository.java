package com.eventostec.api.domain.coupon;

import com.eventostec.api.domain.event.Event;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    Optional<Coupon> findById(UUID id);

    List<Coupon> findByEvent(Event event);

    List<Coupon> findByEventAndValidAfter(Event event, Date date);

    List<Coupon> findByEventAndValid(Event event, Date date);

    void deleteById(UUID id);
}