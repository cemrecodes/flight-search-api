package com.amadeus.flightsearch.controller;

import com.amadeus.flightsearch.dto.AirportDto;
import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.service.AirportService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

  private final AirportService airportService;

  @GetMapping(value = "/{airportId}")
  public ResponseEntity<Airport> getAirport(@PathVariable Long airportId){
    Airport airport = airportService.getAirportById(airportId);
    if( airport != null ){
      return ResponseEntity.ok(airport);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity getAirports(@RequestParam(required = false) String city){
    if( city != null ){
      Airport airports = airportService.getAirportByCity(city);
      if(airports == null){
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(airports);
    }
    return ResponseEntity.ok(airportService.getAll());
  }

  @PostMapping
  public ResponseEntity saveAirport(@RequestBody AirportDto airportDto){
    Airport airport = new Airport();
    if(airportService.getAirportByCity(airportDto.getCity()) != null){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Airport already exists.");
    }
    airport.setCity(airportDto.getCity());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping(value= "/{airportId}")
  public ResponseEntity<Airport> updateAirport(@PathVariable Long airportId, @RequestBody AirportDto airportDto){
   return ResponseEntity.ok(airportService.updateAirport(airportId, airportDto));
  }

  @DeleteMapping(value= "/{airportId}")
  public ResponseEntity<Void> deleteAirport(@PathVariable Long airportId){
    airportService.deleteAirport(airportId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/addAirportWithFaker")
  public ResponseEntity<Airport> addAirportWithFaker(){
    Airport airport = airportService.addAirportWithFaker();
    return ResponseEntity.ok(airport);
  }

}
