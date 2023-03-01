package de.trion.training.common.seats;

import de.trion.training.common.training.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty("remoteRandomSeatService")
@Service
public class RemoteRandomSeatService implements SeatService
{
   private final Logger logger = LoggerFactory.getLogger(getClass());

   private static String NUMBER_API = "http://www.randomnumberapi.com/api/v1.0/randomnumber?min={min}&max={max}";

   private final RestTemplate restTemplate;

   public RemoteRandomSeatService(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @Override
   public Integer seatsFor(Training t) {
      Integer[] seats = restTemplate.getForObject(NUMBER_API, Integer[].class, 0, 20);
      logger.info("(random remote) Seats: {} for training {}", seats, t);
      if(seats == null)
      {
         return null;
      }
      return seats[0];
   }
}
