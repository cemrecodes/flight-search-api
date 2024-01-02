package com.amadeus.flightsearch.service;

import com.amadeus.flightsearch.dto.FlightDto;
import com.amadeus.flightsearch.entity.Flight;
import java.time.OffsetDateTime;
import java.util.List;

public interface FlightService {
  Flight saveFlight(Flight flight);

  void deleteFlight(Long flightId);

  Flight updateFlight(Long flightId, FlightDto flightDto);

  Flight getFlightById(Long flightId);

  List<Flight> getAll();

  List<Flight> searchFlight(String departureAirport, String arrivalAirport, OffsetDateTime departureDatetime, OffsetDateTime arrivalDatetime);

  void saveFlights(List<Flight> flights);

  Flight addFlightWithFaker();
}
