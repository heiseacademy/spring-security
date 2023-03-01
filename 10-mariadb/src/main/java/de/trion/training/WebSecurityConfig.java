package de.trion.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {

   @Autowired
   public void configureAuth(AuthenticationManagerBuilder build, DataSource dataSource) throws Exception {
      var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

      build
            .jdbcAuthentication() // erzeugt UserDetailsService
            .dataSource(dataSource)
            .passwordEncoder(encoder);
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
