package com.amadeus.flightsearch.service;

import com.amadeus.flightsearch.dto.AirportDto;
import com.amadeus.flightsearch.entity.Airport;
import java.util.List;

public interface AirportService {

  Airport saveAirport(Airport airport);

  void deleteAirport(Long id);

  Airport updateAirport(Long airportId, AirportDto airportDto);

  Airport getAirportById(Long id);

  List<Airport> getAll();

  Airport getAirportByCity(String city);

  List<Airport> getAirportsByCity(String city);

  List<Long> getAirportIds();

  Airport addAirportWithFaker();

  Long getAirportCount();
}
