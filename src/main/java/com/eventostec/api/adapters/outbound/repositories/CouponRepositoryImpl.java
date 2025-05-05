package com.eventostec.api.adapters.outbound.repositories;

import com.eventostec.api.domain.coupon.CouponRepository;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.mappers.CouponMapper;
import com.eventostec.api.adapters.outbound.entities.JpaCoupon;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class CouponRepositoryImpl implements CouponRepository {
    private final JpaCouponRepository jpaCouponRepository;
    private final CouponMapper couponMapper;

    public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository, CouponMapper couponMapper) {
        this.jpaCouponRepository = jpaCouponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public Coupon save(Coupon coupon) {
        final JpaCoupon jpaCoupon = new JpaCoupon(coupon);
        this.jpaCouponRepository.save(jpaCoupon);
        return new Coupon(jpaCoupon.getId(),
            jpaCoupon.getCode(),
            jpaCoupon.getDiscount(),
            jpaCoupon.getValid());
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        final Optional<JpaCoupon> optional = this.jpaCouponRepository.findById(id);
        return optional.map(couponMapper::toDomain);
    }

    @Override
    public List<Coupon> findByEvent(Event event) {
        final List<JpaCoupon> jpaCoupons = this.jpaCouponRepository.findByJpaEvent_Id(event.getId());
        return jpaCoupons.stream()
            .map(couponMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findByEventAndValidAfter(Event event, Date since) {
        final List<JpaCoupon> jpaCoupons = this.jpaCouponRepository.findByJpaEvent_IdAndValidAfter(event.getId(), since);
        if (jpaCoupons.isEmpty()) {
            return List.of();
        }
        return jpaCoupons.stream()
            .map(couponMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findByEventAndValid(Event event, Date date) {
        final List<JpaCoupon> jpaCoupons = this.jpaCouponRepository.findByJpaEvent_IdAndValid(event.getId(), date);
        if (jpaCoupons.isEmpty()) {
            return List.of();
        }
        return jpaCoupons.stream()
            .map(couponMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        this.jpaCouponRepository.deleteById(id);
    }
}