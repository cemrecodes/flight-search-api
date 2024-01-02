package com.amadeus.flightsearch.repository;

import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.entity.Flight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

  Airport save(Airport airport);

  void deleteAirportById(Long id);

  Airport getAirportById(Long id);

  Airport getAirportByCity(String city);

  List<Airport> getAirportsByCity(String city);

  @Query("SELECT id FROM Airport")
  List<Long> findAllAirportIds();

}
