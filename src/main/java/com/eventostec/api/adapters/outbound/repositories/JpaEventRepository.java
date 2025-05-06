package com.eventostec.api.adapters.outbound.repositories;

import com.eventostec.api.adapters.outbound.entities.JpaEvent;
import com.eventostec.api.domain.EventAddressProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface JpaEventRepository extends JpaRepository<JpaEvent, Long> {

    @Query("SELECT e.id AS id, e.title AS title, e.description AS description, e.date AS date, e.eventUrl AS eventUrl, e.remote AS remote, a.city AS city, a.state AS state "
        + "FROM JpaEvent e LEFT JOIN JpaAddress a ON e.id = a.jpaEvent.id " 
        + "WHERE e.date >= :currentDate")
    Page<EventAddressProjection> findUpcomingEvents(@Param("currentDate") Date currentDate, Pageable pageable);

    @Query("SELECT e.id AS id, e.title AS title, e.description AS description, e.date AS date, e.eventUrl AS eventUrl, e.remote AS remote, a.city AS city, a.state AS state "
        + "FROM JpaEvent e JOIN JpaAddress a ON e.id = a.jpaEvent.id " 
        + "WHERE (:city = '' OR a.city LIKE %:city%) " 
        + "AND (:state = '' OR a.state LIKE %:state%) " 
        + "AND (e.date >= :startDate AND e.date <= :endDate)")
    Page<EventAddressProjection> findFilteredEvents(@Param("city") String city, @Param("state") String state, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT e.id AS id, e.title AS title, e.description AS description, e.date AS date, e.eventUrl AS eventUrl, e.remote AS remote, a.city AS city, a.state AS state "
        + "FROM JpaEvent e JOIN JpaAddress a ON e.id = a.jpaEvent.id " 
        + "WHERE (:title = '' OR e.title LIKE %:title%)")
    List<EventAddressProjection> findEventsByTitle(@Param("title") String title);
}