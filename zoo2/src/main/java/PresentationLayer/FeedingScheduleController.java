package PresentationLayer;

import ApplicationLayer.Interfaces.FeedingScheduleRepository;
import DomainLayer.Models.FeedingSchedule;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeding")
public class FeedingScheduleController {
    private final FeedingScheduleRepository feeding_rep;

    public FeedingScheduleController(FeedingScheduleRepository feedingRepo) {
        this.feeding_rep = feedingRepo;
    }

    @GetMapping
    public List<FeedingSchedule> getAllSchedules() {
        return feeding_rep.findAll();
    }

    @PostMapping
    public FeedingSchedule addSchedule(@RequestBody FeedingSchedule schedule) {
        feeding_rep.add(schedule);
        return schedule;
    }
}