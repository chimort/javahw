package ApplicationLayer.Services;

import ApplicationLayer.Interfaces.FeedingScheduleRepository;
import DomainLayer.Events.FeedingTimeEvent;
import DomainLayer.Models.FeedingSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FeedingOrganizationService {
    private final FeedingScheduleRepository feeding_rep;

    public FeedingOrganizationService(FeedingScheduleRepository f_rep) {
        this.feeding_rep = f_rep;
    }

    public void addFeedingSchedule(FeedingSchedule schedule) {
        feeding_rep.add(schedule);
    }

    public FeedingTimeEvent check() {
        List<FeedingSchedule> schedules = feeding_rep.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (FeedingSchedule i : schedules) {
            if (!i.isFed() && i.getFeedingTime().isBefore(now)) {
                i.markFed();
                feeding_rep.update(i);
                return new FeedingTimeEvent(i.getId());
            }
        }
        return null;
    }
}
