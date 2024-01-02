package com.amadeus.flightsearch.service.imp;

import com.amadeus.flightsearch.dto.FlightDto;
import com.amadeus.flightsearch.entity.Flight;
import com.amadeus.flightsearch.repository.FlightRepository;
import com.amadeus.flightsearch.service.AirportService;
import com.amadeus.flightsearch.service.FlightService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlightServiceImp implements FlightService {

  private final FlightRepository flightRepository;

  private final AirportService airportService;

  @Override
  public Flight saveFlight(Flight flight) {
    return flightRepository.save(flight);
  }

  @Override
  @Transactional
  public void deleteFlight(Long flightId) {
    flightRepository.deleteFlightById(flightId);
  }

  @Override
  public Flight updateFlight(Long flightId, FlightDto flightDto) {
    Flight flight = new Flight();
    flight.setId(flightId);
    flight.setDepartureDateTime(flightDto.getDepartureDateTime());
    flight.setArrivalDateTime(flightDto.getArrivalDateTime());
    flight.setPrice(flightDto.getPrice());
    return saveFlight(flight);
  }

  @Override
  public Flight getFlightById(Long flightId) {
    return flightRepository.getFlightById(flightId);
  }

  @Override
  public List<Flight> getAll() {
    return flightRepository.findAll();
  }

  @Override
  public List<Flight> searchFlight(String departureAirport, String arrivalAirport, OffsetDateTime departureDatetime, OffsetDateTime arrivalDatetime) {
    return flightRepository.getFlights(departureAirport, arrivalAirport, departureDatetime, arrivalDatetime);
  }

  @Override
  public void saveFlights(List<Flight> flights) {
    flightRepository.saveAll(flights);
  }

  @Override
  public Flight addFlightWithFaker() {
    Flight flight = new Flight();
    List<Long> idList = airportService.getAirportIds();
    Random random = new Random();

    // index for random departure airport
    int randomIndex = random.nextInt(idList.size());
    Long airportId = idList.get(randomIndex);
    idList.remove(airportId);
    flight.setDepartureAirport(airportService.getAirportById(airportId));

    // index for random arrival airport
    randomIndex = random.nextInt(idList.size());
    flight.setArrivalAirport(airportService.getAirportById(idList.get(randomIndex)));

    flight.setPrice(BigDecimal.valueOf(100 + (2000 - 100) * random.nextDouble()));

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime upperBound = now.plusYears(2);
    long secondsRange = now.until(upperBound, java.time.temporal.ChronoUnit.SECONDS);
    long randomSeconds = random.nextInt((int) secondsRange);
    LocalDateTime randomLocalDateTime = now.plusSeconds(randomSeconds);
    randomLocalDateTime = randomLocalDateTime.withSecond(0);
    OffsetDateTime randomOffsetDateTime = randomLocalDateTime.atOffset(ZoneOffset.UTC);
    flight.setDepartureDateTime(randomOffsetDateTime);
    flight.setArrivalDateTime(randomOffsetDateTime.plusHours(random.nextInt(24) + 1));

    return saveFlight(flight);
  }


}
