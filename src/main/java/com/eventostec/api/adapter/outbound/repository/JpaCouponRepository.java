package com.eventostec.api.adapter.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventostec.api.adapter.outbound.entity.JpaCoupon;

import java.util.Date;
import java.util.List;

public interface JpaCouponRepository extends JpaRepository<JpaCoupon, Long> {
    List<JpaCoupon> findByJpaEvent_Id(Long eventId);
    List<JpaCoupon> findByJpaEvent_IdAndValidAfter(Long eventId, Date since);
    List<JpaCoupon> findByJpaEvent_IdAndValid(Long eventId, Date currentDate);
}
