package com.eventostec.api.adapter.outbound.entity;

import com.eventostec.api.domain.data.Address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "address")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String city;
    private String state;

    // Relacionamento com JpaEvent
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false) // Cria a coluna "event_id" na tabela "address"
    private JpaEvent jpaEvent;

    public JpaAddress(Address address) {
        this.city = address.getCity();
        this.state = address.getState();
        if (address.getEvent() != null) {
            this.jpaEvent = new JpaEvent(address.getEvent());
        }
    }
}
