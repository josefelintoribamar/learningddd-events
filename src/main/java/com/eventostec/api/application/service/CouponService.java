package com.eventostec.api.application.service;

import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.adapters.outbound.repositories.CouponRepository;
import com.eventostec.api.adapters.outbound.repositories.JpaEventRepository;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final JpaEventRepository eventRepository;

    @SuppressWarnings("unused")
    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponData) {
        JpaEvent jpaEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponData.code());
        coupon.setDiscount(couponData.discount());
        coupon.setValid(new Date(couponData.valid()));

        /**
         * PRESTAR ATENCAO AQUI
         * 
         * Estava dando erro e comentei. O erro ocorria
         * por conta da fatoracao que iniciei para Event
         * 
         */
        // coupon.setEvent(event);

        return couponRepository.save(coupon);
    }

    public List<Coupon> consultCoupons(UUID eventId, Date currentDate) {
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
}
