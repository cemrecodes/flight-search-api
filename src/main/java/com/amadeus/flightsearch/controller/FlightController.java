package com.amadeus.flightsearch.controller;

import com.amadeus.flightsearch.dto.FlightDto;
import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.entity.Flight;
import com.amadeus.flightsearch.service.AirportService;
import com.amadeus.flightsearch.service.FlightService;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/flights")
@RequiredArgsConstructor
public class FlightController {

  private final FlightService flightService;

  private final AirportService airportService;

  @GetMapping(value = "/{flightId}")
  public ResponseEntity getFlight(@PathVariable Long flightId){
    Flight flight = flightService.getFlightById(flightId);
    if(flight != null){
      return ResponseEntity.ok(flight);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<Flight>> getFlights(){
    return ResponseEntity.ok(flightService.getAll());
  }

  @PostMapping
  public ResponseEntity saveFlight(@RequestBody FlightDto flightDto){
    Flight flight = new Flight();
    Airport departmentAirport = airportService.getAirportByCity(flightDto.getDepartureAirport());
    if(departmentAirport == null){
      return ResponseEntity.badRequest().body("Departure airport doesn't exist");
    }
    Airport arrivalAirport = airportService.getAirportByCity(flightDto.getArrivalAirport());
    if(arrivalAirport == null){
      return ResponseEntity.badRequest().body("Arrival airport doesn't exist");
    }
    flight.setArrivalAirport(arrivalAirport);
    flight.setDepartureAirport(departmentAirport);
    flight.setArrivalDateTime(flightDto.getArrivalDateTime());
    flight.setDepartureDateTime(flightDto.getDepartureDateTime());
    flight.setPrice(flightDto.getPrice());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping(value= "/{flightId}")
  public ResponseEntity updateFlight(@PathVariable Long flightId, @RequestBody FlightDto flightDto){
    Flight flight = flightService.getFlightById(flightId);
    if( flight == null ){
      return ResponseEntity.notFound().build();
    }
    Airport departmentAirport = airportService.getAirportByCity(flightDto.getDepartureAirport());
    if(departmentAirport == null){
      return ResponseEntity.badRequest().body("Departure airport doesn't exist");
    }
    Airport arrivalAirport = airportService.getAirportByCity(flightDto.getArrivalAirport());
    if(arrivalAirport == null){
      return ResponseEntity.badRequest().body("Arrival airport doesn't exist");
    }
    return ResponseEntity.ok(flightService.updateFlight(flightId, flightDto));
  }

  @DeleteMapping(value = "/{flightId}")
  public ResponseEntity<Void> deleteFlight(@PathVariable Long flightId){
    flightService.deleteFlight(flightId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/search")
  public ResponseEntity<HashMap<String, List<Flight>>> searchFlight(
      @RequestParam String departureAirport,
      @RequestParam String arrivalAirport,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate) {

    HashMap<String, List<Flight>> flights = new HashMap<>();
    OffsetDateTime arrivalOffsetDateTime = departureDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    List<Flight> departureFlights = flightService.searchFlight(departureAirport, arrivalAirport, arrivalOffsetDateTime, arrivalOffsetDateTime.plusDays(1));
    flights.put("departureFlights", departureFlights);
    if(arrivalDate != null){
      OffsetDateTime departureOffsetDateTime = arrivalDate.atStartOfDay().atOffset(ZoneOffset.UTC);
      List<Flight> arrivalFlights = flightService.searchFlight(arrivalAirport, departureAirport, departureOffsetDateTime, departureOffsetDateTime.plusDays(1));
      flights.put("arrivalFlights", arrivalFlights);
    }
    return ResponseEntity.ok(flights);
  }

  @GetMapping(value = "/addFlightWithFaker")
  public ResponseEntity<Flight> addFlightWithFaker(){
    Flight flight = flightService.addFlightWithFaker();
    return ResponseEntity.ok(flight);
  }

  @GetMapping(value = "/addFlightsWithFaker")
  public ResponseEntity<List<Flight>> addFlightsWithFaker(){
    List<Flight> flights = new ArrayList<>();
    for(int i = 0; i < 10 ; i++){
      flights.add(flightService.addFlightWithFaker());
    }
    return ResponseEntity.ok(flights);
  }

}
