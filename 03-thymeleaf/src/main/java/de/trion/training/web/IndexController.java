package de.trion.training.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

   private Logger logger = LoggerFactory.getLogger(getClass());

   @RequestMapping("/")
   public String indexRedirect() {
      return "redirect:/trainings";
   }

}
