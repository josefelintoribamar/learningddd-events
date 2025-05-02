package com.eventostec.api.adapters.outbound.entities;

import com.eventostec.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "address")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaAddress {
    @Id
    @GeneratedValue
    private UUID id;

    private String city;
    private String state;

    @OneToOne
    @JoinColumn(name = "event_id")
    //@Setter
    //@Getter
    private JpaEvent jpaEvent;

    public JpaAddress(Address address) {
        this.city = address.getCity();
        this.state = address.getState();
        if (address.getEvent() != null) {
            this.jpaEvent = new JpaEvent(address.getEvent());
        }
    }
}
