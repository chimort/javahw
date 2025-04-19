package ApplicationLayer;

import ApplicationLayer.Interfaces.AnimalRepository;
import ApplicationLayer.Interfaces.EnclosureRepository;
import ApplicationLayer.Interfaces.FeedingScheduleRepository;
import InfrastructureLayer.InMemoryAR;
import InfrastructureLayer.InMemoryER;
import InfrastructureLayer.InMemoryFSR;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public AnimalRepository animalRepository() {
        return new InMemoryAR();
    }

    @Bean
    public EnclosureRepository enclosureRepository() {
        return new InMemoryER();
    }

    @Bean
    public FeedingScheduleRepository feedingScheduleRepository() {
        return new InMemoryFSR();
    }
}
