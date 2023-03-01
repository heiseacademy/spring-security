package de.trion.training;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {

   @Bean
   public UserDetailsManager userDetailsManager() {
      UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("secret")
            .roles("USER", "DEMO")
            .build();

      UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("secret")
            .roles("USER", "ADMIN")
            .build();

      return new InMemoryUserDetailsManager(user, admin);
   }

   @Profile("dev")
   @Bean
   public SecurityFilterChain h2Filter (HttpSecurity httpSecurity) throws Exception {

      httpSecurity
            .antMatcher("/h2-console/**")
            .authorizeRequests().anyRequest().permitAll()
            .and().csrf().disable()
            .headers().frameOptions().sameOrigin();

      return httpSecurity.build();
   }

   @Bean
   public SecurityFilterChain trainings(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
            .mvcMatcher("/trainings/**")
            .authorizeRequests()
            .antMatchers(HttpMethod.POST).hasRole("ADMIN")
            .mvcMatchers("/trainings/*/edit").hasRole("ADMIN")
            .anyRequest().permitAll();

      return httpSecurity.build();
   }

   @Bean
   public SecurityFilterChain login(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.formLogin()
            .defaultSuccessUrl("/trainings")
            .and().logout().logoutSuccessUrl("/");
      return httpSecurity.build();
   }


}
