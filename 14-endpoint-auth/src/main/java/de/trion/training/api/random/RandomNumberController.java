package de.trion.training.api.random;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api/randomnumber")
public class RandomNumberController {
    private final Random random = new Random();

    @GetMapping
    public List<Integer> random(@RequestParam Integer min, @RequestParam Integer max) throws Exception {
        TimeUnit.MILLISECONDS.sleep(75 + random.nextInt(40));
        return List.of(random.nextInt(max + 1 - min) + min);
    }
}
