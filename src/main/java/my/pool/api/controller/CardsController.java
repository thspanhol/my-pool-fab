package my.pool.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.service.CardsService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPool(@PathVariable String id, @Valid @RequestBody PoolDTO poolDTO) {
        cardsService.addPool(id, poolDTO);
    }

    @PutMapping("/addCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCardsToPool(@RequestParam String userId, @RequestParam String poolId, @RequestBody List<Card> list) {
        cardsService.addCardToPool(userId, poolId, list);
    }

    @PutMapping("/deleteCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCardsToPool(@RequestParam String userId, @RequestParam String poolId, @RequestBody List<Card> list) {
        cardsService.deleteCardToPool(userId, poolId, list);
    }

    @PutMapping("/renamePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void renamePool(@RequestParam String userId, @RequestParam String poolId, @RequestParam String rename) {
        cardsService.renamePool(userId, poolId, rename);
    }

    @DeleteMapping("/deletePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePool(@RequestParam String userId, @RequestParam String poolId) {
        cardsService.deletePool(userId, poolId);
    }

}
