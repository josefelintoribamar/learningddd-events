package com.eventostec.api.adapter.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventostec.api.adapter.outbound.entity.JpaAddress;

import java.util.Optional;

public interface JpaAddressRepository extends JpaRepository<JpaAddress, Long> {
    Optional<JpaAddress> findByJpaEvent_Id(Long eventId);
}
