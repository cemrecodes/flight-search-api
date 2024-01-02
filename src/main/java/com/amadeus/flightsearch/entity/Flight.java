package com.amadeus.flightsearch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "flight")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "departure_airport_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Airport departureAirport;

  @ManyToOne
  @JoinColumn(name = "arrival_airport_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Airport arrivalAirport;

  private OffsetDateTime departureDateTime;

  private OffsetDateTime arrivalDateTime;

  private BigDecimal price;
}
