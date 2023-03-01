package de.trion.training;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

   @Bean
   public UserDetailsManager userDetailsManager() {
      UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("secret")
            .roles("USER")
            .build();
      return new InMemoryUserDetailsManager(user);
   }
}
