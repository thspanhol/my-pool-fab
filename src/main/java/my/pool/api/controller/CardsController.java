package my.pool.api.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "Busca todas as cartas existentes utilizando uma api terceira.")
    @GetMapping
    public List<Card> getAllCards() {
        return cardsService.getAll();
    }

    @Operation(description = "Acessa uma pool de cartas especifica pelo seu id.")
    @GetMapping("/{id}")
    public Pool getPool(@PathVariable String id) {
        return cardsService.findPoolById(id);
    }

    @Operation(description = "Busca cartas que contem o nome informado como parâmetro utilizando uma api terceira.")
    @GetMapping("/search")
    public List<Card> getCardsByName(@RequestParam String name) {
        return cardsService.findByName(name);
    }

    @Operation(description = "Adiciona ao usuário do id informado pelo path, a pool informada no body da requisição.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPool(@PathVariable String id, @Valid @RequestBody PoolDTO poolDTO) {
        cardsService.addPool(id, poolDTO);
    }

    @Operation(description = "Adiciona as cartas informadas no body a pool com id informado do usuário com o id informado.")
    @PutMapping("/addCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCardsToPool(@RequestParam String userId, @RequestParam String poolId, @RequestBody List<Card> list) {
        cardsService.addCardToPool(userId, poolId, list);
    }

    @Operation(description = "Remove as cartas informadas no body da pool com id informado do usuário com o id informado.")
    @PutMapping("/deleteCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCardsToPool(@RequestParam String userId, @RequestParam String poolId, @RequestBody List<Card> list) {
        cardsService.deleteCardToPool(userId, poolId, list);
    }

    @Operation(description = "Renomeia a pool com id informado, do usuário com id informado.")
    @PutMapping("/renamePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void renamePool(@RequestParam String userId, @RequestParam String poolId, @RequestParam String rename) {
        cardsService.renamePool(userId, poolId, rename);
    }

    @Operation(description = "Remove a pool com id informado da lista do usuário com id informado.")
    @DeleteMapping("/deletePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePool(@RequestParam String userId, @RequestParam String poolId) {
        cardsService.deletePool(userId, poolId);
    }

}
