package com.eventostec.api.adapters.outbound.entities;

import com.eventostec.api.domain.coupon.Coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaCoupon {
  @Id
  @GeneratedValue
  private UUID id;

  private String code;
  private Integer discount;
  private Date valid;

  @ManyToOne
  @JoinColumn(name = "event_id")
  private JpaEvent event;

  public JpaCoupon(Coupon coupon) {
    this.code = coupon.getCode();
    this.discount = coupon.getDiscount();
    this.valid = coupon.getValid();
    this.event.setId(coupon.getEvent().getId());
    this.event.setDate(coupon.getEvent().getDate());
    this.event.setDescription(coupon.getEvent().getDescription());
    this.event.setEventUrl(coupon.getEvent().getEventUrl());
    this.event.setTitle(coupon.getEvent().getTitle());
    this.event.setRemote(coupon.getEvent().getRemote());
  }
}
