package com.amadeus.flightsearch.service.imp;

import com.amadeus.flightsearch.dto.AirportDto;
import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.repository.AirportRepository;
import com.amadeus.flightsearch.service.AirportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AirportServiceImp implements AirportService {

  private final AirportRepository airportRepository;

  @Override
  public Airport saveAirport(Airport airport) {
    return airportRepository.save(airport);
  }

  @Transactional
  @Override
  public void deleteAirport(Long id) {
    airportRepository.deleteAirportById(id);
  }

    @Override
  public Airport updateAirport(Long airportId, AirportDto airportDto) {
    Airport airport = getAirportById(airportId);
    if( airport != null ){
      airport.setCity(airportDto.getCity());
    }
    return saveAirport(airport);
  }

  @Override
  public Airport getAirportById(Long id) {
    return airportRepository.getAirportById(id);
  }

  @Override
  public List<Airport> getAll() {
    return airportRepository.findAll();
  }

  @Override
  public Airport getAirportByCity(String city) {
    return airportRepository.getAirportByCity(city);
  }

  @Override
  public List<Airport> getAirportsByCity(String city) {
    return airportRepository.getAirportsByCity(city);
  }

  @Override
  public List<Long> getAirportIds() {
    return airportRepository.findAllAirportIds();
  }

  @Override
  public Airport addAirportWithFaker() {
    Faker faker = new Faker();
    Airport airport = new Airport();
    String city = faker.address().cityName();
    if (getAirportByCity(city) == null) {
      airport.setCity(city);
    }
    return saveAirport(airport);
  }

  @Override
  public Long getAirportCount() {
    return airportRepository.count();
  }
}
