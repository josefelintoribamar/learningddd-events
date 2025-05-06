package com.eventostec.api.adapters.outbound.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.eventostec.api.domain.Event;

@Entity
@Table(name = "event")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JpaEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String title;
    private String description;
    private String eventUrl;
    private Boolean remote;
    private Date date;

    public JpaEvent(Event event) {
      this.id = event.getId();
      this.title = event.getTitle();
      this.description = event.getDescription();
      this.eventUrl = event.getEventUrl();
      this.remote = event.getRemote();
      this.date = event.getDate();
    }
}
