package de.trion.training.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

   private final JpaUserDetailsManager userManager;
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public AdminController(JpaUserDetailsManager userManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
      this.userManager = userManager;
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @GetMapping
   public ModelAndView list(Pageable pageable) {
      var mav = new ModelAndView("admin/list");

      mav.addObject("users", userRepository.findAll(pageable));
      mav.addObject("user", new UserEntity());

      return mav;
   }

   @PostMapping
   public ModelAndView add(UserEntity user, BindingResult bindingResult) {
      var mav = new ModelAndView();
      mav.addObject("users", Collections.emptyList());
      mav.addObject("user", user);

      if (bindingResult.hasErrors()) {
         mav.setViewName("/admin/list");
         return mav;
      }

      user.setAuthorities(Set.of(new GrantedAuthorityEntity("ROLE_USER", user)));
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setEnable(true);

      userManager.createUser(user);

      mav.setViewName("redirect:/admin");
      return mav;
   }

   @PostMapping("{id}/delete")
   public ModelAndView delete(@PathVariable Integer id) {
      userRepository.deleteById(id);
      return new ModelAndView("redirect:/admin");
   }

   @Autowired
   @Qualifier("basicAuth")
   private RestTemplate restTemplate;

   @GetMapping("/demo")
   @ResponseBody
   public Object demo() {
      return restTemplate.getForObject("/info", Object.class);
   }
}
