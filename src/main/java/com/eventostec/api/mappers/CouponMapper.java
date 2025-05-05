package com.eventostec.api.mappers;

import com.eventostec.api.adapters.outbound.entities.JpaCoupon;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.domain.coupon.CouponResponseDTO;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "couponRequestDTO.code", target = "code"),
        @Mapping(source = "couponRequestDTO.discount", target = "discount"),
        @Mapping(source = "couponRequestDTO.valid", target = "valid", qualifiedByName = "epochToDate"),
        @Mapping(target = "event", ignore = true)
    })
    @SuppressWarnings("UnmappedTargetProperties")
    Coupon toDomain(CouponRequestDTO couponRequestDTO);

    @Mappings({
        @Mapping(source = "coupon.code", target = "code"),
        @Mapping(source = "coupon.discount", target = "discount"),
        @Mapping(source = "coupon.valid", target = "valid", qualifiedByName = "dateToEpoch")
    })
    CouponRequestDTO toDto(Coupon coupon);

    @Mappings({
        @Mapping(source = "coupon.code", target = "code"),
        @Mapping(source = "coupon.discount", target = "discount"),
        @Mapping(source = "coupon.valid", target = "valid")
    })
    CouponResponseDTO toResponseDto(Coupon coupon);

    @Mappings({
        @Mapping(source = "jpaCoupon.code", target = "code"),
        @Mapping(source = "jpaCoupon.discount", target = "discount"),
        @Mapping(source = "jpaCoupon.valid", target = "valid"),
        @Mapping(source = "jpaCoupon.id", target = "id"),
        @Mapping(target = "event", ignore = true)
    })
    Coupon toDomain(JpaCoupon jpaCoupon);

    @Named("epochToDate")
    default Date epochToDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @Named("dateToEpoch")
    default Long dateToEpoch(Date date) {
        return date != null ? date.getTime() : null;
    }
}