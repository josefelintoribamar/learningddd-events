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
  //@Setter
  //@Getter
  private JpaEvent jpaEvent;

  public JpaCoupon(Coupon coupon) {
      this.code = coupon.getCode();
      this.discount = coupon.getDiscount();
      this.valid = coupon.getValid();
      if (coupon.getEvent() != null) {
          this.jpaEvent = new JpaEvent(coupon.getEvent());
      }
  }
}
