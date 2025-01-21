package my.pool.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.models.CardsResponse;
import my.pool.api.service.cards.CardsService;
import my.pool.api.service.cards.models.PoolEntityDTO;
import my.pool.api.service.cards.models.PoolEntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/cards")
public class CardsController {

    private final CardsService cardsService;

    @Operation(description = "Busca todas as cartas existentes utilizando uma api terceira.")
    @GetMapping
    public Flux<Card> getAllCards() {
        return cardsService.getAll();
    }

    @Operation(description = "Acessa uma pool de cartas especifica pelo seu id.")
    @GetMapping("/{poolId}")
    public Mono<PoolEntityResponse> getPool(@PathVariable String poolId) {
        return cardsService.findPoolById(poolId);
    }

    @Operation(description = "Busca cartas que contem o nome informado como parâmetro utilizando uma api terceira.")
    @GetMapping("/search")
    public Flux<Card> getCardsByName(@RequestParam String name) {
        return cardsService.findByName(name);
    }

    @Operation(description = "Cria uma nova pool.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> addPool(@Valid @RequestBody PoolEntityDTO poolEntityDTO) {
        return cardsService.addPool(poolEntityDTO);
    }

    @Operation(description = "Adiciona as cartas informadas no body a pool com id informado.")
    @PutMapping("/addCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> addCardsToPool(@RequestParam String poolId, @RequestBody List<String> list) {
        return cardsService.addCardToPool(poolId, list);
    }

    @Operation(description = "Remove as cartas informadas no body da pool com id informado do usuário com o id informado.")
    @PutMapping("/deleteCardsToPool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCardsToPool(@RequestParam String poolId, @RequestBody List<String> list) {
        return cardsService.deleteCardToPool(poolId, list);
    }

    @Operation(description = "Renomeia a pool com id informado.")
    @PutMapping("/renamePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> renamePool(@RequestParam String poolId, @RequestParam String rename) {
        return cardsService.renamePool(poolId, rename);
    }

    @Operation(description = "Remove a pool com id informado.")
    @DeleteMapping("/deletePool")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePool(@RequestParam String poolId) {
        return cardsService.deletePool(poolId);
    }
}
