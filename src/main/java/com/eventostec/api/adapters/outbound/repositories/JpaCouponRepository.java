package com.eventostec.api.adapters.outbound.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventostec.api.adapters.outbound.entities.JpaCoupon;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface JpaCouponRepository extends JpaRepository<JpaCoupon, UUID> {
    List<JpaCoupon> findByJpaEvent_Id(UUID eventId);
    List<JpaCoupon> findByJpaEvent_IdAndValidAfter(UUID eventId, Date since);
    List<JpaCoupon> findByJpaEvent_IdAndValid(UUID eventId, Date currentDate);
}
