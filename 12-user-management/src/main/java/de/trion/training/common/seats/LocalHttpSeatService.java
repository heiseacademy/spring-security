package de.trion.training.common.seats;

import de.trion.training.common.training.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty("localHttpSeatService")
@Service
public class LocalHttpSeatService implements SeatService
{
   private final Logger logger = LoggerFactory.getLogger(getClass());

   private static String LOCAL_API = "http://localhost:8080/api/randomnumber?min={min}&max={max}";

   private final RestTemplate restTemplate;

   public LocalHttpSeatService(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @Override
   public Integer seatsFor(Training t) {
      Integer[] seats = restTemplate.getForObject(LOCAL_API, Integer[].class, 0, 20);
      logger.info("Seats: {} for training {}", seats, t);
      if(seats == null)
      {
         return null;
      }
      return seats[0];
   }
}
