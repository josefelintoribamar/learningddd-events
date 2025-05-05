package com.eventostec.api.adapters.outbound.repositories;

import com.eventostec.api.adapters.outbound.entities.JpaAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAddressRepository extends JpaRepository<JpaAddress, Long> {
    Optional<JpaAddress> findByJpaEvent_Id(Long eventId);
}
