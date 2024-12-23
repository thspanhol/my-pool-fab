package my.pool.api.integration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.models.CardsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

//@Service
//@RequiredArgsConstructor
//public class Integration {
//
//    private final RestTemplate restTemplate;
//    private final String baseUrl;
//
//    public List<Card> fullApi(){
//
//        List<Card> completeList = new ArrayList<>();
//        String url = baseUrl;
//
//        while (url != null) {
//            CardsResponse response = restTemplate.getForObject(url, CardsResponse.class);
//
//            if (response == null) break;
//
//            completeList.addAll(response.getResults());
//            url = response.getNext();
//        }
//        return completeList;
//    }
//
//    public List<Card> api(String name){
//
//        CardsResponse response = restTemplate.getForObject(baseUrl + name, CardsResponse.class);
//
//        assert response != null;
//
//        return new ArrayList<>(response.getResults());
//    }
//
//    public List<Card> getDataPool(List<String> poolCards){
//
//        List<Card> completeList = new ArrayList<>();
//
//        poolCards.forEach(card -> {
//            CardsResponse response = restTemplate.getForObject(baseUrl + card, CardsResponse.class);
//            assert response != null;
//            completeList.add(response.getResults().getFirst());
//        });
//
//        return completeList;
//    }
//}
@Service
@RequiredArgsConstructor
public class Integration {

    private final WebClient.Builder webClientBuilder;

    private final String baseUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

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