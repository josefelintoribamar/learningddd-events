package com.eventostec.api.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.eventostec.api.domain.Coupon;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaCoupon {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  private String code;
  private Integer discount;
  private Date valid;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false) // Cria a coluna "event_id" na tabela "address"
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
