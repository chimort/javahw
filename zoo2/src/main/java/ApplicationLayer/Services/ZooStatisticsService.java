package ApplicationLayer.Services;

import ApplicationLayer.Interfaces.AnimalRepository;
import ApplicationLayer.Interfaces.EnclosureRepository;

public class ZooStatisticsService {
    private final AnimalRepository animal_rep;
    private final EnclosureRepository enc_rep;

    public ZooStatisticsService(AnimalRepository a_r, EnclosureRepository e_r) {
        this.animal_rep = a_r;
        this.enc_rep = e_r;
    }

    public int getAnimalCount() {
        return animal_rep.findAll().size();
    }

    public int getFreeEnclosureCount() {
        return (int) enc_rep.findAll().stream().filter(enclosure -> enclosure.getAnimalIds().isEmpty()).count();
    }
}
