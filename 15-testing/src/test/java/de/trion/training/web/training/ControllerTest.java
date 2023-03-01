package de.trion.training.web.training;

import de.trion.training.common.seats.SeatService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN", "DEMO"}, authorities = {})
public class ControllerTest {
   @Autowired
   MockMvc mockMvc;

   @MockBean
   private SeatService seatService;

   @Test
   public void index() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/trainings").queryParam("size", "2"))
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("/trainings/list"))
            .andExpect(model().attribute("trainings", Matchers.hasSize(2)));
   }

   @Test
   public void add() throws Exception {
      mockMvc.perform(post("/trainings")
            .param("location", "Unit Test")
            .param("instructor.name", "Mr. Robot")
            .param("topic", "Testing")
            .with(csrf())
      )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/trainings"));
   }

   @Test
   public void validation() throws Exception {
      mockMvc.perform(post("/trainings")
                  .param("location", "Unit Test")
                  .param("instructor.name", "Mr. Robot")
                  .param("topic", "tst")
                  .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpect(view().name("/trainings/list"))
            .andExpect(model().attributeHasFieldErrors("training", "topic"));
   }

   @Test
   public void ensureCsrf() throws Exception {
      mockMvc.perform(post("/trainings")
                  .param("location", "Unit Test")
                  .param("instructor.name", "Mr. Robot")
                  .param("topic", "tst")
                  .with(csrf().useInvalidToken())
            )
            .andExpect(status().isForbidden());
   }

   @WithAnonymousUser
   @Test
   public void isSecured() throws Exception {
      mockMvc.perform(post("/trainings")
            .param("location", "Unit Test")
            .with(csrf()))
            .andExpect(status().isForbidden());
   }
}
