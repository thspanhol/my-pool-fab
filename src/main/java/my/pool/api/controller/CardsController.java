package my.pool.api.controller;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.service.CardsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/cards")
public class CardsController {

    private final CardsService cardsService;

    @GetMapping
    public List<Card> getAllCards() {
        return cardsService.getAll();
    }

    @GetMapping("/{id}")
    public Pool getPool(@PathVariable String id) {
        return cardsService.findPoolById(id);
    }

    @GetMapping("/search")
    public List<Card> getCardsByName(@RequestParam String name) {
        return cardsService.findByName(name);
    }

    @PutMapping("/{id}")
    public void addPool(@PathVariable String id, @RequestBody PoolDTO poolDTO) {
        cardsService.addPool(id, poolDTO);
    }

}
