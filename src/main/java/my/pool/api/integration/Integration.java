package my.pool.api.integration;

import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.models.CardsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Integration {

    private final WebClient webClient;

    public Flux<Card> fullApi() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(CardsResponse.class)
                .flatMapMany(response ->
                        Flux.fromIterable(response.getResults())
                                .concatWith(fetchAllPages(response.getNext()))
                );
    }

    public Flux<Card> api(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", name).build())
                .retrieve()
                .bodyToMono(CardsResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.getResults()));
    }

    public Flux<Card> getDataPool(List<String> poolCards) {
        return Flux.fromIterable(poolCards)
                .concatMap(card -> webClient.get()
                        .uri(uriBuilder -> uriBuilder.queryParam("q", card).build())
                        .retrieve()
                        .bodyToMono(CardsResponse.class)
                        .map(response -> response.getResults().getFirst())
                );
    }

    private Flux<Card> fetchAllPages(String nextUrl) {
        if (nextUrl == null) {
            return Flux.empty();
        }

        return webClient.get()
                .uri(nextUrl)
                .retrieve()
                .bodyToMono(CardsResponse.class)
                .flatMapMany(response ->
                        Flux.fromIterable(response.getResults())
                                .concatWith(fetchAllPages(response.getNext()))
                );
    }
}