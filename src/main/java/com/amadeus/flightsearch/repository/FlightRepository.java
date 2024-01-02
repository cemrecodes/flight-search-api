package com.amadeus.flightsearch.repository;

import com.amadeus.flightsearch.entity.Flight;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  Flight save(Flight flight);

  void deleteFlightById(Long flightId);
  Flight getFlightById(Long id);

  List<Flight> findAll();

  List<Flight> getFlightsByArrivalAirport_Id(Long id);

  List<Flight> getFlightsByDepartureAirport_Id(Long id);

  List<Flight> getFlightsByArrivalAirport_City(String city);

  List<Flight> getFlightsByDepartureAirport_City(String city);

  @Query("SELECT flight FROM Flight flight WHERE flight.departureAirport.city = :departureAirport  AND flight.arrivalAirport.city = :arrivalAirport AND flight.departureDateTime BETWEEN :departureDatetime AND :arrivalDatetime")
  List<Flight> getFlights(String departureAirport, String arrivalAirport, OffsetDateTime departureDatetime, OffsetDateTime arrivalDatetime);

}

