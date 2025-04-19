package DomainLayer.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedingSchedule {
    private final UUID id;
    private UUID animal_id;
    private LocalDateTime feeding_time;
    private String food_type;
    private boolean fed;

    public FeedingSchedule(UUID animal_id, LocalDateTime feeding_time, String food_type) {
        this.id = UUID.randomUUID();
        this.animal_id = animal_id;
        this.feeding_time = feeding_time;
        this.food_type = food_type;
        this.fed = false;
    }

    public UUID getId() {
        return id;
    }
    public UUID getAnimalId() {
        return animal_id;
    }
    public LocalDateTime getFeedingTime() {
        return feeding_time;
    }
    public void setFeedingTime(LocalDateTime feeding_time) {
        this.feeding_time = feeding_time;
    }
    public String getFoodType() {
        return food_type;
    }
    public void setFoodType(String food_type) {
        this.food_type = food_type;
    }
    public boolean isFed() {
        return fed;
    }
    public void markFed() {
        this.fed = true;
    }

    public void changeSchedule(LocalDateTime new_time) {
        this.feeding_time = new_time;
    }
}