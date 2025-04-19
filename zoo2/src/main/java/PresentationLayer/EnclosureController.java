package PresentationLayer;


import ApplicationLayer.Interfaces.EnclosureRepository;
import DomainLayer.Models.Enclosure;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enclosures")
public class EnclosureController {
    private final EnclosureRepository enclosure_rep;

    public EnclosureController(EnclosureRepository enclosureRepo) {
        this.enclosure_rep = enclosureRepo;
    }

    @GetMapping
    public List<Enclosure> getAllEnclosures() {
        return enclosure_rep.findAll();
    }

    @PostMapping
    public Enclosure addEnclosure(@RequestBody Enclosure enclosure) {
        enclosure_rep.add(enclosure);
        return enclosure;
    }

    @DeleteMapping("/{id}")
    public void deleteEnclosure(@PathVariable UUID id) {
        enclosure_rep.remove(id);
    }
}
