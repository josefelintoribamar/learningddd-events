package com.eventostec.api.application.service;

import com.eventostec.api.adapter.inbound.dto.CouponRequestDTO;
import com.eventostec.api.adapter.outbound.entity.JpaEvent;
import com.eventostec.api.adapter.outbound.repository.JpaEventRepository;
import com.eventostec.api.domain.data.Coupon;
import com.eventostec.api.domain.data.Event;
import com.eventostec.api.domain.repository.CouponRepository;
import com.eventostec.api.mapper.EventMapper;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponServiceImpl {

    private final CouponRepository couponRepository;
    private final JpaEventRepository eventRepository;
    private final EventMapper eventMapper;

    public CouponServiceImpl(CouponRepository couponRepository, JpaEventRepository eventRepository, EventMapper eventMapper) {
        this.couponRepository = couponRepository;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public Coupon addCouponToEvent(Long eventId, CouponRequestDTO couponData) {
        JpaEvent jpaEvent = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponData.code());
        coupon.setDiscount(couponData.discount());
        coupon.setValid(new Date(couponData.valid()));
        coupon.setEvent(this.eventMapper.toDomain(jpaEvent));
        return couponRepository.save(coupon);
    }

    public List<Coupon> consultCoupons(Event event, Date currentDate) {
        return couponRepository.findByEventAndValidAfter(event, currentDate);
    }
}
