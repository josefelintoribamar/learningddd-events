package com.eventostec.api.adapters.outbound.repositories;

import com.eventostec.api.adapters.outbound.entities.JpaAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAddressRepository extends JpaRepository<JpaAddress, UUID> {
    Optional<JpaAddress> findByJpaEvent_Id(UUID eventId);
}
