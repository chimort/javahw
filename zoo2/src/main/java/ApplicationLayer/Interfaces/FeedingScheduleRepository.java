package ApplicationLayer.Interfaces;

import DomainLayer.Models.FeedingSchedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedingScheduleRepository {
    void add(FeedingSchedule schedule);
    void remove(UUID id);
    Optional<FeedingSchedule> findById(UUID id);
    List<FeedingSchedule> findAll();
    void update(FeedingSchedule schedule);
}
