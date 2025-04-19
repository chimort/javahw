package InfrastructureLayer;

import ApplicationLayer.Interfaces.FeedingScheduleRepository;
import DomainLayer.Models.FeedingSchedule;

import java.util.*;

public class InMemoryFSR implements FeedingScheduleRepository {
    private final Map<UUID, FeedingSchedule> store = new HashMap<>();

    @Override
    public void add(FeedingSchedule schedule) {
        store.put(schedule.getId(), schedule);
    }

    @Override
    public void remove(UUID scheduleId) {
        store.remove(scheduleId);
    }

    @Override
    public Optional<FeedingSchedule> findById(UUID scheduleId) {
        return Optional.ofNullable(store.get(scheduleId));
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(FeedingSchedule schedule) {
        store.put(schedule.getId(), schedule);
    }
}
