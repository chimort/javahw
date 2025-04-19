package DomainLayer.Events;

import java.util.UUID;

public class FeedingTimeEvent {
    private final UUID feeding_schedule;

    public FeedingTimeEvent(UUID feeding_schedule) {
        this.feeding_schedule = feeding_schedule;
    }

    public UUID getFeedingSchedule() { return this.feeding_schedule; }
}
