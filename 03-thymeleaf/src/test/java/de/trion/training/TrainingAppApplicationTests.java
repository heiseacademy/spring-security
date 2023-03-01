package de.trion.training;

import de.trion.training.common.training.Training;
import de.trion.training.common.training.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingAppApplicationTests {

   @Test
   void contextLoads() {
   }

   @Autowired
   TrainingRepository trainingRepository;

   @Test
   void locations() {
      trainingRepository.save(Training.withLocation("Test").withTopic("Test").withInstructor("Thomas").build());
      assertThat(trainingRepository.findLocations()).isNotEmpty();
   }

}
