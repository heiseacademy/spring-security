package de.trion.training.web.training;

import de.trion.training.common.seats.SeatService;
import de.trion.training.common.training.Training;
import de.trion.training.common.training.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/trainings")
public class TrainingWebController {
   private final Logger logger = LoggerFactory.getLogger(getClass());

   private final TrainingMapper trainingMapper;
   private final SeatService seatService;
   private final TrainingService trainingService;

   public TrainingWebController(TrainingMapper trainingMapper, SeatService seatService, TrainingService trainingService) {
      this.trainingMapper = trainingMapper;
      this.seatService = seatService;
      this.trainingService = trainingService;
   }

   @GetMapping("")
   public ModelAndView trainings(Pageable pageable) {
      var mav = new ModelAndView("/trainings/list");
      var trainings = trainingService.findAll(pageable);
      var trainingsWithSeats = trainings.getContent().stream().map(t ->{
         var seats = seatService.seatsFor(t);
         var dto = trainingMapper.toDto(t);
         dto.setSeats(seats);
         return dto;
      }).toList();

      mav.addObject("heading", "Alle Trainings");
      mav.addObject("trainings", trainingsWithSeats);

      mav.addObject("page", pageable);
      mav.addObject("totalPages", trainings.getTotalPages());
      mav.addObject("totalElements", trainings.getTotalElements());

      mav.addObject("training", trainingMapper.toDto(new Training()));

      return mav;
   }

   @GetMapping("{id}")
   public ModelAndView training(@PathVariable String id) {
      var mav = new ModelAndView("/trainings/detail");
      var training = trainingService.getById(id);

      mav.addObject("training", trainingMapper.toDto(training));
      return mav;
   }

   @GetMapping("{id}/edit")
   public ModelAndView editTraining(@PathVariable String id) {
      var mav = new ModelAndView("/trainings/edit");
      var training = trainingService.getById(id);

      mav.addObject("training", trainingMapper.toDto(training));
      return mav;
   }

   @PostMapping("{id}")
   public String saveEdit(@PathVariable String id, @Valid @ModelAttribute TrainingDto training,
                          BindingResult bindingResult, HttpServletRequest request)  {
      if(bindingResult.hasErrors()) {
         return "/trainings/edit";
      }

      request.setAttribute("training", training);
      trainingService.save(trainingMapper.fromDto(training));
      return "redirect:/trainings/%s".formatted(id);
   }


   @Transactional
   @PostMapping("")
   public String addTraining(@ModelAttribute("training") @Valid TrainingDto training, BindingResult result, Model model) {
      if (result.hasErrors()) {
         model.addAttribute("trainings", trainingMapper.toDto(trainingService.findAll()));
         model.addAttribute("heading", "Alle Trainings");
         return "/trainings/list";
      }
      var savedTraining = trainingService.save(trainingMapper.fromDto(training));
      logger.info("Saved training {}: {}", savedTraining.getId(), savedTraining.getTopic());
      return "redirect:/trainings";
   }
}
