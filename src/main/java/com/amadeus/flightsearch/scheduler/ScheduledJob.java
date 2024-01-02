package com.amadeus.flightsearch.scheduler;

import com.amadeus.flightsearch.entity.Flight;
import com.amadeus.flightsearch.service.FlightService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ScheduledJob {

  private final FlightService flightService;

  private String url = "http://localhost:8080/flights/addFlightsWithFaker";

  @Scheduled(cron = "0 0 0 * * ?")
  public void fetchAndSaveFlightDaily() {
    List<Flight> flights = callMockApi();
    flightService.saveFlights(flights);
  }

  private List<Flight> callMockApi() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Flight[]> responseEntity = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        Flight[].class);

    return Arrays.asList(responseEntity.getBody());
  }
}
