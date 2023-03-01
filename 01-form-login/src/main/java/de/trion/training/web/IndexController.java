package de.trion.training.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

   @RequestMapping("/")
   public String indexRedirect() {
      return "redirect:/trainings";
   }

}
