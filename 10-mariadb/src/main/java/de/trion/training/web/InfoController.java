package de.trion.training.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
public class InfoController {
   Logger logger = LoggerFactory.getLogger(getClass());

   @RequestMapping("/info")
   public ModelAndView info(Principal principal, Authentication authentication,
                            @AuthenticationPrincipal UserDetails userDetails,
                            @CurrentSecurityContext SecurityContext context) {

      logger.info("\nprincipal: {}\nauthentication: {},\nuserdetails: {}", principal, authentication, userDetails);

      SecurityContext ctx = SecurityContextHolder.getContext();

      logger.info("ctx: {}, {}", context, ctx );

      return new ModelAndView("info", Map.of("heading", "Info!"));
   }
}
