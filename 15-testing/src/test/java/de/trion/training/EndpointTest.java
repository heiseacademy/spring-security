package de.trion.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTest {
   @Autowired
   MockMvc mockMvc;

   @TestConfiguration
   public static class UserCfg {
      @Primary
      @Bean
      UserDetailsManager users(PasswordEncoder passwordEncoder) {
         var endpointAdmin = User.withUsername("endpoint")
               .password("endpoint")
               .roles("ADMIN")
               .passwordEncoder(passwordEncoder::encode)
               .build();
         return new InMemoryUserDetailsManager(endpointAdmin);
      }
   }

   @Test
   public void endpointBasicAuth() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/actuator/info")
            .with(httpBasic("endpoint", "endpoint")))
            .andExpect(status().isOk());
   }

}
