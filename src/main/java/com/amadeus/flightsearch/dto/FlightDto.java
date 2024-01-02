package com.amadeus.flightsearch.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class FlightDto {

  private String departureAirport;
  private String arrivalAirport;
  private OffsetDateTime departureDateTime;
  private OffsetDateTime arrivalDateTime;
  private BigDecimal price;

}
