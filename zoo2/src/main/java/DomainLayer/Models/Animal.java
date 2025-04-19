package DomainLayer.Models;

import java.time.LocalDate;
import java.util.UUID;

public class Animal {
    private final UUID id;
    private String species;
    private String name;
    private LocalDate birthday;
    private Gender gender;
    private String favorite_food;
    private HealthStatus health_status;
    private UUID enclosure_id;

    public Animal(String species, String name, LocalDate birthday, Gender gender, String favorite_food,
                  HealthStatus health_status) {
        this.id = UUID.randomUUID();
        this.species = species;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.favorite_food = favorite_food;
        this.health_status = health_status;
    }

    public UUID getId() {
        return id;
    }
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthDate() {
        return birthday;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthday = birthDate;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getFavoriteFood() {
        return favorite_food;
    }
    public void setFavoriteFood(String favoriteFood) {
        this.favorite_food = favoriteFood;
    }
    public HealthStatus getHealthStatus() {
        return health_status;
    }
    public void setHealthStatus(HealthStatus healthStatus) {
        this.health_status = healthStatus;
    }
    public UUID getEnclosureId() {
        return enclosure_id;
    }
    public void setEnclosureId(UUID enclosureId) {
        this.enclosure_id = enclosureId;
    }

    public void feed() {
        System.out.println("Животное" + this.name + " покормлено!");
    }

    public void treat() {
        this.health_status = HealthStatus.HEALTH;
        System.out.println("Животное" + this.name + " вылечено!");
    }

    public void moveToEnclosure(UUID new_enclosure_id) {
        this.enclosure_id = new_enclosure_id;
    }
}
