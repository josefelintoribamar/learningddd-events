package com.eventostec.api.adapters.outbound.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

import com.eventostec.api.domain.event.Event;

@Table(name = "event")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JpaEvent {
  @Id
  @GeneratedValue
  private UUID id;

  private String title;
  private String description;
  private String imgUrl;
  private String eventUrl;
  private Boolean remote;
  private Date date;

  public JpaEvent(Event event) {
    this.title = event.getTitle();
    this.description = event.getDescription();
    this.imgUrl = event.getImgUrl();
    this.eventUrl = event.getEventUrl();
    this.remote = event.getRemote();
    this.date = event.getDate();
  }

}
