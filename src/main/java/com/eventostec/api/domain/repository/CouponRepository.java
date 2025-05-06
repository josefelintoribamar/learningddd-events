package com.eventostec.api.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.eventostec.api.domain.data.Coupon;
import com.eventostec.api.domain.data.Event;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Long id);

    List<Coupon> findByEvent(Event event);

    List<Coupon> findByEventAndValidAfter(Event event, Date date);

    List<Coupon> findByEventAndValid(Event event, Date date);

    void deleteById(Long id);
}